# Runasimi Edu - Backend

API REST desarrollada con Spring Boot para la aplicación educativa de aprendizaje del idioma quechua.

## 🚀 Tecnologías

- **Spring Boot 3.5.3** - Framework principal
- **Spring Security** - Autenticación y autorización
- **Spring Data JPA** - ORM
- **MariaDB** - Base de datos
- **JWT** - Tokens de autenticación
- **Lombok** - Reducción de código boilerplate
- **ModelMapper** - Mapeo de DTOs

## 📦 Requisitos

- Java 21
- Maven 3.6+
- MariaDB 10.6+

## 🛠️ Instalación Local

```bash
# Clonar el repositorio
git clone https://github.com/riusakigod-kun/quechua-backend.git
cd quechua-backend

# Instalar dependencias
mvn clean install

# Ejecutar la aplicación
mvn spring-boot:run
```

## 🌐 Despliegue

### Railway (Recomendado)
1. Conectar repositorio a Railway
2. Configurar variables de entorno
3. Railway detecta automáticamente el `pom.xml`

### Render
1. Conectar repositorio a Render
2. Build Command: `mvn clean package`
3. Start Command: `java -jar target/backend-0.0.1-SNAPSHOT.jar`

### Heroku
```bash
# Crear aplicación
heroku create quechua-backend

# Configurar buildpack
heroku buildpacks:set heroku/java

# Deploy
git push heroku main
```

## 🔧 Variables de Entorno

```env
# Base de datos
SPRING_DATASOURCE_URL=jdbc:mariadb://localhost:3306/runasimi_edu
SPRING_DATASOURCE_USERNAME=tu_usuario
SPRING_DATASOURCE_PASSWORD=tu_password

# JWT
JWT_SECRET=tu_clave_secreta_muy_larga_y_segura
JWT_EXPIRATION=86400000

# CORS (URL del frontend)
CORS_ALLOWED_ORIGINS=https://tu-frontend.vercel.app

# Puerto (Railway/Render lo configuran automáticamente)
PORT=8080
```

## 📁 Estructura del Proyecto

```
src/main/java/com/runasimi_edu/backend/
├── config/          # Configuraciones (Security, CORS, etc.)
├── controller/      # Controladores REST
├── dto/            # Data Transfer Objects
├── entity/         # Entidades JPA
├── repository/     # Repositorios
├── service/        # Lógica de negocio
├── exception/      # Manejo de excepciones
└── util/           # Utilidades
```

## 🔗 Frontend

El frontend está en un repositorio separado: [quechua-frontend](https://github.com/riusakigod-kun/quechua-frontend)

## 🎯 Endpoints Principales

```
POST   /api/auth/login          # Autenticación
POST   /api/auth/register       # Registro
GET    /api/actividades         # Listar actividades
POST   /api/actividades         # Crear actividad
GET    /api/estudiantes         # Listar estudiantes
PUT    /api/estudiantes/{id}    # Actualizar estudiante
```

## 🐳 Docker (Opcional)

```dockerfile
FROM openjdk:21-jre-slim
COPY target/backend-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app.jar"]
```

## 📊 Base de Datos

### Esquema Principal:
- **usuarios** (estudiantes, docentes)
- **actividades** (ejercicios gamificados)
- **resultados** (progreso del estudiante)
- **categorias** (niveles y tipos de actividad)

## 🔒 Seguridad

- Autenticación JWT
- CORS configurado para el frontend
- Validación de entrada
- Rate limiting (recomendado para producción)

## 📝 Logs

```bash
# Ver logs en Railway
railway logs

# Ver logs en Render
# Disponibles en el dashboard

# Ver logs en Heroku
heroku logs --tail -a quechua-backend
```
