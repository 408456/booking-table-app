DO $$
BEGIN
    IF NOT EXISTS (SELECT 1 FROM pg_type WHERE typname = 'booking_status') THEN
CREATE TYPE booking_status AS ENUM ('CREATED', 'CONFIRMED', 'CANCELLED', 'COMPLETED');
END IF;
END$$;

CREATE TABLE IF NOT EXISTS bookings
(
    id            BIGSERIAL PRIMARY KEY,
    restaurant_id BIGINT         NOT NULL,
    user_id       BIGINT         NOT NULL,
    table_id      BIGINT         NOT NULL,
    booking_time  TIMESTAMP      NOT NULL,
    guests_count  INTEGER        NOT NULL CHECK (guests_count > 0),
    status        booking_status NOT NULL DEFAULT 'CREATED',
    created_at    TIMESTAMP      NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_bookings_restaurant FOREIGN KEY (restaurant_id)
    REFERENCES restaurants (id) ON DELETE CASCADE,
    CONSTRAINT fk_bookings_user FOREIGN KEY (user_id)
    REFERENCES users (id) ON DELETE CASCADE,
    CONSTRAINT fk_bookings_table FOREIGN KEY (table_id)
    REFERENCES tables (id) ON DELETE CASCADE
);

COMMENT ON TABLE bookings IS 'Бронирования столов';
COMMENT ON COLUMN bookings.id IS 'Уникальный идентификатор брони';
COMMENT ON COLUMN bookings.restaurant_id IS 'Идентификатор ресторана';
COMMENT ON COLUMN bookings.user_id IS 'Идентификатор пользователя, создавшего бронь';
COMMENT ON COLUMN bookings.table_id IS 'Идентификатор забронированного стола';
COMMENT ON COLUMN bookings.booking_time IS 'Дата и время начала бронирования';
COMMENT ON COLUMN bookings.guests_count IS 'Количество гостей';
COMMENT ON COLUMN bookings.status IS 'Статус брони (CREATED, CONFIRMED, CANCELLED, COMPLETED)';
COMMENT ON COLUMN bookings.created_at IS 'Дата и время создания брони';