\c "e-commerce-db"

-- Carga de datos para la tabla "categories"
INSERT INTO categories (name) VALUES
('Electronica'),
('Hogar'),
('Libros'),
('Ropa'),
('Juguetes');

-- Carga de datos para la tabla "products"
INSERT INTO products (name, description, price, stock, state, category_id) VALUES
('Telefono inteligente', 'Telefono Android con 128GB de almacenamiento.', 399.99, 50, 'available', 1),
('Televisor LED', 'Televisor LED de 55 pulgadas, resolucion 4K.', 599.99, 20, 'available', 1),
('Aspiradora', 'Aspiradora con alta potencia y bolsa de almacenamiento.', 89.99, 15, 'available', 2),
('Sofa de 3 plazas', 'Sofa comodo de tela gris.', 299.99, 5, 'available', 2),
('El Quijote', 'Libro clasico de Miguel de Cervantes.', 15.99, 100, 'available', 3),
('Camiseta de algodon', 'Camiseta basica de algodon en color blanco.', 9.99, 200, 'available', 4),
('Muñeca de trapo', 'Muñeca suave para niñas pequeñas.', 12.99, 30, 'available', 5);

-- Carga de datos para la tabla "clients"
INSERT INTO clients (name, address, email, password, phone) VALUES
('Juan Perez', 'Calle 123, Ciudad A', 'juan.perez@gmail.com', 'password123', '555-1234'),
('Maria Gomez', 'Avenida 456, Ciudad B', 'maria.gomez@hotmail.com', 'securepass456', '555-5678'),
('Carlos Ramirez', 'Boulevard 789, Ciudad C', 'carlos.ramirez@yahoo.com', 'mypassword789', '555-9101');

-- Carga de datos para la tabla "orders"
INSERT INTO orders (order_date, state, client_id, total, shipping_date) VALUES 
(NOW() - INTERVAL '2 months', 'shipped', 1, 719.98, NOW() - INTERVAL '1 month'), 
(NOW() - INTERVAL '1 month', 'shipped', 2, 35.98, NOW() - INTERVAL '15 days'),  
(NOW() - INTERVAL '3 weeks', 'pending', 3, 49.99, NULL);

-- Carga de datos para la tabla "order_details"
INSERT INTO order_details (order_id, product_id, quantity, unit_price) VALUES 
(1, 1, 1, 699.99),
(1, 5, 1, 49.99), 
(2, 3, 2, 19.99);