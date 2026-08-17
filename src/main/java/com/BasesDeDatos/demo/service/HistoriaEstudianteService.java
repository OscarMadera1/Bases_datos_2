package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.HistoriaEstudiante;
import com.BasesDeDatos.demo.repository.HistoriaEstudianteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriaEstudianteService {

    private final HistoriaEstudianteRepository historiaEstudianteRepository;

    public HistoriaEstudianteService(
            HistoriaEstudianteRepository historiaEstudianteRepository) {

        this.historiaEstudianteRepository =
                historiaEstudianteRepository;
    }

    public List<HistoriaEstudiante> listarPorEstudiante(
            Long estudianteId) {

        return historiaEstudianteRepository
                .listarPorEstudiante(estudianteId);
    }
}