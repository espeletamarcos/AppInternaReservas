# Sistema de Gestión de Recursos y Reservas – Full Stack

Proyecto personal full-stack desarrollado con el objetivo de afianzar conceptos de desarrollo backend y frontend orientados a entornos empresariales.

La aplicación simula un sistema interno de **gestión de recursos y reservas**, exponiendo una **API REST desarrollada con Spring Boot** y consumida desde un **frontend en Angular**.

El foco del proyecto no es la complejidad funcional, sino la correcta estructuración del código, el uso de buenas prácticas y la separación clara de responsabilidades entre capas.

---

## 🧱 Arquitectura

El backend sigue una arquitectura por capas claramente definida:

- **Controller**: exposición de endpoints REST
- **Service**: lógica de negocio
- **Repository**: acceso a datos mediante Spring Data JPA
- **DTOs**: desacoplamiento entre el dominio y la API

Las entidades no se exponen directamente al exterior.  
Se utilizan DTOs de **request** y **response** para mantener una API limpia, segura y mantenible.

---

## 🔧 Tecnologías utilizadas

### Backend
- Java 17
- Spring Boot
- Spring Web
- Spring Data JPA
- Hibernate
- Validation API
- Base de datos PostgreSQL

### Frontend
- Angular
- TypeScript
- HTML / CSS
- Consumo de API REST

---

## 🚀 Funcionalidades principales

- CRUD de recursos
- API REST siguiendo convenciones HTTP
- Validaciones de entrada de datos
- Manejo centralizado de errores
- Comunicación frontend-backend mediante HTTP

---

## ▶️ Ejecución del proyecto

### Backend
- Ejecutar la aplicación Spring Boot desde el IDE o mediante Maven

### Frontend
```bash
npm install
ng serve
