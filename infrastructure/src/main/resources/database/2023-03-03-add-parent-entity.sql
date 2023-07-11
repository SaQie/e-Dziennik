insert into ROLE(id, name)
VALUES ('5c1b848e-1f43-11ee-be56-0242ac120002', 'ROLE_PARENT');

CREATE TABLE PARENT
(
    id uuid PRIMARY KEY DEFAULT gen_random_uuid()
);


ALTER TABLE PARENT
    ADD person_information_id uuid not null unique,
    ADD FOREIGN KEY (person_information_id) references person_information (id);

ALTER TABLE PARENT
    ADD address_id uuid not null unique,
    ADD FOREIGN KEY (address_id) references address (id);

ALTER TABLE PARENT
    ADD user_id uuid not null unique,
    ADD FOREIGN KEY (user_id) references users (id);

ALTER TABLE PARENT
    ADD student_id uuid unique,
    ADD FOREIGN KEY (student_id) references student (id);

ALTER TABLE STUDENT
    DROP COLUMN IF EXISTS parent_first_name;


ALTER TABLE STUDENT
    DROP COLUMN IF EXISTS parent_last_name;


ALTER TABLE STUDENT
    DROP COLUMN IF EXISTS parent_phone_number;

ALTER TABLE STUDENT
    ADD parent_id uuid unique,
    ADD FOREIGN KEY (parent_id) references parent (id);

ALTER TABLE STUDENT
    ADD has_parent_account boolean default false