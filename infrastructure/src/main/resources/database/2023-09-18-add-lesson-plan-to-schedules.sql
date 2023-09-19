ALTER TABLE teacher_schedule
    ADD COLUMN IF NOT EXISTS lesson_plan_id uuid,
    ADD FOREIGN KEY (lesson_plan_id) REFERENCES lesson_plan(id);

ALTER TABLE class_room_schedule
    ADD COLUMN IF NOT EXISTS lesson_plan_id uuid,
    ADD FOREIGN KEY (lesson_plan_id) REFERENCES lesson_plan(id);