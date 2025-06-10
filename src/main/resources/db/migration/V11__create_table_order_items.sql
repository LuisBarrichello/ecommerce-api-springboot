CREATE TABLE order_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT,  -- Chave estrangeira será adicionada posteriormente
    product_name VARCHAR(255) NOT NULL,
    price_at_purchase DECIMAL(15, 2) NOT NULL,
    quantity INT NOT NULL
);
