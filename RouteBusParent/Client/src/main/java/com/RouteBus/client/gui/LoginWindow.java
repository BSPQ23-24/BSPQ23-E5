package com.RouteBus.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    private JLabel emailLabel;
    private JLabel passLabel;
    private JLabel registerLabel;

    private JTextField emailField;
    private JPasswordField passwordField;

    private JButton loginButton;
    private JButton registerButton;

    public LoginWindow() {
        this.setTitle("Login");
        this.setLayout(null);
        this.setBounds(500, 100, 420, 600);
        this.setResizable(false);

        // Set the content pane background to white
        JPanel contentPane = new JPanel(null);
        contentPane.setBackground(Color.WHITE);
        this.setContentPane(contentPane);

        // ELEMENT CREATION
        emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.BLACK);

        passLabel = new JLabel("Password:");
        passLabel.setForeground(Color.BLACK);

        registerLabel = new JLabel("Not registered yet?");
        registerLabel.setForeground(Color.BLACK);

        emailField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Log in");
        loginButton.setToolTipText("Log in");
        loginButton.setBackground(new Color(204, 153, 255));
        loginButton.setBorder(null);

        registerButton = new JButton("Register");
        registerButton.setToolTipText("Register");
        registerButton.setBackground(new Color(204, 153, 255));
        registerButton.setBorder(null);

        // ACTION LISTENERS
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Handle login action here
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the login window
                dispose();
                // Open the registration window
                new RegistrationWindow();
            }
        });

        // POSITIONING
        emailLabel.setBounds(140, 210, 150, 30);
        contentPane.add(emailLabel);

        emailField.setBounds(140, 250, 150, 30);
        contentPane.add(emailField);

        passLabel.setBounds(140, 280, 150, 30);
        contentPane.add(passLabel);

        passwordField.setBounds(140, 320, 150, 30);
        contentPane.add(passwordField);

        loginButton.setBounds(140, 360, 150, 30);
        contentPane.add(loginButton);

        registerLabel.setBounds(140, 390, 150, 30);
        contentPane.add(registerLabel);

        registerButton.setBounds(140, 420, 150, 30);
        contentPane.add(registerButton);

        // Load and display the image
        try {
            BufferedImage image = ImageIO.read(new File("images/icon.png"));
            ImageIcon icon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(100, -30, 200, 300); // Adjust position and size as needed
            contentPane.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    public static void main(String[] args) {
        new LoginWindow();
    }
}


