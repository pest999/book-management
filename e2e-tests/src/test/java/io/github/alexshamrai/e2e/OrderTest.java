package io.github.alexshamrai.e2e;

import io.github.alexshamrai.ApiCall;
import io.github.alexshamrai.ApiPaths;
import io.github.alexshamrai.dto.request.OrderRequest;
import io.github.alexshamrai.dto.response.OrderDto;
import io.github.alexshamrai.e2e.testData.RequestFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import java.util.List;

import static io.github.alexshamrai.e2e.testData.CommonTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

public class OrderTest extends BaseTest{
    @Test
    @DisplayName("Create a new order and verify response data")
    public void testCreateOrder() {
        Long userId = 3L;
        Long bookId = 2L;
        Integer quantity = 1;

        OrderRequest orderRequest = RequestFactory.createOrderRequest(userId, bookId, quantity);
        Response response = ApiCall.post(ORDER_SERVICE_URL, orderRequest);

        assertThat(response.statusCode()).isEqualTo(200);

        var responseUserId = response.jsonPath().getLong("userId");
        var responseBookId = response.jsonPath().getList("orderItems.bookId").get(0);
        var responseQuantity = response.jsonPath().getList("orderItems.quantity").get(0);

        assertThat(responseUserId).isEqualTo(userId);
        assertThat(responseBookId).isEqualTo(bookId.intValue());
        assertThat(responseQuantity).isEqualTo(quantity);
    }

    @Test
    @DisplayName("Retrieve all user orders")
    public void testGetOrders() {

        Response response = ApiCall.get(ORDER_SERVICE_URL);

        assertThat(response.statusCode()).isEqualTo(200);

        var orders = response.jsonPath().getList("", OrderDto.class);

        assertThat(orders).isNotEmpty();
    }

    @Test
    @DisplayName("Retrieve all user orders")
    public void testGetOrdersByUser() {
        Long userId = 2L;
        String orderUrl = String.format(ORDER_SERVICE_URL+ ApiPaths.USER_ORDERS,userId) ;
        Response response = ApiCall.get(orderUrl);

        assertThat(response.statusCode()).isEqualTo(200);

        Long userIdFromResponse = response.jsonPath().getLong("[0].userId");
        List<OrderDto> orders = response.as(List.class);

        assertThat(orders).isNotEmpty();
        assertThat(userIdFromResponse).isEqualTo(userId);
    }

    @Test
    @DisplayName("Retrieve order by ID")
    public void testGetOrderById() {
        Long orderId = 1L;
        String orderUrl = String.format(ORDER_SERVICE_URL+ApiPaths.ORDER_BY_ID, orderId);
        Response response = ApiCall.get(orderUrl);

        assertThat(response.statusCode()).isEqualTo(200);

        OrderDto order = response.as(OrderDto.class);

        assertThat(order.getId()).isEqualTo(orderId);
     }

    @Test
    @DisplayName("Update order details")
    public void testUpdateOrder() {
        Long orderId = 1L;
        String newStatus = "SHIPPED";

        String orderUrl = String.format(ORDER_SERVICE_URL + ApiPaths.ORDER_STATUS, orderId, newStatus);
        Response response = ApiCall.put(orderUrl);

        assertThat(response.statusCode()).isEqualTo(200);

        OrderDto updatedOrder = response.as(OrderDto.class);

        assertThat(updatedOrder.getStatus()).isEqualTo(newStatus);
    }

    @Test
    @DisplayName("Delete an order")
    public void testDeleteOrder() {
        Long orderId = 1L;
        String orderUrl = String.format(ORDER_SERVICE_URL+ApiPaths.ORDER_BY_ID, orderId);
        Response response = ApiCall.delete(orderUrl);

        assertThat(response.statusCode()).isEqualTo(204);
    }

    @ParameterizedTest
    @DisplayName("Verify order retrieval with invalid IDs")
    @ValueSource(longs = {0L, -1L, 999999L})
    public void testGetOrderByIdNegative(Long invalidOrderId) {
        String orderUrl = String.format(ORDER_SERVICE_URL+ApiPaths.ORDER_BY_ID, invalidOrderId);
        Response response = ApiCall.get(orderUrl);

        assertThat(response.statusCode()).isEqualTo(500);
    }

    @Test
    @DisplayName("Retrieve order by ID - not found")
    public void testGetNonExistingOrderById() {
        Long orderId = 999999L;
        String orderUrl = String.format(ORDER_SERVICE_URL + ApiPaths.ORDER_BY_ID, orderId);
        Response response = ApiCall.get(orderUrl);

        assertThat(response.statusCode()).isEqualTo(404);
    }

    @Test
    @DisplayName("Delete all orders")
    public void testDeleteAllOrders() {
        Response response = ApiCall.delete(ORDER_SERVICE_URL);
        assertThat(response.statusCode()).isEqualTo(204);

        Response getResponse = ApiCall.get(ORDER_SERVICE_URL);
        List<OrderDto> orders = getResponse.jsonPath().getList("", OrderDto.class);
        assertThat(orders).isEmpty();
    }

    @Test
    @DisplayName("Check if book stock is updated after order is placed")
    public void testBookStockUpdateAfterOrder() {
        Long userId = 8L;
        Long bookId = 2L;
        Integer initialQuantity = 5;
        Integer quantityToOrder = 1;

        OrderRequest orderRequest = RequestFactory.createOrderRequest(userId, bookId, quantityToOrder);
        Response createResponse = ApiCall.post(ORDER_SERVICE_URL, orderRequest);

        assertThat(createResponse.statusCode()).isEqualTo(200);

        String bookUrl = String.format(BOOK_SERVICE_URL + ApiPaths.BOOK_BY_ID, bookId);
        Response bookResponse = ApiCall.get(bookUrl);
        int updatedStock = bookResponse.jsonPath().getInt("stock");

        assertThat(updatedStock).isEqualTo(initialQuantity - quantityToOrder);
    }
}