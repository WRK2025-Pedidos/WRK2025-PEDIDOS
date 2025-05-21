INSERT INTO orders (id, cart_id, creation_date, total_price, country_tax, payment_method)
VALUES
    ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222', NOW(), 100.00, 21.0, 1.0);

INSERT INTO order_returns (id, order_id, creation_date, total_price)
VALUES
    ('33333333-3333-3333-3333-333333333333', '11111111-1111-1111-1111-111111111111', NOW(), 30.00);

INSERT INTO order_lines (order_id, order_return_id, product, quantity, line_weight, product_price, line_price)
VALUES
    ('11111111-1111-1111-1111-111111111111', NULL, '44444444-4444-4444-4444-444444444444', 2, 1.5, 20.00, 40.00);

INSERT INTO order_lines (order_id, order_return_id, product, quantity, line_weight, product_price, line_price)
VALUES
    (NULL, '33333333-3333-3333-3333-333333333333', '55555555-5555-5555-5555-555555555555', 1, 2.0, 30.00, 30.00);

INSERT INTO order_offers (order_id, offer_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', '66666666-6666-6666-6666-666666666666');
