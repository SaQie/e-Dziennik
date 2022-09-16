CREATE TABLE school_level
(
    school_level_id SERIAL PRIMARY KEY,
    name            varchar(255) NOT NULL UNIQUE
);

INSERT INTO school_level(school_level_id, name)
VALUES (1, 'Szkoła podstawowa'),
       (2, 'Szkoła średnia'),
       (3, 'Szkoła wyższa');

CREATE TABLE school
(
    school_id       SERIAL PRIMARY KEY,
    name            varchar(255) NOT NULL UNIQUE,
    adress          varchar(255) NOT NULL,
    postal_code     varchar(9)   NOT NULL,
    city            varchar(255) NOT NULL,
    nip             varchar(10)  NOT NULL UNIQUE,
    regon           varchar(8)   NOT NULL UNIQUE,
    phone_number    varchar(9)   NOT NULL UNIQUE,
    school_level_id INT          NOT NULL REFERENCES school_level (school_level_id)
);

CREATE TABLE student
(
    student_id          SERIAL PRIMARY KEY,
    first_name          varchar(255) NOT NULL,
    last_name           varchar(255) NOT NULL,
    adress              varchar(255) NOT NULL,
    postal_code         varchar(255) NOT NULL,
    city                varchar(255) NOT NULL,
    pesel               varchar(11)  NOT NULL UNIQUE,
    parent_first_name   varchar(255) NOT NULL,
    parent_last_name    varchar(255) NOT NULL,
    parent_phone_number varchar(9)   NOT NULL,
    school_id           INT          NOT NULL REFERENCES school (school_id)
);
CREATE TABLE teacher
(
    teacher_id   SERIAL PRIMARY KEY,
    first_name   varchar(255) NOT NULL,
    last_name    varchar(255) NOT NULL,
    adress       varchar(255) NOT NULL,
    postal_code  varchar(255) NOT NULL,
    city         varchar(255) NOT NULL,
    pesel        varchar(11)  NOT NULL UNIQUE,
    phone_number varchar(9)   NOT NULL,
    school_id    INT          NOT NULL REFERENCES school (school_id)
);

CREATE TABLE school_class
(
    school_class_id        SERIAL PRIMARY KEY,
    class_name             varchar(255) NOT NULL UNIQUE,
    supervising_teacher_id INT          NOT NULL UNIQUE REFERENCES teacher (teacher_id)
);

ALTER TABLE student
    ADD school_class_id INT,
    ADD FOREIGN KEY (school_class_id) REFERENCES school_class (school_class_id);

ALTER TABLE teacher
    ADD supervising_school_class_id INT UNIQUE,
    ADD FOREIGN KEY (supervising_school_class_id) REFERENCES school_class(supervising_teacher_id);

