package org.nica.loader;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nica.model.Event;

import java.io.IOException;

import static org.nica.Util.loadFromDbVerbal;
import static org.nica.loader.TestUtil.*;

class StandardTest {
    @BeforeAll
    static void buildDefaultProps() throws Exception {
        buildTestDefaultLoadConfigs();
        System.out.println ("salut config test");
    }
    @AfterAll
    static void revertDefaultProps() throws IOException {
        revertTestDefaultLoadConfigs();
        System.out.println ("pa config test");
    }

    @Test
    void withProps() {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                //.withProps() //does nothing
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }

    @Test
    void withPropsCustom() {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withProps(getTestLoadProps())
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }
    @Test
    void withXml() {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withXml()
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }
    @Test
    void withXmlCustom() {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withXml(getTestLoadXmlCfg())
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }
}