package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.Historia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoriaRepository extends JpaRepository<Historia, Historia.HistoriaId>{
    //Buscar Historia por ID del estudiante
    List<Historia> findByTerceroId(Long terceroId);

    //Buscar por estudiante y periodo
    List<Historia> findByTerceroIdandPeriodo(Long terceroId, String periodo);
    
}
