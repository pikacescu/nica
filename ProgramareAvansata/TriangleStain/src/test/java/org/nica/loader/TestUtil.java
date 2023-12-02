package org.nica.loader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.nica.Util.getResourcePath;
import static org.nica.Util.revertDefaultLoadConfigs;


//@SuppressWarnings({"SameParameterValue", "unused"})
@SuppressWarnings({"unused"})
public class TestUtil
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
    @org.junit.jupiter.api.Test
    void tech() {
        System.out.println (new File("").getAbsolutePath());
        System.out.println (getResourcePath(getTestLoadXmlCfg()));

        URL fl = Config.class.getClassLoader().getResource(getTestLoadXmlCfg());
        if (fl == null) fl = Config.class.getClassLoader().getResource(".");
        System.out.println(new File(Objects.requireNonNull(fl).getFile()).getPath());
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
