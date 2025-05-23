-- ******************************************************
--
--                       MODEL
--
-- ******************************************************

CREATE TABLE IF NOT EXISTS orders(
                                     id                  UUID                NOT NULL,
                                     cart_id             UUID                NOT NULL,
                                     creation_date       TIMESTAMP           NOT NULL,
                                     total_price         DECIMAL(10, 3)      NOT NULL,
                                     country_tax         DOUBLE PRECISION    NOT NULL,
                                     payment_method      DOUBLE PRECISION    NOT NULL,
                                     PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS order_returns(
                                            id                  UUID                 NOT NULL,
                                            order_id            UUID                 NOT NULL,
                                            creation_date       TIMESTAMP            NOT NULL,
                                            total_price         DECIMAL(10, 3)       NOT NULL,
                                            PRIMARY KEY (id),
                                            FOREIGN KEY (order_id) REFERENCES orders (id)
);

CREATE TABLE IF NOT EXISTS order_lines (
                                           id                  UUID                ,
                                           order_id            UUID                ,
                                           order_return_id     UUID                ,
                                           product             UUID                NOT NULL,
                                           quantity            INT                 NOT NULL,
                                           line_weight         DOUBLE PRECISION    NOT NULL,
                                           product_price       DECIMAL(10,3)       NOT NULL,
                                           line_price          DECIMAL(10,3)       NOT NULL,
                                           PRIMARY KEY (id),
                                           FOREIGN KEY (order_id) REFERENCES orders(id),
                                           FOREIGN KEY (order_return_id) REFERENCES order_returns(id),
                                           CONSTRAINT chk_order_or_return CHECK (
                                               (order_id IS NULL AND order_return_id IS NOT NULL) OR
                                               (order_id IS NOT NULL AND order_return_id IS NULL)
                                               ));

CREATE TABLE IF NOT EXISTS order_offers (
                                            order_id UUID NOT NULL,
                                            offer_id UUID NOT NULL,
                                            PRIMARY KEY (order_id, offer_id),
                                            FOREIGN KEY (order_id) REFERENCES orders(id)
);