package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.Programa;
import com.BasesDeDatos.demo.service.ProgramaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/programas")
public class ProgramaController {

    @Autowired
    private ProgramaService programaService;

    //Listar programas
    @GetMapping
    public String listarProgramas(Model model){
        model.addAttribute("programas", programaService.listarTodos());
        model.addAttribute("programaObj", new Programa());  // Nombre diferente para el formulario del modal
        return "programas";
    }

    //Guardar programas
    @PostMapping("/guardar")
    public String guardarPrograma(@ModelAttribute("programa") Programa programa){
        programaService.guardarPrograma(programa);
        return "redirect:/programas";
    }

    //Actualizar programa
    @PostMapping("/actualizar")
    public String actualizarPrograma(@ModelAttribute("programa") Programa programa){
        programaService.actualizarPrograma(programa);
        return "redirect:/programas";
    }

    //Eliminar programa
    @GetMapping("/eliminar/{id}")
    public String eliminarPrograma(@PathVariable("id") Long id){
        programaService.eliminarPrograma(id);
        return "redirect:/programas";
    }
    
}
