#  App Salud Activa - API REST

API RESTful stateless para la gestión de hábitos saludables, alimentación, perfiles nutricionales y seguimiento de salud. Desarrollada con **Java 17** y **Spring Boot 3**, aplicando estándares de arquitectura limpia, seguridad robusta con **Spring Security 6**, **JWT** y documentación interactiva con **Swagger UI**.

---

##  Tecnologías Utilizadas

* **Lenguaje:** Java 17
* **Framework Backend:** Spring Boot 3
* **Persistencia & Datos:** Spring Data JPA, Hibernate, MySQL
* **Seguridad & Autenticación:** Spring Security 6, Auth0 java-jwt (HMAC256), BCryptPasswordEncoder
* **Documentación:** Springdoc OpenAPI / Swagger UI
* **Herramientas:** Maven, Lombok, Jakarta Validation

---

##  Arquitectura de Seguridad y Características Clave

* **Autenticación Stateless (JWT):** Generación y validación de tokens JWT mediante `TokenService` con algoritmo HMAC256 y expiración configurable.
* **Control de Acceso Basado en Roles (RBAC):**
  * `ROLE_USER`: Permisos de lectura (`GET`) en catálogos.
  * `ROLE_ADMIN`: Permisos exclusivos para crear, actualizar y eliminar registros (`POST`, `PUT`, `DELETE`).
* **Filtros HTTP Personalizados:** Intercepción de peticiones entrantes mediante `SecurityFilter` (`OncePerRequestFilter`) para inyectar la autenticación en el `SecurityContextHolder`.
* **Manejo Unificado de Excepciones:** Respuestas JSON estandarizadas para errores de validación (`400`), autenticación (`401`), permisos (`403`) y recursos no encontrados (`404`) usando `@RestControllerAdvice`, `AuthenticationEntryPoint` y `AccessDeniedHandler`.

---

##  Variables de Entorno Requeridas

Para ejecutar esta aplicación localmente, es necesario definir las siguientes variables de entorno en el sistema o IDE:

| Variable | Descripción | Ejemplo |
| :--- | :--- | :--- |
| `DB_HOST` | Host de la base de datos | `localhost` |
| `DB_USER` | Usuario de MySQL | `root` |
| `DB_PASSWORD` | Contraseña de MySQL | `tu_contraseña` |
| `JWT_SECRET` | Clave secreta para firmar tokens JWT | `TuClaveSecretaSuperSegura` |
| `JWT_EXPIRATION_HOURS` | Duración del token en horas | `2` |

---

## Documentación de la API (Swagger UI)

Una vez ejecutada la aplicación, la documentación interactiva está disponible en:

 **`http://localhost:8081/swagger-ui.html`**

**Para probar endpoints protegidos desde Swagger:**
1. Registrar un usuario en `POST /usuarios`.
2. Iniciar sesión en `POST /login` y copiar el token generado.
3. Hacer clic en el botón **Authorize** (arriba a la derecha), pegar el token y autorizar.

---

## Autor

* **Anibal Melchor Cordero** - Desarrollador Backend Java