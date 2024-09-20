ALTER TABLE users
ADD CONSTRAINT fk_shopping_cart
FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id);