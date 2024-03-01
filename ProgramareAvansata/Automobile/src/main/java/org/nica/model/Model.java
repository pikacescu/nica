package org.nica.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "Modele")
@SuppressWarnings({"unused"})
public class Model {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    private String nume;
    private int pret;

    @ManyToOne @JoinColumn (name="MarcaId", nullable = false)
    private
    Marca marca;

    public Model() {}
    public Model(String nume, int pret, Marca marca) {
        this.setMarca(marca);
        this.setNume(nume);
        this.setPret(pret);
    }

    public static Map<String, Model> generateData(Map<String, Marca> marci) {
        return Map.ofEntries
            (
                Map.entry ("BMW X3", new Model("X3", 80000, marci.get("BMW"))),
                Map.entry ("BMW X5",  new Model("X5", 80000, marci.get("BMW"))),
                Map.entry ("BMW M3",  new Model("3", 80000, marci.get("BMW"))),
                Map.entry ("BMW M5",  new Model("4", 80000, marci.get("BMW"))),
                Map.entry ("Audi A8", new Model("A8", 70000, marci.get("Audi"))),
                Map.entry ("Audi S6",  new Model("S6", 800000, marci.get("Audi"))),
                Map.entry ("Audi R8",  new Model("R8", 1800000, marci.get("Audi"))),
                Map.entry ("Audi Q7",  new Model("Q7", 80000, marci.get("Audi"))),
                Map.entry ("Volkswagen Geta",  new Model("Geta", 80000, marci.get("Volkswagen"))),
                Map.entry ("Volkswagen Beetle",  new Model("Beetle", 80000, marci.get("Volkswagen"))),
                Map.entry ("Volkswagen Golf",  new Model("Golf", 80000, marci.get("Volkswagen"))),
                Map.entry ("Volkswagen Passat",  new Model("Passat", 80000, marci.get("Volkswagen"))),
                Map.entry ("Volkswagen Phaeton",  new Model("Phaeton", 80000, marci.get("Volkswagen"))),
                Map.entry ("Lamborghini Murcielago",  new Model("Murcielago", 80000, marci.get("Lamborghini"))),
                Map.entry ("Ford Fiesta",  new Model("Fiesta", 12000, marci.get("Ford"))),
                Map.entry ("Ford Sierra",  new Model("Sierra", 20000, marci.get("Ford"))),
                Map.entry ("Ford Escort",  new Model("Escort", 21000, marci.get("Ford"))),
                Map.entry ("Ford Scorpio",  new Model("Scorpio", 22000, marci.get("Ford"))),
                Map.entry ("Ford Transit",  new Model("Transit", 22001, marci.get("Ford"))),
                Map.entry ("Dacia Duster",  new Model("Duster", 22001, marci.get("Dacia"))),
                Map.entry ("Dacia Logan",  new Model("Logan", 22001, marci.get("Dacia"))),
                Map.entry ("Dacia Sandero",  new Model("Sandero", 22001, marci.get("Dacia")))
            );
    }

    @Override
    public String toString() {
        return "Model{nume=" + getNume() + ";pret=" + pret + ";" + getMarca() + "}";
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public int getPret() {
        return pret;
    }

    public void setPret(int pret) {
        this.pret = pret;
    }

    public Object getId() {
        return id;
    }
}
