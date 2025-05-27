DELETE FROM order_lines;
DELETE FROM orders;

INSERT INTO orders (id, cart_id, creation_date, total_price, country_tax, payment_method)
VALUES
    ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222', NOW(), 100.00, 21.0, 1.0);

INSERT INTO order_lines (id, order_id, product, quantity, line_weight, product_price, line_price)
VALUES
    ('22222222-2222-2222-2222-222222222222','11111111-1111-1111-1111-111111111111','44444444-4444-4444-4444-444444444444', 2, 1.5, 20.00, 40.00);