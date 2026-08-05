package com.BasesDeDatos.demo.config;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class ConexionOracle {

    private final JdbcTemplate jdbcTemplate;

    public ConexionOracle(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void probarConexion() {

        String sql = "SELECT 1 FROM DUAL";

        Integer resultado = jdbcTemplate.queryForObject(
                sql,
                Integer.class
        );

        System.out.println("======================================");
        System.out.println("CONEXION A ORACLE EXITOSA");
        System.out.println("Resultado: " + resultado);
        System.out.println("======================================");
    }
}
