package com.BasesDeDatos.demo.dto;

import lombok.Data;
import java.sql.Timestamp;

@Data
public class AuditoriaDTO {
    private String usuario;
    private Timestamp fecha;
    private Long tercId;
    private String nombres;
    private String apellidos;
    private Long cursId;
    private String asignatura;
    private Double notaAnt;
    private Double notaDesp;
}