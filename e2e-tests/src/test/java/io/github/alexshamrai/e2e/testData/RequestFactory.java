package io.github.alexshamrai.e2e.testData;

import io.github.alexshamrai.dto.request.OrderItemRequest;
import io.github.alexshamrai.dto.request.OrderRequest;

import java.util.Arrays;

public class RequestFactory {
    public static OrderRequest createOrderRequest(Long userId, Long bookId, Integer quantity) {
        OrderItemRequest item1 = new OrderItemRequest(bookId, quantity);
        return new OrderRequest(userId, Arrays.asList(item1));
    }
}
