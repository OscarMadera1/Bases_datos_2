package com.BasesDeDatos.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "CURSOS", schema = "HISTORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Curso {
    @Id
    @Column(name = "CURS_ID")
    private Long Id;

    @Column(name = "TERC_ID")
    private Long terceroId;

    @Column(name = "ASIG_ID")
    private Long asignaturaId;

    @Column(name = "CURS_PERIODO", length = 6)
    private String period;
    
}
