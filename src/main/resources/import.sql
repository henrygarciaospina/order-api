INSERT INTO USERS(USERNAME, PASSWORD) VALUES('henry', '1234');

INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 1', 100);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 2', 200);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 3', 300);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 4', 400);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 5', 500);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 6', 600);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 7', 700);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 8', 800);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 9', 900);
INSERT INTO PRODUCTS(NAME, PRICE) VALUES('Product 10', 1000);

INSERT INTO ORDERS(REG_DATE, TOTAL) VALUES (now(), 1500);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (1,1,100,1,100);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (1,2,200,1,200);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (1,3,300,1,300);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (1,4,400,1,400);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (1,5,500,1,500);

INSERT INTO ORDERS(REG_DATE, TOTAL) VALUES (now(), 4000);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (2,6,600,1,600);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (2,7,700,1,700);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (2,8,800,1,800);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (2,9,900,1,900);
INSERT INTO ORDER_LINES(FK_ORDER, FK_PRODUCT, PRICE, QUANTITY, TOTAL) VALUES (2,10,1000,1,1000);
