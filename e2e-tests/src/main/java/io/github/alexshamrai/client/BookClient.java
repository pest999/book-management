package io.github.alexshamrai.client;

import io.github.alexshamrai.ApiPaths;
import io.github.alexshamrai.dto.response.BookDto;
import io.restassured.response.Response;

public class BookClient extends BaseClient {

    public BookClient(String baseUrl) {
        super(baseUrl);
    }

    public Response createBook(BookDto request) {
        return baseClient()
            .body(request)
            .post();
    }

    public Response getBook(Long id) {
        String url = String.format(ApiPaths.BOOK_BY_ID, id);
        return baseClient()
            .get(url);
    }

    public Response getAllBooks() {
        return baseClient()
            .get();
    }

    public Response updateStock(Long id, Integer quantity) {
        String url = String.format(ApiPaths.BOOK_STOCK, id);
        return baseClient()
            .queryParam("quantity", quantity)
            .put(url);
    }

    public Response deleteBook(Long id) {
        String url = String.format(ApiPaths.BOOK_BY_ID, id);
        return baseClient()
            .delete(url);
    }

    public Response deleteAllBooks() {
        return baseClient()
            .delete();
    }
}