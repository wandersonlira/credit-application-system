CREATE TABLE tab_credit (
  id_credit BIGINT GENERATED BY DEFAULT AS IDENTITY NOT NULL,
   credit_code UUID NOT NULL,
   credit_value DECIMAL NOT NULL,
   day_first_installment date NOT NULL,
   number_of_installment INT NOT NULL,
   status SMALLINT,
   customer_id_customer BIGINT,
   CONSTRAINT pk_tab_credit PRIMARY KEY (id_credit)
);

ALTER TABLE tab_credit ADD CONSTRAINT uc_tab_credit_credit_code UNIQUE (credit_code);

ALTER TABLE tab_credit ADD CONSTRAINT FK_TAB_CREDIT_ON_CUSTOMER_ID_CUSTOMER FOREIGN KEY (customer_id_customer) REFERENCES tab_customer (id_customer);