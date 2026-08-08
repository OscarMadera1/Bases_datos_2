package com.BasesDeDatos.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "ASIGNATURAS", schema = "HISTORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Asignatura {

    @Id
    @Column(name = "ASIG_ID")
    private Long id;

    // ¡CAMBIO AQUÍ! Le cambiamos el nombre a la variable para que no choque con el objeto
    @Column(name = "ASIG_ASIGNATURA", length = 50, nullable = false)
    private String nombre;

    @Column(name = "ASIG_CREDITOS")
    private Integer creditos;

    @Column(name = "ASIG_CODIGO", length = 6)
    private String codigo;
    
}
