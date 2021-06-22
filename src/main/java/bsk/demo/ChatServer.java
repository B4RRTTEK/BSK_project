package bsk.demo;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class ChatServer implements Runnable, ChatUser{

    private ServerSocket serverTextSocket;
    private ServerSocket serverFileSocket;
    private Socket clientTextSocket;
    private Socket clientFileSocket;
    private PrintWriter outWriter;
    private BufferedReader inReader;
    private FileInputStream fis = null;
    private BufferedInputStream bis = null;
    private OutputStream os = null;
    private String key;

    public ChatServer(String key) {
        this.key = key;
    }

    public void start(int textPort, int filePort) {
        try {
            serverTextSocket = new ServerSocket(textPort);
            serverFileSocket = new ServerSocket(filePort);
            clientTextSocket = serverTextSocket.accept();
            clientFileSocket = serverFileSocket.accept();
            outWriter = new PrintWriter(clientTextSocket.getOutputStream(), true);
            inReader = new BufferedReader(new InputStreamReader(clientTextSocket.getInputStream()));
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void stop() {
        try {
            inReader.close();
            outWriter.close();
            clientTextSocket.close();
            serverTextSocket.close();
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
            OutputStream out = clientFileSocket.getOutputStream();
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

    public void run() {
        try {
            String inputLine;
            while ((inputLine = inReader.readLine()) != null) {
                if(inputLine.contains("wysylamplik123")) {
                    InputStream in = null;
                    OutputStream out = null;
                    System.out.println("Odbieranie pliku");

                    in = clientFileSocket.getInputStream();

                    String name = inputLine.substring(14);
                    out = new FileOutputStream("E:/" + name);

                    byte[] bytes = new byte[50*1024];

                    int count;
                    while ((count = in.read(bytes)) > 0) {
                        out.write(bytes, 0, count);
                    }

                    File encryptedFile = new File("E:/" + name);
                    File decryptedFile = new File("E:/" + name.substring(9));

                    FileEncrypterDecrypter.decrypt(key, encryptedFile, decryptedFile);
                }
                else {
                    JOptionPane.showMessageDialog(null, inputLine);
                    outWriter.flush();
                }
            }
        }
        catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
