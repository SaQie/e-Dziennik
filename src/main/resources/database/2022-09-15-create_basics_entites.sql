CREATE TABLE school_level
(
    id   SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE school
(
    id              SERIAL PRIMARY KEY,
    name            varchar(255) NOT NULL UNIQUE,
    adress          varchar(255) NOT NULL UNIQUE,
    postal_code     varchar(9)   NOT NULL,
    city            varchar(255) NOT NULL,
    nip             varchar(10)  NOT NULL UNIQUE,
    regon           varchar(8)   NOT NULL UNIQUE,
    phone_number    varchar(9)   NOT NULL UNIQUE,
    school_level_id int          NOT NULL REFERENCES school_level (id)
);

CREATE TABLE STUDENT
(
    id                  SERIAL PRIMARY KEY,
    username            varchar(255) NOT NULL UNIQUE,
    PASSWORD            varchar(255) NOT NULL,
    first_name          varchar(255) NOT NULL,
    last_name           varchar(255) NOT NULL,
    adress              varchar(255) NOT NULL,
    postal_code         varchar(255) NOT NULL,
    city                varchar(255) NOT NULL,
    pesel               varchar(11)  NOT NULL UNIQUE,
    parent_first_name   varchar(255) NOT NULL,
    parent_last_name    varchar(255) NOT NULL,
    parent_phone_number varchar(9)   NOT NULL,
    created_date        date         NOT NULL,
    updated_date        timestamp without time zone,
    last_login_date     timestamp without time zone,
    school_id           INT          NOT NULL REFERENCES school (id)
);

CREATE TABLE teacher
(
    id              SERIAL PRIMARY KEY,
    username        varchar(255) NOT NULL UNIQUE,
    password        varchar(255) NOT NULL,
    first_name      varchar(255) NOT NULL,
    last_name       varchar(255) NOT NULL,
    adress          varchar(255) NOT NULL,
    postal_code     varchar(255) NOT NULL,
    city            varchar(255) NOT NULL,
    pesel           varchar(11)  NOT NULL UNIQUE,
    phone_number    varchar(9)   NOT NULL,
    created_date    date         NOT NULL,
    updated_date    timestamp without time zone,
    last_login_date timestamp without time zone,
    school_id       INT REFERENCES school (id)
);

CREATE TABLE school_class
(
    id           SERIAL PRIMARY KEY,
    class_name   varchar(255) NOT NULL UNIQUE,
    created_date date         NOT NULL,
    teacher_id   INT UNIQUE REFERENCES teacher (id),
    school_id    INT REFERENCES school (id)
);

ALTER TABLE student
    ADD school_class_id INT NOT NULL,
    ADD FOREIGN KEY (school_class_id) REFERENCES school_class (id);

CREATE TABLE subject
(
    id          SERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL UNIQUE,
    description varchar(255),
    teacher_id  INT REFERENCES teacher (id)
);

CREATE TABLE grade
(
    id           SERIAL PRIMARY KEY,
    grade        INT  NOT NULL,
    weight       INT  NOT NULL,
    description  varchar(255),
    created_date date NOT NULL
);


CREATE TABLE ROLE
(
    id   SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE student_subject
(

    id         SERIAL PRIMARY KEY,
    student_id INT REFERENCES student (id),
    subject_id INT REFERENCES subject (id)
);

CREATE UNIQUE INDEX idx_student_subject ON student_subject (student_id, subject_id);


ALTER TABLE grade
    ADD student_subject_id INT REFERENCES student_subject (id);

ALTER TABLE grade
    ADD teacher_id INT REFERENCES teacher (id);

INSERT
INTO ROLE(id,
          name)
VALUES (1,
        'ROLE_ADMIN'),
       (2,
        'ROLE_TEACHER'),
       (3,
        'ROLE_MODERATOR');

ALTER TABLE teacher
    ADD role_id INT REFERENCES ROLE (id);

INSERT INTO school_level(id, name)
values (1, 'PODSTAWOWA');