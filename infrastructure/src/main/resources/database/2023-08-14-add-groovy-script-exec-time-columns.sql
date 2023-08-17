ALTER TABLE groovy_script_result
    ADD COLUMN exec_time bigint NOT NULL default 0;

ALTER TABLE groovy_script
    ADD COLUMN start_time timestamp NOT NULL default current_timestamp;