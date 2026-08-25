package com.BasesDeDatos.demo.dto;

import lombok.Data;

@Data
public class MateriaFaltanteDTO {
    private String nivel; // Ej: "1", "2" (Semestre)
    private String codigo;
    private String nombre;
    private Integer creditos;
}