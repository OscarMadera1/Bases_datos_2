package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.Tercero;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface TerceroRepository  extends JpaRepository<Tercero, Long>{
    //Mapeo del procedimiento de almacenado
    @Procedure(procedureName = "SP_ING_TERCEROS")
    void registrarTercero(
        @Param("TIPO_DOC") String tipoDoc,
        @Param("NRO_DOC") String nroDoc,
        @Param("GENERO") String gernero,
        @Param("NOMBRES") String nombres,
        @Param("APELLIDOS") String apellidos,
        @Param("DIREC") String direc,
        @Param("CORREO") String correo,
        @Param("MOVIL") String movil,
        @Param("TIPO") String tipo
    );

        //Procedimiento de actualizacion de terceros
    @Procedure(procedureName = "SP_ACT_TERCEROS")
    void actualizarTercero(
        @Param("CODIGO") Long codigo,
        @Param("TIPO_DOC") String tipoDoc,
        @Param("NRO_DOC") String nroDoc,
        @Param("GENERO") String genero
    );

    //Procedimiento de eliminacion de terceros
    @Procedure(procedureName = "SP_ELI_TERCEROS")
    void eliminarTercero(
        @Param("CODIGO") Long codigo
    );
}