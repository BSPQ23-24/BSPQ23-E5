package com.RouteBus.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class InitialWindow extends ParentWindow {

    private JLabel welcomeLabel;
    private JLabel promoLabel;
    private JButton loginButton;
    private JButton registerButton;
    private ResourceBundle messages;
    private String[] imagePaths;
    private int currentImageIndex = 0;
    private Timer imageTimer;

    public InitialWindow() {
        super();
        Locale currentLocale = Locale.getDefault();
        messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
        this.setTitle(messages.getString("initialWindowTitle"));
        this.setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        imagePaths = new String[]{"/images/InitialImage1.jpg", "/images/InitialImage2.jpg", "/images/InitialImage3.jpg", 
                                  "/images/InitialImage4.jpg", "/images/InitialImage5.jpg", "/images/InitialImage6.jpg", 
                                  "/images/InitialImage7.jpg"};
        
        JPanel contentPane = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                ImageIcon backgroundIcon = new ImageIcon(getClass().getResource(imagePaths[currentImageIndex]));
                Image backgroundImage = backgroundIcon.getImage();
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
        contentPane.setBackground(colorBackground);
        this.setContentPane(contentPane);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Welcome Label
        welcomeLabel = new JLabel(messages.getString("welcome"));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 36));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.WEST;
        contentPane.add(welcomeLabel, gbc);

        // Promo Label
        promoLabel = new JLabel(messages.getString("promoText"));
        promoLabel.setForeground(Color.WHITE);
        promoLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        gbc.gridy = 1;
        contentPane.add(promoLabel, gbc);

        // Button Panel
        loginButton = new JButton(messages.getString("loginButton"));
        loginButton.setBackground(colorSecondary);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 18));
        loginButton.setPreferredSize(new Dimension(150, 40));
        loginButton.setBorder(null);

        registerButton = new JButton(messages.getString("registerButton"));
        registerButton.setBackground(colorSecondary);
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 18));
        registerButton.setPreferredSize(new Dimension(150, 40));
        registerButton.setBorder(null);

        // ACTION LISTENERS
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    LoginWindow loginWindow = new LoginWindow(InitialWindow.this);
                    loginWindow.setVisible(true);
                });
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SwingUtilities.invokeLater(() -> {
                    RegistrationWindow registerWindow = new RegistrationWindow(InitialWindow.this);
                    registerWindow.setVisible(true);
                });
            }
        });

        // Positioning buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 50, 20));
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        contentPane.add(buttonPanel, gbc);

        startImageTransition();
    }

    private void startImageTransition() {
        imageTimer = new Timer(5000, new ActionListener() { // Change image every 5 seconds
            @Override
            public void actionPerformed(ActionEvent e) {
                currentImageIndex = (currentImageIndex + 1) % imagePaths.length;
                repaint();
            }
        });
        imageTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialWindow window = new InitialWindow();
            window.setVisible(true);
        });
    }
}
