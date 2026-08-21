package com.BasesDeDatos.demo.repository;

import com.BasesDeDatos.demo.dto.AuditoriaDTO;
import com.BasesDeDatos.demo.dto.PreMatriculaDTO;
import com.BasesDeDatos.demo.dto.PromedioDTO;
import com.BasesDeDatos.demo.dto.TercPensumDTO;
import com.BasesDeDatos.demo.dto.DetallePreMatriculaDTO;
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
        String sql = "{call SP_PREMATRIC(?, ?)}";
        jdbcTemplate.update(sql, programaId, periodo);
    }

    public List<PreMatriculaDTO> consultarPreMatriculaPorEstudiante(Long estudianteId) {
        String sql = """
                SELECT
                    h.HIST_PERIODO AS PERIODO,
                    a.ASIG_CODIGO AS ASIG_CODIGO,
                    a.ASIG_ASIGNATURA AS ASIG_NOMBRE
                FROM HISTORIAS h
                JOIN CURSOS c ON h.CURS_ID = c.CURS_ID
                JOIN ASIGNATURAS a ON c.ASIG_ID = a.ASIG_ID
                WHERE h.TERC_ID = ?
                """;

        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PreMatriculaDTO dto = new PreMatriculaDTO();
            // Llenamos el DTO original del reporte para no romper tu vista de Informes
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

    // 2. Consultar la tabla TERC_PENSUMS para el estudiante seleccionado
    public List<TercPensumDTO> consultarTercPensum(Long estudianteId) {
        String sql = "SELECT PENS_ID, TERC_ID, TEPE_PERIODO FROM TERC_PENSUMS WHERE TERC_ID = ?";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            TercPensumDTO dto = new TercPensumDTO();
            dto.setPensId(rs.getLong("PENS_ID"));
            dto.setTercId(rs.getLong("TERC_ID"));
            dto.setTepePeriodo(rs.getString("TEPE_PERIODO"));
            return dto;
        }, estudianteId);
    }

    // 3. Consultar el detalle final (Periodo, Docente Asignado, Nombre de la Asignatura)
    public List<DetallePreMatriculaDTO> consultarDetalleAsignaturas(Long estudianteId) {
        String sql = """
                SELECT
                    h.HIST_PERIODO AS PERIODO,
                    t.TERC_NOMBRES || ' ' || t.TERC_APELLIDOS AS DOCENTE,
                    a.ASIG_ASIGNATURA AS ASIG_NOMBRE
                FROM HISTORIAS h
                JOIN CURSOS c ON h.CURS_ID = c.CURS_ID
                JOIN ASIGNATURAS a ON c.ASIG_ID = a.ASIG_ID
                JOIN TERCEROS t ON c.TERC_ID = t.TERC_ID
                WHERE h.TERC_ID = ?
                """;
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            DetallePreMatriculaDTO dto = new DetallePreMatriculaDTO();
            dto.setPeriodo(rs.getString("PERIODO"));
            dto.setDocenteAsignado(rs.getString("DOCENTE"));
            dto.setAsigNombre(rs.getString("ASIG_NOMBRE"));
            return dto;
        }, estudianteId);
    }

    // 1. Invocar la función SP_PROMEDIO para un estudiante específico (Retorna un número)
    public Double calcularPromedioEstudiante(Long estudianteId) {
        String sql = "SELECT SP_PROMEDIO(?) FROM DUAL";
        Double promedio = jdbcTemplate.queryForObject(sql, Double.class, estudianteId);
        return promedio != null ? promedio : 0.0;
    }

    // 2. Consultar la vista general de V_PROMEDIOS para el reporte
    public List<PromedioDTO> consultarVistaPromedios() {
        String sql = "SELECT TERC_ID, TERC_NOMBRES, TERC_APELLIDOS, PROMEDIO FROM V_PROMEDIOS ORDER BY PROMEDIO DESC";
        return jdbcTemplate.query(sql, (rs, rowNum) -> {
            PromedioDTO dto = new PromedioDTO();
            dto.setTercId(rs.getLong("TERC_ID"));
            dto.setNombres(rs.getString("TERC_NOMBRES"));
            dto.setApellidos(rs.getString("TERC_APELLIDOS"));
            dto.setPromedio(rs.getDouble("PROMEDIO"));
            return dto;
        });
    }
}