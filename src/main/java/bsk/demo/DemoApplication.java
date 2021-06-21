package bsk.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.*;
import java.util.List;
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
        JRadioButton radio1 = new JRadioButton("ECB",true);
        radio1.setBounds(50,180,100,20);
        JRadioButton radio2 = new JRadioButton("CBC",true);
        radio2.setBounds(50,200,100,20);
        JRadioButton radio3 = new JRadioButton("CFB",true);
        radio3.setBounds(50,220,100,20);
        radioGroup.add(radio1);
        radioGroup.add(radio2);
        radioGroup.add(radio3);
        add(radio1);
        add(radio2);
        add(radio3);



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

    public static void main(String[] args) throws NoSuchAlgorithmException {

        KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
        kpg.initialize(2048);
        KeyPair kp = kpg.generateKeyPair();
        Key pub = kp.getPublic();
        Key pvt = kp.getPrivate();


        DemoApplication okienko = new DemoApplication();
        okienko.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        okienko.setVisible(true);
        SpringApplication.run(DemoApplication.class, args);
    }

}
