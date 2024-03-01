/*
 * Request.java
 *
 * Created on 8 ������ 2005 �., 23:22
 */

package com.videochat.server;

import java.net.*;
import java.util.regex.*;

/**
 *
 * @author admin
 */
public class Request
{
    String name;
    String firstHalf;    
    SessionAddress asa, vsa;
    int index;
    User user;
        
    public Request( String _name, int _ardPort, int _ardcPort, int _vrdPort, int _vrdcPort, InetAddress ip, User _user )
    {
        name = _name;        
        asa = new SessionAddress( ip, _ardPort, ip, _ardcPort );
        vsa = new SessionAddress( ip, _vrdPort, ip, _vrdcPort );       
        Pattern p= Pattern.compile( "(.*)([1-3])" ); 
        Matcher m = p.matcher(name);
        if( m.matches() )
        {
            firstHalf = m.group(1);
            index = Integer.parseInt( m.group(2), 10 );
            asa.setIndex( index );
            vsa.setIndex( index );
        }
        else firstHalf = null;
        user = _user;
        System.out.println("constructorul la clasa request request");
        System.out.println("firstHalf = " + firstHalf);
        System.out.println("index = " + index);        
    }
    
    public Request( String _name, User _user )
    {
        name = _name;
        
        asa = vsa = new SessionAddress( _user );
        
        Pattern p= Pattern.compile( "(.*)([1-3])" ); 
        Matcher m = p.matcher(name);
        if( m.matches() )
        {
            firstHalf = m.group(1);
            index = Integer.parseInt( m.group(2), 10 );
            asa.setIndex( index );
            vsa.setIndex( index );
        }
        else firstHalf = null;
        user = _user;
        System.out.println("constructorul la clasa request request");
        System.out.println("firstHalf = " + firstHalf);
        System.out.println("index = " + index); 
    }
    
    synchronized public SessionAddress getASA()
    {
        return asa;
    }
    
    synchronized public SessionAddress getVSA()
    {
        return vsa;
    }
    
    synchronized public void setVSA( SessionAddress _sa )
    {
        if( _sa==null )return;
        System.out.println("setVSA()1");
        vsa = _sa;
        vsa.setIndex( index );
        System.out.println("setVSA()2");
    }
    
    synchronized public void setASA( SessionAddress _sa )
    {
        if( _sa==null )return;
        System.out.println("setASA()1");
        asa = _sa;
        asa.setIndex( index );
        System.out.println("setASA()2");
    }
    
    synchronized public String getName()
    {
        return name;
    }
    
    public String getFirstHalf()
    {
        return firstHalf;
    }
    
    synchronized public int getIndex()
    {
        return index;
    }
    
    synchronized public User getUser()
    {
        return user;
    }
}
