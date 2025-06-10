ALTER TABLE orders
ADD CONSTRAINT fk_order_discount
FOREIGN KEY (discount_id) REFERENCES discounts(id)
ON DELETE SET NULL;

