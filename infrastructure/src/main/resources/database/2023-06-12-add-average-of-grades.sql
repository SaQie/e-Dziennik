ALTER TABLE student_subject
    ADD
        average DECIMAL;


CREATE TABLE school_configuration
(
    id           uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    average_type varchar(50) NOT NULL
);

ALTER TABLE school
    ADD
        school_configuration_id uuid not null unique,
    ADD FOREIGN KEY (school_configuration_id) REFERENCES school_configuration (id);