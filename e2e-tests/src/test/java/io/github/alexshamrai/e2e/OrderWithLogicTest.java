package io.github.alexshamrai.e2e;

import io.github.alexshamrai.ApiCall;
import io.github.alexshamrai.dto.request.OrderRequest;
import io.github.alexshamrai.dto.response.BookDto;
import io.github.alexshamrai.dto.response.OrderDto;
import io.github.alexshamrai.e2e.testData.RequestFactory;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.github.alexshamrai.e2e.testData.CommonTestData.*;
import static org.assertj.core.api.Assertions.assertThat;

class OrderWithLogicTest extends BaseTest {
    private BookDto testBook;

    @BeforeEach
    void cleanUp() {
        ApiCall.delete(BOOK_SERVICE_URL);
        ApiCall.delete(ORDER_SERVICE_URL);

        BookDto bookRequest = BookDto.builder()
                .title("Test Book")
                .author("Test Author")
                .price(29.99)
                .stockQuantity(10)
                .build();

        Response createBookResponse = ApiCall.post(BOOK_SERVICE_URL, bookRequest);
        createBookResponse.then().statusCode(200);
        testBook = createBookResponse.as(BookDto.class);
    }

    @Test
    void shouldCreateOrderSuccessfully() {
        OrderRequest orderRequest = RequestFactory.createOrderRequest(1L, testBook.getId(), 1);

        Response createOrderResponse = ApiCall.post(ORDER_SERVICE_URL, orderRequest);
        createOrderResponse.then().statusCode(200);

        OrderDto createdOrder = createOrderResponse.as(OrderDto.class);
        assertThat(createdOrder.getId()).isNotNull();
        assertThat(createdOrder.getOrderItems()).hasSize(1);
    }

    @Test
    void shouldUpdateBookStockAfterOrderCreation() {
        OrderRequest orderRequest = RequestFactory.createOrderRequest(4L, testBook.getId(), 1);

        Response createOrderResponse = ApiCall.post(ORDER_SERVICE_URL, orderRequest);
        createOrderResponse.then().statusCode(200);

        Response getBookResponse = ApiCall.get(BOOK_SERVICE_URL + testBook.getId());
        getBookResponse.then().statusCode(200);
        BookDto updatedBook = getBookResponse.as(BookDto.class);

        assertThat(updatedBook.getStockQuantity()).isEqualTo(9);
    }

    @Test
    void shouldReturnErrorWhenBookNotAvailable() {
        OrderRequest orderRequest = RequestFactory.createOrderRequest(1L, testBook.getId(), 20);

        Response createOrderResponse = ApiCall.post(ORDER_SERVICE_URL + "/orders", orderRequest);
        createOrderResponse.then().statusCode(400);

        String errorMessage = createOrderResponse.jsonPath().getString("message");
        assertThat(errorMessage).isEqualTo("Not enough stock available");
    }
}
