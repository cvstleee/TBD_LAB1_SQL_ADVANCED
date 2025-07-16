# TBD_Grupo3_Lab1

Laboratorio 1 de Taller de Bases de Datos desarrollado por el grupo 3.

# Integrantes
* [Aylin Castillo](https://github.com/cvstleee) (Desarrolladora Frontend)
* [Francisco Sanchez](https://github.com/Mellowzhong) (Desarrollador FullStack)
* [Alonso Sanhueza](https://github.com/AalSaa) (Desarrollador Backend)
* [Victor Varas](https://github.com/victorvaras) (Desarrollador Frontend)
* [Agustin Vera](https://github.com/Agustin-Vera) (Desarrollador Backend)

# Contenido
El repositorio incluye las siguientes carpetas principales:
* [Backend](https://github.com/Mellowzhong/TBD_Grupo3_Lab1/tree/main/backend)
* [Frontend](https://github.com/Mellowzhong/TBD_Grupo3_Lab1/tree/main/frontend)
* [Database](https://github.com/Mellowzhong/TBD_Grupo3_Lab1/tree/main/database)

# Tecnologías requeridas
* Postgres 16.5
* JDK v17.0.13
* Spring Boot (Gradle + Java 17) v3.3.4
* Sql2o v1.6.0
* Spring Security v6.2.3
* Java-jwt v4.4.0
* Node.js v20.18.0 (LTS)
* Vite v5.4.8
* Vue v3.5.10
* Axios v1.7.7
* Intellij IDEA Ultimate 2024.2

# Pre-instalación
Asegúrese de tener instalados Postgres 16, JDK 17, Node.js e IntelliJ IDEA. Se recomienda utilizar las versiones mencionadas en las tecnologías requeridas.

# Instrucciones de instalación
1. Clonar el repositorio. Puede hacerlo usando el siguiente comando:
   ```sh
   git clone https://github.com/Mellowzhong/TBD_Grupo3_Lab1
   ```
2. Ingresar a la carpeta raíz del repositorio clonado.
3. Ejecutar el script SQL:
  * Para Windows:
    - Copiar la ruta donde se encuentra el script, debería ser algo similar a esto:
      ```sh
      <ruta hacia el repositorio>/TBD_Grupo3_Lab1/database/migration.sql
      ```
    - Ir a la ubicación de la carpeta bin de PostgreSQL, comúnmente es la siguiente:
      ```sh
      C:\Program Files\PostgreSQL\16\bin
      ```
    - Abrir una terminal en esa ruta y ejecutar el siguiente comando:
      ```sh
      psql -U postgres -h localhost -p 5432 -f "<ruta hacia el repositorio>/TBD_Grupo3_Lab1/database/migration.sql"
      ```
      Donde:
        - Usuario: Nombre del usuario administrador de PostgreSQL.
        - Host: Dirección IP del servidor.
        - Puerto: Puerto configurado para el servidor.
  
  * Para linux:
    - Abrir una terminal en la ruta donde se encuentra el script tables.sql y ejecutar el siguiente comando:
      ```sh
      psql postgresql://<Usuario>:<Contraseña>@<Host>:<Puerto>/<Base de datos> -f "migration.sql"
      ```
      Donde:
        - Usuario: Nombre del usuario administrador de PostgreSQL.
        - Contraseña: Contraseña del usuario administrador de PostgreSQL.
        - Host: Dirección IP del servidor.
        - Puerto: Puerto configurado para el servidor.
        - Base de datos: Nombre de la base de datos principal, generalmente se llama postgres.
4. Repetir el paso 3 para los scripts procedures.sql, triggers.sql y dump-data.sql
5. Abrir IntelliJ IDEA y seleccionar la opción "Abrir proyecto". Buscar la dirección del repositorio y seleccionar la carpeta backend.
6. Buscar el archivo BackendApplication.java e iniciarlo.
7. Abrir una terminal en la carpeta frontend y ejecutar los siguientes comandos:
  ```sh
  npm install
  ```
  Luego:
  ```sh
  npm run dev
  ```
8. Ir a su navegador web e ingresar la URL http://localhost:3000/
   
Con todos estos pasos, ya puede utilizar la aplicación web.
