# ERP-TechStore

## Descripción

ERP-TechStore es un sistema de planificación de recursos empresariales (ERP) desarrollado para la gestión integral de una tienda de tecnología. La aplicación permite administrar información relacionada con clientes, productos, ventas y procesos internos de la empresa, centralizando la información en una única plataforma.

El proyecto fue desarrollado utilizando Spring Boot y sigue una arquitectura basada en capas para facilitar la mantenibilidad, escalabilidad y organización del código.

---

## Objetivos del Proyecto

* Automatizar procesos administrativos de una tienda tecnológica.
* Centralizar la gestión de clientes y productos.
* Facilitar el registro y consulta de información empresarial.
* Mejorar la organización y control de los recursos de la empresa.
* Implementar una solución moderna basada en tecnologías Java.

---

## Tecnologías Utilizadas

### Backend

* Java
* Spring Boot
* Spring Data JPA
* Gradle
* Hibernate

### Base de Datos

* MySQL

### Herramientas de Desarrollo

* Visual Studio Code
* IntelliJ IDEA
* Git
* GitHub

---

## Arquitectura del Proyecto

El sistema está organizado siguiendo una arquitectura por capas:

### Model

Contiene las entidades del sistema que representan las tablas de la base de datos.

### Repository

Contiene las interfaces encargadas del acceso a datos mediante Spring Data JPA.

### Service

Implementa la lógica de negocio de la aplicación.

### Controller

Gestiona las solicitudes HTTP y la comunicación con el cliente.

---

## Funcionalidades Implementadas

### Gestión de Clientes

* Registro de clientes.
* Consulta de clientes.
* Actualización de información.
* Eliminación de registros.

### Gestión de Productos

* Administración de inventario.
* Registro de productos.
* Actualización de información de productos.
* Consulta de disponibilidad.

### Gestión Empresarial

* Organización de información comercial.
* Centralización de datos.
* Control de operaciones básicas del negocio.

---

## Estructura del Proyecto

```text
src
├── main
│   ├── java
│   │   └── uts.edu.java.proyecto
│   │       ├── controller
│   │       ├── model
│   │       ├── repository
│   │       └── service
│   └── resources
│       └── application.properties
└── test
```

## Requisitos

* Java 21 o superior
* Gradle
* MySQL Server
* Git

---

## Instalación

### 1. Clonar el repositorio

```bash
git clone https://github.com/Esneider-afk/ERP-TechStore.git
```

### 2. Ingresar al proyecto

```bash
cd ERP-TechStore
```

### 3. Configurar la base de datos

Editar el archivo:

```properties
src/main/resources/application.properties
```

Configurando:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/techstore
spring.datasource.username=usuario
spring.datasource.password=contraseña
```

### 4. Ejecutar el proyecto

Windows:

```bash
gradlew bootRun
```

Linux/Mac:

```bash
./gradlew bootRun
```

---

## Equipo de Desarrollo

Proyecto desarrollado como solución académica para la gestión empresarial de una tienda tecnológica utilizando Spring Boot y tecnologías Java modernas.

---

## Estado del Proyecto

En desarrollo.

Actualmente se encuentran implementados los módulos base para la gestión de información empresarial y se continúa trabajando en nuevas funcionalidades para ampliar las capacidades del sistema.
