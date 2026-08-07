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

    @GetMapping
    public String listarAsignaturas(Model model){
        model.addAttribute("asignaturas", asignaturaService.listarTodas());
        // CAMBIO AQUÍ: Le damos el apodo "asigModel"
        model.addAttribute("asigModel", new Asignatura()); 
        return "asignaturas";
    }

    @PostMapping("/guardar")
    // CAMBIO AQUÍ: Usamos "asigModel"
    public String guardarAsignatura(@ModelAttribute("asigModel") Asignatura asignatura){
        asignaturaService.guardarAsignatura(asignatura);
        return "redirect:/asignaturas";
    }

    @PostMapping("/actualizar")
    // CAMBIO AQUÍ: Usamos "asigModel"
    public String actualizarAsignatura(@ModelAttribute("asigModel") Asignatura asignatura){
        asignaturaService.actualizarAsignatura(asignatura);
        return "redirect:/asignaturas";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarAsignatura(@PathVariable("id") Long id){
        asignaturaService.eliminarAsignatura(id);
        return "redirect:/asignaturas";
    }
}