package com.videochat.client;

import com.videochat.common.*;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.event.*;
import java.awt.Container;
import java.io.*;
import java.util.*;
import java.net.*;
import java.awt.Dimension;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.*;
import javax.swing.event.*;
import java.util.regex.*;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.Component;
import java.util.stream.Collectors;
import javax.swing.JTextField;
import javax.swing.text.html.*;



class AdminChatForm extends JFrame {
    String message;


    class SenderBox extends JFrame {
        JDialog dialog;
        JButton btnOk = new JButton("Ok");
        JButton btnCancel = new JButton("cancel");
        JTextField txtNewName = new JTextField("");
        ClnView view;
        String toDo;
        ActionListener senderActionListener = new ActionListener() {
            public synchronized void actionPerformed(ActionEvent e) {
                String lnfName = e.getActionCommand();
                if(lnfName.equals("Ok")) {
                    message = txtNewName.getText();
                    if(toDo.equals("send message")) {
                        xSendMessage(message);
                    }else if(toDo.equals("send warning")) {
                        xSendWarning(message);
                    }
                    dispose();
                }else if(lnfName.equals("cancel")) {
                    dispose();
                }
            }
        };

        SenderBox(ClnView _view, String str)
        {
            view = _view;
            setLayout(null);
            this.setResizable(false);
            setBounds(200, 200, 220, 103);

            Container container = this.getContentPane();
            container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );

            JPanel jp = new JPanel();
            jp.setLayout( new BoxLayout( jp, BoxLayout.LINE_AXIS ) );

            JPanel jp1 = new JPanel();
            jp1.setLayout( new BoxLayout( jp1, BoxLayout.LINE_AXIS ) );

            txtNewName.setPreferredSize( new Dimension(130, 25) );
            jp1.add( txtNewName );
            jp1.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5  ) );
            container.add( jp1 );

            btnOk.setPreferredSize( new Dimension( 80, 25 ) );
            btnOk.setActionCommand("Ok");
            btnOk.addActionListener(senderActionListener);
            jp.add(btnOk);

            jp.add( Box.createRigidArea( new Dimension( 5,0 ) ) );

            btnCancel.setPreferredSize( new Dimension(80, 25) );
            btnCancel.setActionCommand("cancel");
            btnCancel.addActionListener(senderActionListener);
            jp.add(btnCancel);

            jp.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5  ) );

            container.add( jp );
            pack();

            this.setBackground(Color.lightGray);
            toDo = str;

            dialog = new JDialog(this, str, true);
            dialog.setModal(true);//??Pentru ce trebiue acest rind
        }
    }

    ClnView view;
    Cln client;
    DefaultListModel<User> bui = new DefaultListModel<>();
    DefaultListModel<User> rui = new DefaultListModel<>();
    DefaultListModel<User> blui = new DefaultListModel<>();
    JList<User> bannedUserList = new JList<>();
    JList<User> restoredUserList = new JList<>();
    JList<User> blockedUserList = new JList <> ( );

    void xSendMessage(String str) {
        User its = restoredUserList.getSelectedValue();
        String temp = "";
        if(its == null)return;
        //if(its.trim() == "")return;
        temp += ("user=" + its.getName().trim() + ";");
        client.writer.send("command=admin_send_vi_message;" + temp +  ":" + str);
    }
    void xSendWarning(String str) {
        User its = restoredUserList.getSelectedValue();
        String temp = "";
        if(its == null)return;
        temp += ("user=" + its.getName().trim() + ";");
        client.writer.send("command=admin_send_warning;" + temp + ":" + str);
    }

    JButton btnRestore = new JButton( Parser.h5("restore &gt") );
    JButton btnRestoreAll = new JButton( Parser.h5("restore all &gt&gt") );
    JButton btnKickBann = new JButton( Parser.h5("&lt bann") );
    JButton btnKickBannAll = new JButton( Parser.h5("&lt&lt bann all") );

    JButton btnRequestIP = new JButton( Parser.h5("request IP") );
    JButton btnRestore2 = new JButton(Parser.h5("&lt restore") );
    JButton btnRestoreAll2 = new JButton( Parser.h5("&lt&lt restore all") );
    JButton btnBlock = new JButton( Parser.h5("block &gt") );
    JButton btnBlockAll = new JButton( Parser.h5("block all &gt&gt"));

    JButton btnOk = new JButton( Parser.h5("Ok") );
    JButton btnCancel = new JButton( Parser.h5("cancel") );
    JButton btnSubmit = new JButton( Parser.h5("submit") );
    JButton btnMessage = new JButton( Parser.h5("message") );
    JButton btnWarning = new JButton( Parser.h5("warning") );
    JButton btnKick = new JButton( Parser.h5("kick") );

    List<User> bannedList;
    List<User> existingUserList;
    List<User> blockList = new ArrayList<>();

    JLabel label = new JLabel();

    synchronized void buildLists() {
        bui.addAll(bannedList.stream().filter(u -> !u.name.equals(view.userName)).collect(Collectors.toList()));
        existingUserList.removeAll(bannedList);
        rui.addAll (existingUserList.stream().filter(u -> !u.name.equals(view.userName)).collect(Collectors.toList()));
        //for (User user : bannedList) {
        //    if (!(view.userName.equals(user.getName()))) {
        //        bui.addElement(user);
        //    }
        //    existingUserList.remove(user);
        //}

        //for (User user : existingUserList) {
        //    if (!(view.userName.equals(user))) {
        //        rui.addElement(user);
        //    }
        //}

        bui.removeElement(view.userName);
        existingUserList.removeIf(u -> u.name.equals(view.userName));
    }

    ActionListener adminActionListener = new ActionListener() {
        public synchronized void actionPerformed(ActionEvent e) {
            String lnfName = e.getActionCommand();
            //noinspection IfCanBeSwitch
            if(lnfName.equals("Ok") || lnfName.equals("submit") ) {
                client.banned.clear();
                client.banned.addAll( bannedList );
                client.users.clear();
                client.users.addAll( existingUserList );
                StringBuilder str = new StringBuilder("command=admin_set_bann_list;");
                if(!bannedList.isEmpty()) {
                    for (User user : bannedList) {
                        str.append("user=").append(user).append(";");
                    }
                    str.append(":");
                    client.writer.send(str.toString());
                }else {
                    str = new StringBuilder("command=admin_reset_bann_list;");
                    str.append(":");
                    client.writer.send(str.toString());
                }

                existingUserList.removeAll(bannedList);

                str = new StringBuilder("command=admin_set_forgive_list;");
                if(!existingUserList.isEmpty()) {
                    for (User user : existingUserList) {
                        str.append("user=").append(user).append(";");
                    }
                    str.append(":");
                    client.writer.send(str.toString());
                }

                str = new StringBuilder("command=admin_set_block_list;");
                if(!blockList.isEmpty()) {
                    for (User user : blockList) {
                        str.append("user=").append(user).append(";");
                    }
                    str.append(":");
                    client.writer.send(str.toString());
                }else {
                    str = new StringBuilder("command=admin_reset_block_list;");
                    str.append(":");
                    client.writer.send(str.toString());
                }
                existingUserList.removeAll(blockList);
                client.writer.send("command=admin_request_user_list;:");

                if(lnfName.equals("Ok"))dispose();
            } else if(lnfName.equals("message")) {
                SenderBox x = new SenderBox(view, "send message");
                x.setVisible(true);
            } else if(lnfName.equals("warning")) {
                SenderBox x = new SenderBox(view, "send warning");
                x.setVisible(true);
            } else if(lnfName.equals("kick")) {
                String its = restoredUserList.getSelectedValue().getName();
                rui.removeElement( its );
                client.writer.send("command=admin_kick;user=" + its + ";:");
            } else if(lnfName.equals("cancel")) {
                dispose();
            } else if(lnfName.equals("restore")) {
                User its = bannedUserList.getSelectedValue();
                rui.addElement( its );
                existingUserList.add( its );
                bui.removeElement( its );
                bannedList.remove(its);
            } else if(lnfName.equals("restore_all")) {
                bannedUserList.removeAll();
                for (User user : bannedList) rui.addElement(user);
                existingUserList.addAll( bannedList );
                bannedList.clear();
            } else if(lnfName.equals("bann")) {
                User its = restoredUserList.getSelectedValue();
                bui.addElement( its );
                bannedList.add( its );
                existingUserList.remove( its );
                rui.removeElement( its );
            } else if(lnfName.equals("bann_all")) {
                restoredUserList.removeAll();
                for (User user : existingUserList) bui.addElement(user);
                bannedList.addAll( existingUserList );
                //existingUserList.remove(bannedList);
                existingUserList.removeAll(bannedList);
            } else if(lnfName.equals("block_restore")) {
                User its = blockedUserList.getSelectedValue();
                rui.addElement( its );
                existingUserList.add( its );
                blui.removeElement( its );
                blockList.remove( its );
            } else if(lnfName.equals("block_restore_all")) {
                blockedUserList.removeAll();
                for (User user : blockList) rui.addElement(user);
                existingUserList.addAll( blockList );
                blockList.clear();
            } else if( lnfName.equals("block") ) {
                User  its = restoredUserList.getSelectedValue();
                blui.addElement(its);
                blockList.add(its);
                existingUserList.remove( its );
                rui.removeElement( its );
            } else if( lnfName.equals("block_all") ) {
                restoredUserList.removeAll();
                blui.addAll(existingUserList);
                //for (User user : existingUserList) blui.addElement(user);
                blockList.addAll( existingUserList );
                existingUserList.clear();//remove(blockList);
            } else if( lnfName.equals("request_IP") ) {
                System.out.println(" sa apasat butonul request_IP " );
                String userName =  blockedUserList.getSelectedValue().getName();
                //String ip = null;
                //if( userName.equals( "" ) || userName == null  ) return;
                if( !userName.isEmpty())
                {
                //else {
                    client.blockList.keySet().stream()
                            .filter(u -> u.name.equals(userName)).findFirst()
                            .ifPresent(u -> label.setText( Parser.h5(userName+"'s ip = " + client.blockList.get(u))));
                    //ip = client.blockList.get( userName );
                    //ip = client.blockList.get( userName );
                    //if( ip != null && !ip.equals( "" ) ) label.setText( Parser.h5(userName+"'s ip = " + ip) );
                }
            }
        }
    };

    AdminChatForm(ClnView _view) {
        setResizable(false);
        view = _view;
        client = view.client;

        bannedList = new ArrayList<>( client.banned);

        existingUserList = new ArrayList<>( client.users);

        blockList.addAll(client.blockList.keySet());

        existingUserList.removeIf( u -> u.name.equals(view.userName));
        //existingUserList.remove(view.userName);

        setBounds(200, 200, 744, 200);

        Container container = getContentPane();
        container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );

        JPanel jp1, jp2, top, foot;
        jp1 = new JPanel();
        jp2 = new JPanel();
        top = new JPanel();
        foot = new JPanel();

        jp1.setLayout( new BoxLayout( jp1, BoxLayout.PAGE_AXIS ) );
        jp2.setLayout( new BoxLayout( jp2, BoxLayout.PAGE_AXIS ) );
        top.setLayout( new BoxLayout( top, BoxLayout.LINE_AXIS ) );
        foot.setLayout( new BoxLayout( foot, BoxLayout.LINE_AXIS ) );

        JScrollPane bannedUsrScroller = new JScrollPane(bannedUserList);
        bannedUsrScroller.setPreferredSize( new Dimension(146, 133) );

        btnMessage.setPreferredSize( new Dimension( 50, 20 ) );
        btnMessage.setActionCommand("message");
        btnMessage.addActionListener(adminActionListener);

        label.setPreferredSize( new Dimension( 230, 20 ) );
        foot.add(label);

        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        foot.add(btnMessage);
        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        btnWarning.setPreferredSize( new Dimension( 50, 20 ) );
        btnWarning.setActionCommand("warning");
        btnWarning.addActionListener(adminActionListener);

        foot.add(btnWarning);
        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        btnOk.setPreferredSize( new Dimension(50, 20) );
        btnOk.setActionCommand("Ok");
        btnOk.addActionListener(adminActionListener);

        foot.add(btnOk);
        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        btnSubmit.setPreferredSize( new Dimension(50, 20) );
        btnSubmit.setActionCommand("submit");
        btnSubmit.addActionListener(adminActionListener);

        foot.add(btnSubmit);
        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        btnCancel.setActionCommand( "cancel" );
        btnCancel.setPreferredSize( new Dimension(50, 20) );
        btnCancel.addActionListener(adminActionListener);

        foot.add( btnCancel );
        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        btnKick.setPreferredSize( new Dimension(100, 20) );
        btnKick.addActionListener( adminActionListener );
        btnKick.setActionCommand("kick");

        jp1.add(btnKick);
        jp1.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnRestore.setPreferredSize( new Dimension(100, 20) );
        btnRestore.setActionCommand("restore");
        btnRestore.addActionListener(adminActionListener);

        jp1.add(btnRestore);
        jp1.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnRestoreAll.setPreferredSize( new Dimension(100, 20) );
        btnRestoreAll.setActionCommand("restore_all");
        btnRestoreAll.addActionListener(adminActionListener);

        jp1.add(btnRestoreAll);
        jp1.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnKickBann.setPreferredSize( new Dimension(100, 20) );
        btnKickBann.setActionCommand("bann");
        btnKickBann.addActionListener(adminActionListener);

        jp1.add(btnKickBann);
        jp1.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnKickBannAll.setPreferredSize( new Dimension(100, 20) );
        btnKickBannAll.addActionListener(adminActionListener);
        btnKickBannAll.setActionCommand("bann_all");

        jp1.add( btnKickBannAll );

        JScrollPane restoredUsrScroller = new JScrollPane(restoredUserList);
        restoredUsrScroller.setPreferredSize( new Dimension(146, 133) );

        btnRequestIP.setPreferredSize( new Dimension(100, 20) );
        btnRequestIP.setActionCommand("request_IP");
        btnRequestIP.addActionListener(adminActionListener);

        jp2.add(btnRequestIP);
        jp2.add(  Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnRestore2.setPreferredSize( new Dimension(100, 20) );
        btnRestore2.setActionCommand("block_restore");
        btnRestore2.addActionListener(adminActionListener);

        jp2.add(btnRestore2);
        jp2.add(  Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnRestoreAll2.setActionCommand("block_restore_all");
        btnRestoreAll2.setPreferredSize( new Dimension(100, 20) );
        btnRestoreAll2.addActionListener(adminActionListener);

        jp2.add(btnRestoreAll2);
        jp2.add(  Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnBlock.setActionCommand("block");
        btnBlock.setPreferredSize( new Dimension(100, 20) );
        btnBlock.addActionListener(adminActionListener);

        jp2.add(btnBlock);
        jp2.add(  Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnBlockAll.setActionCommand("block_all");
        btnBlockAll.setPreferredSize( new Dimension(100, 20) );
        btnBlockAll.addActionListener(adminActionListener);

        jp2.add(btnBlockAll);
        jp2.add(  Box.createRigidArea( new Dimension( 0, 5 ) ) );

        JScrollPane blockedUsrScroller = new JScrollPane(blockedUserList);
        blockedUsrScroller.setPreferredSize( new Dimension(146, 133) );

        jp1.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        jp2.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        bannedUsrScroller.setBorder( BorderFactory.createTitledBorder( "banned:" ) );
        restoredUsrScroller.setBorder( BorderFactory.createTitledBorder( "restored:" ) );
        blockedUsrScroller.setBorder( BorderFactory.createTitledBorder( "blocked:" ) );

        top.add( bannedUsrScroller );
        top.add( jp1 );
        top.add( restoredUsrScroller );
        top.add( jp2 );
        top.add( blockedUsrScroller );

        top.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        foot.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        container.add( top );
        container.add( foot );

        pack();

        buildLists();
        setVisible(true);
    }
}

class EnterRoomFrame extends JFrame implements ActionListener
{
    ClnView cv;
    String roomName;
    JTextField txt = new JTextField();
    JButton okButton = new JButton( Parser.h5("OK") );
    JButton cancelButton = new JButton( Parser.h5("Cancel") );

    EnterRoomFrame( ClnView cv, String roomName )
    {
        this.cv = cv;
        this.roomName = roomName.trim();

        JPanel container = new JPanel();
        container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );

        JPanel top = CreateRoomFrame.makePanel( "password:", txt );

        okButton.setActionCommand("ok");
        okButton.addActionListener( this );

        cancelButton.setActionCommand("cancel");
        cancelButton.addActionListener( this );

        JPanel foot = CreateRoomFrame.makeOKCancelPanel( okButton, cancelButton );

        container.add( top );
        container.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
        container.add( foot );

        container.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        this.setContentPane( container );
        this.setTitle("Room " + roomName);
        this.setBounds( 200, 200, 260, 55 );
        this.pack();
        this.setVisible( true );
    }

    public void actionPerformed( ActionEvent e )
    {
        String action  = e.getActionCommand().trim();
        if( action.equals( "cancel" ) )
        {
            cv = null;
            this.setVisible( false );
        }
        else if( action.equals( "ok" ) )
        {
            //to read the password of the room
            //to send it to the server
            String password = txt.getText().trim();
            if ( roomName != null && !roomName.isEmpty()  )
            {
                String command = "command=enter_private_room;name=" + roomName.trim() + ";" ;
                if(!password.isEmpty())
                    command += "password=" + password.trim() +";";
                command += ":";
                cv.send( command );
                this.setVisible( false );
            }
        }
        else
        {
            this.setVisible( false );
        }
    }
}

class CreateRoomFrame extends JFrame implements ActionListener
{
    JTextField txt1 = new JTextField();
    JTextField txt2 = new JTextField();
    JButton okButton = new JButton(Parser.h5("OK"));
    JButton cancelButton = new JButton(Parser.h5("Cancel"));
    ClnView cv;

    static JPanel makePanel( String labelText , JTextField txt )
    {
        JPanel result = new JPanel();
        result.setLayout( new BorderLayout() );

        JLabel label= new JLabel( Parser.h5( labelText ) );

        label.setMaximumSize( new Dimension( 85, 20 ) );
        label.setMinimumSize( new Dimension( 85, 20 ) );
        label.setPreferredSize( new Dimension( 85, 25 ) );
        label.setHorizontalAlignment( label.LEFT );

        txt.setMaximumSize( new Dimension( 125, 20 ) );
        txt.setMinimumSize( new Dimension( 125, 20 ) );
        txt.setPreferredSize( new Dimension( 125, 20 ) );

        result.add( label, BorderLayout.WEST );
        result.add( Box.createRigidArea( new Dimension( 5, 0 ) ), BorderLayout.CENTER );
        result.add( txt, BorderLayout.EAST );

        result.setPreferredSize( new Dimension( 210, 20 ) );
        result.setMaximumSize( new Dimension( 210, 20 ) );
        result.setMinimumSize( new Dimension( 210, 20 ) );
        return result;
    }

    static JPanel makeOKCancelPanel( JButton okButton, JButton cancelButton )
    {
        JPanel result = new JPanel();
        result.setLayout( new BoxLayout( result, BoxLayout.LINE_AXIS ) );

        okButton.setMaximumSize( new Dimension( 50, 20 ) );
        okButton.setMinimumSize( new Dimension( 50, 20 ) );
        okButton.setPreferredSize( new Dimension( 50, 20 ) );

        cancelButton.setMaximumSize( new Dimension( 70, 20 ) );
        cancelButton.setMinimumSize( new Dimension( 70, 20 ) );
        cancelButton.setPreferredSize( new Dimension( 70, 20 ) );

        result.add( Box.createRigidArea( new Dimension( 80, 0 ) ) );
        result.add( okButton );
        result.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
        result.add( cancelButton );

        return result;
    }

    public CreateRoomFrame( ClnView val )
    {
        cv = val;
        JPanel container = new JPanel();
        container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );
        container.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        JPanel top, center, foot;

        top    = makePanel( "name:", txt1 );
        center = makePanel( "password:", txt2 );


        okButton.addActionListener( this );
        okButton.setActionCommand("ok");

        cancelButton.addActionListener( this );
        cancelButton.setActionCommand("cancel");

        foot = makeOKCancelPanel( okButton, cancelButton );

        container.add( top );
        container.add( Box.createRigidArea( new Dimension( 0, 10 ) ) );
        container.add( center );
        container.add( Box.createRigidArea( new Dimension( 0, 10 ) ) );
        container.add( foot );

        this.setContentPane( container );
        this.setTitle("Create a new room");
        this.setBounds(200, 200, 260, 80);
        this.pack();
        this.setVisible( true );
    }

    public void actionPerformed( ActionEvent e )
    {
        String action  = e.getActionCommand();
        if( action.trim().equals( "cancel" ) )
        {
            cv = null;
            this.setVisible( false );
        }
        else if( action.trim().equals( "ok" ) )
        {
            //to read the name of the new room
            //to send it to the server
            String new_name = txt1.getText().trim();
            String password = txt2.getText().trim();
            if( !new_name.isEmpty()  )
            {
                String command = "command=create_private_room;name=" + new_name.trim() + ";";
                if( !password.isEmpty() )
                    command += "password=" + password.trim() +";";
                command += ":";
                cv.send( command );
                this.setVisible( false );
            }
        }
        else
        {
            this.setVisible( false );
        }
    }
}

class RestoreUser extends JFrame {
    ClnView view;
    Cln client;

    DefaultListModel<User> ignoredUserModel = new DefaultListModel<>();
    DefaultListModel<User> restoreUserModel = new DefaultListModel<>();
    JList<User> ignoredUsers = new JList<>(ignoredUserModel);
    JList<User> restoredUsers = new JList<>(restoreUserModel);

    JButton btnRestore = new JButton( Parser.h5("restore &gt") );
    JButton btnRestoreAll = new JButton( Parser.h5("restore all &gt&gt") );
    JButton btnIgnore = new JButton( Parser.h5("&lt ignore") );
    JButton btnIgnoreAll = new JButton( Parser.h5("&lt&lt ignore all") );

    JButton btnOk = new JButton( Parser.h5("Ok") );
    JButton btnCancel = new JButton( Parser.h5("cancel") );

    List<User> ignoreList;
    List<User> existingUserList;


    synchronized void buildLists() {
        ignoredUserModel.addAll(ignoreList.stream().filter(u -> !u.name.equals(view.userName)).collect(Collectors.toList()));
        restoreUserModel.addAll (existingUserList.stream().filter(u ->! u.name.equals(view.userName)).collect(Collectors.toList()));
        //for (User user : ignoreList) {
        //    if (!(view.userName.equals(user.name))) {
        //        iul.addElement(user);
        //    }
        //}

        //for (User user : existingUserList) {
        //    if (!(view.userName.equals(user.name))) {
        //        rul.addElement(user);
        //    }
        //}

    }

    ActionListener restoreActionListener = new ActionListener() {
        public synchronized void actionPerformed(ActionEvent e) {
            String lnfName = e.getActionCommand();
            //noinspection IfCanBeSwitch
            if(lnfName.equals("Ok")) {
                client.ignore.clear();
                client.ignore.addAll(ignoreList);
                /*if( existingUserList.size() > 0 )
                {
                                        Iterator it = existingUserList.iterator();
                                        String curent = new String();
                                        while( it.hasNext() )
                                        {
                                                curent = ( String )it.next();
                                                if( curent.trim()!=""  && !client.existingUserList.contains( curent ) ) client.existingUserList.add( curent );
                                        }
                                }
                 */
                System.out.println( client.ignore);
                StringBuilder str = new StringBuilder("command=set_ignore_list;");
                if(!ignoreList.isEmpty()) {
                    for (User user : ignoreList) {
                        str.append("user=").append(user).append(";");
                    }
                }else {
                    str = new StringBuilder("command=reset_ignore_list;");
                }
                str.append(":");
                client.writer.send(str.toString());
                //client.writer.send("command=request_user_list;:");
                client.writer.send("command=request_all_refresh_user_list;:");

                System.out.println( "lista utilizatorilor ignorati:" + ignoreList );

                dispose();
            }else if(lnfName.equals("cancel")) {
                dispose();
            }else if(lnfName.equals("restore")) {
                User its = ignoredUsers.getSelectedValue();
                if( !existingUserList.contains( its ) ) {
                    restoreUserModel.addElement( its );
                    existingUserList.add( its );
                }
                if( ignoreList.contains( its ) ) {
                    ignoredUserModel.removeElement( its );
                    ignoreList.remove( its );
                }
            } else if(lnfName.equals("restore_all")) {
                ignoredUserModel.removeAllElements();
                restoreUserModel.addAll(ignoreList);
                //for (User user : ignoreList) restoreUserModel.addElement(user);
                existingUserList.addAll(ignoreList);
                ignoreList.clear();
            } else if(lnfName.equals("ignore")) {
                User its = restoredUsers.getSelectedValue();
                if( !ignoreList.contains( its ) ) {
                    ignoredUserModel.addElement(its);
                    ignoreList.add(its);
                }
                if( existingUserList.contains( its ) ) {
                    existingUserList.remove(its);
                    restoreUserModel.removeElement(its);
                }
            } else if(lnfName.equals("ignore_all")) {
                restoreUserModel.removeAllElements();
                ignoredUserModel.addAll(existingUserList);
                //for (User user : existingUserList) ignoredUserModel.addElement(user);
                ignoreList.addAll( existingUserList );
                existingUserList.clear();
            }
        }
    };

    RestoreUser(ClnView view) {
        setResizable(false);
        System.out.println("so far");
        this.view = view;
        this.client = view.client;
        ignoreList = new ArrayList<>(client.ignore);
        existingUserList = new ArrayList<>( );

        ignoreList.removeIf( u -> u.name.equals(view.userName));
        //existingUserList.removeIf( u -> u.name.equals(view.userName));
        //existingUserList.removeAll(ignoreList);

        setBounds(200, 200, 450, 164);

        Container container = this.getContentPane();
        container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );


        JPanel middle,top,foot;

        middle = new JPanel();
        top = new JPanel();
        foot = new JPanel();

        middle.setLayout( new BoxLayout( middle, BoxLayout.PAGE_AXIS ) );
        top.setLayout( new BoxLayout( top, BoxLayout.LINE_AXIS ) );
        foot.setLayout( new BoxLayout( foot, BoxLayout.LINE_AXIS ) );

        JScrollPane ignoredUsrScroller = new JScrollPane(ignoredUsers);
        ignoredUsrScroller.setPreferredSize( new Dimension( 150,70 ) );
        ignoredUsrScroller.setBorder( BorderFactory.createTitledBorder("ignored:") );

        top.add( ignoredUsrScroller );

        btnOk.setPreferredSize( new Dimension( 100, 20) );
        btnOk.setActionCommand("Ok");
        btnOk.addActionListener(restoreActionListener);


        btnCancel.setActionCommand("cancel");
        btnCancel.setPreferredSize( new Dimension( 100, 20) );
        btnCancel.addActionListener(restoreActionListener);


        btnRestore.setPreferredSize( new Dimension( 100, 20) );
        btnRestore.setActionCommand("restore");
        btnRestore.addActionListener(restoreActionListener);

        middle.add(btnRestore);
        middle.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnRestoreAll.setPreferredSize( new Dimension( 100, 20) );
        btnRestoreAll.setActionCommand("restore_all");
        btnRestoreAll.addActionListener(restoreActionListener);

        middle.add(btnRestoreAll);
        middle.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnIgnore.setPreferredSize( new Dimension( 100, 20) );
        btnIgnore.setActionCommand("ignore");
        btnIgnore.addActionListener(restoreActionListener);

        middle.add( btnIgnore );
        middle.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );

        btnIgnoreAll.setPreferredSize( new Dimension( 100, 20) );
        btnIgnoreAll.addActionListener(restoreActionListener);
        btnIgnoreAll.setActionCommand("ignore_all");

        middle.add( btnIgnoreAll );

        middle.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );
        top.add( middle );

        JScrollPane restoredUsrScroller = new JScrollPane(restoredUsers);
        restoredUsrScroller.setPreferredSize( new Dimension( 150, 70 ) );
        restoredUsrScroller.setBorder(BorderFactory.createTitledBorder("restored:") );
        top.add( restoredUsrScroller );

        top.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        container.add( top );

        foot.add( Box.createRigidArea( new Dimension( 266, 0 ) ) );
        foot.add( btnOk );
        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
      foot.add( btnCancel );

        foot.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        container.add( foot );
        pack();

        buildLists();

        setVisible(true);
    }
}

class Connector extends JFrame implements ItemListener {

    String userName = "";
    String groupName = "Guests";
    String port = "8888";
    String address = "localhost";
    ClnView view;

    JTextField txtGroup = new JTextField();
    JTextField txtUser = new JTextField();
    JTextField txtPort = new JTextField();
    JTextField txtAddress = new JTextField();

    JLabel lblUser =  new JLabel();
    JLabel lblGroup =  new JLabel();
    JLabel lblPort =  new JLabel();
    JLabel lblAddress =  new JLabel();

    JButton btnConnect = new JButton( Parser.h5("connect") );
    JButton btnAnonymous1 = new JButton( Parser.h5("Guest") );
    JButton btnCancel = new JButton( Parser.h5("cancel") );

    boolean autologin = false;
    boolean logged = false;
    Container container;

    int tries = 0;
    int x = 0;
    JCheckBox ckMoreParameters = new JCheckBox( Parser.h5( "more parameters") );

    JPanel left, center, right, top, foot;

    public void itemStateChanged(ItemEvent e)
    {
        switch(e.getStateChange())
        {
            case ItemEvent.SELECTED:
                left.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
                left.add( lblPort );
                left.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
                left.add( lblAddress );

                center.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
                center.add( txtPort );
                center.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
                center.add( txtAddress );

                right.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
                right.add( btnCancel );
                right.add( Box.createRigidArea( new Dimension( 0, 25 ) ) );

                container.remove( foot );
                container.add( foot );

                pack();
                break;
            case ItemEvent.DESELECTED:

                left.removeAll();
                center.removeAll();
                right.removeAll();

                left.add(lblUser);
                left.add( Box.createRigidArea( new Dimension( 0 , 5 ) ) );
                left.add( lblGroup );

                center.add( txtUser );
                center.add( Box.createRigidArea( new Dimension( 0 , 5 ) ) );
                center.add( txtGroup );

                right.add( btnConnect );
                right.add( Box.createRigidArea( new Dimension( 0 , 5 ) ) );
                right.add( btnAnonymous1 );

                pack();
                break;
        }
        return;
    }


    Connector(ClnView _view)
    {
        String str;
        view = _view;
        setBounds(300, 300, 320, 130);
        setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );

        left = new JPanel();
        center = new JPanel();
        right = new JPanel();
        top = new JPanel();
        foot = new JPanel();

        setResizable(false);

        container = this.getContentPane();
        container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );

        left.setLayout( new BoxLayout( left, BoxLayout.PAGE_AXIS ) );
        center.setLayout( new BoxLayout( center, BoxLayout.PAGE_AXIS ) );
        right.setLayout( new BoxLayout( right, BoxLayout.PAGE_AXIS ) );
        top.setLayout( new BoxLayout( top, BoxLayout.LINE_AXIS ) );
        foot.setLayout( new BoxLayout( foot, BoxLayout.LINE_AXIS ) );

        lblUser.setPreferredSize( new Dimension( 50,20 ) );
        lblUser.setMaximumSize( new Dimension( 50,20 ) );
        lblUser.setText( Parser.h5("user:") );
        left.add(lblUser);

        left.add( Box.createRigidArea( new Dimension( 0 , 5 ) ) );

        lblGroup.setPreferredSize( new Dimension( 50, 20 ) );
        lblGroup.setMaximumSize( new Dimension( 50, 20 ) );
        lblGroup.setText( Parser.h5( "group:") );

        left.add( lblGroup );

        lblPort.setPreferredSize( new Dimension( 50, 20 ) );
        lblPort.setMaximumSize( new Dimension( 50, 20 ) );
        lblPort.setText( Parser.h5("port:") );

        lblAddress.setPreferredSize( new Dimension( 50, 20 ) );
        lblAddress.setMaximumSize( new Dimension( 50, 20 ) );
        lblAddress.setText( Parser.h5("address:") );


        txtUser.setMaximumSize( new Dimension( 200, 20 ) );
        txtUser.setPreferredSize( new Dimension( 150, 20 ) );
        txtUser.setMinimumSize( new Dimension( 50, 20 ) );
        txtUser.setText( userName );
        center.add( txtUser );

        center.add( Box.createRigidArea( new Dimension( 0 , 5 ) ) );

        txtGroup.setMaximumSize( new Dimension( 200, 20 ) );
        txtGroup.setPreferredSize( new Dimension( 150, 20 ) );
        txtGroup.setMinimumSize( new Dimension( 50, 20 ) );

        txtGroup.setText( groupName );
        center.add( txtGroup );

        ckMoreParameters.setPreferredSize( new Dimension( 50, 20 ) );
        ckMoreParameters.addItemListener(this);
        foot.add(ckMoreParameters);

        txtPort.setMaximumSize( new Dimension( 200, 20 ) );
        txtPort.setPreferredSize( new Dimension( 150, 20 ) );
        txtPort.setMinimumSize( new Dimension( 50, 20 ) );

        txtPort.setText(port);

        txtAddress.setMaximumSize( new Dimension( 200, 20 ) );
        txtAddress.setPreferredSize( new Dimension( 150, 20 ) );
        txtAddress.setMinimumSize( new Dimension( 50, 20 ) );
        txtAddress.setText( address );


        btnConnect.setPreferredSize( new Dimension( 70, 20 ) );
        btnConnect.setMaximumSize( new Dimension( 70, 20 ) );
        btnConnect.setActionCommand("connect");
        btnConnect.addActionListener(connectListener);
        right.add( btnConnect );

        right.add( Box.createRigidArea( new Dimension( 0 , 5 ) ) );

        btnAnonymous1.setPreferredSize( new Dimension( 50, 20 ) );
        btnAnonymous1.setMaximumSize( new Dimension( 70, 20 ) );
        btnAnonymous1.setActionCommand("anonymous");
        btnAnonymous1.addActionListener(connectListener);

        right.add( btnAnonymous1 );

        left.setBorder( BorderFactory.createEmptyBorder(5,5,5,2) );
        center.setBorder( BorderFactory.createEmptyBorder(5,2,5,2) );
        right.setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        top.setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );
        foot.setBorder( BorderFactory.createEmptyBorder(5,5,5,5) );

        top.add( left );
        top.add( center );
        top.add( right );
        container.add( top );
        container.add( foot );

        pack();

        autologin = false;
        logged = false;
    }

    void setAllVisible(boolean vs) {
        this.setVisible( true );
    }

    boolean doConnect()
    {
        try
        {
            view.client.connect();
        }
        catch(Exception err)
        {
            err.printStackTrace();
        }
        if(!view.client.isConnected)
        {
            return false;
        }
        return view.client.isConnected;

    }

    void doLogIn() {
        if(tries == 10){System.exit(0);}
        tries++;
        groupName = txtGroup.getText();
        if(groupName.length() >= 32)
        {
            groupName = groupName.substring(0, 30);
            txtGroup.setText(groupName);
        }
        userName = txtUser.getText();
        if(userName.length() >= 32)
        {
            userName = userName.substring(0, 30);
            txtUser.setText(userName);
        }
        port = txtPort.getText();
        address = txtAddress.getText();

        view.client.isConnected = false;

        view.client.strPort = port;
        view.client.strAddress = address;

        if( !doConnect() )
            return;

        view.client.writer.send("command=connect;user=" + userName + ";group=" + groupName + ";as=false;pwd=x;:");
        view.client.writer.send("command=connect;user=" + userName + ";group=" + groupName + ";as=false;pwd=x;:");

        if( !view.client.reader.waitAccept() )
        {
            return;
        }
        if( view.client.accepted )
        {
            view.disconnected = false;
            view.userName = userName;
            view.groupName = groupName;

            setVisible(false);
            view.frame.setVisible(true);
        }
        else if( tries == 5 )
        {
            System.exit(0);
        }
        view.client.start();
        view.frame.setTitle("Welcome " + view.userName + "!");
        return;
    }

    boolean doAutoLogIn()
    {
        if(tries == 10){System.exit(0);}
        tries++;

        txtUser.setText("Anonymous");

        groupName = txtGroup.getText();
        userName = txtUser.getText();
        port = txtPort.getText();
        address = txtAddress.getText();

        view.client.isConnected = false;

        view.client.strPort = port;
        view.client.strAddress = address;

        if(!doConnect())return false;

        view.client.writer.send("command=connect;user=Anonymous;group=" + groupName + ";as=false;pwd=x;:");
        view.client.isAnonymous = true;
        if(!view.client.reader.waitAccept())
        {
            return false;
        }

        view.groupName = groupName;

        setVisible(false);
        view.frame.setVisible(true);
        view.client.start();

        view.frame.setTitle("Welcome " + view.userName + "!");
        return true;
    }

    ActionListener connectListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            String lnfName = e.getActionCommand();
            if(lnfName.equals("cancel"))
            {
                System.exit(0);
            }
            else if(lnfName.equals("connect"))
            {
                doLogIn();
            }
            else if(lnfName.equals("anonymous"))
            {
                doAutoLogIn();
            }
        }
    };

    boolean connect() {
        this.setVisible(true);
        return true;
    }
}

@SuppressWarnings("removal")
//public class ClnView extends JPanel implements WindowListener {
public class ClnView extends JFrame implements WindowListener {

    boolean disconnected = false;
    JEditorPane doc = new JEditorPane();
    String userName = "Guest";
    String groupName = "Guests";

    List<PrivateClnSession> sessions = new ArrayList<>();

    List<String> localResponses;
    List<String> remoteResponses;
    ListIterator<String> it;

    JFrame frame;
    String strDetail = "";
    User former = null;
    JLabel color = new JLabel();
    JList colors;

    boolean bold = false;
    boolean italic = false;
    boolean font = false;
    String fontColor  = null;

    JPopupMenu menu;
    Container container;

    public void send( String s )
    {
        client.writer.send( s );
    }

    class Renderer extends JLabel implements ListCellRenderer {
        public static final long serialVersionUID = 50L;
        public Renderer() {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }

        /*
         * This method finds the image and text corresponding
         * to the selected value and returns the label, set up
         * to display the text and image.
         */

        synchronized public Component getListCellRendererComponent( JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            User u = ((User)value);
            //System.out.println("getListCellRendererComponent()");

            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            } else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            //Set the icon and text.  If icon was null, say so.
            ImageIcon icon;
            //System.out.println( "u.getIgnored()" + u.getIgnored() );
            if( u.getIgnored() )
            {
                icon = createImageIcon("images/ignored.gif", "ignored");
            }
            else
            {
                boolean a, v;
                icon = createImageIcon( "images/noAudioNoVideo.gif", "noAudioNoVideo" );
            }
            if( icon!=null ) setIcon(icon);
            String text = u.getName();
            if( text!=null ) setText( Parser.h5( text ) ) ;
            //else System.out.println("text = null");
            return this;
        }
    }

    class RoomRenderer extends JLabel implements ListCellRenderer
    {
        public static final long serialVersionUID = 50L;
        public RoomRenderer()
        {
            setOpaque(true);
            setHorizontalAlignment(LEFT);
            setVerticalAlignment(CENTER);
        }

        /*
         * This method finds the corresponding
         * to the selected value and returns the label, set up
         * to display the text.
         */

        synchronized public Component getListCellRendererComponent(
                JList list, Object value, int index, boolean isSelected, boolean cellHasFocus)
        {
            //System.out.println("RoomRenderer::getListCellRendererComponent()" );
            //Get the selected index. (The index param isn't
            //always valid, so just use the value.)
            Room room = (Room)value;
            if (isSelected)
            {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else
            {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }

            //Set the text.
            String name = room.getName();
            if( name!=null )
            {
                 this.setText( Parser.h5( name +"("+ room.getNumberOfDwellers() +  ")" ) ) ;
                 //System.out.println( "name==" + name );
            }
            String toolTipText = room.getRoomDwellersInfo();
            if( toolTipText!=null )
            {
                 this.setToolTipText( toolTipText );
                 //System.out.println( "toolTipText==" + toolTipText + "\"" );
            }
            this.setPreferredSize( new Dimension( 475, 20 ) );

            return this;
        }
    }

    private String substitution( String val ) {
        String stringArray[] = val.split("\n");
        String local = val;
        for( int i=0; i< stringArray.length ; i++) {
            stringArray[i] = Parser.replace( stringArray[i],":)", "<img src=\"smileys\\001.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":|", "<img src=\"smileys\\002.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":((", "<img src=\"smileys\\003.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":H", "<img src=\"smileys\\004.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":}", "<img src=\"smileys\\005.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":C", "<img src=\"smileys\\006.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":[", "<img src=\"smileys\\007.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],"8|", "<img src=\"smileys\\008.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":(", "<img src=\"smileys\\009.gif\">");
            stringArray[i] = Parser.replace( stringArray[i],":{", "<img src=\"smileys\\010.gif\">");
        }
        local ="";
        for( int i=0; i < stringArray.length; i++ ) local = local + stringArray[i];
        return local;
    }

    public String getName() {
        return userName;
    }

    protected static URL getPage(String path)
    {
        java.net.URL url = ClnView.class.getResource(path);
        if (url != null)
        {
            return url;
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    private void setText( String val ) {
        String local = substitution( val );
        //System.out.println("local= " + local);
        try
        {
            doc.setText( local );
        } catch( Exception e )
        {
            e.printStackTrace();
        }
    }

    public void pack()
    {
        frame.pack();
    }

    void write(String str) {
        strDetail = str + "<br>\n" + strDetail;
        setText( strDetail );
    }

    void refresh() {
        // doc.setText( strDetail );
    }


     void addUser(User str) {
        System.out.println("addUser");
        listModel.addElement( str );
        //userList.add( str );
    }

    public void setModel( DefaultListModel dlm )
    {
        userList.setModel( dlm );
        listModel = dlm;
    }

    public void setRoomListModel( DefaultListModel dlm )
    {
        roomList.setModel( dlm );
    }

    Cln client = null;

    boolean isBad = true;

    DefaultListModel<User> listModel=new DefaultListModel<>();


    JList<User> userList =  new JList<>( listModel );

    JTextPane txt = new JTextPane();

    JButton btnClose =  new JButton( Parser.h5("exit") );
    JScrollPane usrPanel = new JScrollPane( userList );

    JButton btnSend = new JButton( Parser.h5("send") );
    JButton btnIgnore = new JButton( Parser.h5("block/unblock") );
    JButton btnPrivate = new JButton( Parser.h5("chat with") );
    JButton btnRestore = new JButton( Parser.h5("restore") );
    JButton btnTools = new JButton( Parser.h5("tools") );
    JButton btnAdmin = new JButton( Parser.h5("administer") );
    //JButton btnHelp = new JButton( Parser.h5("help") );
    //JButton btnRename = new JButton( Parser.h5("rename") );
    JCheckBox ckSayOnlyTo = new JCheckBox( Parser.h5("private") );

    JPanel top = new JPanel();
    JPanel center = new JPanel();
    JPanel foot = new JPanel();
    JPanel interTopCenter3 = new JPanel();

    JButton btnBold, btnItalic;

    JScrollPane jsp;

    Color darkBlue;

    JList roomList = new JList();

    JButton toLobby= new JButton( Parser.h5("back to lobby") );
    JButton toRoom= new JButton( Parser.h5("enter private room") );
    JButton createRoom= new JButton( Parser.h5("create a private room") );

    public Cln getClient()
    {
        return client;
    }

    boolean appendToTop = true;

    boolean sayOnlyTo = false;

    int dy = 0;
    int dx = 0;
    ClnView view = this;
    boolean vs = true;
    void onSetAdmin() {
        btnAdmin.setVisible(vs);
    }

    void onResetAdmin() {
        btnAdmin.setVisible(false);
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    public static ImageIcon createImageIcon(String path, String description)
    {
        URL imgURL =  ClnView.class.getResource(path);
        if (imgURL != null)
        {
            return new ImageIcon(imgURL, description);
        }
        else
        {
            System.err.println("Couldn't find file: " + path);
            return null;
        }
    }

    public void cln_init()
    {
        disconnected = false;

        container = this.getContentPane();
        container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );

        darkBlue = new Color( (float)0.0, (float)0.4, (float)0.58 );
        container.setBackground( darkBlue );

        client = new Cln(this);
        if( client.isBad() )return;

        JPanel interTopCenter = new JPanel();
        interTopCenter.setBackground( darkBlue );
        interTopCenter.setLayout( new BoxLayout( interTopCenter, BoxLayout.LINE_AXIS ) );

        ListSelectionListener lsl = new ListSelectionListener()
        {
            public void valueChanged( ListSelectionEvent e )
            {
                if( e.getValueIsAdjusting() ) return;
                ImageIcon i = (ImageIcon)colors.getSelectedValue();
                color.setIcon( i );
                String local = i.getDescription();

                if( local!=null && !local.trim().equals("") )
                {
                    System.out.println("culoarea aleasa este " + local + " .");
                    String text = getText();

                    font = true;
                    fontColor = local.trim();

                    if( text!=null && !text.trim().equals("") )
                    {

                        Pattern p = Pattern.compile("(.*)<font color=\"[a-z]*\">(.*)</font>(.*)");
                        Matcher m = p.matcher( text );
                        if( !m.matches() )
                        {
                            //System.out.println("Nu corespunde pattern-ului.");
                            text = "<font color=\"" + local + "\">" +text+ "</font>";
                        }
                        else
                        {
                            //System.out.println("Corespunde pattern-ului.");
                            text = m.group(1) + "<font color=\"" + local + "\">" + m.group(2) + "</font>" + m.group(3);
                        }
                        //System.out.println("text= " + text );
                        txt.setText( text );
                    }
                    menu.setVisible( false );
                } else System.out.println(" Nu s-a ales nici o culoare. ");
            }
        };


        interTopCenter.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        interTopCenter.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        interTopCenter.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );


        interTopCenter3.setLayout( new BoxLayout( interTopCenter3, BoxLayout.LINE_AXIS ) );
        interTopCenter3.setBackground( darkBlue );

        MouseListener ml1 = new MouseListener()
        {

            private int realPosition( String val, int pos )
            {
                String local = val.replaceAll("&amp;", "%5%");
                local = local.replaceAll("&gt;", "%4%");
                local = local.replaceAll("&lt;", "%4%");
                Pattern p = Pattern.compile("(.*)(%[0-9]%)(.*)");

                ArrayList<String> al = new ArrayList<String>();
                while( local!="" )
                {
                    Matcher m = p.matcher( local );
                    if( m.matches() )
                    {
                        if( m.group(3).length()>0 ) al.add( m.group(3) );
                        if( m.group(2).length()>0 )al.add( m.group(2) );
                        local = m.group(1);
                    }
                    else
                    {
                        if( local.length()>0 )al.add( local );
                        local = "";
                    }
                }

                int i=0;
                int j=al.size()-1;
                String local2 = null;
                for( ; i<j ; i++, j-- )
                {
                    local2 = al.get(i);
                    al.set(i, al.get(j));
                    al.set(j, local2);
                }

                int count = 0;
                int sum = 0;
                int pos1 = 0;
                boolean continuation;
                for( ; count < al.size() ; count++ )
                {
                    if( !Pattern.matches( "%[0-9]%", al.get(count) ) ) sum += al.get(count).length();
                    else sum = sum + 1;
                    if( sum >= pos )
                    {
                        if( !Pattern.matches( "%[0-9]%", al.get(count) ) ) pos1 = al.get(count).length()-( sum-pos );
                        else pos = 1;
                        break;
                    }
                }
                p = Pattern.compile( "%([0-9])%" );
                Matcher m;
                for( int k=0; k<count; k++ )
                {
                    m = p.matcher( al.get(k) );
                    if( m.matches() ) pos1 += Integer.parseInt( m.group(1), 10);
                    else pos1 += al.get(k).length();
                }

                return pos1;
            }
            public void mouseClicked( MouseEvent e )
            {
                boolean b, i, f;
                String fCol;
                b = i= f = false;
                fCol = null;

                JLabel jl = (JLabel) e.getSource();
                if( jl!=null )
                {
                    String description = ((ImageIcon)((jl).getIcon())).getDescription();
                    if( description==null || description.trim().equals("") ) return;
                    int x = txt.getCaretPosition();
                    String str = undressPatterns();
                    System.out.println( " str = " + str );
                    x = realPosition( str , x );
                    Matcher m = ptATag1.matcher( str );
                    if( m.matches() )
                    {
                        b = true;
                        str = m.group(1) + m.group(2) + m.group(3);

                    }
                    m = ptATag2.matcher( str );
                    if( m.matches() )
                    {
                        i = true;
                        str = m.group(1) + m.group(2) + m.group(3);
                    }
                    m = ptATag3.matcher( str );
                    if( m.matches() )
                    {
                        f = true;
                        fCol = m.group(2);
                        str = m.group(1) + m.group(3) + m.group(4);
                    }

                    if( x > str.length() ) x = ( str.length() );
                    System.out.println( "str= " + str + "str.length() = " + str.length() );
                    System.out.println( "x= " + x);
                    if( x>0 && str.length()>0 && x!= str.length() ) x = x-1;
                    //x = realPosition( str, x );
                    System.out.println("x= " + x);
                    String t1 = str.substring(0, x);
                    String t2 = str.substring( x );
                    //System.out.println("t1 = " + t1);
                    //System.out.println("t2 = " + t2);

                    if( description.equals( "001" ) ) str = (t1 + " :)" + t2);
                    else if( description.equals( "002" ) ) str = (t1 + " :|" + t2);
                    else if( description.equals( "003" ) ) str = (t1 + " :((" + t2);
                    else if( description.equals( "004" ) ) str = (t1 + " :H" + t2);
                    else if( description.equals( "005" ) ) str = (t1 + " :}" + t2);
                    else if( description.equals( "006" ) ) str = (t1 + " :C" + t2);
                    else if( description.equals( "007" ) ) str = (t1 + " :[" + t2);
                    else if( description.equals( "008" ) ) str = (t1 + " 8|" + t2);
                    else if( description.equals( "009" ) ) str = (t1 + " :(" + t2);
                    else if( description.equals( "010" ) ) str = (t1 + " :{" + t2);

                    if( i ) str = "<i>" + str + "</i>";
                    if( b ) str = "<b>" + str + "</b>";
                    if( f ) str = "<font color=\"" + fCol + "\">" + str + "</font>";
                    txt.setText( str );
                    checkForPatterns();
                }
            }
            public void mouseEntered( MouseEvent e ){}
            public void mouseExited( MouseEvent e ){}
            public void mousePressed( MouseEvent e ){}
            public void mouseReleased( MouseEvent e ){}
        };

        ImageIcon im = createImageIcon( "images/bold.gif", "bold" );
        if( im!=null )
        {
            btnBold = new JButton();
            btnBold.setIcon( im );
        }
        else
        {
             btnBold = new JButton( "<html><b>B</b></html>" );
        }
        btnBold.setPreferredSize( new Dimension( 25, 25 ) );
        btnBold.setMaximumSize( new Dimension( 25, 25 ) );
        btnBold.setMinimumSize( new Dimension( 25, 25 ) );
        btnBold.setActionCommand("bold");
        btnBold.addActionListener( mainListener );
        btnBold.setToolTipText("bold/normal font");

        interTopCenter3.add( btnBold );
        interTopCenter3.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        im = createImageIcon( "images/italic.gif", "italic" );
        if( im!=null )
        {
            btnItalic = new JButton();
            btnItalic.setIcon( im );
        }
        else
        {
             btnItalic = new JButton( "<html><i>I</i></html>" );
        }
        btnItalic.setPreferredSize( new Dimension( 25, 25 ) );
        btnItalic.setMaximumSize( new Dimension( 25, 25 ) );
        btnItalic.setMinimumSize( new Dimension( 25, 25 ) );
        btnItalic.setActionCommand("italic");
        btnItalic.setToolTipText("italic/normal font");

        btnItalic.addActionListener( mainListener );

        interTopCenter3.add( btnItalic );
        interTopCenter3.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );

        MouseAdapter ml = new MouseAdapter()
        {
            public void mouseClicked( MouseEvent e )
            {
                if( colors!=null )colors.setVisible( true );
                Rectangle r1 = color.getBounds();
                Rectangle r2 = interTopCenter3.getBounds();

                menu.show( container, ( r1.x + r2.x ) , (r1.y + r2.y - 85) );
            }
        };

        {
            ImageIcon i = createImageIcon( "images/black.gif", "black rect" );
            if( i!=null )
            {
                color = new JLabel( i );
                System.out.println("i!=o");
            }
            else
            {
                color = new JLabel();
            }
        }
        ImageIcon ii= createImageIcon("images/colours.gif", "colours" );
        if( ii!=null )color.setIcon( ii );
        color.setMaximumSize( new Dimension( 25, 25 ) );
        color.setMinimumSize( new Dimension( 25, 25 ) );
        color.setPreferredSize( new Dimension( 25, 25 ) );
        color.setBackground( Color.black );
        color.addMouseListener( ml );
        color.setBorder( BorderFactory.createLineBorder( Color.black ) );
        color.setToolTipText("colored text");

        interTopCenter3.add( color );
        interTopCenter3.add( Box.createRigidArea( new Dimension( 10, 0 ) ) );
        for( int i=1; i< 10; i++ )
        {
            JLabel jl1 = new JLabel();
            ImageIcon i1;
            i1 = createImageIcon( "smileys/" +"00" + i + ".gif", "00"+i );
            if( i1!=null )
            {
                jl1.setIcon( i1 );
                jl1.addMouseListener( ml1 );
                interTopCenter3.add( jl1 );
                if( i!= 10 ) interTopCenter3.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
                i1 = null;
            }
        }

        btnIgnore.setActionCommand("ignore");
        btnIgnore.setMaximumSize( new Dimension( 110, 20 ) );
        btnIgnore.setMinimumSize( new Dimension( 110, 20 ) );
        btnIgnore.addActionListener(mainListener);
        btnIgnore.setToolTipText( "block/unblock a user from the list" );

        center.setLayout( new BoxLayout( center, BoxLayout.LINE_AXIS ) );
        center.setBackground( darkBlue );

        doc.addHyperlinkListener(hyperlinkListener);
        doc.setEditable( false );
        doc.setEditorKit( new HTMLEditorKit() );
        File f = new File(".");
        URL url = null;
        try
        {
            //noinspection deprecation
            url= f.toURL();
        }
        catch( Exception e )
        {
            //noinspection CallToPrintStackTrace
            e.printStackTrace();
        }
        //System.out.println( "url = " + url );
        (( HTMLDocument )(doc.getDocument()) ).setBase(url);

        //System.out.println("base = " + (( HTMLDocument )(doc.getDocument()) ).getBase( ) );
        jsp = new JScrollPane( doc );

        jsp.setPreferredSize( new Dimension( 545, 150 ) );
        jsp.setMaximumSize( new Dimension( 545, 150 ) );
        jsp.setMinimumSize( new Dimension( 545, 150 ) );

        JPanel localPanel = new JPanel();
        localPanel.setLayout( new BoxLayout( localPanel, BoxLayout.PAGE_AXIS ) );
        localPanel.setBackground( darkBlue );

        Renderer  lcr= new Renderer();
        lcr.setPreferredSize( new Dimension( 90, 20 ) );
        userList.setCellRenderer( lcr );
        userList.addMouseListener(userListMouseListener);
        usrPanel.setPreferredSize( new Dimension( 110,120 ) );
        usrPanel.setMaximumSize( new Dimension( 110,120 ) );
        usrPanel.setMinimumSize( new Dimension( 110,120 ) );
        usrPanel.setBorder( BorderFactory.createTitledBorder("users") );

        JPanel centerFoot = new JPanel();
        centerFoot.setLayout( new BorderLayout() );
        centerFoot.add( btnIgnore, BorderLayout.CENTER );
        centerFoot.setPreferredSize( new Dimension( 110, 20 ) );
        centerFoot.setMaximumSize( new Dimension( 110, 20 ) );

        localPanel.add( usrPanel );
        localPanel.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
        localPanel.add( centerFoot );

        center.add( localPanel );
        center.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
        center.add( jsp );

        foot.setLayout( new BoxLayout( foot, BoxLayout.LINE_AXIS ) );
        foot.setBackground( darkBlue );

        JScrollPane jspTxt = new JScrollPane( txt );
        jspTxt.setBorder( BorderFactory.createTitledBorder("Type your message here") );
        jspTxt.setMaximumSize( new Dimension( 505, 60 ) );
        jspTxt.setMinimumSize( new Dimension( 505, 60 ) );
        jspTxt.setPreferredSize( new Dimension( 505, 60 ) );
        txt.addKeyListener(txtKeyListener);
        txt.setEditorKit( new HTMLEditorKit() );

        btnSend.setActionCommand("send");
        btnSend.setPreferredSize( new Dimension( 150, 20 ) );
        btnSend.setMaximumSize( new Dimension( 150, 20 ) );
        btnSend.setMinimumSize( new Dimension( 150, 20 ) );
        btnSend.setToolTipText("send message");

        btnSend.addActionListener(mainListener);

        ckSayOnlyTo.setPreferredSize( new Dimension( 150, 20 ) );
        ckSayOnlyTo.setMaximumSize( new Dimension( 150, 20 ) );
        ckSayOnlyTo.setMinimumSize( new Dimension( 150, 20 ) );
        ckSayOnlyTo.setToolTipText("whispering to another person");

        JPanel footLeft = new JPanel();
        footLeft.setLayout( new BorderLayout() );
        footLeft.setBackground( darkBlue );

        footLeft.add( btnSend, BorderLayout.NORTH );
        footLeft.add( Box.createRigidArea( new Dimension( 0, 10 ) ), BorderLayout.CENTER );
        footLeft.add( ckSayOnlyTo,  BorderLayout.SOUTH );

        footLeft.setMaximumSize( new Dimension( 150, 50 ) );
        footLeft.setMinimumSize( new Dimension( 150, 50 ) );
        footLeft.setPreferredSize( new Dimension( 150, 50 ) );

        foot.add( jspTxt );
        foot.setBackground( darkBlue );
        foot.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
        foot.add( footLeft );

        isBad = false;
        localResponses = new ArrayList<String>();
        remoteResponses = new ArrayList<String>();

        //to create the interface for lobby/private rooms
        //to create interface for at list four buttons

        toLobby.setMaximumSize( new Dimension(150, 20) );
        toLobby.setMinimumSize( new Dimension(150, 20) );
        toLobby.setPreferredSize( new Dimension(150, 20) );
        toLobby.setActionCommand("back_to_lobby");
        toLobby.addActionListener( mainListener );
        toLobby.setToolTipText("moves you to lobby");

        toRoom.setPreferredSize( new Dimension( 150, 20 ) );
        toRoom.setMaximumSize( new Dimension(150, 20) );
        toRoom.setMinimumSize( new Dimension(150, 20) );
        toRoom.setActionCommand("enter_private_room");
        toRoom.addActionListener( mainListener );
        toRoom.setToolTipText("moves you to a room");

        createRoom.setPreferredSize( new Dimension( 150, 20 ) );
        createRoom.setMaximumSize( new Dimension(150, 20) );
        createRoom.setMinimumSize( new Dimension(150, 20) );
        createRoom.setActionCommand("create_private_room");
        createRoom.addActionListener( mainListener );
        createRoom.setToolTipText("creates a room");

        btnAdmin.setActionCommand("admin");
        btnAdmin.setMaximumSize( new Dimension( 150, 20 ) );
        btnAdmin.setMinimumSize( new Dimension( 150, 20 ) );
        btnAdmin.setPreferredSize( new Dimension( 150, 20 ) );
        btnAdmin.addActionListener(mainListener);
        btnAdmin.setVisible( false );

        JPanel bottom = new JPanel();
        bottom.setBackground( darkBlue );
        bottom.setLayout( new BoxLayout( bottom, BoxLayout.LINE_AXIS ) );

        roomList.setCellRenderer( new RoomRenderer() );
        JScrollPane jspRoomList = new JScrollPane( roomList );
        jspRoomList.setMaximumSize( new Dimension( 505, 95 ) );
        jspRoomList.setMinimumSize( new Dimension( 505, 95 ) );
        jspRoomList.setPreferredSize( new Dimension( 505, 95 ) );
        jspRoomList.setBorder( BorderFactory.createTitledBorder("lobby/private rooms") );

        JPanel bottomRight = new JPanel();
        bottomRight.setBackground( darkBlue );
        bottomRight.setLayout( new BoxLayout( bottomRight, BoxLayout.PAGE_AXIS ) );
        bottomRight.setPreferredSize( new Dimension( 150, 95 ) );
        bottomRight.setMaximumSize( new Dimension( 150, 95 ) );
        bottomRight.setMinimumSize( new Dimension( 150, 95 ) );

        JPanel localPanel11 = new JPanel();
        localPanel11.setBackground( darkBlue );
        localPanel11.setLayout( new BorderLayout() );
        localPanel11.setMaximumSize( new Dimension( 150, 45 ) );
        localPanel11.add( toLobby , BorderLayout.NORTH );
        localPanel11.add( Box.createRigidArea( new Dimension( 0,5  ) ), BorderLayout.CENTER );
        localPanel11.add( toRoom , BorderLayout.SOUTH );

        JPanel localPanel22 = new JPanel();
        localPanel22.setBackground( darkBlue );
        localPanel22.setLayout( new BorderLayout() );
        localPanel22.setMaximumSize( new Dimension( 150, 45 ) );
        localPanel22.add( createRoom , BorderLayout.NORTH );
        localPanel22.add( Box.createRigidArea( new Dimension( 0,5  ) ), BorderLayout.CENTER );
        localPanel22.add( btnAdmin, BorderLayout.SOUTH );

        bottomRight.add( localPanel11 );
        bottomRight.add( Box.createRigidArea( new Dimension( 0, 5 ) ) , BorderLayout.CENTER );
        bottomRight.add( localPanel22 );

        bottom.add( jspRoomList );
        bottom.add( Box.createRigidArea( new Dimension( 5, 0 ) ) );
        bottom.add( bottomRight );

        container.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
        container.add( interTopCenter );
        container.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
        container.add( center );
        container.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
        container.add( center );
        container.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
        container.add( interTopCenter3 );
        container.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
        container.add( foot );
        container.add( Box.createRigidArea( new Dimension( 0, 10 ) ) );
        container.add( bottom );

        colors = loadImages();
        if( colors!=null ) {
            colors.addListSelectionListener( lsl );
            colors.setMaximumSize( new Dimension( 108, 108 ) );
            colors.setMinimumSize( new Dimension( 108, 108 ) );
            colors.setPreferredSize( new Dimension( 108, 108 ) );
            colors.setLayoutOrientation(JList.HORIZONTAL_WRAP);
            colors.setVisibleRowCount(4);
        }
        menu = new JPopupMenu();
        menu.setLightWeightPopupEnabled(false);
        if( colors!=null ) menu.insert( colors, 0 );
    }

    private javax.swing.JList loadImages() {
        ArrayList<ImageIcon> al = new ArrayList<ImageIcon>();
        ImageIcon i;
        i = createImageIcon("images/black.gif", "black");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/navy.gif", "navy");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/blue.gif", "blue");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/fuchsia.gif", "fuchsia");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/purple.gif", "purple");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/red.gif", "red");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/maroon.gif", "maroon");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/olive.gif", "olive");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/yellow.gif", "yellow");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/green.gif", "green");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/lime.gif", "lime");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/teal.gif", "teal");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/aqua.gif", "aqua");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/gray.gif", "gray");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        i = createImageIcon("images/silver.gif", "silver");
        if( i!=null )
        {
            al.add( i );
            i = null;
        }
        System.out.println("al.size()= " + al.size() );
        if( al.size()>0 ) return new JList( al.toArray() );
        else return null;
    }

    public void init() {
        cln_init();
        setAllVisible(false);

        aconnector = new Connector(this);
        if(aconnector.logged) setAllVisible(true);
    }

    HyperlinkListener hyperlinkListener = e -> refresh();

    void setAllVisible(boolean _vs)
    {
        vs = _vs;
        this.setVisible(_vs);
    }

    Connector aconnector;

    MouseListener userListMouseListener = new MouseAdapter()
    {
        public void mouseClicked(MouseEvent e)
        {
            System.out.println("mouseClicked(MouseEvent e)1");
            if (e.getClickCount() == 2)
            {
                User local = (User)userList.getSelectedValue();
                if(local == null)
                {
                    System.out.println("local = null");
                    return;
                }
                if(local.getName().trim().equals(""))
                {
                    System.out.println("local.getName().equals(\"\")");
                    return;
                }

            }
            System.out.println("mouseClicked(MouseEvent e)2");
        }
    };
    ClnView cv;
    public ClnView( )
    {
        //pr = val;
        frame = new JFrame();
        frame.setPreferredSize( new Dimension( 680, 575 ) );
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        cln_init();
        cln_main();
        cv = this;
    }


    void cln_main() {
        if( this.isBad ) return;
        frame.setBounds( 50, 50, 680, 575 );
        frame.add( this.container, BorderLayout.CENTER );
        frame.addWindowListener( this );
        int i = 0;

        Connector c = new Connector(this);
        c.connect();

        frame.setTitle("Welcome " + userName + "!");
    }
    boolean firstResponse(){return client.reader.firstResponse;}
    void waitForFirstResponse(){client.reader.firstResponse = false;}

    public static void main(String[] argv)
    {
        ClnView x = new ClnView();
        //System.out.println("main");
        //x.frame = new JFrame();
        //x.frame.setPreferredSize( new Dimension( 680, 470 ) );
        //x.frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        //x.cln_init();
        //x.cln_main();
    }

    void doAutoDrop(String str)
    {
        System.out.println(str);
        System.exit(0);
    }

    void onKick()
    {
        System.out.println("kicked because of not allowed actions");
        System.exit(0);
    }
    void say()
    {
        System.out.println("say");
        String sayStr = getText();
        txt.setText("");
        //vm.setBounds();
        //System.out.println("sayStr = " + sayStr);
        if( sayStr != null && !sayStr.trim().equals("") )
        {

            //String[] temp = txt.getText().trim().split(" \n\t\r\0");

            /*
            for(int j=0; j<temp.length; j++)
            {
                sayStr += temp[j] + " ";
            }
             */
            //System.out.println( "sayStr=" + sayStr );

            if(!sayStr.trim().equals(""))
            {
                //if(sayStr.length() > 256)sayStr = sayStr.substring(0, 255);
                if( ckSayOnlyTo.getSelectedObjects() != null )
                {
                    sayOnlyTo = true;
                    User strUsr = (User)userList.getSelectedValue();

                    if(strUsr == null)return;
                    if(strUsr.getName().trim().equals(""))return;
                    if(strUsr.equals(userName))return; //verificari

                    client.writer.send("command=say_only_to;user=" + strUsr + ";:" + sayStr.trim());
                    System.out.println("command=say_only_to;user=" + strUsr + ";:" + sayStr.trim());

                }else
                {
                    sayOnlyTo = false;
                    System.out.println("sayStr = " + sayStr);
                    client.writer.send("command=say;:" + sayStr.trim());
                }
                localResponses.add(sayStr);
                it = localResponses.listIterator( localResponses.size() );//var membra position ocupa pozitia dupa ultimul element
            }
        }
    }

    void doDisconnect()
    {
        if(disconnected)return;
        client.writer.send("command=disconnect;:");

        try
        {
            client.socket.close();
            System.out.println("socketul obisnuit s-a inchis ");
        }
        catch(IOException err)
        {
            err.printStackTrace();
        }
        System.exit(0);
        disconnected = true;
        //if( pr!=null ) pr.setContinuation( false );
    }

    ActionListener mainListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            String lnfName = e.getActionCommand();
            if(lnfName.equals("close_app"))
            {
                doDisconnect();
            }else if(lnfName.equals("restore"))
            {
                RestoreUser restoreBox = new RestoreUser(client.view);
            } else if(lnfName.equals("send"))
            {
                say();
            }else if(lnfName.equals("admin"))
            {
                AdminChatForm frm = new AdminChatForm(client.view);
            } else if(lnfName.equals("private"))
            {
                int idx = userList.getSelectedIndex();
                if(idx != -1) {
                    User strUsr = (User)userList.getSelectedValue();

                    if( strUsr == null ) return;
                    if( strUsr.equals("") ) return;
                    if( strUsr.equals(userName) ) return;

                    System.out.println("private to" + strUsr);
                    PrivateClnSession s = PrivateClnSession.get( sessions, strUsr.getName() );
                    if(s == null)
                    {
                        createNewPrivateSession( strUsr.getName() );
                    }
                }
            } else if(lnfName.equals("ignore"))
            {
                int idx = userList.getSelectedIndex();
                if( idx != -1 )
                {
                    User strUsr = (User)userList.getSelectedValue();

                    if( strUsr.getName().equals("") )return;
                    if( strUsr.getName().equals(userName) )return;

                    if( !strUsr.getIgnored() ) client.writer.send("command=ignore;user=" + strUsr + ";:");
                    else client.writer.send("command=undo_ignore;user=" + strUsr + ";:");
                }
            } else if( lnfName.equals("italic") )
            {
                //1.textul din txtField
                //2.verificarea la Pattern
                //3.luarea unei decizii in urma rezultatului verificarii
                italic = !italic;
                //if( italic == false ) italic = true;
                //else italic = false;
                String local = getText();
                if( local == null || local.trim().equals("") ) return;
                else
                {
                    Pattern p = Pattern.compile("(.*)<i>(.*)</i>(.*)");
                    Matcher m = p.matcher( local );
                    if( !m.matches() ) local = "<i>" + local + "</i>";
                    else local = m.group(1) + m.group(2) + m.group(3);
                    txt.setText( local );
                    //System.out.println( local );
                }
            } else if( lnfName.equals( "bold" ) )
            {
                //1.textul dinx txtField
                //2.verificarea la Pattern
                //3.luarea unei decizii in urma rezultatului verificarii
                if( bold == false ) bold = true;
                else bold = false;
                String local = getText();
                if( local == null || local.trim().equals("") ) return ;
                else
                {
                    Pattern p = Pattern.compile("(.*)<b>(.*)</b>(.*)");
                    Matcher m = p.matcher( local );
                    if( !m.matches() ) local = "<b>" + local + "</b>";
                    else local = m.group(1) + m.group(2) + m.group(3);
                    txt.setText( local );
                    //vm.setBounds();
                    System.out.println( local );
                }
            }
            else if( lnfName.equals( "create_private_room" ) )
            {
                //to create a frame that will send
                //the name of the new room name to the server
                CreateRoomFrame crf = new CreateRoomFrame( cv );
            }
            else if( lnfName.equals("enter_private_room") )
            {
                int index = roomList.getSelectedIndex();
                if( index!=-1 )
                {
                    Room room = (Room)roomList.getSelectedValue();
                    if( room!=null )
                    {
                        if( room.getPassword() )
                        {
                            EnterRoomFrame erf = new EnterRoomFrame( cv, room.getName() );
                        }
                        send("command=enter_private_room;room=" + room.getName() + ";password= " + ";:");
                    }
                }
            }
            else if( lnfName.equals("back_to_lobby") )
            {
                send("command=back_to_lobby;:");
            }
        }
    };

    String pattern1 = "<p( +[^<>]+=\"[^<>]+\")*>";
    String pattern2 = "</p>";
    String pattern3 = "<body>";
    String pattern4 = "</body>";

    private String getText() {
        boolean needToUpdate = false;
        StringBuilder curent = new StringBuilder(txt.getText());
        String[] s = curent.toString().split("\n");

        int index1, index2, index3, index4;
        index1 = index2 = index3 = index4 = -1;
        curent = new StringBuilder();
        for( int i=0; i<s.length; i++ ) {
            if( Pattern.matches( pattern1, s[i].trim() ) )index1 = i;
            if( Pattern.matches( pattern2, s[i].trim() ) )index2 = i;
            if( (index1!=-1) && (index2!=-1) ) {
                int begin = index1;
                int end = index2;
                StringBuilder local = new StringBuilder();
                for( int it = begin+1 ; it<end ; it++ ) {
                    String actualLine = s[it].trim();

                    if( it!=(begin+1) ) local.append(" ").append(actualLine.trim());
                    else local.append(actualLine.trim());
                }
                //local +=" ";
                index1 = -1;
                index2 = -1;
                if( curent.toString().isEmpty()) curent.append(local);
                else curent.append("<br>").append(local);
            }
            if( Pattern.matches( pattern3, s[i].trim() ) )index3 = i;
            if( Pattern.matches( pattern4, s[i].trim() ) )index4 = i;
        }

        if( curent.toString() == "" ) {
            int begin, end;
            if( index3!=-1 && index4!=-1 ) {
                begin = index3;
                end = index4;
            } else return "";
            System.out.println("begin= " + begin);
            System.out.println("end= " + end);
            for( int it = begin+1 ; it<end ; it++ ) {
                String actualLine = s[it].trim();
                if( it!=(begin+1) ) curent.append(" ").append(actualLine.trim());
                else curent.append(actualLine.trim());
            }
        }
        //System.out.println("curent = \"" + curent+"\"" );
        if( needToUpdate ) txt.setText(curent.toString());
        return curent.toString();
    }

    synchronized PrivateClnSession createNewPrivateSession(String str) {
        PrivateClnSession session = new PrivateClnSession(this, str);
        //setFonts();
        sessions.add(session);
        return session;
    }

    ActionListener txtInputListener = new ActionListener() {
        public void actionPerformed(ActionEvent e) {
            say();
        }
    };

    private void checkForPatterns() {
        //System.out.println( "bold = " + bold );
        //System.out.println( "italic = " + italic );
        //System.out.println( "font = " + font );
        String local = getText();
        if( local!=null && !local.trim().equals("") ) {
            Matcher m;
            if( bold ) {
                m = ptATag1.matcher( local );
                if( !m.matches() ) {
                    local = "<b>"+ local+"</b>";
                    txt.setText( local );
                    //System.out.println("Nu corespunde 1");
                }
            }
            if( italic ) {
                m = ptATag2.matcher( local );
                if( !m.matches() ) {
                    local = "<i>"+ local+"</i>";
                    txt.setText( local );
                    //System.out.println("Nu corespunde 2");
                }
            }
            if( font ) {
                m = ptATag3.matcher( local );
                if( !m.matches() ) {
                    local = "<font color=\"" + fontColor +"\">"+ local + "</font>";
                    //System.out.println("Nu corespunde 3");
                    txt.setText( local );
                }
            }
        }
    }

    static Pattern ptATag1 = Pattern.compile( "^(.*)<[ ]*b[ ]*>(.*)<[ ]*/[ ]*b[ ]*>(.*)" );
    static Pattern ptATag2 = Pattern.compile( "^(.*)<[ ]*i[ ]*>(.*)<[ ]*/[ ]*i[ ]*>(.*)" );
    static Pattern ptATag3 = Pattern.compile( "^(.*)<[ ]*font[ ]+color=[ ]*\"([^<>\\\"]+)\"[ ]*>(.*)<[ ]*/[ ]*font[ ]*>(.*)" );

    private String  undressPatterns() {
        String text = getText();
        Matcher m = ptATag1.matcher( text );
        if( m.matches() ) text = m.group(1) + m.group(2) + m.group(3);
        m = ptATag2.matcher( text );
        if( m.matches() ) text = m.group(1) + m.group(2) + m.group(3);
        m = ptATag3.matcher( text );
        if( m.matches() ) text = m.group(1) + m.group(3) + m.group(4);
        return text;
    }

    private String dressPatterns( String val ) {
        String text = val;
        if( italic ) text = "<i>" + text + "</i>";
        if( bold ) text = "<b>" + text + "</b>";
        if( font ) text = "<font color=\"" + fontColor + "\">" + text + "</font>";
        return text;
    }

    KeyListener txtKeyListener = new KeyListener() {
        boolean control = false;
        boolean shift = false;
        boolean alt = false;
        public void keyReleased(KeyEvent e) {
            checkForPatterns();
            switch( e.getKeyCode() )
            {
                case KeyEvent.VK_CONTROL:
                    control = false;
                    break;
                case KeyEvent.VK_SHIFT:
                    shift = false;
                    break;
                case KeyEvent.VK_ALT:
                    alt = false;
                    break;
                case KeyEvent.VK_ENTER:
                    txt.setText("");
                    break;
            }
        }

        public void keyTyped(KeyEvent e) {
            if(KeyEvent.VK_ENTER == e.getKeyCode() ) txt.setText("");
        }

        public void keyPressed(KeyEvent e) {
            String text;
            //System.out.println("e= " + e );
            switch( e.getKeyCode() ) {
                case KeyEvent.VK_CONTROL:
                    control = true;
                    break;
                case KeyEvent.VK_SHIFT:
                    shift = true;
                    break;
                case KeyEvent.VK_ALT:
                    alt = true;
                    break;
                case KeyEvent.VK_ENTER:
                    say();
                    break;
                case KeyEvent.VK_UP:
                    if( control ) txt.setText(it.hasPrevious()? (String)it.previous():"");
                    break;
                case KeyEvent.VK_LEFT:
                    if( control ) txt.setText(it.hasPrevious()? (String)it.previous():"");
                    break;
                case KeyEvent.VK_DOWN:
                    if( control ) txt.setText(it.hasNext()? (String)it.next():"");
                    break;
                case KeyEvent.VK_RIGHT:
                    if( control ) txt.setText(it.hasNext()? (String)it.next():"");
                    break;
                case KeyEvent.VK_ESCAPE:
                    it= localResponses.listIterator( localResponses.size() );
                    txt.setText("");
                    break;
            }
        }
    };

    public void windowOpened(WindowEvent e){}

    public void windowClosing(WindowEvent e) {
        doDisconnect();
    }

    public void windowClosed(WindowEvent e){}

    public void windowIconified(WindowEvent e){}

    public void windowDeiconified(WindowEvent e){}

    public void windowActivated( WindowEvent e ){}

    public void windowDeactivated( WindowEvent e ){}

}