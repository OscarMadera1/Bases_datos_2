package com.BasesDeDatos.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "PROGRAMAS", schema = "HISTORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Programa {
    @Id
    @Column(name = "PROG_ID")
    private Long id;

    @Column(name = "PROG_PROGRAMA", length = 50, nullable = false)
    private String nombre;
    
}
