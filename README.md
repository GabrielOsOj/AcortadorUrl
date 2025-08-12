# Acortador de urls

# **Acortador de URLs**

Servicio web que permite reducir el largo de las URLs y obtener métricas de uso. Desarrollado con Java y Spring.

## Objetivo

Este proyecto fue creado para practicar la arquitectura REST, el manejo de datos mediante JPA, el manejo de redirecciones y la implementación de seguridad mediante tokens de usuario.

Es un proyecto funcional inspirado en el acortador de URLs de [roadmap.sh](https://roadmap.sh/projects/url-shortening-service), pero con mejoras tanto en funcionalidad como en seguridad.

## Características principales

- Permite la creación y gestión de enlaces cortos a partir de una URL mas larga.
- Endpoint especifico para redirección automática desde el servidor.
- Permite llevar un recuento de accesos a un determinado endpoint.
- Validación de URLs y manejo de errores.
- Rate limiting con Bucket4j para evitar abusos en los endpoints.
- Gestión de URLs sin inicio de sesión, mediante tokens de usuario (OwnerId).
- Implementación de tests unitarios y de integración en todas las capas del proyecto.

## Instalación y despliegue

### Es necesario tener docker instalado en la pc o servidor donde se vaya a desplegar.

1. Creamos un archivo de texto, dentro pegamos el siguiente codigo:

```yaml
version: "3.9"
services:
  url_shortener_backend:
    image: gbdev001/url_shortener_backend
    container_name: url_shortener_backend
    ports:
      - "8080:8080"
    environment:
      - DB_URL=jdbc:mysql://ShortenerBdd:3306/ShortenBdd
      - DB_USER=nombreUsuario
      - DB_PASSWORD=ContraseñaUsuario
    depends_on:
      ShortenBdd:
        condition: service_healthy
  
  ShortenBdd:
    image: mysql:9.2.0
    container_name: ShortenerBdd
    ports:
      - "3300:3306"
    environment:
      - MYSQL_ROOT_PASSWORD=Contraseña Usuario (idem DB_PASSWORD)
      - MYSQL_ROOT_USER=Nombre usuario (idem DB_USER)
      - MYSQL_DATABASE=ShortenBdd
    healthcheck:
      test: ["CMD", "mysqladmin","ping","-h","localhost"]
      timeout: 10s
      retries: 10
```

- Los nombres en las variables de usuario y contraseña deben ser los mismos en ambos servicios.
- Las peticiones se van a realizar en el puerto 8080.

1. renombramos el archivo de texto recien creado a docker-compose.yml
2. Abrimos una nueva terminal y ejecutamos:

```bash
docker compose up
```

### Problema con coors

Para permitir que un sitio web realice peticiones y no haya problema de coors, el contenedor tiene una variable de entorno para agregar la cantidad de sitios que sean necesarios.

```yaml
- ALLOWED_CORS=http://urlNumeroUno http://urlNumeroDos
```

## Descripción de Endpoints

La URL base es:

```bash
localhost:8080/urlShortener
```

### Acortar URL

Para acortar una nueva URL se utiliza el Endpoint:

```
POST /urlShortener/shorten
```

Espera un JSON con la siguiente estructura:

```json
{
    "url":"https://tuUrlParaAcortar",
    "ownerId":""
}
```

Donde:

- Si la URL es valida, responde con un 202.
- Si no es valida, responde un 400.
- Si el OwnerId no existe en la base de datos, se genera uno nuevo y se lo retorna.

Ejemplo de respuesta:

```json
{
    "url": "https://tuUrlParaAcortar",
    "shortCode": "RvitQiwgvu",
    "createdAt": "2025-08-10T17:26:57Z",
    "updatedAt": "2025-08-10T17:26:57Z",
    "ownerId": "c8e63ac6-e5ed-4299-ab8d-20504d40ad3a"
}
```

Se puede enviar un OwnerId existente para asociar la URL al mismo usuario:

```json

{
    "url":"https://tuUrlParaAcortar",
    "ownerId":"c8e63ac6-e5ed-4299-ab8d-20504d40ad3a"
}
```

NOTA: el OwnerId debe existir en la base de datos antes de enlazarlo, NO se puede enviar un nuevo OwnerId, ya que será ignorado.

### Obtener URL

```
GET /urlShortener/UrlAcortada
```

- Esto redirecciona automáticamente a la URL asociada.
- Si no existe, retorna un 404.

### Editar URL

Petición PUT, usando:

```
PUT /urlShortener/UrlAcortada
```

Espera un JSON con la siguiente estructura:

```json
{
    "url":"https://nuevaUrl",
    "ownerId":"fac7b86e-d332-4add-a07d-bee5a4407152"
}
```

- Si todo es correcto, la URL se edita y el servidor retorna un 200.
- Si la URL no comienza con https:// o http://, el servidor responde con un 400
- Si el OwnerId no esta asociado a esa URL, retorna 401.

### Eliminar URL

```
DELETE /urlShortener/ownerId/UrlAcortada
```

- Si el OwnerId y la URL son validas, se elimina toda información de la URL y el servidor retorna un 204.
- Si el OwnerId no esta asociado a esa URL, retorna 401.
- Si la URL no existe, se retorna un 404.

### Estadísticas de una URL

```
GET /urlShortener/UrlAcortada/stats
```

Se pueden ver las distintas estadísticas de una URL, como el recuento de veces que se visito, fecha de edición, etc.

- Si la Petición es valida, el servidor responde un JSON con los datos de la URL.
- Si la URL no existe, retorna 404.

Ejemplo de respuesta:

```json
{
    "url": "https://www.youtube.com",
    "shortCode": "vMmgKiNzhX",
    "createdAt": "2025-08-10T16:05:03Z",
    "updatedAt": "2025-08-10T19:09:50Z",
    "accesCount": 2,
    "ownerId": "fac7b86e-d332-4add-a07d-bee5a4407152"
}
```

### Obtener todas las URLs de un usuario

```
POST /urlShortener/getAll
```

Espera un JSON con la siguiente estructura:

```json
{
    "ownerId":"fac7b86e-d332-4add-a07d-bee5a4407152"
}
```

- Se obtiene un listado con todas las URLs asociadas al usuario.
- Si el usuario no tiene URLs asociadas, se retorna un arreglo vacío.
- Si se envía un OwnerId vacío, retorna 400.

Ejemplo de una respuesta valida:

```json
[
    {
        "url": "https://youtube.com",
        "shortCode": "vMmgKiNzhX",
        "createdAt": "2025-08-10T16:05:03Z",
        "updatedAt": "2025-08-10T19:09:50Z",
        "accesCount": 2,
        "ownerId": "fac7b86e-d332-4add-a07d-bee5a4407152"
    },
    {
        "url": "https://tuUrlParaAcortar",
        "shortCode": "NKHJBWCgvc",
        "createdAt": "2025-08-10T20:51:28Z",
        "updatedAt": "2025-08-10T20:51:28Z",
        "accesCount": 0,
        "ownerId": "fac7b86e-d332-4add-a07d-bee5a4407152"
    }
]
```

## Arquitectura y tecnologías

Es un proyecto orientado al Backend, cuenta con un Frontend básico para pruebas. 

### Backend

- Lenguaje: Java.
- Framework: Spring
    - Spring Boot para la creacion del proyecto.
    - Spring Web para el manejo de enpoints.
    - Spring Data JPA para la gestion de datos.
- Base de datos MySQL
- Tests unitarios con JUnit y Mockito.
- Tests de integración con MockMvc.

### Frontend

- Lógica con JavaScript
- HTML y CSS para estructura y estilos

### Deploy

- Docker para despliegue local y en servidor VPS.