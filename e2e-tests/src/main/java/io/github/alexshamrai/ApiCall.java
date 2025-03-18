package io.github.alexshamrai;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

import static io.restassured.RestAssured.given;

public class ApiCall {
    private static final Logger logger = Logger.getLogger(ApiCall.class);

    public static Response get(String endpoint) {
        logRequest("GET", endpoint);
        Response response = given()
                .contentType(ContentType.JSON)
                .get(endpoint);
        logResponse(response);

        return response;
    }

    public static Response get(String endpoint, Object... pathParams) {
        String newPath = String.format(endpoint, pathParams);
        logRequest("GET", newPath);
        Response response = given()
                .contentType(ContentType.JSON)
                .get(newPath);
        logResponse(response);

        return response;
    }

    public static Response post(String endpoint, String params, Object request) {
        endpoint = String.format(endpoint, params);
        logRequest("POST", endpoint, request);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(endpoint);
        logResponse(response);

        return response;
    }

    public static Response post(String endpoint, Object request) {
        logRequest("POST", endpoint, request);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .post(endpoint);
        logResponse(response);

        return response;
    }

    public static Response put(String endpoint, Object request) {
        logRequest("PUT", endpoint, request);
        Response response = given()
                .contentType(ContentType.JSON)
                .body(request)
                .put(endpoint);
        logResponse(response);

        return response;
    }

    public static Response put(String endpoint) {
        logRequest("PUT", endpoint);
        Response response = given()
                .contentType(ContentType.JSON)
                .put(endpoint);
        logResponse(response);

        return response;
    }

    public static Response deleteOrder(String endpoint, Long orderId) {
        logRequest("DELETE by orderId", endpoint);
        Response response = given()
                .contentType(ContentType.JSON)
                .delete(endpoint + "/" + orderId);
        logResponse(response);

        return response;
    }

    public static Response delete(String endpoint) {
        Response response = given()
                .when()
                .delete(endpoint);
        logResponse(response);

        return response;
    }

    private static void logRequest(String method, String endpoint, Object body) {
        logger.info("Making " + method + " request to: " + endpoint);
        if (body != null) {
            logger.info("Request body: " + body.toString());
        }
    }

    private static void logRequest(String method, String endpoint) {
        logRequest(method, endpoint, null);
    }

    private static void logResponse(Response response) {
        //  TODO use for debugging
        //   logger.info("Response body: " + response.getBody().asString());
    }
}