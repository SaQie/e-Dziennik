create table app_settings(
    id              uuid PRIMARY KEY DEFAULT gen_random_uuid(),
    name varchar(100) not null unique,
    value boolean not null
);
