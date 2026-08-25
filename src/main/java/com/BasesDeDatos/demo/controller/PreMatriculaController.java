package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.dto.DetallePreMatriculaDTO;
import com.BasesDeDatos.demo.dto.TercPensumDTO;
import com.BasesDeDatos.demo.model.Pensum;
import com.BasesDeDatos.demo.repository.PreMatriculaRepository;
import com.BasesDeDatos.demo.service.PensumService;
import com.BasesDeDatos.demo.service.ProgramaService;
import com.BasesDeDatos.demo.service.TerceroService;
import com.BasesDeDatos.demo.service.TercPensumService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.BasesDeDatos.demo.dto.MateriaFaltanteDTO;

import java.util.List;

@Controller
public class PreMatriculaController {

    @Autowired
    private PreMatriculaRepository preMatriculaRepository;
    
    @Autowired
    private ProgramaService programaService;

    @Autowired
    private TerceroService terceroService;

    @Autowired
    private PensumService pensumService;

    @Autowired
    private TercPensumService tercPensumService;

    // ==========================================
    // VISTAS (PANTALLAS)
    // ==========================================

    // 1. Pantalla de Asignar Pensum (El módulo anterior)
    @GetMapping("/asignarPensum")
    public String mostrarPantallaAsignarPensum(Model model) {
        model.addAttribute("programas", programaService.listarTodos());
        model.addAttribute("estudiantes", terceroService.listarTodosPorTipo("0"));
        return "asignar_pensum";
    }

    // 2. Pantalla de Pre-Matrícula (El nuevo módulo de Malla Curricular)
    @GetMapping("/prematricula")
    public String mostrarPantallaPreMatricula(Model model) {
        model.addAttribute("programas", programaService.listarTodos());
        model.addAttribute("estudiantes", terceroService.listarTodosPorTipo("0"));
        return "pre_matricula";
    }

    // ==========================================
    // API REST (Usadas por JavaScript / app.js)
    // ==========================================

    @PostMapping("/prematricula/generar")
    public String ejecutarProcesoPreMatricula(
            @RequestParam("programaId") Long programaId,
            @RequestParam("periodo") String periodo) {
        preMatriculaRepository.generarPreMatriculaBatch(programaId, periodo);
        return "redirect:/prematricula?exito=true"; 
    }

    @GetMapping("/prematricula/pensums/{programaId}")
    @ResponseBody
    public List<Pensum> obtenerPensumsPorPrograma(@PathVariable Long programaId) {
        return pensumService.listarPorPrograma(programaId);
    }

    @GetMapping("/prematricula/api/terc-pensum/{estudianteId}")
    @ResponseBody
    public List<TercPensumDTO> obtenerTercPensum(@PathVariable Long estudianteId) {
        return preMatriculaRepository.consultarTercPensum(estudianteId);
    }

    @GetMapping("/prematricula/api/detalle-asignaturas/{estudianteId}")
    @ResponseBody
    public List<DetallePreMatriculaDTO> obtenerDetalleAsignaturas(@PathVariable Long estudianteId) {
        return preMatriculaRepository.consultarDetalleAsignaturas(estudianteId);
    }

    @PostMapping("/prematricula/api/asignar-pensum")
    @ResponseBody
    public ResponseEntity<String> asignarPensum(
            @RequestParam("estudianteId") Long estudianteId,
            @RequestParam("pensumId") Long pensumId) {
        
        try {
            boolean exito = tercPensumService.registrarMatricula(estudianteId, pensumId);
            
            if (exito) {
                return ResponseEntity.ok("Asignación guardada correctamente");
            } else {
                return ResponseEntity.badRequest().body("El estudiante ya tiene este pensum asignado.");
            }
        } catch (Exception e) {
            e.printStackTrace(); 
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al ejecutar el procedimiento almacenado: " + e.getMessage());
        }
    }

    @GetMapping("/prematricula/api/materias-faltantes")
    @ResponseBody
    public List<MateriaFaltanteDTO> obtenerMateriasFaltantes(
            @RequestParam("pensumId") Long pensumId,
            @RequestParam("estudianteId") Long estudianteId) {
        return preMatriculaRepository.consultarMateriasFaltantes(pensumId, estudianteId);
    }
}