package com.videochat.server;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class User
{
    InetAddress address;

    boolean isConnected = false;

    UserArray ignored;
    UserArray ignoredBy;
    List<Request> solicitors = Collections.synchronizedList(new ArrayList<>() );

    boolean isAdmin;
    String userName = "Guest";
    public Svr connection;
    int delay = 0;
    int inactivityTimeout = 0;
    boolean busy = false;
    boolean isAnonymous = false;

    int timeSpeakLevel = 0;
    int speakLevel = 0;

    boolean video = false;
    boolean audio = false;
    boolean ready = false;


    String room;


    public void setRoom( String val )
    {
        room = val;
    }

    public String getRoom()
    {
        return room;
    }

    synchronized public void setReady( boolean _ready )
    {
        ready = _ready;
    }

    synchronized public boolean getReady()
    {
        return ready;
    }

    synchronized public String info()
    {
        String info = "";
        info += "\"" + userName + "\"/";

        if( audio ) info += "\"yes\"/";
        else info += "\"no\"/";

        if( video ) info +="\"yes\"";
        else info +="\"no\"";
        System.out.println("info = " + info);

        return info;
    }

    //removes request that has certain name( composed of user name + index )
    synchronized public void removeSolicitor( String name )
    {
        if( name==null )return;

        //to find the request by name

        Request r= null;
        synchronized( solicitors ){
            for (Request solicitor : solicitors) {
                r = solicitor;
                if (r.getName().equals(name)) {
                    break;
                } else r = null;
            }
        }

        if( r!=null )
        {
            solicitors.remove( r );
        }


    }

    //removes a certain request r from the requests of this user
    synchronized private void removeSolicitor( Request r )
    {
        if( r == null ) return;
        solicitors.remove( r );

    }

        //the next function must remove all requests that belong to the User u
        //that is passed as an argument the function

        synchronized public void removeSolicitor( User u )
        {
            if( u==null ) return ;

            Request r;
            if(!solicitors.isEmpty()) r = solicitors.get(0);
            else r = null;

            while( r!=null )
            {
                r = null;
                synchronized( solicitors )
                {
                    for (Request solicitor : solicitors) {
                        r = solicitor;
                        if (r.getFirstHalf().equals(u.getName())) {
                            break;
                        } else r = null;
                    }
                }
                if( r!=null ) removeSolicitor( r );
            }
        }

        //removes all requests from the current user( it's RTPManager  )
	synchronized public void removeAllSolicitors()
	{
            synchronized( solicitors )
            {
                while(!solicitors.isEmpty())
                {
                    Request u = solicitors.get(0);
                    System.out.println( "u.getName() =" + u.getName() );
                    User user = u.getUser();
                    user.write("command=close_receiver;index=" + u.getIndex() + ";:");
                    removeSolicitor( u );
                }
            }
	}

    synchronized public List<Request> getSolicitors()
	{
		return solicitors;
	}

	synchronized public String getName()
	{
		return userName;
	}
	synchronized public void setName( String val )
	{
		this.userName = val;
	}

    synchronized void kick()
    {
        write("command=server_kick;:");
        try
        {
            connection.socket.close();
        }catch(Exception err)
        {
            //noinspection CallToPrintStackTrace
            err.printStackTrace();
        }
    }

    synchronized void setIgnoreList(String[] _users)
    {
        if(_users == null)return;
        for (String user : _users) {
            System.out.println("deleting> " + user);
            ignored.del(user);
        }
        System.out.println("command=restored_by;user=" + userName.trim() + ";:");
        ignored.write("command=restored_by;user=" + userName.trim() + ";:");
        ignored.clear();
        for (String userName : _users) {
            User user = Svr.users.get(userName);
            if (!user.isAdmin) ignored.add(user);
            user.ignoredBy.add(this);
        }
    }

    synchronized void resetIgnoreList()
    {
        ignored.write("command=restored_by;user=" + userName.trim() + ";:");
        synchronized(ignored)
        {
            for (User user : ignored) {
                user.ignoredBy.remove(this);
            }
	    }
        ignored.clear();
    }

    User(Svr _connection, boolean _isAdmin)
    {
        connection = _connection;
        isAdmin = _isAdmin;
        ignored = new UserArray();
        ignoredBy = new UserArray();

        address = connection.socket.getInetAddress();

        room = "lobby";
    }

    synchronized void user_say(String _userName, String str)//this user say to _userName
    {
        if(!_userName.equals("server"))
        {
            if(ignored.has(_userName))return;
            if(ignoredBy.has(_userName))return;
        }
        this.busy = true;
        connection.writer.single_write(str);
        delay = 0;
        this.busy = true;
    }

    synchronized void close()
    {
        try
        {
            connection.socket.close();
        }
        catch( java.io.IOException ioe )
        {
            //noinspection CallToPrintStackTrace
            ioe.printStackTrace();
        }
        connection.toRun = false;
    }

    synchronized void setBusy(boolean busy)
    {
        this.busy = busy;
    }


    synchronized public void write(String str)
    {
        this.busy = true;
        try
        {
            //System.out.println("  .." + userName + ">" + str);
            if( !connection.socket.isClosed() )
            {
                System.out.println("socketul nu este inchis");
                System.out.println("se va trimite comanda : " + str);
                connection.writer.single_write(str);
            }
            else System.out.println("socket-ul este inchis");
        }
        catch(Exception err)
        {
            //noinspection CallToPrintStackTrace
            err.printStackTrace();
        }
        this.busy = false;
        delay = 0;
    }

    synchronized boolean isClosed()
    {
        return connection.socket.isClosed();
    }

    synchronized public String toString()
    {
        return userName;
    }
    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass")
    synchronized public boolean equals(Object o)
    {
        return userName.equals(o.toString());
    }

}

