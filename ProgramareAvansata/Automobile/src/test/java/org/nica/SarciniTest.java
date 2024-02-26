package org.nica;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SarciniTest {

    @BeforeAll
    static void config()
    {
        Util.setDefaultProps("hibernate.test.load.properties", "hibernate.test.create.properties");
    }
    @BeforeEach
    void resetSession()
    {
        Util.resetSessionFactory();
    }
    @Test
    void saveNew() {
        //noinspection resource
        Util.build(Util.getCreateProps());
        Util.saveNew();
    }
    @Test
    void getTari()
    {
        Sarcini.getTari().forEach(System.out::println);
    }
    @Test
    void getFirmaNumarMaximal()
    {
        var tp = Sarcini.getFirmaNumarMaximal();
        System.out.printf("Firme cu %d automobile vandute:\n", tp.b);
        tp.a.forEach(System.out::println);
    }
   @Test
    void getFirmaNrMaxAutomobile()
    {
        Long mx = Sarcini.getFirmaNrMaxAutomobile();
        System.out.printf("Numar maximal de automobile vandute de o firma: %d\n", mx);
    }
    @Test
    void extractBmwAudiToSeparateTable()
    {
        Sarcini.extractBmwAudiToSeparateTable ();
        Sarcini.getBmwAudi().forEach(System.out::println);
    }
    @Test
    void getBmwAudi()
    {
        Sarcini.getBmwAudi().forEach(System.out::println);
    }
    @Test
    void excludeAutomobil()
    {
        Sarcini.excludeAutomobil("121bmw");
        Util.showAutomobile();
    }
    @Test
    void getModele()
    {
        Sarcini.getModele().forEach(System.out::println);
    }
    @Test
    void getModel()
    {
        System.out.println(Sarcini.getModel(2L));
    }
    @Test
    void getAlfabetic()
    {
        Sarcini.getAlfabetic().forEach(System.out::println);
    }
    @Test
    void getAlfabeticDesc()
    {
        Sarcini.getAlfabeticDesc().forEach(System.out::println);
    }
    @Test
    void getPretulMediuDinTara()
    {
        String tara = "Germania";
        Double pretMediu = Sarcini.getPretulMediuDinTara(tara);
        System.out.println ("Pretul mediu al automobilelor din " + tara + " este " + pretMediu + " Euro");
    }
    @Test
    void showIeftinScumpVerde()
    {
        Sarcini. getIeftinScumpVerde().forEach(System.out::println);
    }
}
