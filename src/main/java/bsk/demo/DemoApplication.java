package bsk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.io.*;
import java.security.*;

@SpringBootApplication
public class DemoApplication extends  JFrame implements ActionListener {

    private ButtonGroup radioGroup;
    private JButton fileButton;
    private JButton sendButton;
    private JButton sendFileButton;
    private JTextField textField;
    private static ChatUser user = null;
    private File lastFile;
    private static String key = "F5EBCB74076B9376F14493130F95D7FD";

    public DemoApplication()
    {

        setLocation(200,200);
        setSize(500,300);
        setTitle("Wysylanie pliku");
        setLayout(null);

        textField = new JTextField("");
        textField.setBounds(30,20,300,40);
        add(textField);

        sendButton = new JButton("Wyślij tekst");
        sendButton.setBounds(30,80,150,20);
        add(sendButton);
        sendButton.addActionListener(this);

        fileButton = new JButton("Wybierz plik");
        fileButton.setBounds(30,120,150,20);
        add(fileButton);
        fileButton.addActionListener(this);

        sendFileButton = new JButton("Wyślij plik");
        sendFileButton.setBounds(30,140,150,20);
        add(sendFileButton);
        sendFileButton.addActionListener(this);

        radioGroup=new ButtonGroup();
        JRadioButton radio_ecb = new JRadioButton("ECB",true);
        radio_ecb.setBounds(50,160,100,20);
        JRadioButton radio_cbc = new JRadioButton("CBC",true);
        radio_cbc.setBounds(50,180,100,20);
        JRadioButton radio_cfb = new JRadioButton("CFB",true);
        radio_cfb.setBounds(50,200,100,20);
        radioGroup.add(radio_ecb);
        radioGroup.add(radio_cbc);
        radioGroup.add(radio_cfb);
        add(radio_ecb);
        add(radio_cbc);
        add(radio_cfb);



    }
    public void actionPerformed(ActionEvent e)
    {
        Object source = e.getSource();

        if(source == sendButton) {
            String message = textField.getText();
            user.sendMessage(message);
            JOptionPane.showMessageDialog(this,"Wysłano.");
        }

        if(source == fileButton) {
            Scanner fileIn;
            int response;
            JFileChooser chooser = new JFileChooser(".");

            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            response = chooser.showOpenDialog(this);
            if(response == JFileChooser.APPROVE_OPTION) {
                lastFile = chooser.getSelectedFile();
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getName());
            }
        }

        if(source == sendFileButton) {
            File encryptedFile = new File("encrypted" + lastFile.getName());

            try {
                FileEncrypterDecrypter.encrypt(key, lastFile, encryptedFile);
            } catch (CryptoException ex) {
                System.out.println(ex.getMessage());
                ex.printStackTrace();
            }
            user.sendFile(encryptedFile);
            JOptionPane.showMessageDialog(this,"Wysłano plik.");
        }
    }

    public static void generateRSA() throws NoSuchAlgorithmException {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();
        String encodedpub = Base64.getEncoder().encodeToString(pub.getEncoded());
        String encodedpvt = Base64.getEncoder().encodeToString(pvt.getEncoded());



        try (FileOutputStream fos = new FileOutputStream("keys/public/public.key")) {

            fos.write(pub.getEncoded());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (FileOutputStream fos = new FileOutputStream("keys/private/private.key")) {

            fos.write(pvt.getEncoded());

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String[] args) throws NoSuchAlgorithmException {

        System.out.println("server/client?");
        Scanner in = new Scanner(System.in);
        String resp = in.nextLine();

        if(resp.equals("client")) {
            ChatClient client = new ChatClient(key);
            client.startConnection("127.0.0.1", 3333, 3334);
            Thread t = new Thread(client);
            t.start();
            try {
                Thread.sleep(5000);
            }
            catch (Exception e) {
                System.out.println(e.getMessage());
            }
            user = client;
        } else if(resp.equals("server")) {
            ChatServer server = new ChatServer(key);
            server.start(3333, 3334);
            Thread t = new Thread(server);
            t.start();
            user = server;
        }

        generateRSA();

        DemoApplication okienko = new DemoApplication();
        okienko.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okienko.setVisible(true);
        SpringApplication.run(DemoApplication.class, args);

        /*
        File inputFile = new File("E:\\studia\\sem 6\\BSK\\BSK_project\\New Language Leader Advanced.pdf");
        File encryptedFile = new File("encrypted.pdf");
        File decryptedFile = new File("decrypted.pdf");

        try {
            FileEncrypterDecrypter.encrypt(key, inputFile, encryptedFile);
            FileEncrypterDecrypter.decrypt(key, encryptedFile, decryptedFile);
        } catch (CryptoException ex) {
            System.out.println(ex.getMessage());
            ex.printStackTrace();
        }*/
    }

}
