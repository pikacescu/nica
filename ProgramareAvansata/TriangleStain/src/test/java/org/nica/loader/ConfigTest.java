package org.nica.loader;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.nica.model.Event;

import java.io.IOException;

import static org.nica.loader.Tech.*;
import static org.nica.Util.*;

@SuppressWarnings({"SameParameterValue", "unused"})
class ConfigTest {
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
    void withXml() {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withXml()
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @Test
    void withProps() {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                //.withProps() //does nothing
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @Test
    void withXmlRes() {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withXml(getTestLoadXmlCfg())
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @Test
    void withPropsRes() throws Exception {
        String props = getTestLoadProps();
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withProps(props)
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @Test
    void withXmlPath() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withXmlPath(getResourcePath(getTestLoadXmlCfg()).toString())
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @Test
    void withPropsPath() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withPropsPath(getResourcePath(getTestLoadProps()).toString())
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @Test
    void addAnnotatedClass() {
    }

    @Test
    void build() {
    }
}