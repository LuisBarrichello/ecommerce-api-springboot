CREATE TABLE discounts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    discount_code VARCHAR(50),
    discount_amount DECIMAL(10, 2)
);