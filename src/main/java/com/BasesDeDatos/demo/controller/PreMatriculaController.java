package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.dto.DetallePreMatriculaDTO;
import com.BasesDeDatos.demo.dto.TercPensumDTO;
import com.BasesDeDatos.demo.model.Pensum; // Importa tu modelo Pensum
import com.BasesDeDatos.demo.repository.PreMatriculaRepository;
import com.BasesDeDatos.demo.service.PensumService;     // Inyectamos PensumService
import com.BasesDeDatos.demo.service.ProgramaService;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/prematricula")
public class PreMatriculaController {

    @Autowired
    private PreMatriculaRepository preMatriculaRepository;
    
    @Autowired
    private ProgramaService programaService;

    @Autowired
    private TerceroService terceroService;

    @Autowired
    private PensumService pensumService; // Inyección de tu servicio de pensums

   // Muestra la vista cargando solo los estudiantes (TERC_TIPO = '0')
    @GetMapping
    public String mostrarPantallaPreMatricula(Model model) {
        model.addAttribute("programas", programaService.listarTodos());
        // Filtramos usando tu método existente para traer únicamente a los estudiantes ('0')
        model.addAttribute("estudiantes", terceroService.listarTodosPorTipo("0"));
        return "pre_matricula";
    }

    // Proceso Batch de Pre-matrícula
    @PostMapping("/generar")
    public String ejecutarProcesoPreMatricula(
            @RequestParam("programaId") Long programaId,
            @RequestParam("periodo") String periodo) {
        preMatriculaRepository.generarPreMatriculaBatch(programaId, periodo);
        return "redirect:/prematricula?exito=true"; 
    }

    // Endpoint JSON real conectado a tu PensumService
    @GetMapping("/pensums/{programaId}")
    @ResponseBody
    public List<Pensum> obtenerPensumsPorPrograma(@PathVariable Long programaId) {
        return pensumService.listarPorPrograma(programaId);
    }

    // Endpoints JSON para el flujo paso a paso de la interfaz
    @GetMapping("/api/terc-pensum/{estudianteId}")
    @ResponseBody
    public List<TercPensumDTO> obtenerTercPensum(@PathVariable Long estudianteId) {
        return preMatriculaRepository.consultarTercPensum(estudianteId);
    }

    @GetMapping("/api/detalle-asignaturas/{estudianteId}")
    @ResponseBody
    public List<DetallePreMatriculaDTO> obtenerDetalleAsignaturas(@PathVariable Long estudianteId) {
        return preMatriculaRepository.consultarDetalleAsignaturas(estudianteId);
    }
}