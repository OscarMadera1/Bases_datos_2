package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.repository.PreMatriculaRepository;
import com.BasesDeDatos.demo.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/prematricula")
public class PreMatriculaController {

    @Autowired
    private PreMatriculaRepository preMatriculaRepository;
    
    @Autowired
    private ProgramaRepository programaRepository;

    // Muestra la vista y carga la lista real de programas para el selector
    @GetMapping
    public String mostrarPantallaPreMatricula(Model model) {
        model.addAttribute("programas", programaRepository.findAll());
        return "pre_matricula";
    }

    // Procesa el formulario y ejecuta el Procedimiento Almacenado por lotes
    @PostMapping("/generar")
    public String ejecutarProcesoPreMatricula(
            @RequestParam("programaId") Long programaId,
            @RequestParam("periodo") String periodo) {

        // Invoca el SP en Oracle pasándole el ID del programa y el periodo digitado
        preMatriculaRepository.generarPreMatriculaBatch(programaId, periodo);
        
        // Redirige de vuelta a la misma vista con una señal de éxito
        return "redirect:/prematricula?exito=true"; 
    }
}