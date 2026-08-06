package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.Asignatura;
import com.BasesDeDatos.demo.repository.AsignaturaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AsignaturaService {
    @Autowired
    private AsignaturaRepository asignaturaRepository;

    //Listar Asignaturas
    public List<Asignatura> listarTodas(){
        return asignaturaRepository.findAll();
    }

    //Guardar asignatura
    @Transactional
    public void guardarAsignatura(Asignatura asignatura){
        asignaturaRepository.registrarAsignatura(
            asignatura.getAsignatura(),
            asignatura.getCreditos(),
            asignatura.getCodigo()
        );
    }

    //Actualizar asignatura
    @Transactional
    public void actualizarAsignatura(Asignatura asignatura){
        asignaturaRepository.actualizarAsignatura(
            asignatura.getId(),
            asignatura.getAsignatura(),
            asignatura.getCreditos(),
            asignatura.getCodigo()
        );
    }

    //Eliminar asignatura
    @Transactional
    public void eliminarAsignatura(Long id){
        asignaturaRepository.eliminarAsignatura(id);
    }
    
}
