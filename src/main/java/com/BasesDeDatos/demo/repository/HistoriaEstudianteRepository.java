package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.model.HistoriaEstudiante;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class HistoriaEstudianteRepository {

    private final JdbcTemplate jdbcTemplate;

    public HistoriaEstudianteRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<HistoriaEstudiante> listarPorEstudiante(Long estudianteId) {

        String sql = """
                SELECT
                    ESTUDIANTE_ID,
                    HIST_PERIODO,
                    CURS_ID,
                    DOCENTE_NOMBRES,
                    DOCENTE_APELLIDOS,
                    ASIG_ID,
                    ASIG_ASIGNATURA
                FROM HISTORIAS.VW_HISTORIA_ESTUDIANTE
                WHERE ESTUDIANTE_ID = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    HistoriaEstudiante historia =
                            new HistoriaEstudiante();

                    historia.setEstudianteId(
                            rs.getLong("ESTUDIANTE_ID")
                    );

                    historia.setHistPeriodo(
                            rs.getString("HIST_PERIODO")
                    );

                    historia.setCursId(
                            rs.getLong("CURS_ID")
                    );

                    historia.setDocenteNombres(
                            rs.getString("DOCENTE_NOMBRES")
                    );

                    historia.setDocenteApellidos(
                            rs.getString("DOCENTE_APELLIDOS")
                    );

                    historia.setAsigId(
                            rs.getLong("ASIG_ID")
                    );

                    historia.setAsigAsignatura(
                            rs.getString("ASIG_ASIGNATURA")
                    );

                    return historia;
                },
                estudianteId
        );
    }
}