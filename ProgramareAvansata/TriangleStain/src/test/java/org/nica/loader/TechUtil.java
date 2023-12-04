package org.nica.loader;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.net.URL;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;
import static org.nica.Util.getResourcePath;
import static org.nica.loader.TestConfigUtil.getTestLoadXmlCfg;


//@SuppressWarnings({"SameParameterValue", "unused"})
@SuppressWarnings({"unused"})
public class TechUtil
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
    @Test
    void tech() {
        System.out.println (new File("").getAbsolutePath());
        System.out.println (getResourcePath(getTestLoadXmlCfg()));

        URL fl = Config.class.getClassLoader().getResource(getTestLoadXmlCfg());
        if (fl == null) fl = Config.class.getClassLoader().getResource(".");
        System.out.println(new File(Objects.requireNonNull(fl).getFile()).getPath());
        System.out.println(fl.getPath());

    }

}
