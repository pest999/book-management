package io.github.alexshamrai.client;

import io.github.alexshamrai.ApiPaths;
import io.github.alexshamrai.dto.request.OrderRequest;
import io.restassured.response.Response;
import org.apache.log4j.Logger;

public class OrderClient extends BaseClient {
    private static final Logger LOGGER = Logger.getLogger(OrderClient.class);

    public OrderClient(String baseUrl) {
        super(baseUrl);
    }

    public Response createOrder(OrderRequest orderRequest) {
        LOGGER.info("Creating a new order: " + orderRequest);
        Response response = baseClient()
                .body(orderRequest)
                .post();
        logResponse("Create Order", response);
        return response;
    }

    public Response getOrder(Long id) {
        String url = String.format(ApiPaths.ORDER_BY_ID, id);
        LOGGER.info("Fetching order with ID: " + id);
        Response response = baseClient()
                .get(url);
        logResponse("Get Order", response);
        return response;
    }

    public Response getUserOrders(Long userId) {
        String url = String.format(ApiPaths.USER_ORDERS, userId);
        LOGGER.info("Fetching orders for user ID: " + userId);
        Response response = baseClient()
                .get(url);
        logResponse("Get User Orders", response);
        return response;
    }

    public Response updateOrderStatus(Long id, String status) {
        String url = String.format(ApiPaths.ORDER_STATUS, id);
        LOGGER.info("Updating order status. Order ID: " + id + ", New Status: " + status);
        Response response = baseClient()
                .queryParam("status", status)
                .put(url);
        logResponse("Update Order Status", response);
        return response;
    }

    public Response deleteOrder(Long id) {
        String url = String.format(ApiPaths.ORDER_BY_ID, id);
        LOGGER.info("Deleting order with ID: " + id);
        Response response = baseClient()
                .delete(url);
        logResponse("Delete Order", response);
        return response;
    }

    public Response deleteAllOrders() {
        LOGGER.info("Deleting all orders");
        Response response = baseClient()
                .delete();
        logResponse("Delete All Orders", response);
        return response;
    }

    private void logResponse(String action, Response response) {
        LOGGER.info(action + " - Status: " + response.getStatusCode() + ", Response Body: " + response.getBody().asString());
    }
}