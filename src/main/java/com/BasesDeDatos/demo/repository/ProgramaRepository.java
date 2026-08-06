package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long>{

    //Procedimiento crear programa
    @Procedure(procedureName = "HISTORIAS.SP_ING_PROGRAMAS")
    void registrarPrograma(
        @Param("P_PROGRAMA")String programa
    );

    //Procedimiento actualizar programa
    @Procedure(procedureName = "HISTORIAS.SP_ACT_PROGRAMAS")
    void actualizarPrograma(
        @Param("CODIGO") Long codigo,
        @Param("P_PROGRAMA") String programa
    );

    //Procedimieto eliminar programa
    @Procedure(procedureName = "HISTORIAS.SP_ELI_PROGRAMAS")
    void eliminarPrograma(
        @Param("CODIGO") Long codigo
    );
}
