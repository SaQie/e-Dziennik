ALTER TABLE admin
    ADD created_date    date NOT NULL,
    ADD updated_date    timestamp without time zone,
    ADD last_login_date timestamp without time zone;