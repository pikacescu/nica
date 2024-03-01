package com.videochat.client;

import com.videochat.common.Parser;

import javax.swing.*;
import javax.swing.text.html.HTMLEditorKit;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public class PrivateClnSession extends JFrame
{
    ClnView main_app;

    String userNameTo;

    ArrayList<String> localResponses;
    ListIterator<String> it;

    public String sessionId;

    String strDetail = "";

    Cln client;

    JTextField txt = new JTextField();

    JButton btnSend = new JButton( Parser.h5("send") );
    JButton btnIgnore = new JButton( Parser.h5("ignore") );
    JTextPane doc = new JTextPane();

    void write(String str)
	{
		strDetail = str + "<br>" + strDetail;
		doc.setText(strDetail);
    }


    public void private_cln_init()
    {

        if(client.isBad())return;

        JPanel foot = new JPanel();

		Container container = this.getContentPane();
        container.setLayout( new BoxLayout( container, BoxLayout.PAGE_AXIS ) );

        doc.setEditorKit( new HTMLEditorKit() );
        doc.setEditable( false );
        JScrollPane jsp = new JScrollPane( doc );
        jsp.setPreferredSize( new Dimension( 1000,1000 ) );
        jsp.setBorder( BorderFactory.createEmptyBorder( 5,5,5,5 ) );
        localResponses = new ArrayList<>();

        container.add( jsp );

        foot.setLayout( new BoxLayout( foot, BoxLayout.LINE_AXIS ) );

		foot.add( Box.createRigidArea( new Dimension( 5,0 ) ) );
        txt.addActionListener(txtActionListener);
        txt.addKeyListener(txtKeyListener);
        txt.setMaximumSize( new Dimension(1000, 20) );
        foot.add(txt);

        foot.add( Box.createRigidArea( new Dimension( 5,0 ) ) );

        btnSend.setActionCommand("send");
        btnSend.addActionListener(privateSessionActionListener);
        btnSend.setMaximumSize( new Dimension(50, 20) );
        btnSend.setMinimumSize( new Dimension(50, 20) );
        foot.add(btnSend);

        foot.add( Box.createRigidArea( new Dimension( 5,0 ) ) );

        btnIgnore.setActionCommand("ignore");
        btnIgnore.addActionListener(privateSessionActionListener);
        btnIgnore.setMaximumSize( new Dimension( 50, 20) );
        btnIgnore.setMinimumSize( new Dimension( 50, 20) );
        foot.add(btnIgnore);

        foot.add( Box.createRigidArea( new Dimension( 5,0 ) ) );

        container.add( foot );

        addWindowListener(createWindowListener());

        setBounds( 100,100,400,500 );

        setTitle(main_app.userName + ": private with " + userNameTo);
        setVisible(true);
        System.out.println( " private_cln_init() " );
    }

    void say()
    {
        if(!txt.getText().trim().isEmpty())
        {
            String[] sa = txt.getText().trim().split( " \n\t\r\0" );
            StringBuilder sayStr = new StringBuilder();
            for (String s : sa) {
                sayStr.append(s).append(" ");
            }
            if(!sayStr.toString().trim().isEmpty())
            {
                if(sayStr.length() > 256) sayStr = new StringBuilder(sayStr.substring(0, 255));
                client.writer.send("command=private_say_to;user=" + userNameTo + ";:" + sayStr.toString().trim() );
                localResponses.add(sayStr.toString());
                it = localResponses.listIterator( localResponses.size() );
            }
        }
        txt.setText("");
    }

    ActionListener txtActionListener = e -> say();


    WindowListener createWindowListener()
    {
        return new WindowListener()
        {
            public void windowActivated(WindowEvent e){}
            public void windowClosed(WindowEvent e){}
            public void windowClosing(WindowEvent e)
            {
				//main_app.sessions.remove(sessionId);
				main_app.sessions.removeIf(s -> s.sessionId.equals(sessionId));
				dispose();
			}
            public void windowDeactivated(WindowEvent e){}
            public void windowDeiconified(WindowEvent e){}
            public void windowIconified(WindowEvent e){}
            public void windowOpened(WindowEvent e){}
        };
    }


    public PrivateClnSession(ClnView _main_app, String _session_id)
    {
        sessionId = _session_id;
        userNameTo = _session_id;
        main_app = _main_app;
        client = main_app.client;
        private_cln_init();
    }


    ActionListener privateSessionActionListener = new ActionListener()
    {
        public void actionPerformed(ActionEvent e)
        {
            String lnfName = e.getActionCommand();
            if(lnfName.equals("send"))
            {
                say();

            }else if(lnfName.equals("ignore"))
            {
                Iterator<User> it = client.users.iterator();
                User u = null;
                while( it.hasNext() )
                {
                    User local = it.next();
                    if( local.getName().equals(userNameTo) )
                    {
                        u = local;
                        break;
                    }
                }
                if( u != null )
                {
                    client.ignore.add( u );
                }
                client.writer.send("command=ignore;user=" + userNameTo + ";:");
                main_app.sessions.remove(sessionId);//?
                dispose();
            }
        }
    };

    KeyListener txtKeyListener = new KeyListener()
    {
        public void keyReleased(KeyEvent e){}
        public void keyTyped(KeyEvent e)
        {
            int pos = txt.getCaretPosition();
            if(txt.getText().endsWith(" ") || txt.getText().startsWith(" "))
            {
                txt.setText(txt.getText().trim() + " ");
            }
            if(txt.getText().length() > 256)
            {
                txt.setText(txt.getText().substring(0, 256));
            }
            txt.setCaretPosition(pos);
        }
        public void keyPressed(KeyEvent e)
        {
            switch(e.getKeyCode())
            {
            case KeyEvent.VK_UP:
                txt.setText( it.hasPrevious() ? it.previous() : "" );
                return;
            case KeyEvent.VK_DOWN:
                txt.setText( it.hasNext() ? it.next() : "" );
                return;
            case KeyEvent.VK_ESCAPE:
                it = localResponses.listIterator( localResponses.size() );
                txt.setText("");
                //noinspection UnnecessaryReturnStatement
                return;
            }
        }


    };

    static PrivateClnSession get( List<PrivateClnSession> l, String s )
	{
        for (PrivateClnSession psc : l) {
            if (psc.sessionId.equals(s.trim())) return psc;
        }
		return null;
	}

	static void write( List<PrivateClnSession> l, String s )
	{
        for (PrivateClnSession privateClnSession : l) {
            privateClnSession.write(s);
        }
	}
}