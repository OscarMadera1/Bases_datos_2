package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.Pensum;
import com.BasesDeDatos.demo.repository.PensumRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PensumService {

    @Autowired
    private PensumRepository pensumRepository;

    public List<Pensum> listarTodos() {
        return pensumRepository.findAll();
    }

    public List<Pensum> listarPorPrograma(Long programaId) {
        return pensumRepository.findByProgramaId(programaId);
    }
}