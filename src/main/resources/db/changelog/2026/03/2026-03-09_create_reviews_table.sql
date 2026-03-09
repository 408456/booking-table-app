CREATE TABLE IF NOT EXISTS reviews
(
    id            BIGSERIAL PRIMARY KEY,
    restaurant_id BIGINT      NOT NULL,
    user_id       BIGINT      NOT NULL,
    rating        INTEGER     NOT NULL CHECK (rating >= 1 AND rating <= 5),
    comment       VARCHAR(1000),
    created_at    TIMESTAMP   NOT NULL DEFAULT NOW(),

    CONSTRAINT fk_reviews_restaurant FOREIGN KEY (restaurant_id)
    REFERENCES restaurants (id) ON DELETE CASCADE,
    CONSTRAINT fk_reviews_user FOREIGN KEY (user_id)
    REFERENCES users (id) ON DELETE CASCADE
);

COMMENT ON TABLE reviews IS 'Таблица отзывов пользователей о ресторанах';
COMMENT ON COLUMN reviews.id IS 'Уникальный идентификатор отзыва';
COMMENT ON COLUMN reviews.restaurant_id IS 'Идентификатор ресторана, на который оставлен отзыв';
COMMENT ON COLUMN reviews.user_id IS 'Идентификатор пользователя, оставившего отзыв';
COMMENT ON COLUMN reviews.rating IS 'Оценка ресторана (целое число от 1 до 5)';
COMMENT ON COLUMN reviews.comment IS 'Текст отзыва';
COMMENT ON COLUMN reviews.created_at IS 'Дата и время создания отзыва';