\c "e-commerce-db"

CREATE OR REPLACE FUNCTION log_categories()
RETURNS TRIGGER AS 
$$
DECLARE
    current_client_id INTEGER;
BEGIN
    current_client_id := current_setting('application.client_id', true)::INTEGER;

    IF current_client_id IS NULL THEN
        current_client_id := 0;
    END IF;

    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (client_id, table_name, element_id, operation, description) 
        VALUES (current_client_id, 'categories', NEW.id, 'INSERT', 'New Category: ' || NEW.name);
    ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.deleted_at IS NULL AND NEW.deleted_at IS NOT NULL THEN
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'categories', NEW.id, 'DELETE', 'Soft Deleted Category: ' || OLD.name);
        ELSE
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'categories', NEW.id, 'UPDATE', 'Change Name: ' || OLD.name || ' to ' || NEW.name);
        END IF;
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_log_categories ON categories;
CREATE TRIGGER trigger_log_categories
AFTER INSERT OR DELETE OR UPDATE ON categories
FOR EACH ROW
EXECUTE FUNCTION log_categories();


CREATE OR REPLACE FUNCTION log_products()
RETURNS TRIGGER AS 
$$
DECLARE
    current_client_id INTEGER;
BEGIN
    current_client_id := current_setting('application.client_id', true)::INTEGER;

    IF current_client_id IS NULL THEN
        current_client_id := 0;
    END IF;

    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (client_id, table_name, element_id, operation, description) 
        VALUES (current_client_id, 'products', NEW.id, 'INSERT', 'New Product: ' || NEW.name || ' with price: ' || NEW.price || ' , stock: ' || NEW.stock || ' and state: ' || NEW.state);

    ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.deleted_at IS NULL AND NEW.deleted_at IS NOT NULL THEN
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'products', NEW.id, 'DELETE', 'Soft Deleted Product: ' || OLD.name);
        ELSE
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'products', NEW.id, 'UPDATE', 'Updated Product ' || New.name || ' price: ' || OLD.price || ' to ' || NEW.price || ' , stock: ' || OLD.stock || ' to ' || NEW.stock || ' and state: ' || OLD.state || ' to ' || NEW.state);
        END IF;
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_log_products ON products;
CREATE TRIGGER trigger_log_products
AFTER INSERT OR DELETE OR UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION log_products();


CREATE OR REPLACE FUNCTION log_orders()
RETURNS TRIGGER AS 
$$
DECLARE
    current_client_id INTEGER;
BEGIN
    current_client_id := current_setting('application.client_id', true)::INTEGER;

    IF current_client_id IS NULL THEN
        current_client_id := 0;
    END IF;

    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (client_id, table_name, element_id, operation, description) 
        VALUES (current_client_id, 'orders', NEW.id, 'INSERT', 'New Order with total: ' || NEW.total || ' and state: ' || NEW.state);

    ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.deleted_at IS NULL AND NEW.deleted_at IS NOT NULL THEN
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'orders', NEW.id, 'DELETE', 'Soft Deleted Order with total: ' || OLD.total);
        ELSE
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'orders', NEW.id, 'UPDATE', 'Updated Order with total change from ' || OLD.total || ' to ' || NEW.total || ' and state: ' || OLD.state || ' to ' || NEW.state);
        END IF;
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trigger_log_orders ON orders;
CREATE TRIGGER trigger_log_orders
AFTER INSERT OR DELETE OR UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION log_orders();


CREATE OR REPLACE FUNCTION log_order_details()
RETURNS TRIGGER AS 
$$
DECLARE
    current_client_id INTEGER;
BEGIN
    current_client_id := current_setting('application.client_id', true)::INTEGER;

    IF current_client_id IS NULL THEN
        current_client_id := 0;
    END IF;

    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (client_id, table_name, element_id, operation, description) 
        VALUES (current_client_id, 'order_details', NEW.id, 'INSERT', 
                'New Order Detail for Product ID: ' || NEW.product_id || ' with quantity: ' || NEW.quantity || ' for Order ID: '|| New.order_id);

    ELSIF TG_OP = 'UPDATE' THEN
        IF OLD.deleted_at IS NULL AND NEW.deleted_at IS NOT NULL THEN
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'order_details', NEW.id, 'DELETE', 
                    'Soft Deleted Order Detail for Product ID: ' || OLD.product_id || ' with quantity: ' || OLD.quantity || ' for Order ID: '|| OLD.order_id);
        ELSE
            INSERT INTO logs (client_id, table_name, element_id, operation, description) 
            VALUES (current_client_id, 'order_details', NEW.id, 'UPDATE', 
                    'Updated Order Detail for Product ID: ' || NEW.product_id || 
                    ' with quantity change from ' || OLD.quantity || ' to ' || NEW.quantity || ' for Order ID: '|| NEW.order_id);
        END IF;
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;


DROP TRIGGER IF EXISTS trigger_log_order_details ON order_details;
CREATE TRIGGER trigger_log_order_details
AFTER INSERT OR DELETE OR UPDATE ON order_details
FOR EACH ROW
EXECUTE FUNCTION log_order_details();
