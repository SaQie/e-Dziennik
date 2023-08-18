ALTER TABLE chat_room
    ADD COLUMN version int8 default 0;
ALTER TABLE chat_message
    ADD COLUMN version int8 default 0;
ALTER TABLE message_status
    ADD COLUMN version int8 default 0;

