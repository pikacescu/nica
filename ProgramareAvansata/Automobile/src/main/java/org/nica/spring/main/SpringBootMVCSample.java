package org.nica.spring.main;

import org.nica.spring.controller.SpringBootMVCControllerSample;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.stream.Stream;

@SpringBootApplication
public class SpringBootMVCSample {
    public static void main(String[] args) {
        String[] newArgs = Stream.concat
            (
                    Stream.of(args),
                    Stream.of(
                        // By spring.config.name
                        "--spring.config.name=spring.boot.mvc.sample,myboot") //two configs comma separated
                        //    {"--spring.config.name=myboot"})
                        //    {"--spring.config.name=spring.boot.mvc.sample"})
                        //    {"--spring.config.name=spring.boot.mvc.sample,myboot"}) //two configs comma separated
                        // By spring.config.location:
                        //    {"--spring.config.location=classpath:/spring.boot.mvc.sample.properties"}
            )
            .toArray(String[]::new);

        SpringApplication.run(new Class<?>[] {SpringBootMVCSample.class, SpringBootMVCControllerSample.class}, newArgs);
    }
}
