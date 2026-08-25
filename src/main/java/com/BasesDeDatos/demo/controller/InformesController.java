package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.dto.AuditoriaDTO;
import com.BasesDeDatos.demo.dto.MateriaFaltanteDTO;
import com.BasesDeDatos.demo.dto.PromedioDTO;
import com.BasesDeDatos.demo.dto.TercPensumDTO;
import com.BasesDeDatos.demo.repository.PreMatriculaRepository;
import com.BasesDeDatos.demo.service.TerceroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class InformesController {

    @Autowired
    private PreMatriculaRepository preMatriculaRepository;
    
    @Autowired
    private TerceroService terceroService;

    // =========================================================================
    // 1. Mostrar la tabla general de Informes
    // =========================================================================
    @GetMapping("/informes")
    public String mostrarInformes(
            @RequestParam(name = "tipo", required = false, defaultValue = "prematricula") String tipo,
            @RequestParam(name = "estudianteId", required = false) Long estudianteId,
            Model model) {
        
        model.addAttribute("tipoReporte", tipo);
        model.addAttribute("estudiantes", terceroService.listarTodosPorTipo("0"));

        if ("promedios".equals(tipo)) {
            List<PromedioDTO> listaPromedios = preMatriculaRepository.consultarVistaPromedios();
            model.addAttribute("promedios", listaPromedios);
        } 
        else if ("auditoria".equals(tipo)) {
            List<AuditoriaDTO> auditorias = preMatriculaRepository.consultarAuditorias();
            model.addAttribute("auditorias", auditorias);
        }
        else if ("prematricula".equals(tipo) && estudianteId != null) {
            model.addAttribute("estudianteSeleccionado", estudianteId);
            
            // 1. Obtener el pensum asignado al estudiante
            List<TercPensumDTO> asignaciones = preMatriculaRepository.consultarTercPensum(estudianteId);
            
            if (asignaciones != null && !asignaciones.isEmpty()) {
                Long pensumId = asignaciones.get(0).getPensId();
                // 2. Consultar materias faltantes
                List<MateriaFaltanteDTO> materias = preMatriculaRepository.consultarMateriasFaltantes(pensumId, estudianteId);
                model.addAttribute("materias", materias);
            } else {
                model.addAttribute("errorPensum", true); // Bandera si no tiene pensum asignado
            }
        }

        return "informes";
    }

    // =========================================================================
    // 2. Nueva pantalla: Administrar Promedios
    // =========================================================================
    @GetMapping("/promedios")
    public String administrarPromedios(Model model) {
        model.addAttribute("estudiantes", terceroService.listarTodosPorTipo("0"));
        model.addAttribute("promedios", preMatriculaRepository.consultarVistaPromedios());
        return "administrar_promedios"; 
    }

    // =========================================================================
    // 3. Ejecutar la función SP_PROMEDIO (API REST llamada por AJAX)
    // =========================================================================
    @GetMapping("/api/promedios/{estudianteId}")
    @ResponseBody
    public Double ejecutarFuncionPromedio(@PathVariable Long estudianteId) {
        return preMatriculaRepository.calcularPromedioEstudiante(estudianteId);
    }
}