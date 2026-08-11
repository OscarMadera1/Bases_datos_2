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

    @GetMapping("/")
    public String mostrarLogin() {
        return "auth_login"; 
    }

    @PostMapping("/auth/procesar")
    public String procesarLogin(@RequestParam("username") String username, 
                                @RequestParam("password") String password,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {
        
        // Buscamos al usuario en la base de datos
        Tercero usuarioLogueado = terceroService.autenticarUsuario(username, password);

        if (usuarioLogueado != null) {
            // Si existe, guardamos sus datos en la sesión para usarlos en toda la app
            session.setAttribute("usuario", usuarioLogueado);
            return "redirect:/portal"; 
        } else {
            // Si no existe o la clave está mal, lo devolvemos al login con un mensaje de error
            redirectAttributes.addFlashAttribute("error", "Documento o contraseña incorrectos.");
            return "redirect:/"; 
        }
    }

    @GetMapping("/portal")
    public String mostrarPortal(HttpSession session) {
        // Protección de ruta: Si no hay usuario en sesión, lo devolvemos al login
        if(session.getAttribute("usuario") == null) {
            return "redirect:/";
        }
        return "portal"; 
    }

    // Ruta para cerrar sesión
    @GetMapping("/logout")
    public String cerrarSesion(HttpSession session) {
        session.removeAttribute("usuario");
        session.invalidate(); // Destruye la sesión por seguridad
        return "redirect:/";
    }

    // Ruta del menú interno del Módulo de Datos (donde están tus tablas)
    @GetMapping("/index")
    public String mostrarMenuPrincipal() {
        return "index"; 
    }
}