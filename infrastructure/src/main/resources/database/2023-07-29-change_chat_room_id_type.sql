ALTER TABLE chat_room DROP COLUMN chat_id;


ALTER TABLE chat_room ADD COLUMN chat_id uuid NOT NULL default gen_random_uuid();