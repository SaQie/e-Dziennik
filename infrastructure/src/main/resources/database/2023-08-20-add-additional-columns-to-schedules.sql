ALTER TABLE teacher_schedule
    ADD COLUMN IF NOT EXISTS
        description varchar(500) NOT NULL default '';

ALTER TABLE class_room_schedule
    ADD COLUMN IF NOT EXISTS
        description varchar(500) NOT NULL default '';