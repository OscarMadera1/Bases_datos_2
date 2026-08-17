package com.BasesDeDatos.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/portalMatricula")
public class PortalMatricula {

    @GetMapping
    public String mostrarPortal() {
        return "portal_Matricula";
    }
}
