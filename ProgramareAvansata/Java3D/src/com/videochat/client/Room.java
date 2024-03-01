package com.videochat.client;

import java.util.ArrayList;

public class Room {
    //the name of the room and a list of dwellers in this room
    String name;
    boolean password;
    ArrayList<String> roomDwellers;
    StringBuilder roomDwellersInfo = new StringBuilder();

    Room(String val) {
        roomDwellers = new ArrayList<>();
        System.out.println("Room():" + val);
        String[] container = val.split("/");
        int length = container.length;
        if (length > 0) {
            name = container[0];
            password = container[1].equals("yes");
            System.out.println(name);
            for (int index = 2; index < length; index++) {
                String userName = container[index];
                if (!Cln.getAnIgnoredBy(userName))
                    roomDwellers.add(userName);
            }
        }
        roomDwellersInfo.setLength(0);

        synchronized (this) {
            for (String roomDweller : roomDwellers) {
                roomDwellersInfo.append(roomDweller).append(";");
            }
        }

    }

    synchronized String getName() {
        return name;
    }

    //this function is designed to return the tooltip for the rendered label,
    //that will represent this object in the list
    //it returns a comma separated list of users
    synchronized String getRoomDwellersInfo() {
        return roomDwellersInfo.toString();
    }

    synchronized int getNumberOfDwellers() {
        return roomDwellers.size();
    }

    boolean getPassword() {
        return password;
    }
}
