-- ******************************************************
--
--                       MODEL
--
-- ******************************************************

CREATE TABLE ORDERS(
    ID                  UUID                NOT NULL,
    CART_ID             UUID                NOT NULL,
    CREATION_DATE       TIMESTAMP           NOT NULL,
    TOTAL_PRICE         DECIMAL(10, 3)      NOT NULL,
    COUNTRY_TAX         DOUBLE              NOT NULL,
    PAYMENT_METHOD      DOUBLE              NOT NULL,
    PRIMARY KEY (ID)
);

CREATE TABLE ORDER_RETURNS(
    ID                  UUID                 NOT NULL,
    ORDER_ID            UUID                 NOT NULL,
    CREATION_DATE       TIMESTAMP            NOT NULL,
    TOTAL_PRICE         DECIMAL(10, 3)       NOT NULL,
    PRIMARY KEY (ID),
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID)
);

CREATE TABLE ORDER_LINES (
     ORDER_ID            UUID                ,
     ORDER_RETURN_ID           UUID                ,
     PRODUCT             UUID                NOT NULL,
     QUANTITY            INT                 NOT NULL,
     LINE_WEIGHT         DOUBLE              NOT NULL,
     PRODUCT_PRICE       DECIMAL(10,3)       NOT NULL,
     LINE_PRICE          DECIMAL(10,3)       NOT NULL,
     FOREIGN KEY (ORDER_ID) REFERENCES ORDERS(ID),
     FOREIGN KEY (ORDER_RETURN_ID) REFERENCES ORDER_RETURNS(ID),
     CHECK (
         (ORDER_ID IS NOT NULL AND ORDER_RETURN_ID IS NULL)
             OR
         (ORDER_ID IS NULL AND ORDER_RETURN_ID IS NOT NULL)
         )
);

CREATE TABLE ORDER_OFFERS(
    ORDER_ID            UUID                NOT NULL,
    OFFER_ID            UUID                NOT NULL,
    FOREIGN KEY (ORDER_ID) REFERENCES ORDERS (ID), RETURNS (ID)
);