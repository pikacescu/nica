package com.videochat.server;

import java.net.*;

public class SessionAddress
{
    InetAddress dataAddress, controlAddress;
    int dataPort, controlPort;
    int index; //index of request

    User user;
    
    SessionAddress( InetAddress _dataAddress, int _dataPort, InetAddress _controlAddress, int _controlPort )
    {
        dataAddress = _dataAddress;
        dataPort = _dataPort;
        controlAddress = _controlAddress;
        controlPort = _controlPort;
        user = null;
    }
    
    SessionAddress( User _user)
    {
        user = _user;
        dataAddress = null;
        dataPort = -1;
        controlAddress = null;
        controlPort = -1;
    }

    public String toString()
    {
        return "SessionAddress:" +
                "dataAddress:" + dataAddress + 
                ";Dataport" + dataPort + ";"+
                "controlAddress" + controlAddress + ";" +
                "controlPort" + controlPort + ";\n";
    }

    public void setIndex( int _index )
    {
        index = _index;
    }
    
    public int getIndex()
    {
        return index;
    }
}