ALTER TABLE app_settings
    DROP COLUMN IF EXISTS value;


ALTER TABLE app_settings
    ADD boolean_value boolean,
    ADD string_value  varchar(100),
    ADD long_value    bigint,
    ADD CONSTRAINT check_required_column CHECK (boolean_value IS NOT NULL
        OR
                                                string_value IS NOT NULL
        OR
                                                long_value IS NOT NULL);


insert into app_settings(id,name, boolean_value) values (1,'automatically.insert.student.subjects.when.add', false);
insert into app_settings(id, name, boolean_value) values (2,'allow.to.create.student.accounts.independent', false);