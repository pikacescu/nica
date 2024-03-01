package com.videochat.server;

import com.videochat.common.CommunicationPathChooser;
import com.videochat.common.Parser;

import java.io.*;
import java.net.*;
import java.util.*;
import java.lang.*;

public class Svr
{
    boolean toRun = false;

    Parser ps;

    static int PORT = 80;
    static int CONNECTIONS_POOLING = 50;
    static String BIND_ADDRESS = "127.0.0.1";
    static SvrView vw = null;

    synchronized static void writeLog(String str)
    {
        try
        {
            String strDate = (new Date()).toString();
            logFile.writeUTF("\n" + strDate + ":\n" + str);
            logFile.flush();
        }
        catch(IOException err)
        {
            err.printStackTrace();
        }
    }
    
    //this function will return the room with this given name;
    synchronized static UserArray getRoom( String roomName )
    {
        UserArray room = null;
        if( roomName.equals("lobby") )
        {
            room = lobby;
        }
        else
        {
            synchronized( privateRooms )
            {
                Iterator it = privateRooms.iterator();
                while( it.hasNext() )
                {
                    room = (UserArray)it.next();
                    if( room.getName().equals( roomName ) )
                    {
                         break;
                    }
                }
            }
        }
        return room;
    }
    
    //this function will remove User from his room
    synchronized static void removeUser( User user )
    {
        UserArray room = getRoom( user.getRoom() );
        if( room!=null )
        {
            room.remove( user );
            roomStateInfo();
        }           
    }

    //this function sends information about lobby
    //and private rooms to all users registerred at the momment
    synchronized static private void roomStateInfo()
    {
        System.out.println("roomStateInfo()");
        String result;
        result ="loby=" + lobby.info()+";";
        synchronized( privateRooms )
        {
            Iterator it = privateRooms.iterator();
            while( it.hasNext() )
            {
                UserArray room = ( UserArray )it.next();
                String roomInfo = room.info();
                if( roomInfo!=null )
                {
                     result += "room=" + roomInfo + ";";
                }
            }
        }
        result+=":";
        users.write("command=room_info;" + result);
    }
        

    static TimerTask dropInspector = new TimerTask()
    {
        public synchronized void run()
        {
            //int i = 0, j = 0;
            //i = 0; j = 0;
            try
            {
               synchronized( users )
               {
                    User u = null;
                    if( users.size()>0 ) u = users.get(0);
                    while( u!=null )
                    {
                        Iterator it = users.iterator();
                        while( it.hasNext() )
                        {
                            u = (User)it.next();
                            if( u.isClosed() )break;
                            else u =null;
                        }
                        if( u!=null )
                        {
                            String delUser = u.userName;
                            users.del_inspect( u );                            
                            System.out.println("Inspected and found as dropped>>" + delUser + ": timed out");
                            if( vw!=null )vw.write("<b>server&gt;" + delUser + " timed out</b>");
                            users.write("command=server_say;user=server;:" + delUser + " timed out");
                            sendUserList();
                            removeUser( u );
                            writeLog( "Inspected and found as dropped>>" + delUser + ": timed out" + "\n" +
                                             "command=server_say;user=server;:" + delUser + " timed out");
                            if( vw!=null )vw.refreshUsers();                            
                        }
                    }
              }
            }catch(java.lang.NullPointerException err){
                //writeLog(err.printStackTrace());
                err.printStackTrace();
                System.out.println("java.lang.NullPointerException while inspecting");
                sendUserList();
                if( vw!=null )vw.refreshUsers();
            }
            catch( java.util.ConcurrentModificationException cme )
            {
                cme.printStackTrace();
            }

        }
    };

    static TimerTask floodInspector = new TimerTask()
    {
        public synchronized void run()
        {
            //try
            {
                Iterator it = users.iterator();
                while( it.hasNext() ){
                    User usr = ( User )it.next();
                    if(usr.speakLevel != 0)usr.timeSpeakLevel++;
                    if(usr.speakLevel >= 6)
                    {
                           usr.kick();
                           sendUserList();
                           if( vw!=null )vw.refreshUsers();
                    }
                    if(usr.timeSpeakLevel >= 3)
                    {
                           usr.timeSpeakLevel = 0;
                           usr.speakLevel = 0;
                    }
                }
            }

        }
    };

    static TimerTask emptyRoomInspector = new TimerTask()
    {
        public synchronized void run()
        {
            //int i = 0, j = 0;
            //i = 0; j = 0;
            try
            {
               synchronized( privateRooms )
               {
                    UserArray room = null;
                    if( privateRooms.size()>0 ) room = privateRooms.get(0);
                    
                    Iterator it = privateRooms.iterator();
                    while( it.hasNext() )
                    {
                        room = (UserArray)it.next();
                        if( room.size() > 0 )
                        {
                            room.setEmpty( false );
                        }
                        else
                        {
                            if( room.getEmpty() )
                            {
                                 it.remove();
                                 
                            }
                            else
                            {
                                room.setEmpty( true );
                            }
                        }
                    }
              }
            }
            catch(java.lang.NullPointerException err)
            {
                err.printStackTrace();
                writeLog("warning: java.lang.NullPointerException while inspecting");
                System.out.println("java.lang.NullPointerException while inspecting");
                if( vw!=null )vw.refreshUsers();
            }
            catch( java.util.ConcurrentModificationException cme )
            {
                cme.printStackTrace();
            }
            roomStateInfo();
        }
    };



    static void sendUserList()
    {
        System.out.println("userList");
        String usrCmd = "command=user_list;";

        synchronized( users ){
            Iterator it = users.iterator();
            while( it.hasNext() ){
                User u = ( User )it.next();
                if( !u.isAnonymous ) usrCmd += ("user=" + u.info() + ";");
            }
        }
        usrCmd += ":";
        users.write(usrCmd);
        System.out.println("se va trimite comanada: " + usrCmd);
        writeLog("sending user list\n" + usrCmd);
    }

    synchronized static String buildUserList()
    {
        String usrCmd = "command=user_list;";

        synchronized( users )
        {
            Iterator it = users.iterator();
            while( it.hasNext() ){
                User u = ( User )it.next();
                if( !u.isAnonymous && u.getReady() )usrCmd += ("user=" + u.info() + ";");
            }
        }
        usrCmd += ":";
        return usrCmd;
    }

    static String[] reservedNames;

    static ArrayList<String> banList = new ArrayList<>();

    static HashMap< String, String > blockList = new HashMap< String, String >();


    class SvrReader extends Thread
    {
        Parser parser = new Parser();

        DataInputStream  in;
        private Svr s;
        private Socket socket;
        private boolean isBad = true;
        SvrReader(Socket _socket, Svr _s)
        {
            try
            {
                socket = _socket;
                s = _s;
                in = new DataInputStream(socket.getInputStream());
                isBad = false;
            }catch(IOException err)
            {
                err.printStackTrace();
            }
        }
        void kickUser(String reason)
        {
            try
            {
                 s.toRun = false;
                 users.remove(usr);
                 s.writeLog("kick user because of: " + reason);
                 writer.single_write("command=server_kick;:");
                 socket.close();
            }catch(IOException err)
            {
                err.printStackTrace();
            }
            if( vw!=null )vw.refreshUsers();
        }
        
        void kickUser()
        {
            try
            {
                 s.toRun = false;
                 users.remove(usr);
                 writeLog("kick user because of: no reason");
                 writer.single_write("command=server_kick;:");
                 socket.close();
            }catch(IOException err)
            {
                err.printStackTrace();
            }
            if( vw!=null )vw.refreshUsers();
        }
        
        private void changeTheRoom( String formerRoomName, String futureRoomName, String password, User user  )
        {
            if( formerRoomName!=null && futureRoomName!=null && password!=null )
            {
                UserArray formerRoom, futureRoom;
                formerRoom = getRoom( formerRoomName );
                futureRoom = getRoom( futureRoomName );
                if( formerRoomName!=null && futureRoomName!=null && futureRoom.getPassword().equals(password) )
                {
                    formerRoom.remove( user );
                    futureRoom.add( user );
                    user.setRoom( futureRoomName );
                    roomStateInfo();
                    if( !formerRoomName.equals("lobby") ) formerRoom.write("command=server_say;user=server;:" + usr.getName() + " quitted the room");
                    if( !futureRoomName.equals("lobby") ) futureRoom.write("command=server_say;user=server;:" + usr.getName() + " enterred the room");
                }

            }
        }
        
        synchronized public void run(){
            if(isBad)return;
            try
            {
                String strCommand;
                String strUser;
                usr = new User(s, false);
                while(s.toRun)
                {
                    String str = null;
                    str = in.readUTF();
                    System.out.println(str);
                    usr.inactivityTimeout = 0;
                    str = str.trim();
                    writeLog(str);

                    if(!parser.parse( str ))
                    {
                        System.out.println("malformed string");
                        try{socket.close();}catch(Throwable e){ e.printStackTrace(); }
                        return;
                    }

                    System.out.println( "firul numarul: " + getPersonal() );

                    if(parser.getCommand().equals("wakeup"))
                    {
                        if(   s.socket.getInetAddress().getHostAddress().trim().equals( socket.getInetAddress().getHostAddress() )   )
                        {
                            System.out.println("good try to wake up");
                            if( vw!=null )vw.wakeUp();
                        }else
                        {
                            System.out.println("bad try to wake up");
                            try{socket.close();}catch(Throwable e){}
                        }
                        return;
                    }
                    else if(parser.getCommand().equals("stop"))
                    {
                        if(   s.socket.getInetAddress().getHostAddress().trim().equals( socket.getInetAddress().getHostAddress() )   )
                        {
                            System.out.println("good try to stop");
                            stop = true;
                            s.s.close();
                            synchronized( users ){
                                while( users.size()>0 ){
                                    User u = users.get(0);
                                    u.kick();
                                    users.del_inspect( u );
                                    removeUser( u );
                                }
                            }
                            System.exit(1);
                        }else
                        {
                            System.out.println("bad try to wake up");
                            try{socket.close();}catch(Throwable e){}
                        }
                        return;
                    }


                    if(usr.isConnected)
                    {
                        if(parser.getCommand().startsWith("command=admin_"))
                        {
                            if(!usr.isAdmin)
                            {
                                System.out.println("not allowed admin");
                                writeLog("kick {" + usr.userName + "} because if not allowed admin actions");
                                usr.kick();
                                return;
                            }
                        }
                        if(parser.getCommand().equals("say"))
                        {
                             if(usr.isAnonymous && !(usr.isAdmin))
                             {
                                 kickUser("anonymous are not allowed to say");
                                 break;
                             }
                             if(!(parser.getNumberOfParameters() == 0)){try{socket.close();}catch(Throwable e){};return;}

                             usr.speakLevel++;
                             String htm = parser.parseAllTags(parser.getStrInfo());
                             System.out.println( "htm=" + htm );
                             UserArray room = getRoom( usr.getRoom() );
                             if( room!=null )
                             {
                                 room.write_from("command=server_say;user=", ";:" + htm, usr);
                             }
                             if( vw!=null )vw.write("<b>" + usr.userName + "&gt;</b>" + htm);
                        }
                        else if(parser.getCommand().equals("private_say_to"))
                        {
                             if(usr.isAnonymous && !(usr.isAdmin))
                             {
                                 kickUser("anonymous are not allowed to private_say_to");
                                 break;
                             }
                             if(!(parser.getNumberOfParameters() == 1))
                             {
                                 try
                                 {
                                         socket.close();
                                 }
                                 catch(Throwable e)
                                 {};
                                 return;
                             }
                             if(!parser.param(0).equals("user"))
                             {
                                 try
                                 {
                                         socket.close();
                                 }catch(Throwable e)
                                 {}
                                 ;
                                 return;
                             }
                             User usrTo = users.get(parser.val(0));
                             if(!(usrTo == null))
                             {
                                 if(
                                     (!
                                       (
                                          (usrTo.ignored.exists(parser.val(0))) ||
                                          (usrTo.ignoredBy.exists(parser.val(0)))
                                       )
                                     ) || usr.isAdmin
                                   )
                                 {
                                     writer.single_write(
                                             "command=server_private_say_to;from=" + usr.userName + ";to=" +
                                             usrTo.userName +
                                             ";:" + parser.getStrInfo());
                                     usrTo.user_say(usr.userName,
                                             "command=server_private_say_to;from=" + usr.userName + ";to=" +
                                             usrTo.userName +
                                             ";:" + parser.getStrInfo());
                                 }
                             }

                        }else if(parser.getCommand().equals("say_only_to"))
                        {
                            if(usr.isAnonymous && !(usr.isAdmin))
                            {
                                kickUser("anonymous are not allowed to say_only");
                                break;
                            }
                            if(!(parser.getNumberOfParameters() == 1)){try{socket.close();}catch(Throwable e){};return;}
                            if(!parser.param(0).equals("user")){try{socket.close();}catch(Throwable e){};return;}
                            User usrTo = users.get(parser.val(0));
                            String htm = parser.parseAllTags(parser.getStrInfo());
                            if(!(usrTo == null))
                            {
                                writer.single_write(
                                        "command=server_say_only_to;from=" + usr.userName + ";to=" +
                                        usrTo.userName +
                                        ";:" + htm);
                                usrTo.user_say(usr.userName,
                                        "command=server_say_only_to;from=" + usr.userName + ";to=" +
                                        usrTo.userName +
                                        ";:" + htm);
                            }else
                            {
                                System.out.println("null user");
                            }
                        }else if(parser.getCommand().equals("request_user_list"))
                        {
                            writer.single_write(buildUserList());
                        }else if(parser.getCommand().equals("request_all_refresh_user_list"))
                        {
                                users.write(buildUserList());
                                if( vw!=null )vw.refreshUsers();
                        }else if(parser.getCommand().equals("disconnect"))
                        {
                             System.out.println(usr.userName + ":disconnected");
                             if(!usr.isAnonymous)
                             {
                                 users.write("command=server_say;user=server;:" + usr.userName + " disconnected");
                                 //addToHistory("<b>server&gt;" + usr.userName + " disconneted</b>");
                                 if( vw!=null )vw.write("<b>server&gt;" + usr.userName + " disconneted</b>");
                             }
                             s.toRun = false;
                                System.out.println("usr = " + usr);
                                System.out.println("users = " + users); 
                                users.del_inspect( usr );
                                 removeUser( usr );
                                 socket.close();
                             if( vw!=null ) vw.refreshUsers();
                             sendUserList();
                        }
                        else if(parser.getCommand().equals("undo_ignore"))
                        {
                            if(!(parser.getNumberOfParameters() == 1)){try{socket.close();}catch(Throwable e){};return;}//verification
                            if(!parser.param(0).equals("user")){try{socket.close();}catch(Throwable e){ e.printStackTrace(); };return;}//verification
                            
                            User ignored = users.get(parser.val(0));
                            if(!(ignored == null))
                            {
                                usr.ignored.remove( ignored );
                                usr.write("command=server_undo_ignore;user="+ignored.info()+";:");

                                ignored.ignoredBy.remove( usr );
                                ignored.write("command=server_undo_ignored_by;user="+usr.info()+";:");
                                roomStateInfo();
                            }
                        }
                        else if(str.startsWith("command=ignore;"))
                        {
                            if(usr.isAnonymous)
                            {
                                kickUser("anonymous are not allowed to ignore or not");
                                break;
                            }
                            if(!(parser.getNumberOfParameters() == 1)){try{socket.close();}catch(Throwable e){};return;}
                            if(!parser.param(0).equals("user"))
                            {
                                try
                                {
                                    socket.close();
                                }
                                catch(Throwable e)
                                {
                                }
                                return;
                            }
                            User usrIgn = users.get(parser.val(0));
                            //System.out.println( usrIgn.getName() + " ignored by " + usr.getName() );
                            if( usrIgn!= null )
                            {
                                if(!(usrIgn.isAdmin))
                                {
				                    System.out.println(" se adauga la lista de ignorati");
                                    usr.ignored.add(usrIgn);
                                    usr.removeSolicitor( usrIgn );
                                    usr.write("command=server_ignored;user=" + usrIgn.info().trim() + ";:");
                                    
                                    usrIgn.ignoredBy.add(usr);
                                    usrIgn.removeSolicitor( usr );
                                    usrIgn.write("command=server_ignored_by;user=" + usr.info().trim() + ";:");
                                    roomStateInfo();
                                }
                            }

                        }else if(parser.getCommand().equals("set_ignore_list"))
                        {
                            if(parser.getNumberOfParameters() <= 0){try{socket.close();}catch(Throwable e){};return;}
                            String[] ignoreList = new String[ parser.getNumberOfParameters() ];
                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                if(!parser.param(i).equals("user"))
                                {
                                    try
                                    {
                                            socket.close();
                                    }
                                    catch(Throwable e)
                                    {}
                                    return;
                                }
                                ignoreList[i] = parser.val(i);
                                User usrIgn = users.get( ignoreList[i] );
                                if(!(usrIgn.isAdmin))
                                {
                                    usrIgn.write("command=server_ignore;user=" + usr.userName.trim() + ";:");
                                }
                                roomStateInfo();
                            }
                            usr.setIgnoreList(ignoreList);
                        }
                        else if(parser.getCommand().equals("reset_ignore_list"))
                        {
                            usr.resetIgnoreList();
                            roomStateInfo();
                        }
                        else if(parser.getCommand().equals("admin_request_user_list"))
                        {
                            writer.single_write(adminBuildBannList());
                        }
                        else if(parser.getCommand().equals("admin_kick"))
                        {
                            System.out.println("kick command received");
                            if(!(parser.getNumberOfParameters() == 1)){try{socket.close();}catch(Throwable e){}return;}
                            if(!parser.param(0).equals("user")){try{socket.close();}catch(Throwable e){}return;}
                            users.kick(parser.val(0));
                            if( vw!=null )vw.refreshUsers();
                        }
                        else if(parser.getCommand().equals("admin_set_bann_list"))
                        {
                            if(parser.getNumberOfParameters() <= 0){try{socket.close();}catch(Throwable e){}return;}
                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                if(!parser.param(i).equals("user")){try{socket.close();}catch(Throwable e){}return;}
                            }
                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                banList.add(parser.val(i));
                                System.out.println(parser.val(i));
                                users.kick((String)parser.val(i));
                            }


                            String bannStr = adminBuildBannList();
                            System.out.println(bannStr);
                            users.admin_write(bannStr);
                            if( vw!=null )vw.refreshUsers();
                        }
                        else if(parser.getCommand().equals("admin_set_forgive_list"))
                        {
                            if(parser.getNumberOfParameters() <= 0){try{socket.close();}catch(Throwable e){}return;}
                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                if(!parser.param(i).equals("user")){try{socket.close();}catch(Throwable e){}return;}
                            }
                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                String local = parser.val(i);
                                while( banList.indexOf(local) != -1) banList.remove( local );
                                while(  blockList.keySet().contains( local ) ) blockList.remove(  local  );
                            }

                            String bannStr = adminBuildBannList();
                            System.out.println(bannStr);
                            users.admin_write(bannStr);
                        }
                        else if(parser.getCommand().equals("admin_set_block_list"))
                        {
                            if( parser.getNumberOfParameters() <= 0 ){try{socket.close();}catch(Throwable e){}return;}
                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                if(!parser.param(i).equals("user")){try{socket.close();}catch(Throwable e){}return;}
                            }
                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                String local = parser.val(i);
                                User busr = users.get( local );
                                System.out.println(parser.val(i));
                                if(busr != null)
                                {
                                    blockList.put( busr.getName(), busr.connection.socket.getInetAddress().getHostAddress() );
                                    users.block_by_IP( busr.connection.socket.getInetAddress().getHostAddress() );
                                }
                            }

                            String blockStr = adminBuildBlockList();
                            System.out.println(blockStr);
                            users.admin_write(blockStr);
                            if( vw!=null )vw.refreshUsers();
                        }
                        else if(parser.getCommand().equals("admin_send_vi_message"))
                        {
                            if(parser.getNumberOfParameters() <= 0){try{socket.close();}catch(Throwable e){}return;}

                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                if(!parser.param(i).equals("user")){try{socket.close();}catch(Throwable e){}return;}
                                User musr = users.get(parser.val(i));
                                String htm = parser.parseAllTags(parser.getStrInfo());
                                musr.write("command=vi_message;:" + htm);
                            }

                        }else if(parser.getCommand().equals("admin_send_warning"))
                        {
                            if(parser.getNumberOfParameters() <= 0){try{socket.close();}catch(Throwable e){}return;}

                            for(int i = 0; i < parser.getNumberOfParameters(); i++)
                            {
                                if(!parser.param(i).equals("user")){try{socket.close();}catch(Throwable e){}return;}
                                User wusr = users.get(parser.val(i));
                                String htm = parser.parseAllTags(parser.getStrInfo());
                                wusr.write("command=warning_message;:" + htm);
                            }
                        }
                    else if( parser.getCommand().equals("my_coordinates") )
                    {
                        usr.setReady( true );
                        sendUserList();
                        writer.single_write("command=determine_comunication_path;port=" + pathChooser.getReceiverPort() + ";:");
                        System.out.println("S-a trimis lista de useri");
                    }
                    else if( parser.getCommand().equals( "remove_me_as_solicitor" ) )
                    {
                        //System.out.println();
                        User local = users.get( parser.val( 0 ) );
                        if( local!=null ) local.removeSolicitor( ( usr.getName() + parser.val(1) ) );
                    }

                    else if( parser.getCommand().equals("create_private_room") ) 
                    {
                        if( parser.val(0).contains("/") )
                        {
                            writer.single_write("command=reject;reason=no_foward_slashes_are_allowed;:");
                        }
                        else
                        {
                            UserArray room = new UserArray();
                            room.setEmpty( false );
                            room.setName( parser.val(0) );
                            
                            String password;
                            if( parser.getNumberOfParameters()==2 )
                            {
                                 if( parser.val(1).contains("/") )
                                 {
                                     writer.single_write("command=reject;reason=no_foward_slashes_are_allowed;:");
                                     continue;
                                 }
                                 password = parser.val(1);
                            }
                            else
                            {
                                 password = "";
                            }
                            room.setPassword( password );
                            
                            if( privateRooms.size() < 16 )
                            {
                                privateRooms.add( room );
                                roomStateInfo();
                            }
                        }
                    }
                    else if( parser.getCommand().equals("enter_private_room") ) 
                    {
                        String formerRoomName = usr.getRoom();
                        String futureRoomName = parser.val(0);
                        String password;
                        if( parser.getNumberOfParameters()==2 )
                        {
                             password = parser.val(1);
                        }
                        else
                        {
                             password = "";
                        }
                        changeTheRoom( formerRoomName, futureRoomName, password, usr ); 
                    }
                    else if( parser.getCommand().equals("back_to_lobby") )
                    {
                        String formerRoomName = usr.getRoom();
                        changeTheRoom( formerRoomName, "lobby", "" , usr );
                    }
                }
                else
                {
                    if(parser.getCommand().equals("connect"));
                    {
                        if(parser.getNumberOfParameters() != 4){try{socket.close();}catch(Throwable e){}return;}
                        if(!parser.param(0).equals("user")){try{socket.close();}catch(Throwable e){}return;}
                        if(!parser.param(1).equals("group")){try{socket.close();}catch(Throwable e){}return;}
                        if(!parser.param(2).equals("as")){try{socket.close();}catch(Throwable e){}return;}
                        if(!parser.param(3).equals("pwd")){try{socket.close();}catch(Throwable e){}return;}

                        if(parser.val(0).length() > 32)
                        {
                            writer.single_write("command=reject;reason=user_name_too_long;:");
                        }
                        else if(parser.val(1).length() > 32)
                        {
                            writer.single_write("command=reject;reason=group_name_too_long;:");
                        }
                        else if( parser.val(0).contains("/") )
                        {
                            writer.single_write("command=reject;reason=no_foward_slashes_are_allowed;:");
                        }
                        else if(parser.val(0).toLowerCase().equals("anonymous"))
                        {
                            int i = 0;
                            usr.isConnected = true;
                            usr.isAnonymous = true;
                            usr.userName = parser.val(0);
                            while(users.exists(usr.userName))
                            {
                                usr.userName = parser.val(0) + i;
                            }
                            users.add(usr);
                            writer.single_write("command=accept_as;user=" + usr.userName + ";:");
                            usr.setReady( false );                                
                            writer.single_write("command=determine_capture_devices;:"  );
                            if( vw!=null )vw.refreshUsers();
                            roomStateInfo();
                        }else
                        {
                            int i = 0;
                            for(i = 0; i < reservedNames.length; i++)
                            {
                                if(parser.val(0).toLowerCase().startsWith(reservedNames[i].toLowerCase()))
                                {
                                    System.out.println("command=reject;reason=reserved_entry;:");
                                    writer.single_write("command=reject;reason=reserved_entry;:");
                                }
                            }
                            if(i == reservedNames.length)
                            {
                                if(users.exists(parser.val(0)))
                                {
                                    writer.single_write("command=reject;reason=duplicate_entry;:");
                                    writeLog("command=reject;reason=duplicate_entry;:");
                                }else if(  banList.contains(parser.val(0))  )
                                {
                                    writer.single_write("command=reject;reason=banned_entry;:");
                                    writeLog("command=reject;reason=banned_entry;:");
                                    socket.close();
                                }else if(  blockList.containsKey( parser.val(0) ) || blockList.containsValue(socket.getInetAddress().getHostAddress())   )         ///blockList.exists(parser.val(0)) != -1 || blockList.exists(socket.getInetAddress().getHostAddress()) != -1)
                                {
                                    writer.single_write("command=reject;reason=blocked_computer;:");
                                    writeLog("command=reject;reason=blocked_computer;:");
                                    socket.close();
                                }else
                                {
                                    usr.isConnected = true;

                                    usr.setName( parser.val(0) );

                                    users.add( usr );
                                    lobby.add( usr );

                                    writer.single_write("command=accept;:");
                                    s.writeLog("\n>>>user " + usr.userName + " accepted");
                                    System.out.println(">>>user " + usr.userName + " accepted");

                                    String message = "command=server_say;user=server;:Welcome  to the chat user interface.This is The Lobby room , please behave yourself in here. Start your own private room to do anything you want.";
                                    usr.write( message );

                                    users.write("command=server_say;user=server;:" + usr.userName + "  connected!");

                                    if( vw!=null )vw.write("<b>server&gt; " + usr.userName + "  in the house!</b>");

                                    writer.single_write("command=determine_capture_devices;:"  );

                                    if( vw!=null )vw.refreshUsers();
                                    roomStateInfo();
                                }
                            }
                        }
                    }
                }
              }
                
                //users.del(usr.userName);
            }catch(IOException err)
            {
                err.printStackTrace();
                s.toRun = false;
                users.del_inspect(usr);
                removeUser( usr );
                //if (!socket.isClosed()) continue;
                try
                {
                    socket.close();
                }
                catch(IOException e)
                {
                    e.printStackTrace();
                }
                if(!usr.isConnected)return;                
                if( vw!=null )vw.write("<b>server&gt;" + usr.userName + "dropped from the server</b>");
                System.out.println("IOException>>>>" + usr.userName + " dropped from the server");
                err.printStackTrace();
                
                if((!usr.isAnonymous) && (usr.isConnected))
                {
                    users.write("command=server_say;user=server;:" + usr.userName + " dropped from the server");
                    sendUserList();
                }

                if( vw!=null )vw.refreshUsers();
            }
        }
    }


    static String adminBuildBannList()
    {
        String ret = "command=server_set_bann_list;";
                for(int i = 0; i< banList.size() ; i++ ){
            String u = banList.get(i);
            ret += ("user=" + u + ";");
        }
        ret += ":";
        return ret;
    }

    synchronized static String adminBuildBlockList()
    {
        String ret = "command=server_set_block_list;";

        synchronized( blockList ){
            Set s = blockList.entrySet();
            Iterator it =s.iterator();
            while( it.hasNext() ) ret += ("user=" + ((User)((Map.Entry)it.next()).getKey()).info() + ";" + "IP=" + (String)((Map.Entry)it.next()).getValue() + ";");
	}
        ret += ":";
        return ret;
    }

    class SvrWriter
    {
        private boolean isBad = true;
        private Socket socket;
        private DataOutputStream out;
        private boolean ready = false;
        private Svr s;

        boolean isReady(){return ready;}
        SvrWriter(Socket _socket, Svr _s)
        {
            try
            {
                socket = _socket;
                s = _s;
                out = new DataOutputStream(socket.getOutputStream());
            }catch(IOException err)
            {
                err.printStackTrace();
            }
        }

        public void multiple_write(String str)
        {
            users.write("str");
        }
        public void single_write(String str)
        {
            usr.setBusy(true);
            try
            {
                out.writeUTF(str);//send to client
                out.flush();
            }
            catch(IOException err)
            {
                //noinspection CallToPrintStackTrace
                err.printStackTrace();
            }
            if(str.equals("exit"))
            {
                s.toRun = false;
            }
            usr.setBusy(false);
        }
    }

    static String sVersion = "1.0.0.8";

    Socket socket;

    boolean bad = true;

    Svr(Socket _socket)
    {
        socket = _socket;
        bad = false;
        System.out.println("client connected:");
    }

    SvrWriter writer = null;
    SvrReader reader = null;
    void start(){run();}
    public void run()
    {
        if(bad)
        {
            System.out.println("bad client");
            return;
        }
        writer = new SvrWriter(socket, this);
        reader = new SvrReader(socket, this);
        toRun = true;
        reader.start();
    }
   
    User usr;
    public static UserArray users;
    public static UserArray lobby;
    public static ArrayList<UserArray> privateRooms;
    static DataOutputStream logFile;
    
    int personal;
    static boolean stop = false;
    static ServerSocket s = null;
    static SocketSelector socketSelector;
    static CommunicationPathChooser pathChooser = new CommunicationPathChooser();
    
    public void setPersonal( int val )
    {
		personal = val;
	}

	public int getPersonal()
	{
		return personal;
	}
//   

    public static void main(String[] args)
    {
        boolean isServerOpen = false;

        try
        {
            try
            {

                DataInputStream iniFile = new DataInputStream( new BufferedInputStream(new FileInputStream("resources/svr.ini")));
                BufferedReader ini = new BufferedReader(new InputStreamReader(iniFile));
                String strln;
                String strtk;
                reservedNames = new String[6];

                reservedNames[0] = "admin";
                reservedNames[1] = "server";
                reservedNames[2] = "root";
                reservedNames[3] = "Zeus";
                reservedNames[4] = "Filipski";
                reservedNames[5] = "Paganini";

                @SuppressWarnings("removal") Integer cPort = new Integer(0);
                while(ini.ready())
                {
                    strln = ini.readLine().trim();
                    String[] tk = strln.split(" ");

                    if( tk[0].trim().equals("[PORT]"))
                    {
                        PORT = Integer.parseInt(tk[1]);
                    }
                    else if( tk[0].trim().equals("[CONNECTIONS_POOLING]"))
                    {
                        CONNECTIONS_POOLING = Integer.parseInt(tk[1]);
                    }
                    else if( tk[0].trim().equals("[BIND_ADDRESS]"))
                    {
                        BIND_ADDRESS = tk[1].trim();
                    }
                }
            }catch(EOFException err)
            {
                err.printStackTrace();
                System.out.println("error");
                return;
            }

            String startStr = (new Date()).toString();
            startStr = "dfasdsdafdsa";
            logFile = new DataOutputStream( new BufferedOutputStream(new FileOutputStream("./server.log", true)) );
            logFile.writeUTF("\n----------------started again---------------------\n");
            logFile.flush();

            System.out.println("date/time:" + (new Date()).toString());
            logFile.writeUTF("date/time:" + (new Date()).toString());
            InetAddress ADDR = InetAddress.getByName(BIND_ADDRESS);

            s = new ServerSocket(PORT, CONNECTIONS_POOLING );
            
            pathChooser.start();

            logFile.writeUTF("server started on>" + s);
            logFile.flush();
            isServerOpen = true;

            users = new UserArray();
            lobby = new UserArray();
            lobby.setName("lobby");
            lobby.setPassword("");
            privateRooms = new ArrayList<UserArray>();
            
            Timer timer = new Timer(true);
            timer.schedule(dropInspector, 2000, 2000);
            //timer.schedule(inactivityInspector, 2000, 60000);
            timer.schedule(floodInspector, 1000, 1000);
            timer.schedule(emptyRoomInspector, 120000, 120000 );
            socketSelector = new SocketSelector(users);
            int n = 0;
            try
            {
                vw = new SvrView(users);
            }
            catch( Exception e )
            {
                e.printStackTrace();
                vw = null;
            }
            while( !stop )
            {
                Socket socket = s.accept();
                System.out.println(socket.getInetAddress().getHostAddress());
                //Svr server = new Svr( socket );
                Svr server = (Svr)socketSelector.select( socket);
                if( server!=null )
                {
                    n++;
                    server.setPersonal(n);
                    server.start();
                }
                
                System.out.println("begin new session: " + socket);
                System.out.println("stop = " + stop);
            }
        }
        catch(IOException err)
        {
            err.printStackTrace();
            System.out.println("exception in thread main");
        }
        finally
        {
            try
            {
                if (isServerOpen && s != null) s.close();
                isServerOpen = false;
                if( pathChooser!=null )
                {
                    System.out.println("Inchiderea variabilei pathChooser.");
                    pathChooser.setToRun( false );
                    pathChooser.close();
                    socketSelector.close();
                }
            }
            catch(IOException err)
            {
                err.printStackTrace();
            }
        }
    }
}