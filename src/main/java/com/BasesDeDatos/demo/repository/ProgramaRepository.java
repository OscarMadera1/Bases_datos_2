package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.Programa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProgramaRepository extends JpaRepository<Programa, Long>{

    //Procedimiento crear programa
    @Procedure(procedureName = "SP_ING_PROGRAMAS")
    void registrarPrograma(
        @Param("P_PROGRAMA")String programa
    );

    //Procedimiento actualizar programa
    @Procedure(procedureName = "SP_ACT_PROGRAMAS")
    void actualizarPrograma(
        @Param("PROG_ID") Long codigo,
        @Param("P_PROGRAMA") String programa
    );

    //Procedimiento eliminar programa
    @Procedure(procedureName = "SP_ELI_PROGRAMAS")
    void eliminarPrograma(
        @Param("PROG_ID") Long codigo
    );
}
