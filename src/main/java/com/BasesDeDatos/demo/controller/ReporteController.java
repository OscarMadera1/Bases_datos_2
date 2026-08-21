package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.dto.PreMatriculaDTO;
import com.BasesDeDatos.demo.repository.PreMatriculaRepository;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/informes")
public class ReporteController {

    @Autowired
    private PreMatriculaRepository preMatriculaRepository;

    // Inyectamos tu TerceroService en lugar del Repositorio directamente
    @Autowired
    private TerceroService terceroService;

    @GetMapping
    public String mostrarModuloInformes(
            @RequestParam(name = "tipo", defaultValue = "prematricula") String tipoReporte,
            @RequestParam(name = "estudianteId", required = false) Long estudianteId,
            Model model) {

        // 1. Enviamos el tipo de reporte a la vista para que el menú lateral sepa cuál
        // marcar
        model.addAttribute("tipoReporte", tipoReporte);

        // 2. Lógica exclusiva para el reporte de Pre-matrícula
        if ("prematricula".equals(tipoReporte)) {

            // Llenamos el <select> usando tu servicio y pasándole "0" como String
            model.addAttribute("estudiantes", terceroService.listarTodosPorTipo("0"));

            // Si el usuario seleccionó un estudiante y le dio al botón "Consultar"
            if (estudianteId != null) {
                // Trae las asignaturas pre-matriculadas desde la BD
                List<PreMatriculaDTO> materias = preMatriculaRepository
                        .consultarPreMatriculaPorEstudiante(estudianteId);

                // Pasamos los datos a la tabla HTML
                model.addAttribute("materias", materias);
                model.addAttribute("estudianteSeleccionado", estudianteId);
            }
        }

        if ("auditoria".equals(tipoReporte)) {
            model.addAttribute("auditorias", preMatriculaRepository.consultarAuditorias());
        }

        // 3. Renderizamos la plantilla informes.html
        return "informes";
    }
}