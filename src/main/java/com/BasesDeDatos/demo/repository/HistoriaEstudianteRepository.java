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
                    h.TERC_ID AS ESTUDIANTE_ID,
                    h.HIST_PERIODO,
                    h.CURS_ID,
                    d.TERC_NOMBRES AS DOCENTE_NOMBRES,
                    d.TERC_APELLIDOS AS DOCENTE_APELLIDOS,
                    a.ASIG_ID,
                    a.ASIG_ASIGNATURA
                FROM HISTORIAS.HISTORIAS h
                JOIN HISTORIAS.CURSOS c ON h.CURS_ID = c.CURS_ID
                JOIN HISTORIAS.TERCEROS d ON c.TERC_ID = d.TERC_ID
                JOIN HISTORIAS.ASIGNATURAS a ON c.ASIG_ID = a.ASIG_ID
                WHERE h.TERC_ID = ?
                """;

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {
                    HistoriaEstudiante historia = new HistoriaEstudiante();

                    historia.setEstudianteId(rs.getLong("ESTUDIANTE_ID"));
                    historia.setHistPeriodo(rs.getString("HIST_PERIODO"));
                    historia.setCursId(rs.getLong("CURS_ID"));
                    historia.setDocenteNombres(rs.getString("DOCENTE_NOMBRES"));
                    historia.setDocenteApellidos(rs.getString("DOCENTE_APELLIDOS"));
                    historia.setAsigId(rs.getLong("ASIG_ID"));
                    historia.setAsigAsignatura(rs.getString("ASIG_ASIGNATURA"));

                    return historia;
                },
                estudianteId
        );
    }
}