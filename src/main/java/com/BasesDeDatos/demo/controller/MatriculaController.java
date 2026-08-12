package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.service.ProgramaService;
import com.BasesDeDatos.demo.service.TercPensumService;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private TercPensumService tercPensumService;

    @Autowired
    private TerceroService terceroService;

    @GetMapping
    public String mostrarFormulario (Model model){
        model.addAttribute("terceros", terceroService.listarTodos());
        return "/matriculas";
    }

    //Procesar la matricula primiparo
    @PostMapping("/guardar")
    public String registrarMatricula(
        @RequestParam("terceroId") Long terceroId,
        @RequestParam("pesumId") long pensumId){
            tercPensumService.registrarMatricula(terceroId, terceroId);
            return "redirect:/matriculas";
        }
    
}
