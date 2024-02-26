package org.nica.loader;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class Standard {
    StandardServiceRegistryBuilder registry = new StandardServiceRegistryBuilder();
    MetadataSources srcs = null;
    MetadataSources getMetadataSources()
    {
        if (srcs == null) srcs = new MetadataSources(registry.build());
        return srcs;
    }
    public Standard(){}

    public Standard withProps(String props) //by default "hibernate.properties"
    {
        registry.loadProperties(props);
        return this;
    }
    public Standard addAnnotatedClass(Class<?>... cls)
    {
        MetadataSources srcs = getMetadataSources();
        for (Class<?> cl: cls)
            srcs.addAnnotatedClass(cl);
        return this;
    }
    public SessionFactory build()
    {
        MetadataSources srcs = getMetadataSources();
        return srcs.buildMetadata().buildSessionFactory();
    }
}
