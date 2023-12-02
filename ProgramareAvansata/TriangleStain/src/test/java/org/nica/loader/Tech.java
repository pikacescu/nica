package org.nica.loader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.*;
import static org.nica.Util.getResourcePath;
import static org.nica.Util.revertDefaultLoadConfigs;

public class Tech
{
    public static void assertNoThrow(Executable executable) {
        assertNoThrow(executable, "must not throw");
    }
    public static void assertNoThrow(Executable executable, String message) {
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
        assertThrows (NullPointerException.class, () -> {throw new NullPointerException("salut");});
        assertThrows (Exception.class, () -> {throw new Exception("salut");}, "must throw NullPointerException");
    }
    @Test
    protected void techStandardAssertDoesNotThrow()
    {
        assertDoesNotThrow(() -> {});
    }
    /*
    public static void loadFromDb(SessionFactory sessionFactory)  {

        sessionFactory.inTransaction(session -> {
            session.createSelectionQuery("from Event", Event.class)
                    .getResultList()
                    .forEach(event -> System.out.println("Event (" + event.getDate() + ") : " + event.getTitle()));
        });
    }
    public static void loadFromDbVerbal(SessionFactory sessionFactory)  throws Exception {
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
    } //*/
    @org.junit.jupiter.api.Test
    void tech() throws MalformedURLException {
        System.out.println (new File("").getAbsolutePath());
        System.out.println (getResourcePath(getTestLoadXmlCfg()));

            URL fl = Config.class.getClassLoader().getResource(getTestLoadXmlCfg());
            if (fl == null) fl = new URL ("");
            //fl.getFile()
            System.out.println(new File(fl.getFile()).getPath());
            System.out.println(fl.getPath());

    }
    public static String getTestLoadXmlCfg(){return "hibernate.test.load.cfg.xml";}
    public static String getTestDefaultXmlCfg(){return "hibernate.cfg.xml";}
    public static String getTestLoadProps(){return "hibernate.test.load.properties";}
    public static String getTestDefaultLoadProps(){return "hibernate.properties";}

    public static void buildTestDefaultLoadConfigs() throws IOException {
        revertDefaultLoadConfigs();
        Files.copy(getResourcePath(getTestLoadXmlCfg()), getResourcePath(getTestDefaultXmlCfg()));
        Files.copy(getResourcePath(getTestLoadProps()), getResourcePath(getTestDefaultLoadProps()));
    }
    public static void revertTestDefaultLoadConfigs() throws IOException {
        Files.deleteIfExists(getResourcePath(getTestDefaultXmlCfg()));
        Files.deleteIfExists(getResourcePath(getTestDefaultLoadProps()));
    }

}
