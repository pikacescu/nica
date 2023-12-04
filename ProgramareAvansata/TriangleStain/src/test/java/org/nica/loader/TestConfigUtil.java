package org.nica.loader;

import java.io.IOException;
import java.nio.file.Files;

import static org.nica.Util.getResourcePath;
import static org.nica.Util.revertDefaultConfigs;


//@SuppressWarnings({"SameParameterValue", "unused"})
@SuppressWarnings({"unused"})
public class TestConfigUtil
{

    public static String getTestLoadXmlCfg(){return "hibernate.test.load.cfg.xml";}
    public static String getTestLoadProps() {return "hibernate.test.load.properties";}
    public static String getTestCreateXmlCfg(){return "hibernate.test.create.cfg.xml";}
    public static String getTestCreateProps(){return "hibernate.test.create.properties";}
    public static String getTestDefaultXmlCfg(){return "hibernate.cfg.xml";}
    public static String getTestDefaultProps(){return "hibernate.properties";}

    public static void buildTestDefaultLoadConfigs() throws IOException {
        revertDefaultConfigs();
        Files.copy(getResourcePath(getTestLoadXmlCfg()), getResourcePath(getTestDefaultXmlCfg()));
        Files.copy(getResourcePath(getTestLoadProps()), getResourcePath(getTestDefaultProps()));
    }
    public static void buildTestDefaultCreateConfigs() throws IOException {
        revertDefaultConfigs();
        Files.copy(getResourcePath(getTestLoadXmlCfg()), getResourcePath(getTestDefaultXmlCfg()));
        Files.copy(getResourcePath(getTestLoadProps()), getResourcePath(getTestDefaultProps()));
    }

    public static void revertTestDefaultConfigs() throws IOException {
        Files.deleteIfExists(getResourcePath(getTestDefaultXmlCfg()));
        Files.deleteIfExists(getResourcePath(getTestDefaultProps()));
    }

}
