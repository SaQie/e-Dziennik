CREATE TABLE ADDRESS(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    address varchar(255) not null,
    postal_code varchar(8) not null,
    city varchar(255) not null
);

ALTER TABLE student DROP COLUMN address, DROP COLUMN postal_code, DROP COLUMN city;
ALTER TABLE teacher DROP COLUMN address, DROP COLUMN postal_code, DROP COLUMN city;
ALTER TABLE school DROP COLUMN adress, DROP COLUMN postal_code, DROP COLUMN city;

ALTER TABLE student add address_id uuid not null, add foreign key (address_id) REFERENCES ADDRESS(id);
ALTER TABLE teacher add address_id uuid not null, add foreign key (address_id) REFERENCES ADDRESS(id);
ALTER TABLE school add address_id uuid not null, add foreign key (address_id) REFERENCES ADDRESS(id);