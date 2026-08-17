package com.BasesDeDatos.demo.service;

import com.BasesDeDatos.demo.model.Tercero;
import com.BasesDeDatos.demo.repository.TerceroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.Locale.filter;


@Service
public class TerceroService {
    //Crear la instancia del repositorio automáticamente
    @Autowired
    private TerceroRepository terceroRepository;

    //Obtener la lista de todos los terceros registrados
    public List<Tercero> listarTodos(){
        return terceroRepository.findAll();
    }

    public List<Tercero> listarTodosPorTipo(String tipo) {
        return terceroRepository.findByTipo(tipo);
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

        //Metodo para actualizar con el procedimiento de actualizacion
    @Transactional
    public void actualizarTercero(Tercero tercero){
        terceroRepository.actualizarTercero(
            tercero.getId(),
            tercero.getTipoDoc(),
            tercero.getNroDoc(),
            tercero.getGenero()
        );
    }

        //Metodo para elminar con el procedimiento de eliminacion
        @Transactional
        public void eliminarTercero(Long id){
            terceroRepository.eliminarTercero(id);
        }


        // Método para autenticar al usuario en el login
    public Tercero autenticarUsuario(String nroDoc, String clave) {
        return terceroRepository.findByNroDocAndClave(nroDoc, clave);
    }
}
