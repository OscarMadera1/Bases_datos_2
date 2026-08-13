package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.Historia;
import com.BasesDeDatos.demo.repository.HistoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoriaService {
    @Autowired
    private HistoriaRepository historiaRepository;

    public List<Historia> obtenerMateriasEstudiante (Long terceroId){
        return historiaRepository.findByTerceroId(terceroId);
    }
    
}
