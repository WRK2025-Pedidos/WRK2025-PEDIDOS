-- ******************************************************
--
--                       MODEL
--
-- ******************************************************

CREATE TABLE IF NOT EXISTS ORDERS(
    ID                  UUID                NOT NULL,
    CART_ID             UUID                NOT NULL,
    CREATION_DATE       TIMESTAMP           NOT NULL,
    TOTAL_PRICE         DECIMAL(10, 3)      NOT NULL,
    COUNTRY_TAX         DOUBLE              NOT NULL,
    PAYMENT_METHOD      DOUBLE              NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE IF NOT EXISTS ORDER_RETURNS(
    ID                  UUID                 NOT NULL,
    ORDER_ID            UUID                 NOT NULL,
    CREATION_DATE       TIMESTAMP            NOT NULL,
    TOTAL_PRICE         DECIMAL(10, 3)       NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID)
);

CREATE TABLE IF NOT EXISTS ORDER_LINES (
     ORDER_ID            UUID                ,
     ORDER_RETURN_ID     UUID                ,
     PRODUCT             UUID                NOT NULL,
     QUANTITY            INT                 NOT NULL,
     LINE_WEIGHT         DOUBLE              NOT NULL,
     PRODUCT_PRICE       DECIMAL(10,3)       NOT NULL,
     LINE_PRICE          DECIMAL(10,3)       NOT NULL,
     FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID),
     FOREIGN KEY (ORDER_RETURN_ID) REFERENCES ORDER_RETURNS(ID),
     CHECK (
            (ORDER_ID IS NULL AND ORDER_RETURN_ID IS NOT NULL)
            OR
            (ORDER_ID IS NOT NULL AND ORDER_RETURN_ID IS NULL)
         )
);

CREATE TABLE IF NOT EXISTS ORDER_OFFERS (
    ORDER_ID UUID NOT NULL,
    OFFER_ID UUID NOT NULL,
    PRIMARY KEY (order_id, offer_id),
    FOREIGN KEY (order_id) REFERENCES orders(id)
);