package com.BasesDeDatos.demo;

import com.BasesDeDatos.demo.config.ConexionOracle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {

		ConfigurableApplicationContext context =
				SpringApplication.run(DemoApplication.class, args);

		ConexionOracle conexion =
				context.getBean(ConexionOracle.class);

		conexion.probarConexion();
	}
}
