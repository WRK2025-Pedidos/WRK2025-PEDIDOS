-- ******************************************************
--
--                       MODEL
--
-- ******************************************************

CREATE TABLE IF NOT EXISTS orders(
                                     id                  UUID                NOT NULL,
                                     user_id             UUID                NOT NULL,
                                     creation_date       TIMESTAMP           NOT NULL,
                                     total_price         DECIMAL(10,2)      NOT NULL,
                                     country_tax         DOUBLE PRECISION    NOT NULL,
                                     payment_method      DOUBLE PRECISION    NOT NULL,
                                     order_return        BOOLEAN            NOT NULL,
                                     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_lines (
                                       id                  UUID                NOT NULL,
                                       order_id            UUID                NOT NULL,
                                       product             BIGINT              NOT NULL,
                                       quantity            INT                 NOT NULL,
                                       line_weight         DOUBLE PRECISION    NOT NULL,
                                       line_price          DECIMAL(10,2)       NOT NULL,
                                       returned_quantity   INT                 NOT NULL DEFAULT 0,
                                       FOREIGN KEY (order_id) REFERENCES orders(id)
                                       );

CREATE TABLE IF NOT EXISTS order_offers (
                                        order_id UUID NOT NULL,
                                        offer_id BIGINT NOT NULL,
                                        PRIMARY KEY (order_id, offer_id),
                                        FOREIGN KEY (order_id) REFERENCES orders(id)
);

CREATE TABLE IF NOT EXISTS order_returned_product_quantities (
                                         order_id            UUID                NOT NULL,
                                         product_id          BIGINT              NOT NULL,
                                         returned_quantity   INT                 NOT NULL,
                                         PRIMARY KEY (order_id, product_id),
                                         FOREIGN KEY (order_id) REFERENCES orders(id)
);