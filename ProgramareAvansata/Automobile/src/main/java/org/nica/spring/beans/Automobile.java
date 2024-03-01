package org.nica.spring.beans;

import org.nica.spring.Auto;
import org.nica.spring.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


@Configuration
public class Automobile {
    @Bean(name="person")
    @Scope("prototype")
    public Person getPerson(){
        Person p = new Person();
        //noinspection SpellCheckingInspection
        p.setName("Djordj");
        p.setAge(26);
        return p;
    }
    @Bean(name="auto")
    @Scope("prototype")
    public Auto getAuto(){
        Auto p = new Auto();
        p.setModel("BMW");
        return p;
    }

}
