CREATE TABLE IF NOT EXISTS orders (
                                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                      user_id BIGINT NOT NULL,
                                      order_date TIMESTAMP NOT NULL,
                                      status VARCHAR(255) NOT NULL,
                                      total_amount DOUBLE NOT NULL
);

CREATE TABLE IF NOT EXISTS order_items (
                                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                           book_id BIGINT NOT NULL,
                                           quantity INT NOT NULL,
                                           price DOUBLE NOT NULL,
                                           order_id BIGINT NOT NULL,
                                           FOREIGN KEY (order_id) REFERENCES orders(id) ON DELETE CASCADE
);