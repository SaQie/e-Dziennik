ALTER TABLE lesson_plan
    ADD COLUMN IF NOT EXISTS
        teacher_id uuid not null references teacher (id) default gen_random_uuid();

ALTER TABLE lesson_plan
    ADD COLUMN IF NOT EXISTS is_substitute bool not null default FALSE;

