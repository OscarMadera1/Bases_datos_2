package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.Tercero;
import com.BasesDeDatos.demo.service.TerceroService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class LoginController {

    @Autowired
    private TerceroService terceroService;

    // 1. Mostrar la vista del Login
    @GetMapping("/")
    public String mostrarLogin() {
        return "auth_login"; 
    }

    // 2. Procesar las credenciales
    @PostMapping("/auth/procesar")
    public String procesarLogin(@RequestParam("username") String username, 
                                @RequestParam("password") String password,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        
        // Buscamos al usuario en la base de datos
        Tercero usuarioLogueado = terceroService.autenticarUsuario(username, password);

        // AQUÍ ESTÁ LA LÓGICA QUE FALTABA EN TU CÓDIGO
        if (usuarioLogueado != null) {
            // Si el usuario existe, se guarda en la sesión y entra al portal
            session.setAttribute("usuario", usuarioLogueado);
            return "redirect:/portal"; 
        } else {
            // Si no existe, se devuelve al inicio con error
            redirectAttributes.addFlashAttribute("error", "Documento o contraseña incorrectos.");
            return "redirect:/"; 
        }
    }

    // 3. Vista del Portal (Ya no necesita validación manual gracias al Interceptor)
    @GetMapping("/portal")
    public String mostrarPortal() {
        return "portal"; 
    }

    // 4. Vista del menú interno (Módulo de Datos)
    @GetMapping("/index")
    public String mostrarMenuPrincipal() {
        return "index"; 
    }

    // 5. Ruta para cerrar sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.removeAttribute("usuario");
        session.invalidate(); // Destruye la sesión por seguridad
        return "redirect:/";
    }
}