package io.github.alexshamrai.orderservice;

import io.github.alexshamrai.orderservice.client.BookClient;
import io.github.alexshamrai.orderservice.model.Order;
import io.github.alexshamrai.orderservice.repository.OrderRepository;
import io.github.alexshamrai.orderservice.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceApplicationTests {

    @MockBean
    private BookClient bookClient;  // Mocked BookClient

    @MockBean
    private OrderRepository orderRepository;

    @Autowired
    private OrderService orderService;

    @Test
    void contextLoads() {
    }

    @Test
    void testGetAllOrders() {
        Order order = new Order();
        order.setUserId(1L);
        order.setStatus("PENDING");

        when(orderRepository.findAll()).thenReturn(Collections.singletonList(order));

        List<Order> orders = orderService.getAllOrders();

        assertNotNull(orders);
        assertEquals(1, orders.size());
    }

    @Test
    void testGetOrder() {
        Order order = new Order();
        order.setUserId(1L);
        order.setStatus("PENDING");

        when(orderRepository.findById(anyLong())).thenReturn(Optional.of(order));

        Order result = orderService.getOrder(1L);

        assertNotNull(result);
        assertEquals("PENDING", result.getStatus());
    }
}