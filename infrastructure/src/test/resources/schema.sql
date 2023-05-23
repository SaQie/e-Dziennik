INSERT INTO school_level(id, name) values (gen_random_uuid(), 'UNIVERSITY');
INSERT INTO school_level(id, name) values (gen_random_uuid(), 'PRIMARY');
INSERT INTO school_level(id, name) values (gen_random_uuid(), 'HIGH');

INSERT INTO role(id, name) VALUES (gen_random_uuid(), 'ROLE_ADMIN');
INSERT INTO role(id, name) VALUES (gen_random_uuid(), 'ROLE_STUDENT');
INSERT INTO role(id, name) VALUES (gen_random_uuid(), 'ROLE_PARENT');
INSERT INTO role(id, name) VALUES (gen_random_uuid(), 'ROLE_TEACHER');

CREATE TABLE IF NOT EXISTS email_activation_tokens
(
    user_id         uuid      not null unique,
    token           uuid      not null unique default gen_random_uuid(),
    expiration_date timestamp not null
);

CREATE INDEX IF NOT EXISTS idx_email_activation_tokens_token ON email_activation_tokens (token);