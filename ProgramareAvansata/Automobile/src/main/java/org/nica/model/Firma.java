package org.nica.model;

import jakarta.persistence.*;

import java.util.Map;

@Entity
@Table(name = "Firme")
public class Firma {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true, nullable = false)
    private String nume;
    @ManyToOne
    @JoinColumn(name="TaraId", nullable = false)  //insertable=false, updatable=false
    private
    Tara tara;
    @SuppressWarnings("unused")
    public Firma () {}
    public Firma (String nume, Tara tara) {
        this.setNume(nume);
        this.setTara(tara);
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }

    public Tara getTara() {
        return tara;
    }

    public void setTara(Tara tara) {
        this.tara = tara;
    }

    public static Map<String, Firma> generateData(Map<String, Tara> tari)
    {
        return Map.ofEntries
            (
                Map.entry ("General Motors", new Firma ("General Motors", tari.get("SUA"))),
                Map.entry ("Ford", new Firma ("Ford", tari.get("SUA"))),
                Map.entry ("BMW", new Firma ("BMW", tari.get("Germania"))),
                Map.entry ("Audi Volkswagen", new Firma ("Audi Volkswagen", tari.get("Germania"))),
                Map.entry ("Opel", new Firma ("Opel", tari.get("Germania"))),
                Map.entry ("Subaru", new Firma ("Subaru", tari.get("Japonia"))),
                Map.entry ("Honda", new Firma ("Honda", tari.get("Japonia"))),
                Map.entry ("Mitsubishi", new Firma ("Mitsubishi", tari.get("Japonia"))),
                Map.entry ("Toyota", new Firma ("Toyota", tari.get("Japonia"))),
                Map.entry ("Dacia", new Firma ("Dacia", tari.get("Romania"))),
                Map.entry ("Fiat", new Firma ("Fiat", tari.get("Italia")))
            );
    }
    @Override
    public String toString() {
        return "Firma{" +
                "nume=" + getNume() +
                ";" + getTara() +
                '}';
    }
}
