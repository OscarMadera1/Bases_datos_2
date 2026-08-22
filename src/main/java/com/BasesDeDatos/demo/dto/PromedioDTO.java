package com.BasesDeDatos.demo.dto;

public class PromedioDTO {
    private Long tercId;
    private String nombres;
    private String apellidos;
    private Double promedio;

    // Getters y Setters
    public Long getTercId() { return tercId; }
    public void setTercId(Long tercId) { this.tercId = tercId; }
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }
    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }
    public Double getPromedio() { return promedio; }
    public void setPromedio(Double promedio) { this.promedio = promedio; }
}