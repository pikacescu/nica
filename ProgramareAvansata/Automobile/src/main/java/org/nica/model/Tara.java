package org.nica.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "Tari")
public class Tara {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true, nullable = false)
    private String nume;
    @SuppressWarnings("unused")
    public Tara (){}
    public Tara (String nume){
        this.setNume(nume);}


    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    @Override
    public String toString() {
        return "Tara{" +
                "nume=" + getNume() +
                '}';
    }

    public static Map<String, Tara> generateData()
    {
        return Map.ofEntries
            (
                Map.entry ("SUA", new Tara("SUA")),
                Map.entry ("Germania", new Tara("Germania")),
                Map.entry ("Japonia", new Tara("Japonia")),
                Map.entry ("Romania", new Tara("Romania")),
                Map.entry ("Italia", new Tara("Italia")),
                Map.entry ("Franta", new Tara("Franta"))
            );
    }

}
