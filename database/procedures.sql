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
CREATE OR REPLACE FUNCTION get_user_operations_report()
RETURNS TABLE (
    client_name VARCHAR,
    client_email VARCHAR,
    total_inserts BIGINT,
    total_updates BIGINT,
    total_deletes BIGINT,
    total_operations BIGINT,
    description_operations TEXT[]
) AS $$
BEGIN
    RETURN QUERY
    SELECT
        c.name AS client_name,
        c.email AS client_email,
        COUNT(CASE WHEN l.operation = 'INSERT' THEN 1 END) AS total_inserts,
        COUNT(CASE WHEN l.operation = 'UPDATE' THEN 1 END) AS total_updates,
        COUNT(CASE WHEN l.operation = 'DELETE' THEN 1 END) AS total_deletes,
        COUNT(*) AS total_operations,
        ARRAY_AGG(l.description) AS description_operations
    FROM
        logs l
    INNER JOIN
        clients c ON l.client_id = c.id
    WHERE
        c.deleted_at IS NULL
    GROUP BY
        c.name, c.email
    ORDER BY
        total_operations DESC;
END;
$$ LANGUAGE plpgsql;

-- Uso del procedimiento
-- SELECT * FROM get_user_operations_report();