package org.nica.spring;

public class Auto {
    String model = "Audi";
    public Auto(){System.out.println ("construct new");}

    public void setModel(String model) {
        this.model = model;
    }

    public String getModel() {
        return model;
    }
}
