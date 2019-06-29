package mEncryptServerGUI;

import java.awt.*;
import java.awt.event.*;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class MessageFrameGUI implements Runnable
{
    Thread              t;

    DataInputStream     input;
    DataOutputStream    output;

    private     Frame   mainFrame;
    private     Panel   mainPanel;
    private     Panel   messagePanel;
    private     Panel   messageEncryptPanel;

    boolean             hasexit       =  false;

    Label               title           = new Label("The Chat");
    Label               username        = new Label("Username: ");
    Label               myPrints        = new Label("Norberto Studios");
    Label               madeBy          = new Label("Made By: ");
    Label               chatTitle       = new Label("Chat");
    Label               onOffTitle      = new Label("Encryption");

    TextArea            logAreaField    = new TextArea();

    TextField           messageField    = new TextField();
    TextField           usernameField   = new TextField();

    Button              sendBtn         = new Button("Send");
    Button              startChatBtn    = new Button("Start Chat");

    CheckboxGroup       onOff           = new CheckboxGroup();
    Checkbox            on              = new Checkbox("ON",onOff,false);
    Checkbox            off             = new Checkbox("OFF", onOff, true);

    Label               titleText       = new Label("Encrypt");

    Label               messageText     = new Label("Message");
    Label               mText           = new Label("M");
    TextField           messageTField   = new TextField();

    Label               keyText         = new Label("Key");
    Label               kText           = new Label("K");
    TextField           keyTField       = new TextField();

    Label               cypherText      = new Label("Cypher");
    Label               cText           = new Label("C");
    TextField           cypherTField    = new TextField();

    Button              clearBtn        = new Button("Clear");
    Button              encryptBtn      = new Button("Encrypt");
    Button              decryptBtn      = new Button("Decrypt");
    Button              sendEncryptBtn  = new Button("Send");

    Font                titleFont       = new Font(Font.SANS_SERIF, Font.BOLD, 34);

    String              userString;
    String              m;
    String              k;
    String              c;

    Socket              socket;




    public MessageFrameGUI(int x, int y, Socket socket)
    {
        this.socket = socket;

        preparedMain(x,y);
        try
        {
            input  = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        }catch (IOException e){}


        startChatBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userString = usernameField.getText();
                entered(); // in the chat
                setMessagePanel(x,y);
            }
        });

        on.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                oneTimePad(x,y);
            }
        });

        off.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {

                mainFrame.remove(messageEncryptPanel);
                setMessagePanel(x,y);
            }
        });


        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String message = messageField.getText();

                if(message.equalsIgnoreCase("exit"))
                {
                    exit();
                }

                try
                {
                    output.writeUTF(userString +": "+message);
                }catch (IOException ex){}

                messageField.setText("");
            }
        });
        messageField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String message = messageField.getText();

                if(message.equalsIgnoreCase("exit"))
                {
                    exit();
                }

                try
                {
                    output.writeUTF(userString +": "+message);
                }catch (IOException ex){}

                messageField.setText("");
            }
        });

        sendEncryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                String message = messageTField.getText();
                String enMessage =": ";

                if(message.equalsIgnoreCase(""))
                {
                    message = cypherTField.getText();
                    enMessage = "(Encrypted): ";
                }


                if(message.equalsIgnoreCase("exit"))
                {
                    exit();
                }

                try
                {
                    output.writeUTF(userString +enMessage+message);
                }catch (IOException ex){}

                messageField.setText("");
            }
        });


        t = new Thread(this);
        t.start();

    }

    public void run()
    {
        while (true)
        {
            if(hasexit)
            {
                break;
            }

            try{
                String message = input.readUTF();
                logAreaField.append(message+"\n");
            }catch (IOException exc){ }


        }
        try {
            socket.close();
            System.out.println("Socket..Closed.");
        }
        catch (IOException e){}


    }

    public void preparedMain(int x, int y)
    {
        mainFrame = new Frame();

        mainFrame.setTitle("Client");
        mainFrame.setLayout(null);
        mainFrame.setSize(380,540);

        mainFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exit();
            }
        });

        setMainPanel(x,y);

        mainFrame.add(mainPanel);
        mainFrame.setVisible(true);
    }

    public void setMainPanel(int x, int y)
    {
        mainPanel       = new Panel();
        Panel user      = new Panel();


        mainPanel.setLayout(null);
        mainPanel.setBounds(x-10,y,380, 540);
        mainPanel.setBackground(new Color(86, 135, 211));



        user.setLayout(null);
        user.setBounds(40,220,280, 220);
        user.setBackground(new Color(104, 164, 255));

        title.setBounds(30,60,340,50);
        title.setAlignment(Label.CENTER);
        title.setFont(titleFont);
        //title.setForeground(Color.WHITE);

        username.setBounds(30,10,280,40);
        username.setFont(new Font("Serif", 0, 16));

        usernameField.setBounds(30,50,230,40);

        //startChatBtn.setForeground(Color.black);
        startChatBtn.setBackground(new Color(92, 152, 242));
        startChatBtn.setFont(new Font("Serif", Font.BOLD, 25));
        startChatBtn.setBounds(0,120,280,100);

        madeBy.setBounds(150, 480, 50,60 );
        madeBy.setFont(new Font("Serif",Font.ITALIC,12));
        myPrints.setBounds(200,480,170,60);
        myPrints.setFont(new Font("Serif",Font.BOLD,16));

        user.add(username);
        user.add(usernameField);
        user.add(startChatBtn);

        mainPanel.add(title);
        mainPanel.add(user);
        mainPanel.add(madeBy);
        mainPanel.add(myPrints);



    }

    public  void setMessagePanel(int x, int y)
    {
        mainFrame.remove(mainPanel);
        mainFrame.setSize(540,450);
        mainFrame.setTitle(userString+": Client");

        messagePanel    = new Panel();

        messagePanel.setLayout(null);
        messagePanel.setBounds(x-10,y,540,450);
        messagePanel.setBackground(Color.lightGray);

        messagePanel.add(chatTitle);
        messagePanel.add(logAreaField);
        messagePanel.add(messageField);
        messagePanel.add(sendBtn);
        messagePanel.add(onOffTitle);
        messagePanel.add(on);
        messagePanel.add(off);

        chatTitle.setAlignment(Label.CENTER);
        chatTitle.setFont(titleFont);

        logAreaField.setEditable(false);

        chatTitle.setBounds(30,30,480,40);
        logAreaField.setBounds(30,80,480,260);
        messageField.setBounds(30,350,390,30);
        sendBtn.setBounds(430,350,80,30);
        onOffTitle.setBounds(150,385,80,30);
        on.setBounds(230,380,60,40);
        off.setBounds(300,380,60,40);

        mainFrame.add(messagePanel);
    }

    public void oneTimePad(int x, int y)
    {
        mainFrame.remove(messagePanel);
        mainFrame.setSize(840,460);
        messageEncryptPanel    = new Panel();

        messageEncryptPanel.setLayout(null);
        messageEncryptPanel.setBounds(x-10,y,840,460);
        messageEncryptPanel.setBackground(Color.darkGray);

        titleText.setFont(titleFont);

        messageEncryptPanel.add(titleText);
        messageEncryptPanel.add(messageText);
        messageEncryptPanel.add(mText);
        messageEncryptPanel.add(messageTField);
        messageEncryptPanel.add(keyText);
        messageEncryptPanel.add(kText);
        messageEncryptPanel.add(keyTField);
        messageEncryptPanel.add(cypherText);
        messageEncryptPanel.add(cText);
        messageEncryptPanel.add(cypherTField);
        messageEncryptPanel.add(clearBtn);
        messageEncryptPanel.add(sendEncryptBtn);
        messageEncryptPanel.add(encryptBtn);
        messageEncryptPanel.add(decryptBtn);
        messageEncryptPanel.add(onOffTitle);
        messageEncryptPanel.add(on);
        messageEncryptPanel.add(off);
        messageEncryptPanel.add(chatTitle);
        messageEncryptPanel.add(logAreaField);

        chatTitle.setBounds(30,30,480,40);
        logAreaField.setBounds(30,80,480,290);

        titleText.setBounds(630, 30, 200, 40);

        messageText.setBounds(580, 80, 100, 20);
        mText.setBounds(550, 110, 20, 30);
        messageTField.setBounds(580, 110, 220, 30);

        keyText.setBounds(580, 160, 100, 20);
        kText.setBounds(550, 190, 20, 30);
        keyTField.setBounds(580, 190, 220, 30);

        cypherText.setBounds(580, 240, 100, 20);
        cText.setBounds(550, 270, 20, 30);
        cypherTField.setBounds(580, 270, 220, 30);

        clearBtn.setBounds(580, 320, 100, 50);
        sendEncryptBtn.setBounds(700, 320, 100, 50);

        encryptBtn.setBounds(580, 380, 100, 50);
        decryptBtn.setBounds(700, 380, 100, 50);

        onOffTitle.setBounds(150,385,80,30);
        on.setBounds(230,380,60,40);
        off.setBounds(300,380,60,40);

        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m = messageTField.getText();
                k = keyTField.getText();
                c = cypherTField.getText();

                messageTField.setText("");
                keyTField.setText("");
                cypherTField.setText("");
            }
        });

        encryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m = messageTField.getText();
                k = keyTField.getText();
                c = cypherTField.getText();
                String a = "";

                c = encryption(m, k);

                if(k.equalsIgnoreCase(""))
                {
                    System.out.println("The Key is empty!!");

                }else {

                    int[] cLookInt = new int[c.length()];

                    for (int i = 0; i < c.length(); i++) {
                        cLookInt[i] = (char) c.charAt(i) + 33;
                        a += Character.toString((char) cLookInt[i]);
                    }


                    messageTField.setText("");
                    cypherTField.setText(a);

                }

            }
        });

        decryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                m = messageTField.getText();
                k = keyTField.getText();
                c = cypherTField.getText();
                String a = "";

                // m = encryptAndDecrypt.encrypt(c,k);

                int[] cLookInt = new int[c.length()];

                if(k.equalsIgnoreCase(""))
                {
                    System.out.println("The Key is empty!!");

                }else {
                    for (int i = 0; i < c.length(); i++) {
                        cLookInt[i] = (char) c.charAt(i) - 33;
                        a += Character.toString((char) cLookInt[i]);
                    }

                    m = encryption(a, k);


                    messageTField.setText(m);
                    cypherTField.setText("");

                }

            }
        });

        mainFrame.add(messageEncryptPanel);

    }

    public String encryption(String message, String key)
    {
        System.out.println("The Message is: " + message);
        System.out.println("The Key is: " + key);

       // String cypherBinary = "";
        String cypherText = "";


        String[] cypherValue = new String[message.length()];
        String[] str = new String[message.length()];
        int[] cypherInt = new int[message.length()];


        for (int i = 0; i < message.length(); i++)
        {

            cypherValue[i] = Integer.toBinaryString(message.charAt(i) ^ key.charAt(i));

//            if (i == cypherValue.length - 1) {
//                cypherBinary += cypherValue[i];
//            } else {
//                cypherBinary += cypherValue[i] + ",";
//            }

        }

        for (int i = 0; i < message.length(); i++) {

            cypherInt[i] = Integer.parseInt(cypherValue[i], 2);

            str[i] = Character.toString((char) cypherInt[i]);

            cypherText += str[i];

        }

        System.out.println("The Cypher text: " + cypherText);


        System.out.println("----------------------------------------");

        return cypherText;
    }

    public void entered()
    {
        hasexit = false;
        try{
            output.writeUTF(userString +": has entered the chat!!");
        }catch (IOException ex){}

    }

    public void exit()
    {
        hasexit = true;
        try{
            output.writeUTF(userString +": Left the chat!!");
        }catch (IOException ex){}


        mainFrame.dispose();

    }

}
