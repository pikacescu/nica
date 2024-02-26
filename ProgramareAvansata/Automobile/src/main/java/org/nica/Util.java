package org.nica;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nica.loader.Standard;
import org.nica.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.io.Closeable;
import java.util.List;

@SuppressWarnings("unused")
public class Util {

    public static Class<?>[] classes = new Class<?>[]{Tara.class, Firma.class, Marca.class, Model.class, Automobil.class, AutomobilBmwAudi.class};
    private static SessionFactory sessionFactory = null;
    static class Transactioner implements Closeable
    {
        Session session;
        Transactioner ()
        {
            this(Util.getCurrentSession());
        }
        Transactioner (Session session)
        {
            this.session = session;
            this.session.beginTransaction();
        }

        Session get(){return session;}
        @Override
        public void close() {
            this.session.getTransaction().commit();
            this.session.close();
        }
    }
    public static class Pair<A, B> {public A a; public B b; public Pair(A a, B b){this.a=a;this.b=b;}}

    static void saveNew() {

        var tari = Tara.generateData();
        var firme = Firma.generateData(tari);
        var marci = Marca.generateData(firme);
        var modele = Model.generateData(marci);
        var automobile = Automobil.generateData(modele);

        getSessionFactory().inTransaction(session -> {
            for (var t: tari.values()) session.persist (t);
            for (var f: firme.values()) session.persist (f);
            for (var m: marci.values()) session.persist (m);
            for (var m: modele.values()) session.persist (m);
            for (var a: automobile) session.persist (a);
        });
        showAutomobile();
    }

    public static SessionFactory build(String propName)
    {
        setSessionFactory(new Standard()
                .withProps(propName)
                .addAnnotatedClass(classes)
                .build());
        return getSessionFactory();
    }

    public static void show(Class<?> cls)  {
        getSessionFactory().inTransaction(session -> session.createSelectionQuery("from " + cls.getName(), cls)
                .getResultList()
                .forEach(System.out::println)
        );
    }

    public static void showAutomobile()  {
        show(Automobil.class);
    }
    public static void showModele()  {
        show(Model.class);
    }
    public static void showTari()  {
        show(Tara.class);
    }
    public static void showFirme()  {
        show(Firma.class);
    }
    public static void showMarci()  {
        show(Marca.class);
    }

    public static List<Automobil> getAutomobile() {
        /* */
        Session session = getCurrentSession();
        session.beginTransaction();
        List<Automobil> ret = session.createSelectionQuery("from Automobil", Automobil.class)
                .getResultList();
        session.getTransaction().commit();
        session.close();
        return ret;
    }

    static private String loadProps = "hibernate.load.properties";
    static private String createProps = "hibernate.create.properties";
    public static String getLoadProps(){return loadProps;}
    public static String getCreateProps(){return createProps;}
    public static void setDefaultProps (String loadProps, String createProps)
    {
        Util.loadProps = loadProps;
        Util.createProps = createProps;
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) sessionFactory = build(getLoadProps());
        return sessionFactory;
    }
    public static Session getCurrentSession()
    {
        return getSessionFactory().getCurrentSession();
    }
    public static void setSessionFactory(SessionFactory sessionFactory) {
        Util.sessionFactory = sessionFactory;
    }
    public static void resetSessionFactory() {
        Util.sessionFactory = null;
    }


}
