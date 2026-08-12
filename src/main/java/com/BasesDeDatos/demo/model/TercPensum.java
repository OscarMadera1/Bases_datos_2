package com.BasesDeDatos.demo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Entity
@Table(name = "TERC_PENSUMS", schema = "HISTORIAS")
@Data
@NoArgsConstructor
@AllArgsConstructor
@IdClass(TercPensum.TercPensumId.class) //Clave primaria compuesta
public class TercPensum {

    @Id
    @Column(name = "PENS_ID")
    private Long pensumId;

    @Id
    @Column(name = "TERC_ID")
    private Long terceroId;

    @Column(name = "TEPE_PERIODO", length = 6)
    private String periodo;

    //Clave compuesta
    public static class TercPensumId implements Serializable{
        private Long pensumId;
        private Long terceroId;
    }
    
}
