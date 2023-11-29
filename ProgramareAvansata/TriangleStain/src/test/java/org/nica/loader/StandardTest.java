package org.nica.loader;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.nica.model.Event;

class StandardTest {
    protected void loadFromDb(SessionFactory sessionFactory)  throws Exception {
        /* */
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }

    @Test
    void withProps() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                //.withProps() //does nothing
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb (sessionFactory);
    }

    @Test
    void withPropsCustom() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withProps("hibernate.properties")
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb (sessionFactory);
    }
    @Test
    void withXml() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withXml()
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb (sessionFactory);
    }
    @Test
    void withXmlCustom() throws Exception {
        SessionFactory sessionFactory = new org.nica.loader.Standard()
                .withXml("hibernate.cfg.xml")
                .addAnnotatedClass(Event.class)
                .build();
        loadFromDb (sessionFactory);
    }
}