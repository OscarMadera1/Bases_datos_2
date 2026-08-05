package com.BasesDeDatos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VistaController {

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/pagina-terceros")
    public String terceros() {
        return "terceros";
    }

    @GetMapping("/pagina-programas")
    public String programas() {
        return "programas";
    }

    @GetMapping("/pagina-asignaturas")
    public String asignaturas() {
        return "asignaturas";
    }
}