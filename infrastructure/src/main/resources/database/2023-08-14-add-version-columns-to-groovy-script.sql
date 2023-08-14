ALTER TABLE groovy_script
    ADD COLUMN version int8 default 0;
ALTER TABLE groovy_script_status
    ADD COLUMN version int8 default 0;
ALTER TABLE groovy_script_result
    ADD COLUMN version int8 default 0;