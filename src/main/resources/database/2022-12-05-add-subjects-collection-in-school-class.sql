ALTER TABLE subject
    ADD school_class_id INT NOT NULL,
    ADD FOREIGN KEY (school_class_id) REFERENCES school_class (id);