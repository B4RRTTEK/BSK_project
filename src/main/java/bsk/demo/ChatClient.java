package bsk.demo;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class ChatClient implements Runnable, ChatUser{

    private Socket textSocket;
    private Socket fileSocket;
    private PrintWriter outWriter;
    private BufferedReader inReader;
    private final static int FILE_SIZE = 6022386;
    private FileOutputStream fos = null;
    private BufferedOutputStream bos = null;
    private int current = 0;
    private int bytesRead ;

    public void startConnection(String ip, int textport, int fileport) {
        try {
            textSocket = new Socket(ip, textport);
            fileSocket = new Socket(ip, fileport);
            outWriter = new PrintWriter(textSocket.getOutputStream(), true);
            inReader = new BufferedReader(new InputStreamReader(textSocket.getInputStream()));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void sendMessage(String msg) {
        outWriter.println(msg);
    }

    public void sendFile(File file) {
        try {
            // Get the size of the file
            long length = file.length();
            byte[] bytes = new byte[50 * 1024];
            InputStream in = new FileInputStream(file);
            OutputStream out = fileSocket.getOutputStream();
            outWriter.println("wysylamplik123" + file.getName());

            int count;
            while ((count = in.read(bytes)) > 0) {
                out.write(bytes, 0, count);
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void stopConnection() {
        try {
            inReader.close();
            outWriter.close();
            textSocket.close();
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void run() {
        try {
            String inputLine;
            while ((inputLine = inReader.readLine()) != null) {
                if(inputLine.contains("wysylamplik123")) {
                    InputStream in = null;
                    OutputStream out = null;
                    System.out.println("Odbieranie pliku");

                    in = fileSocket.getInputStream();

                    String name = inputLine.substring(14);
                    out = new FileOutputStream("E:/" + name);

                    byte[] bytes = new byte[50*1024];

                    int count;
                    while ((count = in.read(bytes)) > 0) {
                        out.write(bytes, 0, count);
                    }
                    out.close();
                    in.close();
                }
                else{
                    JOptionPane.showMessageDialog(null, inputLine);
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
