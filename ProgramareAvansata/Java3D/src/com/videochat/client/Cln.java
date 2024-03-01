package com.videochat.client;

import com.videochat.common.Parser;

import javax.swing.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

//import static java.util.stream.Collectors.toCollection;


public class Cln
{
    Parser parser = new Parser(); //var.componenta

    boolean toRun = false; //variabile componente
    boolean accepted = false;
    boolean isAdmin = false;

    ///////////////////////////////////////////////////
    List<User> ignore = new ArrayList<>(); //lista userilor ignorati
    List<User> users = new ArrayList<>(); //lista userilor existenti
    List<User> banned = new ArrayList<>(); //lista userilor interzisi
    static List<User> ignoredBy = new ArrayList<>();	//lisa userilor ce ma ignora
    ///////////////////////////////////////////////////

    Map<User, String> blockList = new HashMap<>(); //clasa necesara de studiat

    //this function retrieves an ignored by with the given name
    static synchronized boolean getAnIgnoredBy( String name )
    {
        return ignoredBy.stream().anyMatch(u -> u.name.equals(name));
    }

    class ClnReader extends Thread
    {
        private boolean isBad = true;
        boolean firstResponse = false;
        DataInputStream in;
        private Cln cln;

        ClnReader(Socket socket, Cln cln) //constructor
        {
            try
            {
                //variabile componente
                in = new DataInputStream( socket.getInputStream() );
                this.cln = cln;
                isBad = false;
            }
            catch( java.io.IOException err )
            {
                //noinspection CallToPrintStackTrace
                err.printStackTrace();
            }
        }

        @SuppressWarnings("BooleanMethodIsAlwaysInverted")
        synchronized boolean waitAccept()//functia poate ajunge la ultima instrunctie
        					             //doar daca decurge procesul de acceptare
        {
            if( cln.accepted )return true;
            String str;
            try
            {
                str = in.readUTF(); //se citeste un string din DataInputStream
                if( !parser.parse( str ) )return false;//Daca nu poate poate fi obtinuta
                                                  //o comanda din stringul citit functia intoarce valoarea false
                System.out.println( "svr_str: " + str );//afisarea in consola
                										//sistemului a comenzii selectate
                if( parser.getCommand().equals("accept") )
                {
                    if(parser.getNumberOfParameters() != 0)return false;
                    cln.accepted = true;
                }
                else if(str.trim().startsWith("command=accept_as;"))
                {
                    if( parser.getNumberOfParameters() != 1 )return false;
                    if( !parser.param(0).equals("user") )return false;
                    cln.accepted = true;
                    cln.view.userName = parser.val(0);
                }
                firstResponse = true;
                return true;
            }catch(IOException err)
            {
                //noinspection CallToPrintStackTrace
                err.printStackTrace();
            }
            return false;
        }

        synchronized void processServerPrivateSayTo()//procesarea mesajului dintr-un private room
        {
            if( parser.getNumberOfParameters() != 2 )return;
            if( !parser.param(0).equals("from") )return;
            if( !parser.param(1).equals("to") )return; //verificarea rezultatului procesului de partitionare

            PrivateClnSession session = null;

            if( view.userName.equals( parser.val(0) ) ) //urmeaza procesul de precizare a sesiunii private
            {

                session = PrivateClnSession.get( view.sessions, parser.val(1) );
            }
            else if( view.userName.equals( parser.val(1) ) )
            {
				session = PrivateClnSession.get( view.sessions, parser.val(0) );
                if(session == null)
                {
                    session = view.createNewPrivateSession( parser.val(0) );
                }
            }
            if( !(session == null) )
            	session.write("<font size=4><b>" + parser.val(0) + "&gt;</b>&nbsp;&nbsp;    " +  parser.getStrInfo()+"</size>"  );
            							//scrierea informatiei in sesiunea privata
        }

        synchronized void processUserList( List<User> ul )
        {
            System.out.println("processUserList( List<User> ul ) ");
            if( ul == null ) return;

            view.listModel.removeAllElements();
            DefaultListModel<User> localModel = new DefaultListModel<>();
            synchronized( this )
            {
                for (User user : ul) {
                    localModel.addElement(user);
                }
            }
            view.setModel( localModel );
        }

        synchronized void processUserList()
        {
            if(parser.getNumberOfParameters() < 1)return;//verificare

            for(int i = 0; i < parser.getNumberOfParameters(); i++)
            {
                if(!parser.param(i).equals("user"))return;
            } //verificari

            //ArrayList<User> userList = new ArrayList<>(); //alocari

            synchronized( this )
            {
                users.clear();
                users = parser.paramValues.stream().map(User::generate).collect(Collectors.toList());
            }

            //users.addAll( userList );
            ignore.retainAll( users );
            users.removeAll(ignoredBy);

            //to mark ignored Users
            for (User local : users) {
                if (ignore.contains(local)) {
                    local.setIgnored(true);
                }
            }

            synchronized(this)
            {
                view.listModel.removeAllElements();//
                DefaultListModel<User> localModel = new DefaultListModel<>();
                for (User user : users) {
                    localModel.addElement(user);
                }
                view.setModel( localModel );
            }
        }

        //this function has to process information from server about rooms
        //it is invoked at the arrival of the room_info message;
        //this function is designed to work with the parser object
        synchronized void processRoomInfoMessage()
        {
            int numberOfParameters = parser.getNumberOfParameters();
            if( numberOfParameters > 0 )
            {
                //creation of a list of room
                DefaultListModel<Room> listModel = new DefaultListModel<>();
                parser.paramValues
                        .stream().map(Room::new)
                        .forEach(listModel::addElement);
                //this list must be loaded in a swing list
                view.setRoomListModel( listModel );
            }
        }

        public void run()
        {
            String str;
            if(isBad)return;
            try
            {
                while(cln.toRun)
                {
                    if(!cln.accepted)return;
                    System.out.println("gata de citire");
                    str = in.readUTF();//read received from server
                    System.out.println("svr_str: " + str);
                    if( !parser.parse( str ) )
                    {
						System.out.println( "nu s-a partitionat" );
						continue;
					}
                    if(cln.accepted)
                    {
                        synchronized(this)
                        {
                            if(parser.getCommand().equals("server_say") )
                            {
                                if(parser.getNumberOfParameters() != 1)continue;
                                if( !parser.param(0).equals("user") )continue;
                                if(parser.val(0).equals("server"))
                                {
                                    //System.out.println("!!!!!!!!!!!!!!!!!!!!!!cu font");
                                        view.write("<b>" + parser.val(0) + "</b> :&nbsp;&nbsp;" + parser.getStrInfo() );
                                }else
                                {
                                        view.write("<b>" + parser.val(0) + "</b> :&nbsp;&nbsp;" + parser.getStrInfo() );
                                }
                            }
                            if(parser.getCommand().equals("server_set_admin"))
                            {
                                if(parser.getNumberOfParameters() != 1)continue;
                                if(!parser.param(0).equals("value"))continue;
                                if(parser.val(0).equals("true"))
                                {
                                    cln.view.onSetAdmin();
                                    isAdmin = true;
                                }
                                else if(parser.val(0).equals("false"))
                                {
                                    cln.view.onResetAdmin();
                                    isAdmin = false;
                                }
                            }
                            else if(parser.getCommand().equals("server_private_say_to"))
                            {
                                processServerPrivateSayTo();
                            }else if(parser.getCommand().equals("server_say_only_to"))
                            {
                                if(parser.getNumberOfParameters() != 2){System.out.println("not 2");continue;}
                                if(!parser.param(0).equals("from")){System.out.println("not from");continue;}
                                if(!parser.param(1).equals("to")){System.out.println("not to");continue;}
                                view.write("<i><u><b>" + parser.val(0) + "</b> is whispering to " + parser.val(1) + " : &nbsp;&nbsp;" +  parser.getStrInfo()  + "</u></i>");
                            }
                            else if(parser.getCommand().equals("user_list"))
                            {
                                processUserList();
                            }
                            else if(parser.getCommand().equals("restored_by"))
                            {
                                synchronized(this)
                                {
                                    if(parser.getNumberOfParameters() != 1)continue;
                                    if(!parser.param(0).equals("user"))continue;

                                    Iterator<User> it = ignoredBy.iterator();
                                    User u = null;
                                    while( it.hasNext() )
                                    {
                                        User local1 = it.next();
                                        User local2 = User.generate( parser.val(0) );
                                        if( local1.equals( local2 ) )
                                        {

                                            u = local1;
                                            break;
                                        }
                                    }
                                    if( u!=null )
                                    {
                                        cln.users.add( u);
                                        ignoredBy.remove(u);
                                        view.listModel.addElement( u );
                                    }

                                }
                            }
                            else if(parser.getCommand().equals("server_kick"))
                            {
                                    if(parser.getNumberOfParameters() != 0)continue;
                                    cln.toRun = false;
                                    cln.view.onKick();
                            }else if(str.startsWith("command=server_ignored_by;"))
                            {
                                //System.out.println("Primita comanda server_ignored_by");
                                synchronized(this)
                                {
                                    if( parser.getNumberOfParameters() != 1 )continue;
                                    if(!parser.param(0).equals("user"))continue;
                                    try
                                    {
                                        Iterator<User> it = cln.users.iterator();
                                        User u = null;
                                        while( it.hasNext() )
                                        {
                                            User local1 = it.next();
                                            User local2 = User.generate( parser.val(0) );

                                            if( local1.equals( local2 ) )
                                            {
                                                System.out.println("egalitate");
                                                u = local1;
                                                it.remove();
                                                break;
                                            }
                                        }

                                        if( u != null )
                                        {
                                            ignoredBy.add( u );
                                            //cln.view.sessions.remove( u.getName() );
                                            final String uname = u.getName();
                                            cln.view.sessions.removeIf (s->s.sessionId.equals(uname));
                                            //cln.view.sessions.removeAll(s -> s.sessionId.equals(u.name));//.remove( u.getName() );
                                            //cln.view.sessions.removeAll(s -> s.sessionId.equals(u.name));//.remove( u.getName() );
                                            processUserList( cln.users);
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        //noinspection CallToPrintStackTrace
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else if(str.startsWith("command=server_ignored;"))
                            {
                                synchronized(this)
                                {
                                    if(parser.getNumberOfParameters() != 1)continue;
                                    if(!parser.param(0).equals("user"))continue;
                                    try
                                    {
                                        Iterator<User> it = cln.users.iterator();
                                        User u = null;
                                        while( it.hasNext() )
                                        {
                                            User local1 = it.next();
                                            User local2 = User.generate( parser.val(0) );
                                            if( local1.equals( local2 ) )
                                            {
                                                it.remove();
                                                u = local1;
                                                u.setIgnored(true);
                                                break;
                                            }
                                        }
                                        if( u != null )
                                        {
                                            cln.ignore.add( u );
                                            cln.users.add( u );
                                            final String uname = u.getName();
                                            cln.view.sessions.removeIf (s->s.sessionId.equals(uname));

                                            //cln.view.sessions.remove( u.getName() );

                                            processUserList( cln.users);
                                        }
                                    }
                                    catch(Exception e)
                                    {
                                        //noinspection CallToPrintStackTrace
                                        e.printStackTrace();
                                    }
                                }
                            }
                            else if( parser.getCommand().equals("server_undo_ignore") )
                            {
                                System.out.println("Server_Undo_Ignore");
                                //1. to remove the ignored user from ignoreList
                                //2. to mark this user in c.existingUserList as normal
                                //3. to load a new modelList for the userList
                                User formerIgnored = User.generate( parser.val(0) );
                                if( formerIgnored!=null )
                                {
                                    ignore.remove( formerIgnored );
                                    cln.users.removeIf(localUser -> localUser.equals(formerIgnored));
                                    cln.users.add( formerIgnored );
                                    processUserList( cln.users);
                                }
                            }
                            else if( parser.getCommand().equals("server_undo_ignored_by") )
                            {
                                System.out.println("Server_Undo_Ignored_By");
                                //1.to remove this user from ignoredByList
                                //2.to add this user in the c.existingUserList
                                //3.to refresh displayed user list
                                User formerIgnoredBy = User.generate( parser.val(0) );
                                if( formerIgnoredBy!=null )
                                {
                                    Iterator<User> it = cln.users.iterator();
                                    while( it.hasNext() )
                                    {
                                        User localUser= it.next();
                                        if( localUser.equals( formerIgnoredBy ) )
                                        {
                                            it.remove();
                                        }
                                    }
                                    it = ignoredBy.iterator();
                                    while( it.hasNext() )
                                    {
                                        User localUser= it.next();
                                        if( localUser.equals( formerIgnoredBy ) )
                                        {
                                            it.remove();
                                        }
                                    }
                                    cln.users.add( formerIgnoredBy );
                                    processUserList( cln.users);
                                }
                            }
                            else //noinspection SpellCheckingInspection
                                if(parser.getCommand().equals("server_set_bann_list"))
                            {
                                if(parser.getNumberOfParameters() > 0) {
                                    banned.clear();
                                    for (String userName : parser.paramValues) {
                                        banned.add(new User(userName));
                                    }
                                }
                            }else if(parser.getCommand().equals("server_set_block_list"))
                            {
                                if(parser.getNumberOfParameters() > 0)
                                {
                                    for ( int i = 0; i < parser.getNumberOfParameters(); i++ ){
                                        User user = new User( parser.val(i * 2) );
                                        String local = parser.val((i * 2) + 1);
                                        if( local != null ) blockList.put(user, local);
                                    }
                                }
                            }
                            else if(parser.getCommand().equals("vi_message"))
                            {
                                    if(parser.getNumberOfParameters() != 0)break;
                                    view.write("<font color=blue><b>very important message : </b>" +  parser.getStrInfo()  + "</font>");
                                    PrivateClnSession.write( view.sessions, "<font color=blue><b>very important message : </b>" +  parser.getStrInfo()  + "</font>" );
                            }
                            else if(parser.getCommand().equals("warning_message"))
                            {
                                    if(parser.getNumberOfParameters() != 0)break;
                                    view.write("<font color=red><b>warning : </b>" +  parser.getStrInfo()  + "</font>");
                                    PrivateClnSession.write( view.sessions, "<font color=red><b>warning : </b>" +  parser.getStrInfo()  + "</font>" );
                            }
                            else if( parser.getCommand().equals("room_info") )
                            {
                                processRoomInfoMessage();
                            }

                        }
                    }
                }
            }
            catch(Throwable err)
            {
                //noinspection CallToPrintStackTrace
                err.printStackTrace();
            }
            /*catch( java.net.SocketException se )
            {
                se.printStackTrace();
            }
            catch(IOException err)
            {
                err.printStackTrace();
            }*/finally
            {
                try
                {
                    cln.socket.close();
                }catch(IOException err)
                {
                    //noinspection CallToPrintStackTrace
                    err.printStackTrace();
                }
            }
        }
    }

    class ClnWriter
    {
        private boolean isBad = true;
        DataOutputStream out;
        private Cln c;
        private Socket socket;

        ClnWriter(Socket _socket, Cln _c)
        {
            try
            {
                socket = _socket;
                c = _c;
                out = new DataOutputStream(socket.getOutputStream());
                isBad = false;
            }catch(java.io.IOException err)
            {
                //noinspection CallToPrintStackTrace
                err.printStackTrace();
            }
        }
        synchronized public void send(String str)
        {
            if( socket.isClosed() ) view.write("you are disconnected");
            if(isBad)return;

            try
            {
                if(str.startsWith("command=say;"))c.responses++;
                out.writeUTF(str.trim());
                out.flush();
            }catch(IOException err)
            {
                //noinspection CallToPrintStackTrace
                err.printStackTrace();
                c.toRun = false;
            }
            if(str.trim().equals("exit"))
            {
                c.toRun = false;
            }
        }
    }

    InetAddress address;
    Socket socket;
    boolean bad;
    ClnWriter writer = null;
    ClnReader reader = null;

    int responses = 0;
    boolean isBad()
    {
		return bad;
	}

    public void start()
    {
		run();
	}
    public void run()
    {
        if(bad)
        {
            return;
        }
        bad = true;

        toRun = true;
        reader.start();
        bad = false;

    }

    ClnView view;
    boolean isConnected = false;
    String strAddress = "127.0.0.1";
    String strPort = "80";
    int iPort;
    boolean isAnonymous = false;

    void connect() {
        isConnected = false;
        isAnonymous = false;
        accepted = false;
        view.userName = "Guest";
        view.groupName = "Guest";

        try
        {
            iPort = Integer.parseInt(strPort);
            address = InetAddress.getByName(strAddress);
            socket = new Socket(address, iPort);
            writer = new ClnWriter(socket, this);
            reader = new ClnReader(socket, this);
            isConnected = true;
        }
        catch(IOException err)
        {
            //noinspection CallToPrintStackTrace
            err.printStackTrace();
        }
    }

    Cln(ClnView _view)
    {
        view = _view;
        bad = false;
    }


}