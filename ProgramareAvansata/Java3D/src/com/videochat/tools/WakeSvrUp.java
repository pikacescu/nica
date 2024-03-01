package com.videochat.tools;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;

public class WakeSvrUp
{

    WakeSvrUp(){}

    public static void main(String[] args) throws Throwable
    {
        boolean isServerOpen = false;
        Socket s = null;

        int PORT = 80;
        String BIND_ADDRESS = "127.0.0.1";

        DataInputStream iniFile =
              new DataInputStream(
                new BufferedInputStream(new FileInputStream("resources/svr.ini")));
        BufferedReader ini = new BufferedReader(new InputStreamReader(iniFile));
        String strln;
        String strtk;
        @SuppressWarnings("removal")
        Integer cPort = new Integer(0);
        System.out.println("before while");
        while(ini.ready())
        {
            strln = ini.readLine();
            String[] temp = strln.trim().split( " " );
            strtk = temp[0];
            if(strtk.equals("[PORT]"))
            {
                PORT = Integer.parseInt( temp[1].trim() );
            }else if(strtk.equals("[CONNECTIONS_POOLING]"))
            {
                Integer.parseInt( temp[1].trim() );
            }else if(strtk.equals("[BIND_ADDRESS]"))
            {
                BIND_ADDRESS = temp[1].trim();
            }
        }
        System.out.println("after while");

        InetAddress ADDR = InetAddress.getByName(BIND_ADDRESS);
        System.out.println("got ADDR");
        try
        {
            s = new Socket(ADDR, PORT);
            System.out.println("socket created");
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            System.out.println("got DataOutputStream");
            out.writeUTF("command=wakeup;:");
            Thread.sleep(2000);
            System.out.println("data sent");
        }finally
        {
            s.close();
        }

        System.out.println("begin new session: " + s);

    }
}