package com.example.gohome;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.web.WebApplicationInitializer;

@SpringBootApplication
public class GohomeApplication extends SpringBootServletInitializer implements WebApplicationInitializer {


    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application){

        return application.sources(GohomeApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(GohomeApplication.class, args);
    }
}
