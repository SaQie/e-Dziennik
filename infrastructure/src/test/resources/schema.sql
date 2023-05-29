INSERT INTO school_level(id, name, version) values (gen_random_uuid(), 'UNIVERSITY', 1);
INSERT INTO school_level(id, name, version) values (gen_random_uuid(), 'PRIMARY', 1);
INSERT INTO school_level(id, name, version) values (gen_random_uuid(), 'HIGH', 1);

INSERT INTO role(id, name, version) VALUES (gen_random_uuid(), 'ROLE_ADMIN', 1);
INSERT INTO role(id, name, version) VALUES (gen_random_uuid(), 'ROLE_STUDENT', 1);
INSERT INTO role(id, name, version) VALUES (gen_random_uuid(), 'ROLE_PARENT', 1);
INSERT INTO role(id, name, version) VALUES (gen_random_uuid(), 'ROLE_TEACHER', 1);

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