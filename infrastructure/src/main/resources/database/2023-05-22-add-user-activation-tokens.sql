CREATE TABLE email_activation_tokens
(
    user_id         uuid      not null unique,
    token           uuid      not null unique default gen_random_uuid(),
    expiration_date timestamp not null
);

CREATE INDEX idx_email_activation_tokens_token ON email_activation_tokens (token);

ALTER TABLE users
    ADD is_active bool not null default false;