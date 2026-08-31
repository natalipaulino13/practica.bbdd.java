# Biblioteca — Java (JDBC + SQLite)

Práctica del módulo Programación (DAW). Gestión de una biblioteca mediante una base de datos SQLite utilizando JDBC. El programa permite realizar operaciones CRUD sobre los libros, realizar búsquedas y ordenar los registros.

## Enunciado

El programa gestiona una biblioteca almacenando los libros en una base de datos **SQLite**.

Cada libro contiene:

* ID
* Título
* Autor
* Año de publicación
* Género
* Disponibilidad

La aplicación dispone de un menú desde el que se pueden añadir, consultar, modificar y eliminar libros, además de realizar diferentes tipos de búsquedas y ordenar los resultados.

## Base de datos

La aplicación utiliza una base de datos SQLite llamada:

```text
biblioteca.db
```

La tabla utilizada es `libros`:

```text
libros
├── id
├── titulo
├── autor
├── anio
├── genero
└── disponible
```

La tabla se crea automáticamente al iniciar el programa si todavía no existe.

## Main

Clase principal del programa.

Establece la conexión con SQLite mediante JDBC y muestra un menú con diferentes opciones:

```text
---MENÚ---
1.-Añadir libro
2.-Listar todos los libros
3.-Buscar libro
4.-Actualizar libro
5.-Eliminar libro
6.-Listar libros disponibles
7.-Búsqueda avanzada
8.-Listar libros ordenados
0.-Salir
```

## Funcionalidades

### Añadir libro

Permite introducir:

* Título
* Autor
* Año de publicación
* Género
* Disponibilidad

Antes de añadirlo se comprueba que no exista otro libro con el mismo título, autor y año.

También se valida que el año esté entre **1500 y 2026** y que la disponibilidad sea `1` o `0`.

### Listar libros

Muestra todos los libros almacenados en la base de datos junto con su información y disponibilidad.

### Buscar libro

Permite buscar un libro mediante:

* ID
* Título
* Autor

Las consultas se realizan mediante `PreparedStatement`.

### Actualizar libro

Permite modificar los datos de un libro buscando previamente su ID.

Si un campo se deja vacío, se mantiene el valor anterior.

### Eliminar libro

Permite eliminar un libro mediante su ID y solicita confirmación antes de realizar el borrado.

### Listar libros disponibles

Muestra únicamente los libros cuyo campo `disponible` tiene el valor `1`.

### Búsqueda avanzada

Permite combinar varios filtros:

* Autor
* Género
* Disponibilidad

La consulta utiliza parámetros mediante `PreparedStatement`.

### Listar libros ordenados

Permite ordenar los libros por:

1. Título
2. Autor
3. Año de publicación

## Ejemplo de ejecución

```text
Conexión establecida correctamente

---MENÚ---
1.-Añadir libro
2.-Listar todos los libros
3.-Buscar libro
4.-Actualizar libro
5.-Eliminar libro
6.-Listar libros disponibles
7.-Búsqueda avanzada
8.-Listar libros ordenados
0.-Salir

Elige una opción: 1

Introduce el titulo:
El Quijote

Introduce el autor:
Miguel de Cervantes

Introduce el año de publicación:
1605

Introduce el género:
Novela

Disponibilidad (1 = disponible, 0 = no disponible):
1

Libro El Quijote añadido
```

Ejemplo de listado:

```text
1 | El Quijote | Miguel de Cervantes | 1605 | Novela | Disponible
2 | Cien años de soledad | Gabriel García Márquez | 1967 | Novela | No disponible
```

## Conceptos utilizados

* Java
* Programación Orientada a Objetos (POO)
* JDBC
* SQLite
* Bases de datos
* SQL
* `Connection`
* `DriverManager`
* `Statement`
* `PreparedStatement`
* `ResultSet`
* Operaciones CRUD
* `INSERT`
* `SELECT`
* `UPDATE`
* `DELETE`
* Consultas parametrizadas
* `ORDER BY`
* `LIKE`
* `Scanner`
* Excepciones `SQLException`
* Menús mediante `switch`
* Validación de datos

## Operaciones CRUD

| Operación  | SQL utilizado |
| ---------- | ------------- |
| Crear      | `INSERT`      |
| Leer       | `SELECT`      |
| Actualizar | `UPDATE`      |
| Eliminar   | `DELETE`      |

## Autor

[Nataly Github](https://github.com/natalipaulino13) — DAW 2025/2026
