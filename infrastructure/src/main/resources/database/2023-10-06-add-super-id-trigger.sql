ALTER TABLE users
    ADD super_id uuid;

CREATE OR REPLACE FUNCTION update_user_super_id() RETURNS TRIGGER AS
$$
BEGIN
    UPDATE users SET super_id = NEW.id WHERE id = NEW.user_id;
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;


CREATE TRIGGER student_super_id
    AFTER INSERT
    ON student
    FOR EACH ROW
EXECUTE FUNCTION update_user_super_id();

CREATE TRIGGER student_super_id
    AFTER INSERT
    ON admin
    FOR EACH ROW
EXECUTE FUNCTION update_user_super_id();

CREATE TRIGGER parent_super_id
    AFTER INSERT
    ON parent
    FOR EACH ROW
EXECUTE FUNCTION update_user_super_id();

CREATE TRIGGER teacher_super_id
    AFTER INSERT
    ON teacher
    FOR EACH ROW
EXECUTE FUNCTION update_user_super_id();