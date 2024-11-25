\c "e-commerce-db"

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

-- Reporte de operaciones realizadas por los clientes
CREATE OR REPLACE FUNCTION get_client_operations_report()
RETURNS TABLE (
    id INTEGER,
    client_id INTEGER,
    table_name VARCHAR,
    element_id INTEGER,
    operation TEXT,
    description TEXT,
    date TIMESTAMP
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        l.id AS id,                     
        l.client_id AS client_id,              
        l.table_name AS table_name,     
        l.element_id AS element_id,     
        l.operation AS operation,       
        l.description AS description,   
        l.date AS date                 
    FROM
        logs AS l        
    ORDER BY
        l.date DESC;                   
END;
$$
 LANGUAGE plpgsql;

-- Uso del procedimiento
-- SELECT * FROM get_user_operations_report();