package com.BasesDeDatos.demo.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "VW_HISTORIA_ESTUDIANTE", schema = "HISTORIAS")
public class HistoriaEstudianteDTO {
    @Id
    private Long cursId;
    private Long estudianteId;
    private String histPeriodo;
    private String docenteNombres;
    private String docenteApellidos;
    private Long asigId;
    private String asigAsignatura;
}
