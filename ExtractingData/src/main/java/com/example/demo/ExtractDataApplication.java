package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
@EnableRabbit
public class ExtractDataApplication {

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(ExtractDataApplication.class);
		// Use a distinct port to avoid conflicts with other modules
		Map<String, Object> defaultProps = new HashMap<>();
		defaultProps.put("server.port", "8082");
		// Allow circular references to resolve legacy bean wiring issues
		defaultProps.put("spring.main.allow-circular-references", true);
		app.setDefaultProperties(defaultProps);
		app.run(args);
	}

	@Bean
	public DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		dataSource.setDriverClassName("org.postgresql.Driver");
		dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres");
		return dataSource;
	}
}