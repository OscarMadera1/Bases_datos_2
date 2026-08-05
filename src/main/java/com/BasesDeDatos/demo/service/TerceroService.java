package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.Tercero;
import com.BasesDeDatos.demo.repository.TerceroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TerceroService {
    //Crear la instancia del repositorio automaticamente
    @Autowired
    private TerceroRepository terceroRepository;

    //Obtener la lista de todos los terceros registrados
    public List<Tercero> listarTodos(){
        return terceroRepository.findAll();
    }

    //Metodo para guardar con el procedimiento de guardado
    @Transactional
    public void guardarTercero(Tercero tercero){
        terceroRepository.registrarTercero(
            tercero.getTipoDoc(),
            tercero.getNroDoc(),
            tercero.getGenero(),
            tercero.getNombres(),
            tercero.getApellidos(),
            tercero.getDireccion(),
            tercero.getCorreo(),
            tercero.getMovil(),
            tercero.getTipo()
        );
    }
}
