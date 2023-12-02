package org.nica;

import org.hibernate.SessionFactory;
import org.nica.loader.Config;
import org.nica.loader.Standard;
import org.nica.model.Event;

import static java.time.LocalDateTime.now;
import static org.nica.Util.*;

public class StainMain {

    public static void main(String[] args) throws Exception {

        System.out.println("Hello world!");
        StainMain triangle = new StainMain();
        if (args.length > 0)
            switch(args[0])
            {
            case "firstrun":
                triangle.firstRun();
                triangle.saveNew();
                return;

            case "fromCustomPropResource": //not default
                sessionFactory = new Config()
                        .withProps(getLoadProps())
                        .addAnnotatedClass(Event.class)
                        .build();
                loadFromDb(sessionFactory);
                return;
            case "fromStandardProp": //not default
                sessionFactory = new org.nica.loader.Standard()
                        .withProps(getLoadProps())
                        .addAnnotatedClass(Event.class)
                        .build();
                loadFromDb(sessionFactory);
                return;
            }
        System.out.println("Nothing to be done");
    }

    private void saveNew() {
        sessionFactory.inTransaction(session -> {
            session.persist(new Event("Our very first event!", now()));
            session.persist(new Event("A follow up event", now()));
        });
        loadFromDb(sessionFactory);
    }

    static SessionFactory sessionFactory;
    protected void firstRun()  throws Exception {
        buildDefaultLoadConfigs ();
        sessionFactory = new Standard()
                .addAnnotatedClass(Event.class)
                .build();
        revertDefaultLoadConfigs() ;
        saveNew();
    }


}