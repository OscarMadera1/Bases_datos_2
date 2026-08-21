package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.dto.AuditoriaDTO;
import com.BasesDeDatos.demo.dto.PreMatriculaDTO;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PreMatriculaRepository {

    private final JdbcTemplate jdbcTemplate;

    public PreMatriculaRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 1. Ejecutar el Procedimiento Almacenado de Oracle
    public void generarPreMatriculaBatch(Long programaId, String periodo) {
        // La sintaxis {call ...} es la estándar de JDBC para Procedimientos Almacenados
        String sql = "{call SP_PREMATRIC(?, ?)}";
        jdbcTemplate.update(sql, programaId, periodo);
    }

    public List<PreMatriculaDTO> consultarPreMatriculaPorEstudiante(Long estudianteId) {

        // Consulta SQL ajustada exactamente al diagrama entidad-relación
        String sql = """
                SELECT
                    p.PREM_PERIODO AS PERIODO,
                    a.ASIG_CODIGO AS ASIG_CODIGO,
                    a.ASIG_ASIGNATURA AS ASIG_NOMBRE
                FROM PREMATRICULAS p
                JOIN ASIGNATURAS a ON p.ASIG_ID = a.ASIG_ID
                WHERE p.TERC_ID = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PreMatriculaDTO dto = new PreMatriculaDTO();

            // Los nombres aquí deben coincidir con los alias (AS ...) de la consulta SQL
            dto.setPeriodo(rs.getString("PERIODO"));
            dto.setAsigCodigo(rs.getString("ASIG_CODIGO"));
            dto.setAsigNombre(rs.getString("ASIG_NOMBRE"));

            return dto;
        }, estudianteId);
    }

    public List<AuditoriaDTO> consultarAuditorias() {
        String sql = "SELECT * FROM V_AUDITORIAS ORDER BY AUDI_FECHA DESC";

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            AuditoriaDTO dto = new AuditoriaDTO();
            dto.setUsuario(rs.getString("AUDI_USUARIO"));
            dto.setFecha(rs.getTimestamp("AUDI_FECHA"));
            dto.setTercId(rs.getLong("TERC_ID"));
            dto.setNombres(rs.getString("TERC_NOMBRES"));
            dto.setApellidos(rs.getString("TERC_APELLIDOS"));
            dto.setCursId(rs.getLong("CURS_ID"));
            dto.setAsignatura(rs.getString("ASIG_ASIGNATURA"));
            dto.setNotaAnt(rs.getDouble("HIST_NOTA_ANT"));
            dto.setNotaDesp(rs.getDouble("HIST_NOTA_DESP"));
            return dto;
        });
    }
}