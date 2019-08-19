package com.tec.cloud.api.quote;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author tec
 */
@SpringBootApplication
//@ComponentScan({"com.tec.platform.config", "com.tec.cloud.api.quote"})
public class QuoteApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(QuoteApiApplication.class, args);
    }

}