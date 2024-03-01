/*
 * SocketSelector.java
 *
 * Created on 07 ianuarie 2006, 11:21
 */

package com.videochat.server;

import java.net.Socket;
import java.io.DataInputStream;

import com.videochat.common.Parser;

import java.net.ServerSocket;

/**
 *
 * @author oleg1
 *this class will be used to determine the fate of the socket
 */
public class SocketSelector extends Thread
{
    Parser parser;
    ServerSocket serverSocket;
    UserArray users;
    /** Creates a new instance of SocketSelector */
    public SocketSelector( UserArray _users )
    {
        parser = new com.videochat.common.Parser();
        try
        {
            serverSocket = new ServerSocket( 8889 );
        }
        catch( java.io.IOException ioe )
        {
            ioe.printStackTrace();
        }
        users = _users;
        start();
    }
    
    /*
     *this function will do the actual work
     *of determining the fate of socket
     */
    public Object select( Socket socket )
    {
        System.out.println( "SocketSelector::select()" );
        
        try
        {
            if( socket==null ) return null;
            System.out.println("etapa 1");
            
            DataInputStream dis = new DataInputStream( socket.getInputStream() );
            System.out.println("etapa 2");
            socket.setSoTimeout(50000);
            
            String command = null;
            try
            {
                 command = dis.readUTF();
            }
            catch( java.io.EOFException eofe )
            {
                eofe.printStackTrace();
            }
            if( command!=null ) System.out.println("command = " + command);
            else System.out.println("command=null");
            if( command!=null && parser.parse( command ) )
            {
                System.out.println("S-a partitionat");
                /*
                 * actual determination of socket fate
                 */
                if(parser.getCommand().trim().equals("connect"))
                {
                    socket.setSoTimeout(0);
                    Svr server= new Svr( socket );
                    return server; 
                }
                else if(parser.getCommand().trim().equals("media"))
                {
                    if (socket != null) throw new NullPointerException("command is media");
                    /*
                     *creation of a new media socket for a certain user;
                     *val 1 in parser must be the user
                     */
                    socket.setSoTimeout(0);
                    String userName  = parser.val(0);
                    if( userName!=null && !userName.equals("") )
                    {
                        //gasirea userului dat
                        //stabilirea media socketului
                        User user = users.get( userName );
                        if( user == null )  socket.close();
                    }
                    else
                    {
                        socket.close();
                    }
                    return null;
                }
            }
            else
            {
                /*
                 *line received could not be parsed and this socket is going to be closed             
                 */
                if( command==null ) System.out.println("stringul este null");
                else System.out.println(" command= " + command);
                System.out.println("Nu s-a partitionat");
                socket.close();
                return null;
            } 
            
        }
        catch( java.net.SocketTimeoutException ste )
        {
            ste.printStackTrace();
            try
            {
                socket.close();
            }
            catch( java.io.IOException ioe )
            {
                ioe.printStackTrace();
            }
        }
        catch( java.io.IOException ioe  )
        {
            ioe.printStackTrace();
            try
            {
                if( socket!=null )socket.close();
            }
            catch( java.io.IOException ioe1 )
            {
                ioe1.printStackTrace();
            }
            return null;
        }
        return null;
        
    }
    boolean toRun = true;
    
    public void run()
    {
        while(toRun)
        {
            try
            {
                Socket socket = serverSocket.accept();
                Object o = select( socket);
                if( o!=null )
                {
                    socket.close();
                }
            }
            catch( java.io.IOException ioe )
            {
                ioe.printStackTrace();
            }
        }
    }
    
    public void close()
    {
        toRun = false;
        try
        {
            serverSocket.close();
        }
        catch( java.io.IOException ioe )
        {
            ioe.printStackTrace();
        }
    }
}
