ALTER TABLE order_items
ADD CONSTRAINT fk_orderitem_order
FOREIGN KEY (order_id) REFERENCES orders(id)
ON DELETE CASCADE;