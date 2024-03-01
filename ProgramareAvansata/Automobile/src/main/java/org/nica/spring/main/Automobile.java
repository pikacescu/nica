package org.nica.spring.main;

import org.nica.spring.controller.AutomobileRest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;

// Most universal: @SpringBootApplication
// Same as: @Configuration @EnableAutoConfiguration @ComponentScan
@Configuration
@EnableAutoConfiguration //can exclude with (exclude={BlaBlaBla.class ...})
//@ComponentScan //disable component scan
public class Automobile {
    public static void main(String[] args)
    {
        SpringApplication.run(new Class<?>[]{Automobile.class, AutomobileRest.class}, args);
    }

}
