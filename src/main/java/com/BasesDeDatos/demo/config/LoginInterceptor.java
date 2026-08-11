package com.BasesDeDatos.demo.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        
        // Si no hay un usuario en la sesión, lo pateamos de vuelta a la raíz (el login)
        if (session.getAttribute("usuario") == null) {
            response.sendRedirect("/");
            return false; // Detiene la petición aquí mismo
        }
        
        return true; // Si tiene sesión, lo deja pasar a la vista que solicitó
    }
}