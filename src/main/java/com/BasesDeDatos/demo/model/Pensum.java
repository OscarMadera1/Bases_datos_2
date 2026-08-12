package com.BasesDeDatos.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "PENSUMS", schema = "HISTORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pensum {
    @Id
    @Column(name = "PENS_ID")
    private Long id;

    @Column(name = "PROG_ID")
    private Long programaId;

    @Column(name = "PENS_PERIODO")
    private String periodo;
    
}
