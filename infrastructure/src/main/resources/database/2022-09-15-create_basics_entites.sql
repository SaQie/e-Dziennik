CREATE TABLE school_level
(
    id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE school
(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name            varchar(255) NOT NULL UNIQUE,
    adress          varchar(255) NOT NULL UNIQUE,
    postal_code     varchar(9)   NOT NULL,
    city            varchar(255) NOT NULL,
    nip             varchar(10)  NOT NULL UNIQUE,
    regon           varchar(9)   NOT NULL UNIQUE,
    phone_number    varchar(9)   NOT NULL UNIQUE,
    school_level_id uuid          NOT NULL REFERENCES school_level (id)
);

CREATE TABLE STUDENT
(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    username            varchar(255) NOT NULL UNIQUE,
    PASSWORD            varchar(255) NOT NULL,
    first_name          varchar(255) NOT NULL,
    last_name           varchar(255) NOT NULL,
    address             varchar(255) NOT NULL,
    postal_code         varchar(255) NOT NULL,
    city                varchar(255) NOT NULL,
    pesel               varchar(11)  NOT NULL UNIQUE,
    parent_first_name   varchar(255) NOT NULL,
    parent_last_name    varchar(255) NOT NULL,
    parent_phone_number varchar(9)   NOT NULL,
    created_date        date         NOT NULL,
    updated_date        timestamp without time zone,
    last_login_date     timestamp without time zone,
    school_id           uuid          NOT NULL REFERENCES school (id)
);

CREATE TABLE teacher
(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    username        varchar(255) NOT NULL UNIQUE,
    password        varchar(255) NOT NULL,
    first_name      varchar(255) NOT NULL,
    last_name       varchar(255) NOT NULL,
    address         varchar(255) NOT NULL,
    postal_code     varchar(255) NOT NULL,
    city            varchar(255) NOT NULL,
    pesel           varchar(11)  NOT NULL UNIQUE,
    phone_number    varchar(9)   NOT NULL,
    created_date    date         NOT NULL,
    updated_date    timestamp without time zone,
    last_login_date timestamp without time zone,
    school_id       uuid NOT NULL REFERENCES school (id)
);

CREATE TABLE school_class
(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    class_name   varchar(255) NOT NULL,
    created_date date         NOT NULL,
    teacher_id   uuid UNIQUE REFERENCES teacher (id),
    school_id    uuid          NOT NULL REFERENCES school (id)
);

ALTER TABLE student
    ADD school_class_id uuid NOT NULL,
    ADD FOREIGN KEY (school_class_id) REFERENCES school_class (id);

CREATE TABLE subject
(
    id          uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name        varchar(255) NOT NULL,
    description varchar(255),
    teacher_id  uuid REFERENCES teacher (id)
);

CREATE TABLE grade
(
    id           uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    grade        INT  NOT NULL,
    weight       INT  NOT NULL,
    description  varchar(255),
    created_date date NOT NULL
);


CREATE TABLE ROLE
(
    id   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(255) NOT NULL UNIQUE
);

CREATE TABLE student_subject
(

    id         uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    student_id uuid REFERENCES student (id),
    subject_id uuid REFERENCES subject (id)
);

CREATE UNIQUE INDEX idx_student_subject ON student_subject (student_id, subject_id);


ALTER TABLE grade
    ADD student_subject_id uuid REFERENCES student_subject (id);

ALTER TABLE grade
    ADD teacher_id uuid REFERENCES teacher (id);

INSERT
INTO ROLE(id,
          name)
VALUES (gen_random_uuid(),
        'ROLE_ADMIN'),
       (gen_random_uuid(),
        'ROLE_TEACHER'),
       (gen_random_uuid(),
        'ROLE_STUDENT');

ALTER TABLE teacher
    ADD role_id uuid REFERENCES ROLE (id);

INSERT INTO school_level(id, name)
values (gen_random_uuid(), 'PRIMARY SCHOOL'),
       (gen_random_uuid(), 'HIGH SCHOOL'),
       (gen_random_uuid(), 'UNIVERSITY');