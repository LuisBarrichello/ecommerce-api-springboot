ALTER TABLE shopping_cart
ADD CONSTRAINT fk_shopping_cart_user
FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE shopping_cart
ADD CONSTRAINT fk_shopping_cart_discount
FOREIGN KEY (discount_id) REFERENCES discounts(id);