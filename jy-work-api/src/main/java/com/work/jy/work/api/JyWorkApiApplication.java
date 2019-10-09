package com.work.jy.work.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"com.work"})
public class JyWorkApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(JyWorkApiApplication.class, args);
    }

}
