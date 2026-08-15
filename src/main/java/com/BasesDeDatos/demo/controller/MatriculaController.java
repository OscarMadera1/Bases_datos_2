package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.Pensum;
import com.BasesDeDatos.demo.service.PensumService;
import com.BasesDeDatos.demo.service.ProgramaService;
import com.BasesDeDatos.demo.service.TercPensumService;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/matriculas")
public class MatriculaController {

    @Autowired
    private TercPensumService tercPensumService;

    @Autowired
    private TerceroService terceroService;

    @Autowired
    private PensumService pensumService;

    @Autowired
    private ProgramaService programaService;

    @GetMapping
    public String mostrarFormulario(Model model) {

        model.addAttribute("terceros", terceroService.listarTodos());
        model.addAttribute("programas", programaService.listarTodos());
        /*model.addAttribute("pensums", pensumService.listarTodos());*/

        return "matriculas";
    }

    @GetMapping("/pensums/{programaId}")
    @ResponseBody
    public List<Pensum> listarPensumsPorPrograma(
            @PathVariable Long programaId) {

        return pensumService.listarPorPrograma(programaId);
    }

    // Procesar la matrícula de primíparo
    @PostMapping("/guardar")
    public String registrarMatricula(
            @RequestParam("terceroId") Long terceroId,
            @RequestParam("pensumId") Long pensumId) {

        tercPensumService.registrarMatricula(terceroId, pensumId);

        return "redirect:/matriculas";
    }
}