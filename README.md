# Sistema de Login y Gestión de Productos - JavaFX + MySQL + BCrypt

Aplicación de escritorio desarrollada en **Java** con **JavaFX** y **MySQL** que implementa un sistema de autenticación seguro mediante **BCrypt** y control de acceso basado en roles (**ADMIN**, **VENDEDOR** y **CLIENTE**). El sistema incluye un módulo CRUD completo para la gestión de productos con permisos dinámicos según el rol del usuario.

---

## Características

* Registro de usuarios con contraseñas protegidas mediante BCrypt.
* Inicio de sesión con validación de credenciales utilizando hash.
* Control de acceso basado en roles (RBAC).
* CRUD completo de productos.
* Interfaz gráfica desarrollada con JavaFX y FXML.
* Conexión a MySQL mediante JDBC.
* Reporte de productos en consola.
* Cierre de sesión con retorno a la ventana de inicio de sesión.

---

## Roles del sistema

| Rol          | Permisos                                                                            |
| ------------ | ----------------------------------------------------------------------------------- |
| **ADMIN**    | Crear, modificar, eliminar, buscar, mostrar e imprimir reportes.                    |
| **VENDEDOR** | Crear, eliminar, buscar, mostrar e imprimir reportes. No puede modificar productos. |
| **CLIENTE**  | Solo puede buscar, visualizar productos e imprimir reportes.                        |

---

## Tecnologías utilizadas

* Java 25
* JavaFX 25
* MySQL
* JDBC
* jBCrypt
* Maven

---

## Requisitos

Antes de ejecutar el proyecto es necesario tener instalado:

* JDK 24 o superior
* MySQL Server 8.0 o superior
* Maven (recomendado)
* IntelliJ IDEA (o cualquier IDE compatible con JavaFX)

---

## Configuración de la base de datos

Crear una base de datos llamada:

```sql
control_productos
```

### Tabla `producto`

```sql
CREATE TABLE producto (
    id INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(20) NOT NULL UNIQUE,
    nombre VARCHAR(100) NOT NULL,
    precio DOUBLE NOT NULL
);
```

### Tabla `usuarios`

```sql
CREATE TABLE usuarios (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password_hash VARCHAR(100) NOT NULL,
    rol VARCHAR(20) NOT NULL
);
```



Las contraseñas corresponden a:

* admin → `admin123`
* cliente → `cliente123`
* vendedor → `vendedor123`

---

## Configuración del proyecto

Clonar el repositorio:

```bash
git clone https://github.com/usuario/repositorio.git
```

Ingresar al proyecto:

```bash
cd repositorio
```

Abrir el proyecto como **Maven Project** en IntelliJ IDEA.

Editar la clase `Conexion.java` y configurar las credenciales de MySQL:

```java
private static final String JDBC_URL = "jdbc:mysql://localhost:3306/control_productos";
private static final String JDBC_USER = "tu_usuario";
private static final String JDBC_PASSWORD = "tu_contraseña";
```

Modifica el puerto si tu servidor MySQL utiliza otro diferente.

---

## Ejecución

Desde IntelliJ IDEA ejecuta la clase:

```text
Launcher.java
```

O desde la terminal:

```bash
mvn clean compile
mvn javafx:run
```

---

## Estructura del proyecto

```text
src
└── main
    ├── java
    │   └── org
    │       └── epn
    │           └── crudproductosmelvasuarez
    │               ├── Application.java
    │               ├── Launcher.java
    │               ├── controlador
    │               │   ├── CatalogosProdController.java
    │               │   └── LoginController.java
    │               └── modelo
    │                   ├── Conexion.java
    │                   ├── Crud.java
    │                   ├── ImplCrud.java
    │                   ├── Producto.java
    │                   ├── Seguridad.java
    │                   ├── Usuario.java
    │                   └── UsuarioDAO.java
    └── resources
        ├── Catalogo_Productos.fxml
        └── Login.fxml
```

---

## Funcionalidades

* Autenticación segura mediante BCrypt.
* Registro de usuarios con contraseñas cifradas.
* Validación de credenciales utilizando `BCrypt.checkpw()`.
* Control de acceso por roles.
* CRUD completo utilizando `PreparedStatement`.
* Prevención de inyección SQL.
* Reporte de productos en formato tabular.

---



## Autor

**Melva Suárez**

Escuela Politécnica Nacional - ESFOT

Programación Orientada a Objetos

---

## Licencia

Este proyecto fue desarrollado con fines académicos y educativos. Puedes utilizarlo, modificarlo y adaptarlo para tus propios proyectos respetando la autoría correspondiente.
