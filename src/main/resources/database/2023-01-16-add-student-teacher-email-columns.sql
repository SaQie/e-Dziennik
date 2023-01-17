ALTER TABLE teacher
    ADD email varchar(255) NOT NULL UNIQUE;

ALTER TABLE student
    ADD email varchar(255) NOT NULL UNIQUE;


CREATE TABLE users
(
    id              SERIAL PRIMARY KEY,
    username        varchar(255) NOT NULL UNIQUE,
    PASSWORD        varchar(255) NOT NULL,
    email           varchar(255) NOT NULL UNIQUE,
    created_date    date         NOT NULL,
    updated_date    timestamp without time zone,
    last_login_date timestamp without time zone,
    role_id         INT REFERENCES role (id)
);

ALTER TABLE admin
    ADD user_id int not null unique,
    ADD FOREIGN KEY (user_id) references users(id);

ALTER TABLE teacher
    ADD user_id int not null unique,
    ADD FOREIGN KEY (user_id) references users(id);

ALTER TABLE teacher drop column if exists username;
ALTER TABLE teacher drop column if exists password;
ALTER TABLE teacher drop column if exists email;
ALTER TABLE teacher drop column if exists role_id;
ALTER TABLE teacher drop column if exists created_date;
ALTER TABLE teacher drop column if exists updated_date;
ALTER TABLE teacher drop column if exists last_login_date;

ALTER TABLE student
    ADD user_id int not null unique,
    ADD FOREIGN KEY (user_id) references users(id);

ALTER TABLE student drop column if exists username;
ALTER TABLE student drop column if exists password;
ALTER TABLE student drop column if exists email;
ALTER TABLE student drop column if exists role_id;
ALTER TABLE student drop column if exists created_date;
ALTER TABLE student drop column if exists updated_date;
ALTER TABLE student drop column if exists last_login_date;