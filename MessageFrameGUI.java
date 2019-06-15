package mEncryptServerGUI;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageFrameGUI implements Runnable, ActionListener
{
    Thread              t;

    DataInputStream     input;
    DataOutputStream    output;

    private Frame mainFrame;
    private Panel mainPanel;
    private Panel messagePanel;
    private Panel mEncryptedPanel;

    Label               title         = new Label("Welcome to the chat");
    Label               username      = new Label("Username: ");

    TextArea            logAreaField  = new TextArea();

    TextField           messageField  = new TextField();
    TextField           usernameField = new TextField();

    Button              sentBtn       = new Button("Send");
    Button              encryptBtn    = new Button("Encrypted");
    Button              startChatBtn  = new Button("Start Chat");

    String              user;

    public MessageFrameGUI(int x, int y, Socket socket)
    {

        preparedMain(x,y);
        try
        {
            input  = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){}

        startChatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                user = usernameField.getText();
                setMessagePanel(x,y);
            }
        });

        sentBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = messageField.getText();

                if(message.equalsIgnoreCase("exit"))
                {
                    try{
                        output.writeUTF(user+": Left the chat!!");
                    }catch (IOException ex){}

                    System.exit(0);
                }

                try
                {
                    output.writeUTF(user +": "+message);
                }catch (IOException ex){}

                messageField.setText("");
            }
        });


        t = new Thread(this);
        t.start();

    }

    public void actionPerformed(ActionEvent event)
    {

    }

    public void run()
    {
        while (true)
        {
            try{
                String message = input.readUTF();
                logAreaField.append(message+"\n");
            }catch (IOException exc){}
        }
    }


    public void preparedMain(int x, int y)
    {
        mainFrame = new Frame();

        mainFrame.setTitle("Client");
        mainFrame.setLayout(null);
        mainFrame.setSize(540,300);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                try{
                    output.writeUTF(user+": Left the chat!!");
                }catch (IOException ex){}
                System.exit(0);
            }
        });

        setMainPanel(x,y);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public void setMainPanel(int x, int y)
    {
        mainPanel       = new Panel();

        mainPanel.setLayout(null);
        mainPanel.setBounds(x,y,540, 300);
        mainPanel.setBackground(Color.gray);

        title.setBounds(30,30,540,50);
        username.setBounds(30,110,300,40);
        usernameField.setBounds(30,200,80,40);
        startChatBtn.setBounds(430,200,80,40);

        mainPanel.add(title);
        mainPanel.add(username);
        mainPanel.add(usernameField);
        mainPanel.add(startChatBtn);

    }

    public  void setMessagePanel(int x, int y)
    {

        mainFrame.remove(mainPanel);
        mainFrame.setSize(540,450);
        messagePanel    = new Panel();

        messagePanel.setLayout(null);
        messagePanel.setBounds(x,y,540,450);
        messagePanel.setBackground(Color.red);

        messagePanel.add(logAreaField);
        messagePanel.add(messageField);
        messagePanel.add(sentBtn);
        messagePanel.add(encryptBtn);

        logAreaField.setBounds(30,30,480,260);
        messageField.setBounds(30,300,390,40);
        sentBtn.setBounds(430,300,80,40);

        mainFrame.add(messagePanel);
    }
}
