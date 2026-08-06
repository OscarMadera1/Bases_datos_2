package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.Programa;
import com.BasesDeDatos.demo.repository.ProgramaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProgramaService {

    @Autowired
    private ProgramaRepository programaRepository;

    //Listar todos los programas
    public List<Programa> listarTodos(){
        return programaRepository.findAll();
    }

    //Guardar programa
    @Transactional
    public void guardarPrograma(Programa programa){
        programaRepository.registrarPrograma(programa.getPrograma());
    }
    
    //Actualizar programa
    @Transactional
    public void actualizarPrograma(Programa programa){
        programaRepository.actualizarPrograma(programa.getId(), programa.getPrograma());
    }

    //Eliminar programa
    @Transactional
    public void eliminarPrograma(Long id){
        programaRepository.eliminarPrograma(id);
    }
}
