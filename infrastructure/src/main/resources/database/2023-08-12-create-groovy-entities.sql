CREATE TABLE IF NOT EXISTS groovy_script_status
(
    id   uuid primary key default gen_random_uuid(),
    name varchar(100) not null unique
);

INSERT INTO groovy_script_status(id, name)
VALUES ('c578961a-3947-11ee-be56-0242ac120002', 'EXECUTING');
INSERT INTO groovy_script_status(id, name)
VALUES ('c5789a84-3947-11ee-be56-0242ac120002', 'SUCCESS');
INSERT INTO groovy_script_status(id, name)
VALUES ('c5789bce-3947-11ee-be56-0242ac120002', 'ERROR');

CREATE TABLE IF NOT EXISTS groovy_script
(
    id                      uuid primary key default gen_random_uuid(),
    user_id                 uuid not null references users (id),
    content                 TEXT not null,
    groovy_script_status_id uuid not null references groovy_script_status (id)
);


CREATE TABLE IF NOT EXISTS groovy_script_result
(
    id               uuid primary key default gen_random_uuid(),
    result           text not null,
    groovy_script_id uuid  not null references groovy_script (id)
);