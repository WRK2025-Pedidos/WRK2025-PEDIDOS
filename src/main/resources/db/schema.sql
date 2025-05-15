-- ******************************************************
--
--                       MODEL
--
-- ******************************************************

CREATE TABLE ORDERS(
    ID                  BIGINT              NOT NULL,
    CART_ID             BIG_INT             NOT NULL,
    CREATION_DATE       TIMESTAMP           NOT NULL,
    TOTAL_PRICE         DECIMAL(10, 3)      NOT NULL,
    COUNTRY_TAX         DOUBLE              NOT NULL,
    PAYMENT_METHOD      DOUBLE              NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE ORDER_LINES(
    ORDER_ID            BIGINT              NOT NULL,
    PRODUCT             BIGINT              NOT NULL,
    QUANTITY            INT                 NOT NULL,
    LINE_WEIGHT         DOUBLE              NOT NULL,
    PRODUCT_PRICE       DECIMAL(10, 3)      NOT NULL,
    LINE_PRICE          DECIMAL(10, 3)      NOT NULL,
    REFUND              BOOLEAN             NOT NULL
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID)
);

CREATE TABLE ORDER_OFFERS(
    ORDER_ID            BIGINT              NOT NULL,
    OFFER_ID            BIGINT              NOT NULL,
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID)
);