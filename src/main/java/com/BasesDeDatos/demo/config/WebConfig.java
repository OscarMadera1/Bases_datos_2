package com.BasesDeDatos.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/**") // Protege TODAS las rutas de tu proyecto (/programas, /asignaturas, etc.)
                .excludePathPatterns(
                        "/",                  // Deja libre la pantalla del login
                        "/auth/procesar",     // Deja libre el proceso de validar contraseña
                        "/style.css",         // Deja libre tu archivo de estilos
                        "/app.js",            // Deja libre tu JavaScript
                        "/error"              // Deja libre la página de errores internos
                );
    }
}