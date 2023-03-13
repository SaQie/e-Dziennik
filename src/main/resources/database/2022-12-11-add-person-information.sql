CREATE TABLE person_information
(
    id           SERIAL PRIMARY KEY,
    first_name   varchar(50)  NOT NULL,
    last_name    varchar(50)  NOT NULL,
    full_name    varchar(100) NOT NULL,
    phone_number varchar(10),
    pesel        varchar(11)  NOT NULL
);

ALTER TABLE student
    DROP COLUMN IF EXISTS first_name;
ALTER TABLE student
    DROP COLUMN IF EXISTS last_name;
ALTER TABLE student
    DROP COLUMN IF EXISTS pesel;
ALTER TABLE teacher
    DROP COLUMN IF EXISTS first_name;
ALTER TABLE teacher
    DROP COLUMN IF EXISTS last_name;
ALTER TABLE teacher
    DROP COLUMN IF EXISTS pesel;
ALTER TABLE teacher
    DROP COLUMN IF EXISTS phone_number;

ALTER TABLE student
    ADD person_information_id INT NOT NULL,
    ADD FOREIGN KEY (person_information_id) REFERENCES person_information (id);

ALTER TABLE teacher
    ADD person_information_id INT NOT NULL,
    ADD FOREIGN KEY (person_information_id) REFERENCES person_information (id);