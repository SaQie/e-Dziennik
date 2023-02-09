ALTER TABLE teacher drop column if exists person_information_id;
ALTER TABLE teacher drop column if exists address_id;
ALTER TABLE student drop column if exists person_information_id;
ALTER TABLE student drop column if exists address_id;


ALTER TABLE users
    ADD person_information_id int unique,
    ADD FOREIGN KEY (person_information_id) references person_information(id);

ALTER TABLE users
    ADD address_id int unique,
    ADD FOREIGN KEY (address_id) references address(id);