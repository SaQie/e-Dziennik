CREATE TABLE IF NOT EXISTS class_room
(
    id              uuid primary key default gen_random_uuid(),
    school_id       uuid         not null references school (id),
    class_room_name varchar(255) not null unique,
    version         int8             default 0
);


CREATE TABLE IF NOT EXISTS lesson_plan
(
    id              uuid primary key default gen_random_uuid(),
    school_class_id uuid        not null references school_class (id),
    subject_id      uuid        not null references subject (id),
    class_room_id   uuid        not null unique references class_room (id),
    start_date      timestamptz not null,
    end_date        timestamptz not null,
    duration        bigint      not null,
    version         int8             default 0
);



CREATE TABLE IF NOT EXISTS class_room_schedule
(
    id            uuid primary key default gen_random_uuid(),
    class_room_id uuid        not null references class_room (id),
    start_date    timestamptz not null,
    end_date      timestamptz not null,
    duration      bigint      not null,
    version       int8             default 0
);

CREATE TABLE IF NOT EXISTS teacher_schedule
(
    id         uuid primary key default gen_random_uuid(),
    teacher_id uuid        not null references teacher (id),
    start_date timestamptz not null,
    end_date   timestamptz not null,
    duration   bigint      not null,
    version    int8             default 0
);