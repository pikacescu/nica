package org.nica.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "Automobile")
public class Automobil {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name="ModelId", nullable = false)
    private
    Model model;

    @Column(nullable = false)
    private String culoare;
    @Column(unique = true, nullable = false)
    private String nrInregistrare;
    private int anProducere;
    private int lunaAnCumparare;

    @SuppressWarnings("unused")
    public Automobil(){}
    public Automobil(Model marca, int anProducere, int lunaAnCumparare, String nrInregistrare, String culoare){
        this.setModel(marca);
        this.setAnProducere(anProducere);
        this.setLunaAnCumparare(lunaAnCumparare);
        this.setCuloare(culoare);
        this.setNrInregistrare(nrInregistrare);
    }

    public String getCuloare() {
        return culoare;
    }

    public void setCuloare(String culoare) {
        this.culoare = culoare;
    }

    public int getAnProducere() {
        return anProducere;
    }

    public void setAnProducere(int anProducere) {
        this.anProducere = anProducere;
    }

    public int getLunaAnCumparare() {
        return lunaAnCumparare;
    }

    public int getAnCumparare() {
        return lunaAnCumparare / 100;
    }
    public int getLunaCumparare() {
        return lunaAnCumparare % 100;
    }
    public String formatCumparare() {
        return getLunaCumparare() + "/" + getAnCumparare();
    }

    public void setLunaAnCumparare(int lunaAnCumparare) {
        this.lunaAnCumparare = lunaAnCumparare;
    }
    public void setLunaAnCumparare(int anCumparare, int lunaCumparare) {
        this.lunaAnCumparare = anCumparare * 100 + lunaCumparare;
    }
    public String getNrInregistrare() {
        return nrInregistrare;
    }

    public void setNrInregistrare(String nrInregistrare) {
        this.nrInregistrare = nrInregistrare;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }


    @Override
    public String toString() {
        return "Automobil{" + getModel() +
                ";numar=" + nrInregistrare +
                ";culoare=" + culoare +
                ";an{produs=" + anProducere +
                ";cumparat=" + lunaAnCumparare + "}}";
    }


    public static List<Automobil> generateData(Map<String, Model> modele) {
        return List.of
                (
                        new Automobil(modele.get("BMW X3"), 1991, 199201, "123bmw", "verde"),
                        new Automobil(modele.get("BMW X5"), 2022, 202211, "121bmw", "rosu"),
                        new Automobil(modele.get("Audi Q7"), 2022, 202202, "111aud", "rosu"),
                        new Automobil(modele.get("Audi Q7"), 2022, 202212, "211aud", "verde"),
                        new Automobil(modele.get("Ford Sierra"), 2022, 202204, "211for", "verde"),
                        new Automobil(modele.get("Ford Scorpio"), 2022, 202209, "212for", "verde"),
                        new Automobil(modele.get("Dacia Duster"), 2022, 202208, "211dac", "alb")
                );
    }

}
