package mEncryptServerGUI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MyServer
{
    private final int MAX_CLIENTS = 10;

    ClientHandler[] ch = new ClientHandler[MAX_CLIENTS];

    public static void main(String[] args) {
        MyServer server = new MyServer();
    }

    public  MyServer()
    {

        try{
            System.out.println("Starting the server...");

            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println("Server is accepting Communication on port: 8080");

            for (int i = 0; i < MAX_CLIENTS; i++) {
                Socket socket = serverSocket.accept();
                System.out.println("Connection Client Accepted.");
                ch[i] = new ClientHandler(socket);
                System.out.println("Client Handler " +i+"Ready");
            }

        }catch(IOException e){}
    }

    public class ClientHandler implements Runnable
    {
        DataInputStream     input;
        DataOutputStream    output;
        Thread              t;

        public ClientHandler(Socket socket)
        {
            try {
                input   = new DataInputStream(socket.getInputStream());
                output  = new DataOutputStream(socket.getOutputStream());
            }catch (IOException e){}

            t = new Thread(this);
            t.start();
        }

        @Override
        public void run()
        {
            while (true)
            {
                try {
                    String message = input.readUTF();
                    System.out.println(message);

                    for (int i = 0; i < MAX_CLIENTS; i++)
                    {
                        if(ch[i] != null)
                        {

                            ch[i].output.writeUTF(message);
                        }
                      //  System.out.println(ch[i] + message);
                    }
                }catch (IOException e) {}
            }
        }
    }
}
