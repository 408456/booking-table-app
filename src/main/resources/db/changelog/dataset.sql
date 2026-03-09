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

INSERT INTO cuisines (name, description)
VALUES ('Итальянская кухня',
        'Традиционные блюда Италии'),
       ('Русская кухня',
        'Русская традиционная кухня'),
       ('Японская кухня',
        'Японская кухня')
;

INSERT INTO restaurants (title, description, address, avg_sum, menu, is_published)
VALUES ('Chang',
        'Ресторан японской кухни',
        'Москва, ул. Тверская, 12',
        1000,
        'https://example.com/menu/la-la.pdf',
        TRUE),
       ('Terrassa',
        'Смешанная кухня',
        'Москва, ул. Арбат, 8',
        20000,
        'https://example.com/menu/terrassa.pdf',
        TRUE);

INSERT INTO restaurants_cuisines (restaurant_id, cuisine_id)
VALUES (1, 1),
       (2, 1),
       (2, 2),
       (2, 3);