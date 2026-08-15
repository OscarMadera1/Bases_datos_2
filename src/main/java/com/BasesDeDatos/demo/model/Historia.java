package com.BasesDeDatos.demo.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.io.Serializable;

//Mapero tabla de Historias
@Entity
@Table(name = "HISTORIAS", schema = "HISTORIAS")
@Data
@AllArgsConstructor
@NoArgsConstructor
@IdClass(Historia.HistoriaId.class)
public class Historia {
    @Id
    @Column(name = "HIST_PERIODO", length = 6)
    private String periodo;

    @Id
    @Column(name = "TERC_ID")
    private Long terceroId;

    @Id
    @Column(name = "CURS_ID")
    private Long cursoId;

    @Column(name = "HIST_NOTA", precision = 2, scale = 1)
    private BigDecimal nota;

    //Clave primaria compuesta
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class HistoriaId implements Serializable{
        private String periodo;
        private Long terceroId;
        private Long cursoId;
    }

    
}
