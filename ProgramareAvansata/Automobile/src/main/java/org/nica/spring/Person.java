package org.nica.spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class Person {
    String name;
    int age;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Name:" + name + ";age:"+age;
    }

    @Autowired
    @Qualifier("auto")
    Auto auto;
    public Auto getAuto(){return auto;}

}
