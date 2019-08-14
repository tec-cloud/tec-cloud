package com.tec.cloud.api.quote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author tec
 */
@EnableEurekaClient
@SpringBootApplication
@ComponentScan({"com.tec.platform.config", "com.tec.cloud.api.quote"})
public class QuoteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuoteApiApplication.class, args);
    }

}