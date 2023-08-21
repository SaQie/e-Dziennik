CREATE TABLE IF NOT EXISTS class_room
(
    id              uuid primary key default gen_random_uuid(),
    school_id       uuid         not null references school (id),
    class_room_name varchar(255) not null,
    version         int8             default 0
);

CREATE UNIQUE INDEX IF NOT EXISTS idx_school_id_class_room_name ON class_room (school_id, class_room_name);


CREATE TABLE IF NOT EXISTS lesson_plan
(
    id              uuid primary key default gen_random_uuid(),
    school_class_id uuid        not null references school_class (id),
    subject_id      uuid        not null references subject (id),
    class_room_id   uuid        not null references class_room (id),
    lesson_order    int8        NOT NULL unique,
    start_date      timestamp not null,
    end_date        timestamp not null,
    duration        bigint      not null,
    version         int8             default 0
);

CREATE INDEX IF NOT EXISTS idx_lesson_order ON lesson_plan (lesson_order);

CREATE TABLE IF NOT EXISTS class_room_schedule
(
    id            uuid primary key default gen_random_uuid(),
    class_room_id uuid        not null references class_room (id),
    start_date    timestamp not null,
    end_date      timestamp not null,
    duration      bigint      not null,
    version       int8             default 0
);

CREATE TABLE IF NOT EXISTS teacher_schedule
(
    id         uuid primary key default gen_random_uuid(),
    teacher_id uuid        not null references teacher (id),
    start_date timestamp not null,
    end_date   timestamp not null,
    duration   bigint      not null,
    version    int8             default 0
);