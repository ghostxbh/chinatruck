package com.uzykj.chinatruck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class ChinatruckApplication {

    public static void main(String[] args) {
        SpringApplication.run(ChinatruckApplication.class, args);
    }

}
