CREATE TYPE role_type AS ENUM ('ADMIN', 'CLIENT');

CREATE TABLE users
(
    id          BIGSERIAL PRIMARY KEY,
    first_name  VARCHAR(100) NOT NULL,
    last_name   VARCHAR(100) NOT NULL,
    phone       VARCHAR(20)  NOT NULL UNIQUE,
    email       VARCHAR(255) NOT NULL UNIQUE,
    password    VARCHAR(255) NOT NULL,
    is_verified BOOLEAN      NOT NULL DEFAULT FALSE,
    role        VARCHAR(20)  NOT NULL,
    created_at  TIMESTAMP    NOT NULL DEFAULT NOW(),
    updated_at  TIMESTAMP    NOT NULL DEFAULT NOW()
);

COMMENT ON TYPE role_type IS 'Тип роли пользователя: ADMIN или CLIENT';

COMMENT ON TABLE users IS 'Таблица пользователей системы';
COMMENT ON COLUMN users.id IS 'Уникальный идентификатор пользователя';
COMMENT ON COLUMN users.first_name IS 'Имя пользователя';
COMMENT ON COLUMN users.last_name IS 'Фамилия пользователя';
COMMENT ON COLUMN users.phone IS 'Номер телефона пользователя, уникальный';
COMMENT ON COLUMN users.email IS 'Электронная почта пользователя, уникальная';
COMMENT ON COLUMN users.password IS 'Хэш пароля пользователя';
COMMENT ON COLUMN users.is_verified IS 'Флаг верификации пользователя';
COMMENT ON COLUMN users.role IS 'Роль пользователя (ADMIN или CLIENT)';
COMMENT ON COLUMN users.created_at IS 'Дата и время создания пользователя';
COMMENT ON COLUMN users.updated_at IS 'Дата и время последнего обновления пользователя';

