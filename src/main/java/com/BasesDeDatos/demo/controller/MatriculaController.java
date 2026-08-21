package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.model.HistoriaEstudiante;
import com.BasesDeDatos.demo.model.HistoriaEstudianteProjection;
import com.BasesDeDatos.demo.model.Pensum;
import com.BasesDeDatos.demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
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

    @Autowired
    private HistoriaEstudianteService historiaEstudianteService;

    @GetMapping
    public String mostrarFormulario(Model model) {

        model.addAttribute("terceros", terceroService.listarTodosPorTipo("0"));
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

    @PostMapping("/guardar")
    public String registrarMatricula(
            @RequestParam("terceroId") Long terceroId,
            @RequestParam("pensumId") Long pensumId,
            Model model) {

        tercPensumService.registrarMatricula(terceroId, pensumId);

        model.addAttribute(
                "terceros",
                terceroService.listarTodosPorTipo("0")
        );

        model.addAttribute(
                "programas",
                programaService.listarTodos()
        );

        model.addAttribute(
                "matriculas",
                historiaEstudianteService.listarPorEstudiante(terceroId)
        );

        List<HistoriaEstudiante> matriculas =
                historiaEstudianteService.listarPorEstudiante(terceroId);

        return "matriculas";
    }

    @Autowired
    private HistoriaService historiaService; // Inyéctalo si no lo tienes

    // NUEVO ENDPOINT PARA CONSULTAR EL PROMEDIO VÍA AJAX (FETCH)
    @GetMapping("/promedio/{terceroId}")
    @ResponseBody
    public BigDecimal obtenerPromedioEstudiante(@PathVariable Long terceroId) {
        return historiaService.calcularPromedioEstudiante(terceroId);
    }

}