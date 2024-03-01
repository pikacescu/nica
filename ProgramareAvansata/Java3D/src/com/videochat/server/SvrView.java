package com.videochat.server;

import com.videochat.common.*;

import java.lang.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.text.html.HTMLEditorKit;


public class SvrView extends JFrame
{

    static UserArray users;

    public SvrView(UserArray _users)
    {
        users = _users;
        initComponents();
        //setVisible( true );
    }
    void wakeUp()
    {
        setBounds(150, 150, 700, 500);
        setVisible( true );
        refreshUsers();
    }

    void write( String _str )
    {
		if( _str==null || _str.trim().isEmpty()) return;
		String text = doc.getText();
		if( text!=null ) text = _str + text;
		else text = _str;
		doc.setText( text );
	}


    synchronized public void refreshUsers()
    {
        try
        {
            dlm.removeAllElements();

            java.util.List<User> objs = users.getUsers();
            if(objs == null) return;
            if(objs.isEmpty()) return;

			synchronized( objs )
			{
                for (User obj : objs) dlm.addElement(obj);
			}

        }
        catch(Throwable e)
        {
        }
    }


    private ActionListener commandListener = new ActionListener()
    {
        public synchronized void actionPerformed(ActionEvent e)
        {
            String strAction = e.getActionCommand();
            User usr = (User)jListUsers.getSelectedValue();
            String str = "";
            if(usr == null)return;
            if(strAction.equals("setAdmin"))
            {
                str = "command=console_set_admin;user=" + usr.toString() + "value=true;:";
                users.setAdmin( usr.userName, true);
            }
            else if(strAction.equals("resetAdmin"))
            {
                str = "command=console_set_admin;user=" + usr.toString() + "value=false;:";
                users.setAdmin( usr.userName, false);
            }
            else if(strAction.equals("resetAllAdmins"))
            {
                users.resetAllAdmins();
            }
            else if(strAction.equals("kick"))
            {
                if(users.contains( usr ))
                {
                    users.kick( usr.toString() );
                    System.out.println("no such user");
                }
            }
            else if(strAction.equals("bann"))
            {
				if( usr!=null )
				{
					usr.connection.banList.add( usr.userName );
					usr.kick();
				}
            }
            else if(strAction.equals("block"))
            {
				usr.connection.blockList.put( usr.userName, usr.connection.socket.getInetAddress().getHostAddress().trim() );
				users.block_by_IP( users.get_IP( usr.userName ) );
            }else if(strAction.equals("sendMessage"))
            {
                String htm = Parser.parseAllTags(jTextMessage.getText());
                usr.write("command=vi_message;:" + htm);
            }
            else if(strAction.equals("sendWarning"))
            {
				String htm = Parser.parseAllTags(jTextMessage.getText());
                usr.write("command=warning_message;:" + htm);
            }
        }
    };

    private void initComponents()
    {
        setTitle("server");
        setBounds( 100, 100, 600, 430 );

        doc.setEditable(false);
        doc.setEditorKit( new HTMLEditorKit() );

        jScrollAdditionalInfo.setViewportView(doc);
        jScrollAdditionalInfo.setPreferredSize( new Dimension( 300, 360 ) );
        jScrollAdditionalInfo.setMaximumSize( new Dimension( 1000, 1000 ) );
        jScrollAdditionalInfo.setMinimumSize( new Dimension( 300, 360 ) );

		JPanel btnContainer1, btnContainer2;

		btnContainer1 = new JPanel();
		btnContainer2 = new JPanel();
		btnContainer1.setLayout( new BoxLayout( btnContainer1, BoxLayout.PAGE_AXIS ) );
		btnContainer2.setLayout( new BoxLayout( btnContainer2, BoxLayout.PAGE_AXIS ) );

        btnSetAdmin.setActionCommand("setAdmin");
        btnSetAdmin.addActionListener(commandListener);
        btnSetAdmin.setPreferredSize( new Dimension( 150, 20 ) );
        btnSetAdmin.setMinimumSize( new Dimension( 150, 20 ) );
        btnSetAdmin.setMaximumSize( new Dimension( 150, 20 ) );

        btnResetAdmin.setActionCommand("resetAdmin");
        btnResetAdmin.addActionListener(commandListener);
        btnResetAdmin.setPreferredSize( new Dimension( 150, 20 ) );
        btnResetAdmin.setMinimumSize( new Dimension( 150, 20 ) );
        btnResetAdmin.setMaximumSize( new Dimension( 150, 20 ) );

        btnResetAllAdmins.setActionCommand("resetAllAdmins");
        btnResetAllAdmins.addActionListener(commandListener);
        btnResetAllAdmins.setPreferredSize( new Dimension( 150, 20 ) );
        btnResetAllAdmins.setMinimumSize( new Dimension( 150, 20 ) );
        btnResetAllAdmins.setMaximumSize( new Dimension( 150, 20 ) );

        btnKick.setActionCommand("kick");
        btnKick.addActionListener(commandListener);
        btnKick.setPreferredSize( new Dimension( 150, 20) );
        btnKick.setMinimumSize( new Dimension( 150, 20) );
        btnKick.setMaximumSize( new Dimension( 150, 20) );

        btnBann.setActionCommand("bann");
        btnBann.addActionListener(commandListener);
        btnBann.setPreferredSize( new Dimension( 150, 20 ) );
        btnBann.setMinimumSize( new Dimension( 150, 20 ) );
        btnBann.setMaximumSize( new Dimension( 150, 20 ) );

        btnBlock.setActionCommand("block");
        btnBlock.addActionListener(commandListener);
        btnBlock.setPreferredSize( new Dimension( 150, 20) );
        btnBlock.setMinimumSize( new Dimension( 150, 20) );
        btnBlock.setMaximumSize( new Dimension( 150, 20) );

        btnSendMessage.setActionCommand("sendMessage");
        btnSendMessage.addActionListener(commandListener);
        btnSendMessage.setPreferredSize( new Dimension( 150, 20 ) );
        btnSendMessage.setMinimumSize( new Dimension( 150, 20 ) );
        btnSendMessage.setMaximumSize( new Dimension( 150, 20 ) );

        btnSendWarning.setActionCommand("sendWarning");
        btnSendWarning.addActionListener(commandListener);
        btnSendWarning.setPreferredSize( new Dimension( 150, 20 ) );
        btnSendWarning.setMaximumSize( new Dimension( 150, 20 ) );

        btnContainer1.add( btnSetAdmin );
        btnContainer1.add( Box.createRigidArea( new Dimension( 0,5 ) ) );
        btnContainer1.add( btnResetAdmin );
        btnContainer1.add( Box.createRigidArea( new Dimension( 0,5 ) ) );
        btnContainer1.add( btnResetAllAdmins );
        btnContainer1.add( Box.createRigidArea( new Dimension( 0,5 ) ) );
        btnContainer1.add(  btnSendMessage );

        btnContainer2.add( btnKick );
        btnContainer2.add( Box.createRigidArea( new Dimension( 0,5 ) ) );
        btnContainer2.add( btnBann );
        btnContainer2.add( Box.createRigidArea( new Dimension( 0,5 ) ) );
        btnContainer2.add( btnBlock );
        btnContainer2.add( Box.createRigidArea( new Dimension( 0,5 ) ) );
        btnContainer2.add( btnSendWarning );

        btnContainer1.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 2 ) );
        btnContainer2.setBorder( BorderFactory.createEmptyBorder( 5, 2, 5, 5 ) );

        JPanel foot = new JPanel();
        foot.setLayout( new BoxLayout( foot, BoxLayout.LINE_AXIS ) );
        foot.add( btnContainer1 );
        foot.add( btnContainer2 );

       	jListUsers.setSelectionMode( ListSelectionModel.SINGLE_SELECTION );
        jScrollUsers.setViewportView( jListUsers );
        jScrollUsers.setPreferredSize( new Dimension( 304, 50 ) );
        jScrollUsers.setMaximumSize( new Dimension( 304, 1000 ) );
        jScrollUsers.setMinimumSize( new Dimension( 304, 50 ) );

        jTextMessage.setPreferredSize( new Dimension( 304, 20 ) );
        jTextMessage.setMaximumSize( new Dimension( 304, 20 ) );
        jTextMessage.setMinimumSize( new Dimension( 304, 20 ) );

		JPanel left = new JPanel();
		left.setLayout( new BoxLayout( left, BoxLayout.PAGE_AXIS ) );
		left.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

        left.add( jScrollUsers );
		left.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
		left.add( jTextMessage );
		left.add( Box.createRigidArea( new Dimension( 0, 5 ) ) );
       	left.add( foot );

       	Container container = this.getContentPane();
       	container.setLayout( new BoxLayout( container, BoxLayout.LINE_AXIS ) );

		jScrollAdditionalInfo.setBorder( BorderFactory.createEmptyBorder( 5, 5, 5, 5 ) );

		container.add( jScrollAdditionalInfo );
		container.add( left );

        //usrPanel.setLayout(new BorderLayout());
        //usrPanel.add("Center", jScrollUsers);
        //usrPanel.setBounds(400, 20, 190, 160);
        //pane.add(usrPanel);

        pack();

    }


    private JButton          btnSetAdmin           = new JButton( Parser.h5( "set admin" ) );
	private JButton          btnResetAdmin         = new JButton( Parser.h5( "reset admin" ) );
    private JButton          btnResetAllAdmins     = new JButton( Parser.h5( "reset all admins" ) );

    private JButton          btnBlock              = new JButton( Parser.h5( "block by IP" ) );
    private JButton          btnKick               = new JButton( Parser.h5( "kick" ) );
    private JButton          btnBann               = new JButton( Parser.h5( "block" ) );

    private JButton          btnSendMessage        = new JButton( Parser.h5( "send message") );
    private JButton          btnSendWarning        = new JButton( Parser.h5( "send warning") );

    private JScrollPane      jScrollAdditionalInfo = new JScrollPane();
    private JScrollPane      jScrollUsers          = new JScrollPane();

    private DefaultListModel<User> dlm                   = new DefaultListModel <>();
    private JList<User>            jListUsers            = new JList<>( dlm );

    private JTextPane        doc                   = new JTextPane();
    private JTextField       jTextMessage          = new JTextField();


}