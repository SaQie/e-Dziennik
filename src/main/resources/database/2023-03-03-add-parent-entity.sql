insert into ROLE(id, name)
VALUES (4, 'ROLE_PARENT');

CREATE TABLE PARENT
(
    id serial primary key
);


ALTER TABLE PARENT
    ADD person_information_id int not null unique,
    ADD FOREIGN KEY (person_information_id) references person_information (id);

ALTER TABLE PARENT
    ADD address_id int not null unique,
    ADD FOREIGN KEY (address_id) references address (id);

ALTER TABLE PARENT
    ADD user_id int not null unique,
    ADD FOREIGN KEY (user_id) references users (id);

ALTER TABLE PARENT
    ADD student_id int not null unique,
    ADD FOREIGN KEY (student_id) references student (id);

ALTER TABLE STUDENT
    DROP COLUMN IF EXISTS parent_first_name;


ALTER TABLE STUDENT
    DROP COLUMN IF EXISTS parent_last_name;


ALTER TABLE STUDENT
    DROP COLUMN IF EXISTS parent_phone_number;