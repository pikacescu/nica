package org.nica.spring.main;
//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.beans.factory.*;
import org.nica.spring.beans.Automobile;
import org.nica.spring.Person;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

//@SpringBootApplication
//@RestController
public class Beans {
    public static void main(String[] args)
    {
        ///*
        //ApplicationContext context  = new AnnotationConfigApplicationContext("org.nica");
        ApplicationContext context  = new AnnotationConfigApplicationContext("org.nica.spring.beans");
        //ApplicationContext context  = new AnnotationConfigApplicationContext(Automobile.class);
        //ApplicationContext applicationContext  = new ClassPathXmlApplicationContext();
        Person p =  context.getBean("person",Person.class);
        Person p2 =  context.getBean("person",Person.class);
        p2.setName("Jora");
        System.out.println(p);
        System.out.println(p2); //*/
    }
}
