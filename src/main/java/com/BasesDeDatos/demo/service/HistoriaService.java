package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.Historia;
import com.BasesDeDatos.demo.repository.HistoriaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class HistoriaService {
    @Autowired
    private HistoriaRepository historiaRepository;

    public List<Historia> obtenerMateriasEstudiante (Long terceroId){
        return historiaRepository.findByTerceroId(terceroId);
    }

    // NUEVO MÉTODO PARA CALCULAR PROMEDIO
    public BigDecimal calcularPromedioEstudiante(Long terceroId) {
        List<Historia> historias = historiaRepository.findByTerceroId(terceroId);
        if (historias == null || historias.isEmpty()) {
            return BigDecimal.ZERO; // Si no tiene notas, promedio es 0
        }
        
        BigDecimal suma = BigDecimal.ZERO;
        for (Historia h : historias) {
            if (h.getNota() != null) {
                suma = suma.add(h.getNota());
            }
        }
        return suma.divide(new BigDecimal(historias.size()), 2, RoundingMode.HALF_UP);
    }
}
