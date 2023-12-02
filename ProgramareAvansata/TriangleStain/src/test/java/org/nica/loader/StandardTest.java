package org.nica.loader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nica.model.Event;

import java.io.IOException;

import static org.nica.loader.Tech.*;
import static org.nica.Util.*;

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
    void withProps() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                //.withProps() //does nothing
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }

    @Test
    void withPropsCustom() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withProps(getTestLoadProps())
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }
    @Test
    void withXml() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withXml()
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }
    @Test
    void withXmlCustom() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withXml(getTestLoadXmlCfg())
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDbVerbal(sessionFactory);
    }
}