package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.repository.TercPensumRepositori;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TercPensumService {
    @Autowired
    private TercPensumRepositori tercPensumRepositori;

    @Transactional
    public void registrarMatricula (Long terceroId, Long pensumId){
        tercPensumRepositori.matricularEstudiantePesum(terceroId, pensumId);
    }
}
