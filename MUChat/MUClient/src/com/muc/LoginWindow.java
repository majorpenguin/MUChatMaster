package com.muc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginWindow extends JFrame{

    private final ChatClient client;
    JTextField loginField = new JTextField();
    JPasswordField passwordField = new JPasswordField();
    JButton loginButton = new JButton("Login");

    public LoginWindow(){

        super("Login");
        this.client = new ChatClient("localhost", 8818);
        client.connect();

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JPanel pnl = new JPanel();
        pnl.setLayout(new BoxLayout(pnl, BoxLayout.Y_AXIS));
        pnl.setSize(400, 400);
        pnl.add(loginField);
        pnl.add(passwordField);
        pnl.add(loginButton);

        loginButton.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e){
                doLogin();
            }
        });

        getContentPane().add(pnl, BorderLayout.CENTER);

        pack();

        setVisible(true);
    }

    private void doLogin(){
        String login = loginField.getText();
        String password = passwordField.getText();

        try{
            if (client.login(login, password)){
                UserList userList = new UserList(client);
                JFrame frame = new JFrame("User list");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(400, 600);

                frame.getContentPane().add(userList, BorderLayout.CENTER);
                frame.setVisible(true);

                setVisible(false);
            }
            else{
                // show error message
                JOptionPane.showMessageDialog(this, "Invalid credentials");
            }
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        LoginWindow loginWin = new LoginWindow();
        loginWin.setVisible(true);
    }
}
