package com.BasesDeDatos.demo.controller;

import com.BasesDeDatos.demo.dto.HistoriaEstudianteDTO;
import com.BasesDeDatos.demo.repository.HistoriaEstudianteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/historias")
public class HistoriaController {

    @Autowired
    private HistoriaEstudianteRepository historiaEstudianteRepository;

    @GetMapping("/historiaEstudiante/{estudianteId}")
    public String verHistoriaAcademica(@PathVariable("estudianteId") Long estudianteId, Model model){
        List<HistoriaEstudianteDTO> materias = historiaEstudianteRepository.findByEstudianteId(estudianteId);
        model.addAttribute("materias", materias);
        return "vista-historia";
    }
}
