DELETE FROM order_offers;
DELETE FROM order_lines;
DELETE FROM orders;

INSERT INTO orders (id, user_id, creation_date, total_price, country_tax, payment_method, order_return)
VALUES
    ('11111111-1111-1111-1111-111111111111', '22222222-2222-2222-2222-222222222222', NOW(), 100.00, 21.0, 1.0, false);

INSERT INTO order_lines (id, order_id, product, quantity, line_weight, product_price, line_price, returned_quantity)
VALUES
    ('22222222-2222-2222-2222-222222222222','11111111-1111-1111-1111-111111111111',50, 2, 1.5, 20.00, 40.00, 0);

INSERT INTO order_offers (order_id, offer_id)
VALUES
    ('11111111-1111-1111-1111-111111111111', 66666);