package mEncryptServerGUI;

import java.awt.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class OneTimePadGUI extends Frame {
    Label titleText;
    Label mText, kText, cText;
    Label messageText, keyText, cypherText;
    TextField messageTField, keyTField, cypherTField;
    Button sendBtn, clearBtn, encryptBtn, decryptBtn;

    EncryptAndDecrypt encryptAndDecrypt = new EncryptAndDecrypt();

    public OneTimePadGUI() {
        setTitle("Encryption");

        // not default layout
        setLayout(null);

        // Setting the Frame position and size
        setBounds(100, 100, 320, 460);

        // Set the frame visible
        setVisible(true);

        //Setting the window listener to exit the program when the x in the window is pressed
        this.addWindowListener(
                new WindowAdapter() {
                    @Override
                    public void windowClosing(WindowEvent e) {
                        // dispose();
                        System.exit(0);
                    }
                });


        Font title = new Font(Font.SANS_SERIF, Font.BOLD, 34);

        titleText = new Label("Encrypt");


        messageText = new Label("Message");
        mText = new Label("M");
        messageTField = new TextField();

        keyText = new Label("Key");
        kText = new Label("K");
        keyTField = new TextField();

        cypherText = new Label("Cypher");
        cText = new Label("C");
        cypherTField = new TextField();

        clearBtn = new Button("Clear");
        sendBtn = new Button("Send");

        encryptBtn = new Button("Encrypt");
        decryptBtn = new Button("Decrypt");

        titleText.setFont(title);

        add(titleText);

        add(messageText);
        add(mText);
        add(messageTField);

        add(keyText);
        add(kText);
        add(keyTField);

        add(cypherText);
        add(cText);
        add(cypherTField);

        add(clearBtn);
        add(sendBtn);

        add(encryptBtn);
        add(decryptBtn);

        titleText.setBounds(100, 30, 200, 40);

        messageText.setBounds(50, 80, 100, 20);
        mText.setBounds(20, 110, 20, 30);
        messageTField.setBounds(50, 110, 220, 30);

        keyText.setBounds(50, 160, 100, 20);
        kText.setBounds(20, 190, 20, 30);
        keyTField.setBounds(50, 190, 220, 30);

        cypherText.setBounds(50, 240, 100, 20);
        cText.setBounds(20, 270, 20, 30);
        cypherTField.setBounds(50, 270, 220, 30);

        clearBtn.setBounds(50, 320, 100, 50);
        sendBtn.setBounds(170, 320, 100, 50);

        encryptBtn.setBounds(50, 380, 100, 50);
        decryptBtn.setBounds(170, 380, 100, 50);


        clearBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m = messageTField.getText();
                String k = keyTField.getText();
                String c = cypherTField.getText();

                messageTField.setText("");
                keyTField.setText("");
                cypherTField.setText("");
            }
        });


        encryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m = messageTField.getText();
                String k = keyTField.getText();
                String c = cypherTField.getText();
                String a = "";

                c = encryptAndDecrypt.encrypt(m, k);


                int[] cLookInt = new int[c.length()];

                for (int i = 0; i < c.length(); i++) {
                    cLookInt[i] = (char) c.charAt(i) + 33;
                    a += Character.toString((char) cLookInt[i]);
                }


                messageTField.setText("");
                cypherTField.setText(a);
            }
        });

        decryptBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String m = messageTField.getText();
                String k = keyTField.getText();
                String c = cypherTField.getText();
                String a = "";

                // m = encryptAndDecrypt.encrypt(c,k);

                int[] cLookInt = new int[c.length()];

                for (int i = 0; i < c.length(); i++) {
                    cLookInt[i] = (char) c.charAt(i) - 33;
                    a += Character.toString((char) cLookInt[i]);
                }

                m = encryptAndDecrypt.encrypt(a, k);


                messageTField.setText(m);
                cypherTField.setText("");

            }
        });

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });


    }

//    @Override
//    public void actionPerformed(ActionEvent e) {
//
//
//    }

//    public String getMessageTField()
//    {
//        return null;
//    }
//    public String getKeyTField()
//    {
//        return null;
//    }
//    public String getCypherTField()
//    {
//        return null;
//    }
}
