package org.nica.model;

import jakarta.persistence.*;

@Entity
@Table(name = "AutomobileBmwAudi")
public class AutomobilBmwAudi {
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
    public AutomobilBmwAudi(){}
    public AutomobilBmwAudi(Automobil automobil)
    {
        this(   automobil.getModel(),
                automobil.getAnProducere(),
                automobil.getLunaAnCumparare(),
                automobil.getNrInregistrare(),
                automobil.getCuloare());
    }
    public AutomobilBmwAudi(Model model, int anProducere, int lunaAnCumparare, String nrInregistrare, String culoare){
        this.setModel(model);
        this.setAnProducere(anProducere);
        this.setLunaAnCumparare(lunaAnCumparare);
        this.setCuloare(culoare);
        this.setNrInregistrare(nrInregistrare);
    }


    public Model getAutomobil() {
        return getModel();
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

    public void setLunaAnCumparare(int lunaAnCumparare) {
        this.lunaAnCumparare = lunaAnCumparare;
    }
    public String formatCumparare() {
        return getLunaCumparare() + "/" + getAnCumparare();
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

    @Override
    public String toString() {
        return "Automobil{" + getModel() +
                ";numar=" + nrInregistrare +
                ";culoare=" + culoare +
                ";an{produs=" + anProducere +
                ";cumparat=" + lunaAnCumparare + "}}";
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }
}
