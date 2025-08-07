package com.runasimi_edu.backend.config;



import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns(
                    // Desarrollo local
                    "http://localhost:8088",
                    "http://localhost:3000",
                    "http://127.0.0.1:8088", 
                    "http://127.0.0.1:3000",
                    // Producción Railway
                    "https://*.up.railway.app",
                    "https://*.railway.app",
                    // Otros servicios comunes
                    "https://*.vercel.app",
                    "https://*.netlify.app"
                )
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(3600); // Cache preflight por 1 hora
    }
}

