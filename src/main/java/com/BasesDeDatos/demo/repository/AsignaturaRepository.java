package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.Asignatura;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AsignaturaRepository extends JpaRepository<Asignatura, Long> {

    //Procedimiento crear asignatura
    @Procedure(procedureName = "SP_ING_ASIGNATURAS")
    void registrarAsignatura(
        @Param("P_ASIGNATURA") String asignatura,
        @Param("P_CREDITOS") Integer creditos,
        @Param("P_CODIGO") String codigo
    );

    //Procedimiento actualizar asignatura
    @Procedure(procedureName = "SP_ACT_ASIGNATURAS")
    void actualizarAsignatura(
        @Param("CODIGO") Long id,
        @Param("P_ASIGNATURA") String asignatura,
        @Param("P_CREDITOS") Integer crditos,
        @Param("P_CODIGO") String codigo
    );

    //Procedimiento eliminar asignatura
    @Procedure(procedureName = "SP_ELI_ASIGNATURAS")
    void eliminarAsignatura(
        @Param("CODIGO") Long id
    );

}
