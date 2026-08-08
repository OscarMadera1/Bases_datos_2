package com.BasesDeDatos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    // 1. La raíz carga el login de usuario y contraseña
    @GetMapping("/")
    public String mostrarLogin() {
        return "auth_login"; 
    }

    // 2. Procesa el login y redirige al portal
    @PostMapping("/auth/procesar")
    public String procesarLogin(@RequestParam("username") String username, @RequestParam("password") String password) {
        return "redirect:/portal"; 
    }

    // 3. El portal principal con los 3 botones (Datos, Procesos, Informes)
    @GetMapping("/portal")
    public String mostrarPortal() {
        return "portal"; 
    }

    // 4. El menú interno del Módulo de Datos
    @GetMapping("/index")
    public String mostrarMenuPrincipal() {
        return "index"; 
    }
}