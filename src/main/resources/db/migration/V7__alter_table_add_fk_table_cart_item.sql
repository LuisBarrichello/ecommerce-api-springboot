ALTER TABLE cart_item
ADD CONSTRAINT fk_cart_item_shopping_cart
FOREIGN KEY (shopping_cart_id) REFERENCES shopping_cart(id);

ALTER TABLE cart_item
ADD CONSTRAINT fk_cart_item_product
FOREIGN KEY (product_id) REFERENCES products(id);