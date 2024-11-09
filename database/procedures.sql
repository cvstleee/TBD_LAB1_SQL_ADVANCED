
-- Desactivar un producto cuando su stock llegue a cero
CREATE OR REPLACE PROCEDURE deactivate_product_with_zero_stock()
LANGUAGE SQL
AS $$
    UPDATE products 
    SET state = 'no disponible'
    WHERE stock = 0;
$$;


-- Actualizar precios de los productos de una categoria en base a un porcentaje de incremento
CREATE OR REPLACE PROCEDURE update_products_prices_with_rate(rate NUMERIC, category_name VARCHAR)
LANGUAGE SQL
AS $$
    UPDATE products
    SET price = price * rate
    WHERE products.category_id = (SELECT id FROM categories WHERE name = category_name);
$$;
