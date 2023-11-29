package org.nica;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.nica.model.Event;
//import org.nica.loader.Config;

import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import static java.time.LocalDateTime.now;

public class StainMain {

    public static void main(String[] args) throws Exception {

        //*
        System.out.println("Hello world!");
        StainMain triangle = new StainMain();
        if (args.length > 0)
            switch(args[0])
            {
            case "firstrun":
                triangle.firstRun(args);
                triangle.saveNew();
                return;
            case "normalrun":
                triangle.normalRun(args);
                return;
            case "fromConfigXmlResource":
                triangle.fromConfigXmlResource(args);
                return;
            case "fromConfigDefaultXmlResource":
                triangle.fromConfigDefaultXmlResource(args);
                return;
            case "fromConfigDefaultPropResource":
                triangle.fromConfigDefaultPropResource(args);
                return;
            case "normalrun3":
                triangle.fromCustomPropFile(args);
                return;
            case "fromCustomPropResource":
                sessionFactory = new org.nica.loader.Config()
                        .withProps("hibernate.properties")
                        .addAnnotatedClass(Event.class)
                        .build();
                triangle.loadFromDb(args);
                return;
            case "fromStandardPropDefault":
                sessionFactory = new org.nica.loader.Standard()
                        .withProps()
                        .addAnnotatedClass(Event.class)
                        .build();
                triangle.loadFromDb(args);
                return;
            case "fromStandardProp":
                sessionFactory = new org.nica.loader.Standard()
                        .withProps("hibernate.properties")
                        .addAnnotatedClass(Event.class)
                        .build();
                triangle.loadFromDb(args);
                return;
            } //*/
    }

    private void saveNew() {
        sessionFactory.inTransaction(session -> {
            session.persist(new Event("Our very first event!", now()));
            session.persist(new Event("A follow up event", now()));
        });
        sessionFactory.inTransaction(session -> {
            session.createSelectionQuery("from Event", Event.class)
                    .getResultList()
                    .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        });
    }

    static SessionFactory sessionFactory;
    protected void firstRun(String[] args)  throws Exception {
        sessionFactory = buildSessionFactoryFromStandardRegistry ();
        saveNew();
    }
    protected void fromConfigXmlResource(String[] args)  throws Exception {
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(Event.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }
    String getResourcePath(String path){
        //System.out.println (new File("").getAbsolutePath()); //get current path
        URL fl = this.getClass().getClassLoader().getResource(path);
        return fl.getPath();
    }

    protected void fromCustomPropFile (String[] args)  throws Exception {
        Properties properties = new Properties();
        properties.load(new FileInputStream( getResourcePath("hibernate.properties")));
        sessionFactory = new Configuration()
                .addProperties(properties)
                .addAnnotatedClass(Event.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }
    protected void fromConfigDefaultXmlResource(String[] args)  throws Exception {
        sessionFactory = new Configuration().configure()
                .addAnnotatedClass(Event.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }
    protected void fromConfigDefaultPropResource(String[] args)  throws Exception {
        sessionFactory = new Configuration()
                .addAnnotatedClass(Event.class)
                .buildSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }
    protected void loadFromDb(String[] args)  throws Exception {
       /* */
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }

    protected SessionFactory buildSessionFactoryFromStandardRegistry(Class<?> ... cls)  throws Exception
    {
        if (sessionFactory != null) return sessionFactory;
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder().build();
        return new MetadataSources(registry)
                        .addAnnotatedClass(Event.class)
                        .buildMetadata()
                        .buildSessionFactory();
    }
    protected void normalRun(String[] args)  throws Exception {
        sessionFactory = buildSessionFactoryFromStandardRegistry();
        sessionFactory.inTransaction(session -> {
            session.createSelectionQuery("from Event", Event.class)
                    .getResultList()
                    .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        });
    }
}