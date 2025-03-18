package io.github.alexshamrai.e2e.utils;

import io.github.alexshamrai.e2e.testData.CommonTestData;
import org.apache.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ConnectionToDBH2 {
    private static final Logger logger = Logger.getLogger(ConnectionToDBH2.class);

    private static final String URL = CommonTestData.DB_URL;
    private static final String USERNAME = CommonTestData.DB_USERNAME;
    private static final String PASSWORD = CommonTestData.DB_PASSWORD;
    private static final String DRIVER_CLASS = CommonTestData.DB_DRIVER_CLASS;

    public static String getOrderById(Long orderId) {
        String query = "SELECT * FROM orders WHERE id = " + orderId;
        return executeQueryForSingleResult(query);
    }

    public static List<String> getAllOrders() {
        String query = "SELECT * FROM orders";
        return executeQuery(query);
    }

    public static List<String> getOrdersByUserId(Long userId) {
        String query = "SELECT * FROM orders WHERE user_id = " + userId;
        return executeQuery(query);
    }

    private static Connection getConnection() throws SQLException {
        try {
            logger.info("Establishing connection to the database...");
            Class.forName(DRIVER_CLASS);
            Connection connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            logger.info("Successfully connected to the database.");
            return connection;
        } catch (ClassNotFoundException e) {
            logger.error("Driver not found: " + e.getMessage());
            throw new SQLException("Driver not found", e.getMessage());
        } catch (SQLException e) {
            logger.error("Connection failed: " + e.getMessage());
            throw e;
        }
    }

    private static boolean isConnectionValid(Connection connection) {
        try {
            boolean valid = connection != null && connection.isValid(2);
            if (valid) {
                logger.info("Connection is valid.");
            } else {
                logger.warn("Connection is not valid.");
            }
            return valid;
        } catch (SQLException e) {
            logger.error("Error checking connection validity: " + e.getMessage());
            return false;
        }
    }

    public static String executeQueryForSingleResult(String query) {
        logger.info("Executing query: " + query);
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            if (isConnectionValid(connection)) {
                ResultSet rs = stmt.executeQuery(query);
                if (rs.next()) {
                    logger.info("Query returned result: " + rs.getString(1));
                    return rs.getString(1);
                } else {
                    logger.warn("No results found for query: " + query);
                }
            }
        } catch (SQLException e) {
            logger.error("Error executing query: " + query, e);
        }
        return null;
    }

    private static List<String> executeQuery(String query) {
        List<String> results = new ArrayList<>();
        logger.info("Executing query: " + query);
        try (Connection connection = getConnection(); Statement stmt = connection.createStatement()) {
            if (isConnectionValid(connection)) {
                ResultSet rs = stmt.executeQuery(query);
                while (rs.next()) {
                    results.add(rs.getString(1));
                }
                logger.info("Query executed successfully, " + results.size() + " results found.");
            }
        } catch (SQLException e) {
            logger.error("Error executing query: " + query, e);
        }
        return results;
    }
}