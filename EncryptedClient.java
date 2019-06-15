package mEncryptServerGUI;

import java.io.IOException;
import java.net.Socket;

public class EncryptedClient
{
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 8080);

            // the frame here;
            MessageFrameGUI messageFrameGUI = new MessageFrameGUI(10,10,socket);

        }catch (IOException e) {}
    }
}
