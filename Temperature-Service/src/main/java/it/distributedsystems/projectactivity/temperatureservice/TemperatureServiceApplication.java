package it.distributedsystems.projectactivity.temperatureservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
@EnableCaching
public class TemperatureServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemperatureServiceApplication.class, args);
	}

}
