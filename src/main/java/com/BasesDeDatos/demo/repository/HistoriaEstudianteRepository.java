package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.dto.HistoriaEstudianteDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaEstudianteRepository extends JpaRepository<HistoriaEstudianteDTO, Long> {
    List<HistoriaEstudianteDTO> findByEstudianteId(Long estudianteId);

    
}