package org.nica.loader;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Properties;

@SuppressWarnings({"SameParameterValue", "unused"})
public class Config {
    private Configuration cfg = new Configuration();
    public Config(){}
    public Config withXml()
    {
        cfg.configure(); //"hibernate.cfg.xml"
        return this;
    }
    Config withProps() //do nothing
    {
        return this; //"hibernate.properties"
    }
    public Config withXml(String xml) //"hibernate.cfg.xml"
    {
        cfg.configure(xml);
        return this;
    }
    private String getResourcePath(String path){
        //System.out.println (new File("").getAbsolutePath()); //get current path
        URL fl = this.getClass().getClassLoader().getResource(path);
        if (fl == null) return "";
        return fl.getPath();
    }
    public Config withProps(String props) throws Exception
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream(getResourcePath(props))); //"hibernate.properties"
        cfg = new Configuration().addProperties(properties);
        return this;
    }
    Config withPath(String path) throws Exception
    {
        Properties properties = new Properties();
        properties.load(new FileInputStream(path));
        cfg = new Configuration().addProperties(properties);
        return this;
    }
    Config withXmlPath(String xml) throws Exception
    {
        cfg.configure(new File(xml));
        return this;
    }
    Config withPropsPath(String path) throws Exception
    {
        return withPath (path);
    }
    public Config addAnnotatedClass(Class<?>... cls)
    {
        for (Class<?> cl: cls)
            cfg.addAnnotatedClass(cl);
        return this;
    }
    public SessionFactory build()
    {
        return cfg.buildSessionFactory();
    }
}
