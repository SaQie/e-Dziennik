INSERT INTO school_level(id, name, version)
values ('ff1330f2-1f42-11ee-be56-0242ac120002', 'UNIVERSITY', 1)
ON CONFLICT DO NOTHING;
INSERT INTO school_level(id, name, version)
values ('ff131a86-1f42-11ee-be56-0242ac120002', 'PRIMARY SCHOOL', 1)
ON CONFLICT DO NOTHING;
INSERT INTO school_level(id, name, version)
values ('ff131d60-1f42-11ee-be56-0242ac120002', 'HIGH SCHOOL', 1)
ON CONFLICT DO NOTHING;

INSERT INTO role(id, name, version)
VALUES ('5c1b7dae-1f43-11ee-be56-0242ac120002', 'ROLE_ADMIN', 1)
ON CONFLICT DO NOTHING;
INSERT INTO role(id, name, version)
VALUES ('5c1b82fe-1f43-11ee-be56-0242ac120002', 'ROLE_STUDENT', 1)
ON CONFLICT DO NOTHING;
INSERT INTO role(id, name, version)
VALUES ('5c1b848e-1f43-11ee-be56-0242ac120002', 'ROLE_PARENT', 1)
ON CONFLICT DO NOTHING;
INSERT INTO role(id, name, version)
VALUES ('5c1b80d8-1f43-11ee-be56-0242ac120002', 'ROLE_TEACHER', 1)
ON CONFLICT DO NOTHING;
INSERT INTO role(id, name, version)
VALUES ('5c1b8650-1f43-11ee-be56-0242ac120002', 'ROLE_DIRECTOR', 1)
ON CONFLICT DO NOTHING;

INSERT INTO groovy_script_status(id, name, version)
VALUES ('c578961a-3947-11ee-be56-0242ac120002', 'EXECUTING', 1)
ON CONFLICT DO NOTHING;
INSERT INTO groovy_script_status(id, name, version)
VALUES ('c5789a84-3947-11ee-be56-0242ac120002', 'SUCCESS', 1)
ON CONFLICT DO NOTHING;
INSERT INTO groovy_script_status(id, name, version)
VALUES ('c5789bce-3947-11ee-be56-0242ac120002', 'ERROR', 1)
ON CONFLICT DO NOTHING;

INSERT INTO message_status(id,name,version)
VALUES('36572198-2cab-11ee-be56-0242ac120002', 'DELIVERED', 1);
INSERT INTO message_status(id,name,version)
VALUES('36571ed2-2cab-11ee-be56-0242ac120002', 'RECEIVED', 1);

CREATE TABLE IF NOT EXISTS email_activation_tokens
(
    user_id         uuid      not null unique,
    token           uuid      not null unique default gen_random_uuid(),
    expiration_date timestamp not null
);

CREATE INDEX IF NOT EXISTS idx_email_activation_tokens_token ON email_activation_tokens (token);

-- Hibernate have a problems to parse the '$$' delimiters, so i had change the delimiters to ''
CREATE OR REPLACE FUNCTION assign_journal_number() RETURNS TRIGGER AS
'
    DECLARE
        V_LAST_JOURNAL_NUMBER INT;
    BEGIN
        SELECT MAX(s.journal_number)
        INTO V_LAST_JOURNAL_NUMBER
        FROM student s
        WHERE s.school_class_id = NEW.school_class_id;

        IF V_LAST_JOURNAL_NUMBER IS NULL THEN
            UPDATE student
            SET journal_number = 1
            WHERE id = NEW.id;
        ELSE
            V_LAST_JOURNAL_NUMBER = V_LAST_JOURNAL_NUMBER + 1;
            UPDATE student
            SET journal_number = V_LAST_JOURNAL_NUMBER
            WHERE id = NEW.id;
        END IF;
        RETURN NEW;
    END;
' LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER journal_number_trigger
    AFTER INSERT
    ON student
    FOR EACH ROW
EXECUTE FUNCTION assign_journal_number();