create table app_settings(
    id serial primary key,
    name varchar(100) not null unique,
    value boolean not null
);
