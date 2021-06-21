package bsk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.Cipher;
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

    public DemoApplication()
    {


        setLocation(200,200);
        setSize(500,300);
        setTitle("Wysylanie pliku");
        setLayout(null);

        fileButton = new JButton("Wybierz plik");
        fileButton.setBounds(30,120,150,20);
        add(fileButton);
        fileButton.addActionListener(this);


        JTextField text = new JTextField("");
        text.setBounds(30,20,300,40);
        add(text);

        sendButton = new JButton("Wyślij");
        sendButton.setBounds(30,80,150,20);
        add(sendButton);
        sendButton.addActionListener(this);

        radioGroup=new ButtonGroup();
        JRadioButton radio_ecb = new JRadioButton("ECB",true);
        radio_ecb.setBounds(50,140,100,20);
        JRadioButton radio_cbc = new JRadioButton("CBC",true);
        radio_cbc.setBounds(50,160,100,20);
        JRadioButton radio_cfb = new JRadioButton("CFB",true);
        radio_cfb.setBounds(50,180,100,20);
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
            JOptionPane.showMessageDialog(this,
                    "Wysłano.");
        }

        if(source == fileButton) {
            File file;
            Scanner fileIn;
            int response;
            JFileChooser chooser = new JFileChooser(".");

            chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
            response = chooser.showOpenDialog(null);
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


        generateRSA();

        DemoApplication okienko = new DemoApplication();
        okienko.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okienko.setVisible(true);
        SpringApplication.run(DemoApplication.class, args);
    }

}
