CREATE OR REPLACE PROCEDURE update_product(
    p_id INT,
    p_name VARCHAR(255),
    p_description TEXT,
    p_price DECIMAL(10, 2),
    p_stock INT,
    p_state VARCHAR(50),
    p_deleted_at TIMESTAMP
) 
LANGUAGE plpgsql
AS $$
DECLARE
    v_state VARCHAR(50);
BEGIN
    IF p_stock <= 0 THEN
        v_state := 'not available';
    ELSE 
        v_state := p_state;
    END IF;

    UPDATE products
    SET name = p_name,
        description = p_description,
        price = p_price,
        stock = p_stock,
        state = v_state
    WHERE id = p_id AND deleted_at IS NULL;
END;
$$;

-- Actualizar precios de los productos de una categoria en base a un porcentaje de incremento
CREATE OR REPLACE PROCEDURE update_products_prices_with_rate(rate NUMERIC, category_id INT)
LANGUAGE plpgsql
AS $$
BEGIN
    UPDATE products
    SET price = price * (1 + (rate / 100))
    WHERE products.category_id = category_id;
END;
$$;
