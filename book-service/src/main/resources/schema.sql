CREATE TABLE IF NOT EXISTS book
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    title          VARCHAR(255) NOT NULL,
    author         VARCHAR(255) NOT NULL,
    price          DOUBLE       NOT NULL,
    stock_quantity INT          NOT NULL
);
