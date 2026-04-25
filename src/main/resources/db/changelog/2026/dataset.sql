INSERT INTO users (first_name, last_name, phone, email, password, is_verified, role)
VALUES ('Админ',
        'Админов',
        '+79000000001',
        'admin@gmail.com',
        '$2a$04$XmuFFQCP9/YJ4B0A48ZxZ.aLXxkSAwqp2V5eBhzYlDHomvYXM7JvC',
        TRUE,
        'ADMIN'),
       ('Клиент',
        'Клиентов',
        '+79000000002',
        'client@gmail.com',
        '$2a$04$XmuFFQCP9/YJ4B0A48ZxZ.aLXxkSAwqp2V5eBhzYlDHomvYXM7JvC',
        TRUE,
        'CLIENT');
--
-- INSERT INTO reviews (restaurant_id, user_id, rating, comment, created_at)
-- VALUES (1, 2, 5, 'Отличный ресторан, рекомендую', DEFAULT),
--        (2, 2, 4, 'Хорошее место, но высокие цены', DEFAULT),
--        (1, 1, 3, 'Очень шумно и грязно', DEFAULT),
--        (2, 1, 5, 'Прекрасный вид, вкусная еда', DEFAULT);
--
-- INSERT INTO tables (restaurant_id, seats, description, is_available)
-- VALUES (1, 2, 'Маленький столик у окна', TRUE),
--        (1, 4, 'Стол в центре зала', FALSE),
--        (1, 6, 'Большой стол для компании', TRUE),
--        (1, 8, 'Банкетный стол', FALSE),
--
--        (2, 2, 'Столик в углу для двоих', TRUE),
--        (2, 4, 'Круглый стол для компании', FALSE),
--        (2, 6, 'Семейный стол', TRUE),
--        (2, 8, 'Стол на террасе', FALSE);
--
--
-- INSERT INTO bookings (restaurant_id, user_id, table_id, start_time, end_time, guests_count, status)
-- VALUES (1, 2, 1,
--         '2026-10-15 18:00:00', '2026-10-15 20:00:00', 2, 'CREATED'),
--        (1, 1, 3,
--         '2026-05-11 18:00:00', '2026-05-11 20:30:00', 5, 'CONFIRMED'),
--        (2, 1, 1,
--         '2026-05-15 18:00:00', '2026-05-15 19:00:00', 2, 'COMPLETED'),
--        (2, 2, 5,
--         '2026-11-10 19:00:00', '2026-11-10 21:00:00', 2, 'CANCELLED');