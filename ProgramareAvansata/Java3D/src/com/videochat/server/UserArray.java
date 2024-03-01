package com.videochat.server;

import java.util.*;

public class UserArray extends ArrayList<User>
{
    
    //this parameter is for lists of users that will function as private rooms
    String name;
    boolean empty;
    String password;
       
    public void setPassword( String new_password )
    {
        password = new_password;
    }
    
    public String getPassword()
    {
        return password;
    }
    
    public boolean getEmpty()
    {
        return empty;
    }
    
    public void setEmpty( boolean val )
    {
        empty = val;
    }
    
    synchronized public String getName()
    {
        return name;
    }
    
    synchronized public void setName( String new_name )
    {
        name = new_name;
    }
    
    synchronized public String info()
    {
        StringBuilder result = new StringBuilder(name);

        result.append("/").append((password.isEmpty()) ? "no" : "yes");
        synchronized(this)
        {
            for (User user : this) {
                result.append("/").append(user.getName());
            }
        }
        System.out.println( "result = " + result );
        return result.toString();
    }
    
    synchronized List<User> getUsers()
    {
        return this;
    }

    synchronized  void write(String str)
    {
        for (User localUser : this) {
            System.out.println("userului " + localUser + " i se va trimite lista de useri.");
            localUser.write(str);
        }
            
    }

    synchronized void admin_write(String str)
    {
        for (User u : this) {
            if (u.isAdmin) u.write(str);
        }
    }

    @SuppressWarnings("SameParameterValue")
    synchronized void write_from(String strPre, String strPost, User usr)
    {
        for (User sr : this) {
            if (sr.ignored.exists(usr.userName) || sr.ignoredBy.exists(usr.userName)) continue;
            sr.write(strPre + usr.userName + strPost);
        }
    }

    synchronized void del(String name)
    {
        synchronized( this ){
            for (User u : this) {
                if (u.userName.equals(name)) remove(u);
            }
        }
    }
    synchronized void del_inspect(User ru)
    {
        if( ru==null ) return;
        ru.close();
        
        remove( ru );

        for (User u : this) {
            synchronized (this) {
                u.ignored.remove(ru);
            }
            synchronized (this) {
                ru.ignoredBy.remove(ru);
            }
            if (u.getSolicitors().contains(ru)) u.removeSolicitor(ru);
        }
        
        ru.removeAllSolicitors();
    }

    synchronized boolean exists(String name)
    {
        for (User u : this) {
            if (u.userName.equals(name)) return true;
        }
        return false;
    }

    synchronized boolean has(String name)
    {
        for (User user : this) {
            if (user.userName.equals(name)) return true;
        }
        return false;
    }

    synchronized public User get(String name)
    {
        if(name == null)return null;
        if(name.trim().isEmpty())return null;

        for (User u : this) {
            if (u.userName.equals(name)) return u;
        }
        return null;
    }

    synchronized String get_IP(String name)
    {
        if(name == null)return null;
        if(name.trim().isEmpty())return null;

        for (User u : this) {
            if (u.userName.equals(name)) return u.connection.socket.getInetAddress().getHostAddress().trim();
        }
        return null;
    }

    synchronized void setAdmin(String userName, boolean val)
    {

        for (User u : this) {
            if (u.userName.equals(userName)) {
                u.isAdmin = val;
                u.write("command=server_set_admin;value=" + val + ";:");
                u.ignoredBy.clear();
                for (User user : this) user.ignored.remove(user);
            }

        }
    }

    synchronized void kick(String userName)
    {
        User usr = get(userName);
        if(usr == null)return;
        usr.kick();
    }

    synchronized void block_by_IP(String IP)
    {
        for (User usr : this) {
            if (usr.connection.socket.getInetAddress().getHostAddress().trim().equals(IP)) {
                Svr.banList.add(usr.userName);
                usr.kick();
            }
        }
    }

    synchronized void resetAllAdmins()
    {
        for (User u : this) {
            u.isAdmin = false;
            u.write("command=server_set_admin;value=false;:");
        }
    }
}