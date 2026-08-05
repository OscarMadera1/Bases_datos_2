package com.BasesDeDatos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/pagina-terceros")
    public String terceros() {
        return "terceros"; // Busca el archivo terceros.html
    }

    @GetMapping("/pagina-programas")
    public String programas() {
        return "programas"; // Busca el archivo programas.html
    }

    @GetMapping("/pagina-asignaturas")
    public String asignaturas() {
        return "asignaturas"; // Busca el archivo asignaturas.html
    }
}