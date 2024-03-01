package org.nica.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "Marci")
public class Marca {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String nume;
    @ManyToOne
    @JoinColumn(name="FirmaId")
    private Firma firma;
    @SuppressWarnings("unused")
    public Marca (){}
    public Marca (String nume, Firma firma)
    {
        this.setNume(nume);
        this.setFirma(firma);
    }

    @Override
    public String toString() {
        return "Marca{" +
                "nume=" + getNume() +
                ";" + getFirma() +
                '}';
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Firma getFirma() {
        return firma;
    }

    public void setFirma(Firma firma) {
        this.firma = firma;
    }

    public static Map<String, Marca> generateData(Map<String, Firma> firme)
    {
        return Map.ofEntries
                (
                        Map.entry ("Chevrolet", new Marca ("Chevrolet", firme.get ("General Motors"))),
                        Map.entry ("Cadillac", new Marca ("Cadillac", firme.get ("General Motors"))),
                        Map.entry ("GMC", new Marca ("GMC", firme.get ("General Motors"))),
                        Map.entry ("Ford", new Marca ("Ford", firme.get ("Ford"))),
                        Map.entry ("Mustang", new Marca ("Mustang", firme.get ("Ford"))),
                        Map.entry ("BMW", new Marca ("BMW", firme.get ("BMW"))),
                        Map.entry ("Mini Cooper", new Marca ("Mini Cooper", firme.get ("BMW"))),
                        Map.entry ("Audi", new Marca ("Audi", firme.get ("Audi Volkswagen"))),
                        Map.entry ("Volkswagen", new Marca ("Volkswagen", firme.get ("Audi Volkswagen"))),
                        Map.entry ("Lamborghini", new Marca ("Lamborghini", firme.get ("Audi Volkswagen"))),
                        Map.entry ("Seat", new Marca ("Seat", firme.get ("Audi Volkswagen"))),
                        Map.entry ("Skoda", new Marca ("Skoda", firme.get ("Audi Volkswagen"))),
                        Map.entry ("Opel", new Marca ("Opel", firme.get ("Opel"))),
                        Map.entry ("Subaru", new Marca ("Subaru", firme.get ("Subaru"))),
                        Map.entry ("Honda", new Marca ("Honda", firme.get ("Honda"))),
                        Map.entry ("Mitsubishi", new Marca ("Mitsubishi", firme.get ("Mitsubishi"))),
                        Map.entry ("Toyota", new Marca ("Toyota", firme.get ("Toyota"))),
                        Map.entry ("Dacia", new Marca ("Dacia", firme.get ("Dacia")))
                );
    }

}
