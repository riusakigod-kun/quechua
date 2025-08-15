# Runasimi Edu - Frontend

Aplicación web educativa interactiva y gamificada para el aprendizaje temprano del idioma quechua, orientada a estudiantes de secundaria.

## 🚀 Tecnologías

- **React Native Web** - Para desarrollo multiplataforma
- **React Navigation** - Navegación
- **Axios** - Cliente HTTP
- **Webpack** - Bundler
- **React Router DOM** - Enrutamiento web

## 📦 Instalación

```bash
# Clonar el repositorio
git clone https://github.com/riusakigod-kun/INTERFAZ.git
cd INTERFAZ

# Instalar dependencias
npm install

# Ejecutar en desarrollo
npm run web

# Build para producción
npm run build:web
```

## 🌐 Despliegue en Vercel

Este proyecto está configurado para desplegarse automáticamente en Vercel:

- **Framework**: Other
- **Build Command**: `npm run build:web`
- **Output Directory**: `dist`

## 🔗 Backend

El backend está en un repositorio separado: [quechua-backend](https://github.com/riusakigod-kun/quechua-backend)

## 📁 Estructura del Proyecto

```
src/
├── components/     # Componentes reutilizables
├── screens/        # Pantallas de la aplicación
├── navigation/     # Configuración de navegación
├── context/        # Context API
├── hooks/          # Custom hooks
├── services/       # APIs y servicios
└── assets/         # Recursos estáticos
```

## 🎮 Funcionalidades

- Dashboard para estudiantes y docentes
- Actividades gamificadas
- Sistema de progreso
- Perfil de usuario
- Juegos por niveles

## 🔧 Variables de Entorno

Crea un archivo `.env` con:

```env
REACT_APP_API_URL=https://tu-backend-url.com/api
```
