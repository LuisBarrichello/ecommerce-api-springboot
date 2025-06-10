CREATE TABLE orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    discount_id BIGINT,
    status VARCHAR(255) NOT NULL,
    price_total DECIMAL(15, 2) NOT NULL,
    payment_method VARCHAR(255),
    tracking_code VARCHAR(255),
    taxes DECIMAL(15, 2),
    delivery_date TIMESTAMP,
    return_deadline TIMESTAMP,
    dispatch_date TIMESTAMP,
    date_of_receipt TIMESTAMP,
    freight_price DECIMAL(15, 2),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);