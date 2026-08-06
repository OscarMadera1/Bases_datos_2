package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.Asignatura;
import com.BasesDeDatos.demo.service.AsignaturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/asignaturas")
public class AsignaturaController {

    @Autowired
    private AsignaturaService asignaturaService;

    //Listar Asignaturas
    @GetMapping
    public String listarAsignaturas(Model model){
        model.addAttribute("asignaturas", asignaturaService.listarTodas());
        model.addAttribute("asignaturaObj", new Asignatura()); // Cambiado de "asignatura" a "asignaturaObj"
        return "asignaturas";
    }

    //Guardar asignatura
    @PostMapping("/guardar")
    public String guardarAsignatura(@ModelAttribute("asignatura") Asignatura asignatura){
        asignaturaService.guardarAsignatura(asignatura);
        return "redirect:/asignaturas";
    }

    //Actualizar asignatura
    @PostMapping("/actualizar")
    public String actualizarAsignatura(@ModelAttribute("asignatura") Asignatura asignatura){
        asignaturaService.actualizarAsignatura(asignatura);
        return "redirect:/asignaturas";
    }

    //Eliminar asignatura
    @GetMapping("/eliminar/{id}")
    public String eliminarAsignatura(@PathVariable("id")Long id){
        asignaturaService.eliminarAsignatura(id);
        return "redirect:/asignaturas";
    }

    
}
