CREATE TABLE school_level
(
    id   SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);

INSERT INTO school_level(id, name)
VALUES (1, 'Szkoła podstawowa'),
       (2, 'Szkoła średnia'),
       (3, 'Szkoła wyższa');

CREATE TABLE school
(
    id              SERIAL PRIMARY KEY,
    name            varchar(255) NOT NULL UNIQUE,
    adress          varchar(255) NOT NULL,
    postal_code     varchar(9)   NOT NULL,
    city            varchar(255) NOT NULL,
    nip             varchar(10)  NOT NULL UNIQUE,
    regon           varchar(8)   NOT NULL UNIQUE,
    phone_number    varchar(9)   NOT NULL UNIQUE,
    school_level_id INT          NOT NULL REFERENCES school_level (id)
);

CREATE TABLE student
(
    id                  SERIAL PRIMARY KEY,
    username            varchar(255) NOT NULL UNIQUE,
    password            varchar(255) NOT NULL,
    first_name          varchar(255) NOT NULL,
    last_name           varchar(255) NOT NULL,
    adress              varchar(255) NOT NULL,
    postal_code         varchar(255) NOT NULL,
    city                varchar(255) NOT NULL,
    pesel               varchar(11)  NOT NULL UNIQUE,
    parent_first_name   varchar(255) NOT NULL,
    parent_last_name    varchar(255) NOT NULL,
    parent_phone_number varchar(9)   NOT NULL,
    school_id           INT          REFERENCES school (id)
);
CREATE TABLE teacher
(
    id           SERIAL PRIMARY KEY,
    username     varchar(255) NOT NULL UNIQUE,
    password     varchar(255) NOT NULL,
    first_name   varchar(255) NOT NULL,
    last_name    varchar(255) NOT NULL,
    adress       varchar(255) NOT NULL,
    postal_code  varchar(255) NOT NULL,
    city         varchar(255) NOT NULL,
    pesel        varchar(11)  NOT NULL UNIQUE,
    phone_number varchar(9)   NOT NULL,
    school_id    INT          REFERENCES school (id)
);

CREATE TABLE school_class
(
    id                     SERIAL PRIMARY KEY,
    class_name             varchar(255) NOT NULL UNIQUE,
    supervising_teacher_id INT          NOT NULL UNIQUE REFERENCES teacher (id)
);

ALTER TABLE student
    ADD school_class_id INT,
    ADD FOREIGN KEY (school_class_id) REFERENCES school_class (id);

ALTER TABLE teacher
    ADD supervising_school_class_id INT UNIQUE,
    ADD FOREIGN KEY (supervising_school_class_id) REFERENCES school_class (supervising_teacher_id);

CREATE TABLE subject
(
    id          SERIAL PRIMARY KEY,
    name        varchar(255) NOT NULL UNIQUE,
    description varchar(255),
    id_teacher  INT          NOT NULL REFERENCES teacher (id)
);

CREATE TABLE subject_class
(
    id              SERIAL PRIMARY KEY,
    id_subject      INT NOT NULL REFERENCES subject (id),
    id_school       INT NOT NULL REFERENCES school (id),
    id_school_class INT NOT NULL REFERENCES school_class (id)
);


CREATE TABLE rating
(
    id          SERIAL PRIMARY KEY,
    rating      INT NOT NULL,
    weight      INT NOT NULL,
    description varchar(255)
);

CREATE TABLE rating_subject_student
(
    id         SERIAL PRIMARY KEY,
    id_student INT NOT NULL REFERENCES student (id),
    id_rating  INT NOT NULL REFERENCES rating (id),
    id_subject INT NOT NULL REFERENCES subject (id)
);

CREATE TABLE teacher_roles
(
    teacher_id SERIAL PRIMARY KEY,
    role       varchar(255) NOT NULL
)