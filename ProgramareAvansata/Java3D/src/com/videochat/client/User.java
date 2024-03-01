package com.videochat.client;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class User {
    String name;
    boolean ignored;

    public User(String _name) {
        name = _name;
        ignored = false;
    }

    public static User generate(String info) {
        System.out.println("User::generate():info = " + info);
        Pattern p = Pattern.compile("\"([^\"]*)\"/\"([^\"]*)\"/\"([^\"]*)\"");
        Matcher m = p.matcher(info);
        if (m.matches()) {
            System.out.println("m.group(1) :" + m.group(1));
            return new User(m.group(1));
        }
        return null;
    }

    public void setIgnored(boolean val) {
        ignored = val;
    }

    public boolean getIgnored() {
        return ignored;
    }

    public boolean equals(User u) {
        if (u == null) return false;

        return name.equals(u.getName());
    }

    public String toString() {
        return name;
    }

    public String getName() {
        System.out.println("name = " + name);
        return name;
    }

}
