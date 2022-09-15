CREATE TABLE school_level(
                             school_level_id SERIAL PRIMARY KEY,
                             name varchar(255) NOT NULL UNIQUE
);

INSERT INTO school_level(school_level_id, name) VALUES (1,'Szkoła podstawowa'),
                                                       (2, 'Szkoła średnia'),
                                                       (3, 'Szkoła wyższa');

CREATE TABLE school(
    school_id SERIAL PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE,
    adress varchar(255) NOT NULL,
    postal_code varchar(9) NOT NULL,
    city varchar(255) NOT NULL ,
    nip varchar(10) NOT NULL UNIQUE ,
    regon varchar(8) NOT NULL UNIQUE ,
    phone_number varchar(9) NOT NULL UNIQUE,
    school_level_id INT NOT NULL UNIQUE REFERENCES school_level(school_level_id)
);

