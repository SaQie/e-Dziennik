CREATE TABLE IF NOT EXISTS director
(
    id           uuid primary key default gen_random_uuid(),
    user_id      uuid         not null unique references users (id),
    address_id   uuid         not null unique references address (id),
    school_id    uuid         not null unique references school (id),
    first_name   varchar(50)  NOT NULL,
    last_name    varchar(50)  NOT NULL,
    full_name    varchar(100) NOT NULL,
    phone_number varchar(10),
    version      int8
);

ALTER TABLE school
    ADD COLUMN director_id uuid UNIQUE;

insert into ROLE(id, name)
VALUES ('5c1b8650-1f43-11ee-be56-0242ac120002', 'ROLE_DIRECTOR');


CREATE TRIGGER director_super_id
    AFTER INSERT
    ON director
    FOR EACH ROW
EXECUTE FUNCTION update_user_super_id();