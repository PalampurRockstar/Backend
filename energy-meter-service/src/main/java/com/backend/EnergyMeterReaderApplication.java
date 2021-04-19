package com.backend;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class EnergyMeterReaderApplication {
    @Value("${description}")
    String description;
    @GetMapping("/test")
    public String test(){
        return description+" working!";
    }

    public static void main(String[] args) {
        SpringApplication.run(EnergyMeterReaderApplication.class,args);
    }
}
