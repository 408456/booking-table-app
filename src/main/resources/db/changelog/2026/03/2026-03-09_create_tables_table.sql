CREATE TABLE IF NOT EXISTS tables
(
    id            BIGSERIAL PRIMARY KEY,
    restaurant_id BIGINT      NOT NULL,
    seats         INTEGER     NOT NULL CHECK (seats > 0),
    description   VARCHAR(255),
    is_available  BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at    TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_tables_restaurant FOREIGN KEY (restaurant_id)
    REFERENCES restaurants (id) ON DELETE CASCADE
);

COMMENT ON TABLE tables IS 'Столы в ресторанах';
COMMENT ON COLUMN tables.id IS 'Уникальный идентификатор стола';
COMMENT ON COLUMN tables.restaurant_id IS 'Идентификатор ресторана, которому принадлежит стол';
COMMENT ON COLUMN tables.seats IS 'Количество посадочных мест за столом';
COMMENT ON COLUMN tables.description IS 'Описание стола';
COMMENT ON COLUMN tables.is_available IS 'Доступен ли стол для бронирования';
COMMENT ON COLUMN tables.created_at IS 'Дата и время добавления стола';