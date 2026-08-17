package com.BasesDeDatos.demo.model;

import lombok.Data;

@Data
public class HistoriaEstudiante {

    private Long estudianteId;
    private String histPeriodo;
    private Long cursId;
    private String docenteNombres;
    private String docenteApellidos;
    private Long asigId;
    private String asigAsignatura;
}