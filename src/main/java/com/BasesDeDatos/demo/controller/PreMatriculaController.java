package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.dto.DetallePreMatriculaDTO;
import com.BasesDeDatos.demo.dto.TercPensumDTO;
import com.BasesDeDatos.demo.model.Pensum;
import com.BasesDeDatos.demo.repository.PreMatriculaRepository;
import com.BasesDeDatos.demo.service.PensumService;
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
    private PensumService pensumService;

    @GetMapping
    public String mostrarPantallaPreMatricula(Model model) {
        model.addAttribute("programas", programaService.listarTodos());
        model.addAttribute("estudiantes", terceroService.listarTodos());
        return "pre_matricula";
    }

    @PostMapping("/generar")
    public String ejecutarProcesoPreMatricula(
            @RequestParam("programaId") Long programaId,
            @RequestParam("periodo") String periodo) {
        preMatriculaRepository.generarPreMatriculaBatch(programaId, periodo);
        return "redirect:/prematricula?exito=true"; 
    }

    @GetMapping("/pensums/{programaId}")
    @ResponseBody
    public List<Pensum> obtenerPensumsPorPrograma(@PathVariable Long programaId) {
        return pensumService.listarPorPrograma(programaId);
    }

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