ALTER TABLE teacher
    ADD email varchar(255) NOT NULL UNIQUE;

ALTER TABLE student
    ADD email varchar(255) NOT NULL UNIQUE;