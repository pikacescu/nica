package org.nica;

import org.junit.jupiter.api.Test;
import org.nica.model.Tara;

class UtilTest {

    @Test
    void saveNew() {
        Util.build("hibernate.test.create.properties");
        Util.saveNew();
    }

    @Test
    void showAutomobile()
    {
        Util.build("hibernate.test.load.properties");
        Util.showAutomobile();
    }

    @Test
    void showTari()
    {
        Util.build("hibernate.test.load.properties");
        Util.showTari();
    }

    @Test
    void showModele() {
        Util.build("hibernate.test.load.properties");
        Util.showModele();
    }

    @Test
    void showFirme() {
        Util.build("hibernate.test.load.properties");
        Util.showFirme();
    }

    @Test
    void showMarci() {
        Util.build("hibernate.test.load.properties");
        Util.showMarci();
    }
    @Test
    void show() {
        Util.build("hibernate.test.load.properties");
        Util.show(Tara.class);
    }

}