INSERT INTO orders (user_id, order_date, status, total_amount)
VALUES (1, '2025-03-16T10:00:00', 'PENDING', 69.97),
       (2, '2025-03-16T11:00:00', 'COMPLETED', 59.98);

INSERT INTO order_items (order_id, book_id, quantity, price)
VALUES (1, 1, 2, 19.99), -- 2 книги из book-service с id = 1
       (1, 2, 1, 29.99), -- 1 книга из book-service с id = 2
       (2, 3, 1, 39.99); -- 1 книга из book-service с id = 3
