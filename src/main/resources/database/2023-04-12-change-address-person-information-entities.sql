DROP TABLE IF EXISTS person_information CASCADE ;

ALTER TABLE student
    DROP COLUMN IF EXISTS person_information_id;


ALTER TABLE STUDENT
    ADD
    first_name   varchar(50)  NOT NULL;

ALTER TABLE STUDENT
    ADD
    last_name    varchar(50)  NOT NULL;

ALTER TABLE STUDENT
    ADD
    full_name    varchar(100) NOT NULL;

ALTER TABLE STUDENT
    ADD
    phone_number varchar(10);

ALTER TABLE STUDENT
    ADD
    pesel        varchar(11)  NOT NULL;