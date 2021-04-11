package com.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class EnergyMeterReaderApplication {
    public static void main(String[] args) {
        SpringApplication.run(EnergyMeterReaderApplication.class,args);
    }
}
