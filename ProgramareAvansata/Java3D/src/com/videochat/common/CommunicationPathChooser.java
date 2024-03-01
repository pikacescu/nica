/*
 * CommunicationPathChooser.java
 *
 * Created on 09 ianuarie 2006, 20:52
 */

package com.videochat.common;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 *
 * @author olegciobanu
 */
public class CommunicationPathChooser extends Thread
{
    DatagramSocket receiver, transmitter;
    boolean toRun = true;
    
    /** Creates a new instance of CommunicationPathChooser */
    public CommunicationPathChooser()
    {
        try
        {
            receiver = new DatagramSocket();
            transmitter = new DatagramSocket();
        }
        catch( java.net.SocketException se )
        {
            //noinspection CallToPrintStackTrace
            se.printStackTrace();
        }
    }
    
    public int getReceiverPort()
    {
        if( receiver!=null )
        {
            return receiver.getLocalPort();
        }
        return -1;
    }
    
    public void run()
    {
        byte[] ba = new byte[1024];
        DatagramPacket packet = new DatagramPacket( ba, ba.length );
        while( toRun )
        {
            try
            {
                receiver.receive( packet );
                String port = new String( packet.getData() );
                int numericPort;
                numericPort =  Integer.parseInt( port.trim() );
                packet.setPort( numericPort );
                packet.setAddress( packet.getAddress() );
                transmitter.send( packet );
            }
            catch( IOException ioe )
            {
                //noinspection CallToPrintStackTrace
                ioe.printStackTrace();
            }                
        }
    }

    public void setToRun( boolean value )
    {
        toRun = value;
    }
    
    public void close()
    {
        receiver.close();
        transmitter.close();
    }
}
