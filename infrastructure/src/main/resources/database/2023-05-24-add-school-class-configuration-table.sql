CREATE TABLE school_class_configuration
(
    id                   uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    max_students_size    INT  NOT NULL,
    auto_assign_subjects bool NOT NULL    DEFAULT TRUE
);

ALTER TABLE school_class
    ADD
        school_class_configuration_id uuid not null unique,
    ADD FOREIGN KEY (school_class_configuration_id) REFERENCES school_class_configuration (id);