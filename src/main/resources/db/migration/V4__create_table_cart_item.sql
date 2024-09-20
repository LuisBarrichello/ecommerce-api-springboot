CREATE TABLE cart_item (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shopping_cart_id BIGINT, -- Será adicionada como chave estrangeira posteriormente
    product_id BIGINT,       -- Será adicionada como chave estrangeira posteriormente
    quantity INT,
    price DECIMAL(10, 2)
);
