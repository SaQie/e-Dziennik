ALTER TABLE student
    ADD COLUMN version int8 default 0;
ALTER TABLE teacher
    ADD COLUMN version int8 default 0;
ALTER TABLE admin
    ADD COLUMN version int8 default 0;
ALTER TABLE parent
    ADD COLUMN version int8 default 0;
ALTER TABLE student_subject
    ADD COLUMN version int8 default 0;
ALTER TABLE subject
    ADD COLUMN version int8 default 0;
ALTER TABLE school_class
    ADD COLUMN version int8 default 0;
ALTER TABLE school
    ADD COLUMN version int8 default 0;
ALTER TABLE school_level
    ADD COLUMN version int8 default 0;
ALTER TABLE school_class_configuration
    ADD COLUMN version int8 default 0;
ALTER TABLE grade
    ADD COLUMN version int8 default 0;
ALTER TABLE address
    ADD COLUMN version int8 default 0;
ALTER TABLE role
    ADD COLUMN version int8 default 0;
ALTER TABLE users
    ADD COLUMN version int8 default 0;


UPDATE school_level
    SET version = 0;

UPDATE role
    SET version = 0;