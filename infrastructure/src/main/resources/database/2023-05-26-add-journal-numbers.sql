ALTER TABLE student
    ADD COLUMN journal_number int;


CREATE INDEX idx_student_journal_number ON student (journal_number);

CREATE OR REPLACE FUNCTION assign_journal_number() RETURNS TRIGGER AS
$$
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
$$ LANGUAGE plpgsql;

CREATE OR REPLACE TRIGGER journal_number_trigger
    AFTER INSERT
    ON student
    FOR EACH ROW
EXECUTE FUNCTION assign_journal_number();