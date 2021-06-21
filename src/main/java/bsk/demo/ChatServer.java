package bsk.demo;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class ChatServer implements Runnable, ChatUser{

    private ServerSocket serverSocket;
    private Socket clientSocket;
    private PrintWriter out;
    private BufferedReader in;


    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
            clientSocket = serverSocket.accept();
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void stop() {
        try {
            in.close();
            out.close();
            clientSocket.close();
            serverSocket.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void sendMessage(String msg) {
        out.println(msg);
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
