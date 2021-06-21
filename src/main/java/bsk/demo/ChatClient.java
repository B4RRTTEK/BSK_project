package bsk.demo;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class ChatClient implements Runnable, ChatUser{

    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;

    public void startConnection(String ip, int port) {
        try {
            clientSocket = new Socket(ip, port);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendMessage(String msg) {
        out.println(msg);
    }

    public void stopConnection() {
        try {
            in.close();
            out.close();
            clientSocket.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void run() {
        try {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                JOptionPane.showMessageDialog(null, inputLine);
                out.flush();
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
