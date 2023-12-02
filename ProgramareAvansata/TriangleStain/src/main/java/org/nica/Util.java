package org.nica;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nica.loader.Config;
import org.nica.model.Event;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;

public class Util {
    public static void loadFromDb(SessionFactory sessionFactory)  {
        /* */
        sessionFactory.inTransaction(session -> {
            session.createSelectionQuery("from Event", Event.class)
                    .getResultList()
                    .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        });
    }
    public static void loadFromDbVerbal(SessionFactory sessionFactory) {
        /* */
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();
        session.createSelectionQuery("from Event", Event.class)
                .getResultList()
                .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        session.getTransaction().commit();
        session.close();
    }
    public static Path getResourcePath(String resName){
        URL fl = Config.class.getClassLoader().getResource(".");
        return Path.of(new File(fl.getFile()).getPath(), resName);
    }

    public static String getLoadXmlCfg(){return "hibernate.load.cfg.xml";}
    public static String getDefaultXmlCfg(){return "hibernate.cfg.xml";}
    public static String getLoadProps(){return "hibernate.load.properties";}
    public static String getDefaultLoadProps(){return "hibernate.properties";}

    public static void buildDefaultLoadConfigs() throws IOException {
        revertDefaultLoadConfigs();
        Files.copy(getResourcePath(getLoadXmlCfg()), getResourcePath(getDefaultXmlCfg()));
        Files.copy(getResourcePath(getLoadProps()), getResourcePath(getDefaultLoadProps()));
    }
    public static void revertDefaultLoadConfigs() throws IOException {
        Files.deleteIfExists(getResourcePath(getDefaultXmlCfg()));
        Files.deleteIfExists(getResourcePath(getDefaultLoadProps()));
    }
}
