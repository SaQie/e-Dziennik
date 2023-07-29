CREATE TABLE message_status
(
    id   uuid primary key default gen_random_uuid(),
    name varchar(100) NOT NULL UNIQUE
);

CREATE TABLE chat_message
(
    id                uuid primary key      default gen_random_uuid(),
    chat_id           uuid         NOT NULL,
    sender_id         uuid         NOT NULL,
    recipient_id      uuid         NOT NULL,
    sender_name       varchar(100) NOT NULL,
    recipient_name    varchar(100) NOT NULL,
    content           TEXT         NOT NULL,
    date              timestamptz  NOT NULL default current_timestamp,
    message_status_id uuid         not null references message_status (id)

);


CREATE TABLE chat_room
(
    id           uuid primary key default gen_random_uuid(),
    chat_id      varchar(255) NOT NULL,
    sender_id    uuid         NOT NULL,
    recipient_id uuid         NOT NULL
);
