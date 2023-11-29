package org.nica.loader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.nica.model.Event;

import java.io.File;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

@SuppressWarnings({"SameParameterValue", "unused"})
class ConfigTest {
    static void assertNoThrow(Executable executable) {
        assertNoThrow(executable, "must not throw");
    }
    static void assertNoThrow(Executable executable, String message) {
        try {
            executable.execute();
        } catch (Throwable err) {
            fail(message);
        }
    }
    @Test
    protected void techStandardAssertThrow()
    {
        assertThrows(Throwable.class, () -> {throw new Exception("salut");});
    }
    @Test
    protected void techStandardAssertDoesNotThrow()
    {
        assertDoesNotThrow(() -> {});
    }
    protected void loadFromDb(SessionFactory sessionFactory)  {
        /* */
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }

    private String getResourcePath(String path){
        //System.out.println (new File("").getAbsolutePath()); //get current path
        URL fl = Config.class.getClassLoader().getResource(path);
        if (fl == null) return "";
        return fl.getPath();
    }
    @org.junit.jupiter.api.Test
    void tech() {
        System.out.println (new File("").getAbsolutePath());
        System.out.println (getResourcePath("hibernate.cfg.xml"));

    }
    @org.junit.jupiter.api.Test
    void withXml() {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withXml()
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @org.junit.jupiter.api.Test
    void withProps() {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                //.withProps() //does nothing
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @org.junit.jupiter.api.Test
    void withXmlRes() {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withXml("hibernate.cfg.xml")
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @org.junit.jupiter.api.Test
    void withPropsRes() throws Exception {
        String props = "hibernate.properties";
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withProps(props)
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @org.junit.jupiter.api.Test
    void withXmlPath() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withXmlPath(getResourcePath("hibernate.cfg.xmlxx"))
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @org.junit.jupiter.api.Test
    void withPropsPath() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Config()
                .withPropsPath(getResourcePath("hibernate.properties"))
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb(sessionFactory);
    }

    @org.junit.jupiter.api.Test
    void addAnnotatedClass() {
    }

    @org.junit.jupiter.api.Test
    void build() {
    }
}