package org.shanzhaozhen.bestapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableCaching
@ComponentScan("org.shanzhaozhen")
public class BestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(BestApiApplication.class, args);
    }

}
