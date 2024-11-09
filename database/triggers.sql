-- Funcion para operaciones sobre categories
CREATE OR REPLACE FUNCTION log_categories()
RETURNS TRIGGER AS 
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('categories', NEW.id, 'INSERT', 'New Category: ' || NEW.name);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('categories', NEW.id, 'UPDATE', 'Change Name: ' || OLD.name || ' to ' || NEW.name);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('categories', OLD.id, 'DELETE', 'Deleted Category: ' || OLD.name);
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

-- Funcion para operaciones sobre products
CREATE OR REPLACE FUNCTION log_products()
RETURNS TRIGGER AS 
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('products', NEW.id, 'INSERT', 'New Product: ' || NEW.name);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('products', NEW.id, 'UPDATE', 'Updated Product price: ' || OLD.price || ' to ' || NEW.price);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('products', OLD.id, 'DELETE', 'Deleted Product: ' || OLD.name);
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

-- Funcion para operaciones sobre clients
CREATE OR REPLACE FUNCTION log_clients()
RETURNS TRIGGER AS 
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('clients', NEW.id, 'INSERT', 'New Client: ' || NEW.name);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('clients', NEW.id, 'UPDATE', 'Updated Client: ' || OLD.name || ' to ' || NEW.name);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('clients', OLD.id, 'DELETE', 'Deleted Client: ' || OLD.name);
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

-- Funcion para operaciones sobre orders
CREATE OR REPLACE FUNCTION log_orders()
RETURNS TRIGGER AS 
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('orders', NEW.id, 'INSERT', 'New Order with total: ' || NEW.total);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('orders', NEW.id, 'UPDATE', 'Updated Order with total change from ' || OLD.total || ' to ' || NEW.total);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('orders', OLD.id, 'DELETE', 'Deleted Order with total: ' || OLD.total);
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;

-- Funcion para operaciones sobre order_details
CREATE OR REPLACE FUNCTION log_order_details()
RETURNS TRIGGER AS 
$$
BEGIN
    IF TG_OP = 'INSERT' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('order_details', NEW.id, 'INSERT', 'New Order Detail for Product ID: ' || NEW.product_id || ' with quantity: ' || NEW.quantity);
    ELSIF TG_OP = 'UPDATE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('order_details', NEW.id, 'UPDATE', 'Updated Order Detail for Product ID: ' || NEW.product_id || ' with quantity change from ' || OLD.quantity || ' to ' || NEW.quantity);
    ELSIF TG_OP = 'DELETE' THEN
        INSERT INTO logs (table_name, idElement, operation, description) 
        VALUES ('order_details', OLD.id, 'DELETE', 'Deleted Order Detail for Product ID: ' || OLD.product_id || ' with quantity: ' || OLD.quantity);
    END IF;

    RETURN NEW;
END;
$$ 
LANGUAGE plpgsql;



-- Trigger para categories
DROP TRIGGER IF EXISTS trigger_log_categories ON categories;
CREATE TRIGGER trigger_log_categories
AFTER INSERT OR DELETE OR UPDATE ON categories
FOR EACH ROW
EXECUTE FUNCTION log_categories();

-- Trigger para products
DROP TRIGGER IF EXISTS trigger_log_products ON products;
CREATE TRIGGER trigger_log_products
AFTER INSERT OR DELETE OR UPDATE ON products
FOR EACH ROW
EXECUTE FUNCTION log_products();

-- Trigger para clients
DROP TRIGGER IF EXISTS trigger_log_clients ON clients;
CREATE TRIGGER trigger_log_clients
AFTER INSERT OR DELETE OR UPDATE ON clients
FOR EACH ROW
EXECUTE FUNCTION log_clients();

-- Trigger para orders
DROP TRIGGER IF EXISTS trigger_log_orders ON orders;
CREATE TRIGGER trigger_log_orders
AFTER INSERT OR DELETE OR UPDATE ON orders
FOR EACH ROW
EXECUTE FUNCTION log_orders();

-- Trigger para order_details
DROP TRIGGER IF EXISTS trigger_log_order_details ON order_details;
CREATE TRIGGER trigger_log_order_details
AFTER INSERT OR DELETE OR UPDATE ON order_details
FOR EACH ROW
EXECUTE FUNCTION log_order_details();
