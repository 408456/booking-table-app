INSERT INTO roles (name, description)
VALUES ('ADMIN', 'Administrator') ON CONFLICT (name) DO NOTHING;
INSERT INTO roles (name, description)
VALUES ('CLIENT', 'Client') ON CONFLICT (name) DO NOTHING;

INSERT INTO users (first_name, last_name, phone, email, password, is_verified, created_at, updated_at)
VALUES ('Admin', 'Adminov', '+79991112233', 'admin@example.com', '$2a$04$LBV6eBuJcx4u7a6zSrBEsOCQNCx/Z8h1OxmU.Bm0H9vr1VAJ6gcN2', true, NOW(), NOW());

INSERT INTO user_roles (user_id, role_id)
VALUES ((SELECT id FROM users WHERE email = 'admin@example.com'), (SELECT id FROM roles WHERE name = 'ADMIN'));