package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.TercPensum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TercPensumRepositori extends JpaRepository<TercPensum, TercPensum.TercPensumId> {

    @Procedure(procedureName = "SP_ING_TERC_PENSUMS")
    void matricularEstudiantePesum(
        @Param("T_ID") Long terceroId,
        @Param("P_ID") Long pesumId
    );

    boolean existsByTerceroIdAndPensumId(
            Long terceroId,
            Long pensumId
    );

    @Query("""
        SELECT tp
        FROM TercPensum tp
        ORDER BY tp.terceroId
    """)
    List<TercPensum> listarMatriculas();

}