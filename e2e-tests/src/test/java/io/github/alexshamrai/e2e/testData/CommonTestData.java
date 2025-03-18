package io.github.alexshamrai.e2e.testData;

import io.github.cdimascio.dotenv.Dotenv;

public class CommonTestData {
    private static final Dotenv dotenv = Dotenv.configure().load();
    public static final String BOOK_SERVICE_URL = dotenv.get("TEST_DOMAIN_BOOK_SERVICE") + dotenv.get("PREFIX_BOOK_SERVICE");
    public static final String ORDER_SERVICE_URL = dotenv.get("TEST_DOMAIN_ORDER_SERVICE") + dotenv.get("PREFIX_ORDER_SERVICE");

    public static final String DB_URL = dotenv.get("DB_URL");
    public static final String DB_USERNAME = dotenv.get("DB_USERNAME");
    public static final String DB_PASSWORD = dotenv.get("DB_PASSWORD");
    public static final String DB_DRIVER_CLASS = dotenv.get("DB_DRIVER_CLASS");
}