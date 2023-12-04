package org.nica;

import org.hibernate.SessionFactory;
import org.nica.loader.Standard;
import org.nica.model.Event;

import static org.nica.Util.*;

public class StainMain {

    public static void main(String[] args) {

        SessionFactory sessionFactory;

        if (args.length > 0)
            switch(args[0])
            {
            case "create": //not default
                sessionFactory = new Standard()
                        .withProps(getCreateProps())
                        .addAnnotatedClass(Event.class)
                        .build();
                saveNew(sessionFactory);
                loadFromDb(sessionFactory);
                return;

            case "load": //not default
                sessionFactory = new org.nica.loader.Standard()
                        .withProps(getLoadProps())
                        .addAnnotatedClass(Event.class)
                        .build();
                loadFromDb(sessionFactory);
                return;
            }
        System.out.println("Nothing to be done");
    }

}