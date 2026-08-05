package com.BasesDeDatos.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name= "TERCEROS", schema = "HISTORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor


public class Tercero {
    @Id
    @Column(name = "TERC_ID")
    private Long id;

    @Column(name = "TERC_TIPO_DOC", length = 2)
    private String tipoDoc;

    @Column(name = "TERC_NRO_DOC", length = 10)
    private String nroDoc;

    @Column(name = "TERC_GENERO", length = 1)
    private String genero;

    @Column(name = "TERC_NOMBRES", length = 50)
    private String nombres;

    @Column(name = "TERC_APELLIDOS", length = 50)
    private String apellidos;

    @Column(name = "TERC_DIREC", length = 50)
    private String direccion;

    @Column(name = "TERC_CORREO", length = 50)
    private String correo;

    @Column(name = "TERC_MOVIL", length = 10)
    private String movil;

    @Column(name = "TERC_TIPO", length = 1)
    private String tipo;
}
