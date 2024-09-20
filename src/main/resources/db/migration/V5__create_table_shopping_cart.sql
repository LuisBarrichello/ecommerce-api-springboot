CREATE TABLE shopping_cart (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE,  -- Será adicionada como chave estrangeira posteriormente
    price_total_items DECIMAL(10, 2),
    discount_id BIGINT,     -- Será adicionada como chave estrangeira posteriormente
    taxes DECIMAL(10, 2),
    shipping DECIMAL(10, 2),
    status ENUM(
        'ACTIVE',
        'COMPLETED',
        'CANCELED',
        'PENDING_PAYMENT'
    ),
    payment_method VARCHAR(50),
    price_total_final DECIMAL(10, 2),
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);