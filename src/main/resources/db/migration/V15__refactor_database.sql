-- Migration V15: refactor the database

-- Adjustments to the products table
ALTER TABLE products
MODIFY COLUMN weight DECIMAL(10,2),
MODIFY COLUMN dimensions VARCHAR(100); -- "10x20x30 - height, width, depth"

-- creating the roles table
CREATE TABLE IF NOT EXISTS roles (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);
-- Popular roles basicas (Exemplo, se necessário no início)
-- INSERT INTO roles (name) VALUES ('CUSTOMER'), ('ADMIN') ON DUPLICATE KEY UPDATE name=name;


-- Adjusting the users table to use roles
ALTER TABLE users
DROP COLUMN role;

ALTER TABLE users
ADD COLUMN role_id BIGINT;

-- Se roles foram populadas acima e users existem e precisam de um role default:
-- UPDATE users u SET u.role_id = (SELECT r.id FROM roles r WHERE r.name = 'CUSTOMER') WHERE u.role_id IS NULL;
-- Se role_id DEVE ser NOT NULL (requer que todos os users tenham um role_id):
-- ALTER TABLE users MODIFY COLUMN role_id BIGINT NOT NULL;

ALTER TABLE users
ADD CONSTRAINT fk_user_role FOREIGN KEY (role_id) REFERENCES roles(id);

-- creating the payment_methods table
CREATE TABLE IF NOT EXISTS payment_methods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    method VARCHAR(50) NOT NULL UNIQUE
);

-- adjusting the shopping_cart table
-- NÃO vamos mais remover o UNIQUE de user_id para manter um carrinho por usuário.
-- A linha "DROP INDEX IF EXISTS user_id" foi REMOVIDA.

ALTER TABLE shopping_cart -- Apenas ajustes de timestamp e status
MODIFY COLUMN created_at DATETIME DEFAULT CURRENT_TIMESTAMP, -- Consistente com V1
MODIFY COLUMN updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- Consistente com V1
MODIFY COLUMN status ENUM('ACTIVE', 'COMPLETED', 'CANCELED', 'PENDING_PAYMENT', 'SHIPPED', 'DELIVERED') NOT NULL DEFAULT 'ACTIVE';


-- adjusting the orders table
ALTER TABLE orders
MODIFY COLUMN status ENUM('ACTIVE', 'COMPLETED', 'CANCELED', 'PENDING_PAYMENT', 'SHIPPED', 'DELIVERED') NOT NULL;

ALTER TABLE orders
ADD COLUMN payment_method_id BIGINT;

ALTER TABLE orders
ADD CONSTRAINT fk_order_payment_method FOREIGN KEY (payment_method_id) REFERENCES payment_methods(id);

-- Opcional: Remover a coluna antiga payment_method de orders se não for mais necessária
-- ALTER TABLE orders DROP COLUMN IF EXISTS payment_method;

-- adjusting the order_items table
ALTER TABLE order_items
ADD COLUMN product_id BIGINT;

ALTER TABLE order_items
ADD CONSTRAINT fk_order_item_product FOREIGN KEY (product_id) REFERENCES products(id);

-- adjusting the discounts table
ALTER TABLE discounts
ADD CONSTRAINT unique_discount_code UNIQUE (discount_code);