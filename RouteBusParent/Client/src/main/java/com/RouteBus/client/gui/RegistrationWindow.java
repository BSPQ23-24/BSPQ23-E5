package com.RouteBus.client.gui;
import com.RouteBus.client.gateway.ServerGateway;
import com.toedter.calendar.JDateChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.regex.Pattern;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class RegistrationWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private ServerGateway serverGateway;
    private JTextField tName;
    private JTextField tSurname;
    private JTextField tEmail;
    private JDateChooser dBirthDate;

    private JPasswordField password;
    private JPasswordField passwordR;

    public RegistrationWindow() {

        this.setTitle("REGISTER NEW USER");
        this.setLayout(null);
        this.setBounds(500, 100, 420, 600);
        this.setResizable(false);

        URL urlBackGround;

        try {
            urlBackGround = new URL("https://www.caf.com/media/6860/las-carreteras-de-america-latina-no-estan-suficientemente-preparadas-para-enfrentar-el-cambio-climatico.jpg");
            Image imageBackground = new ImageIcon(urlBackGround).getImage().getScaledInstance(420, 600, Image.SCALE_SMOOTH);
            this.setContentPane(new JLabel(new ImageIcon(imageBackground)));
        } catch (Exception e) {
            System.out.println("# Problem while setting the background: " + e);
        }

        JPanel panel = new JPanel();
        panel.setOpaque(false);
        panel.setBounds(40, 80, 340, 450);
        panel.setLayout(null);
        this.getContentPane().add(panel);

        JLabel titleLabel = new JLabel("Registration");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setBounds(66, 0, 200, 30);
        panel.add(titleLabel);

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setForeground(Color.WHITE);
        nameLabel.setBounds(30, 50, 80, 30);
        panel.add(nameLabel);

        tName = new JTextField();
        tName.setBounds(140, 50, 200, 30);
        panel.add(tName);

        JLabel surnameLabel = new JLabel("Surname:");
        surnameLabel.setForeground(Color.WHITE);
        surnameLabel.setBounds(30, 90, 80, 30);
        panel.add(surnameLabel);

        tSurname = new JTextField();
        tSurname.setBounds(140, 90, 200, 30);
        panel.add(tSurname);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setForeground(Color.WHITE);
        emailLabel.setBounds(30, 130, 80, 30);
        panel.add(emailLabel);

        tEmail = new JTextField();
        tEmail.setBounds(140, 130, 200, 30);
        panel.add(tEmail);

        JLabel birthDateLabel = new JLabel("Birth Date:");
        birthDateLabel.setForeground(Color.WHITE);
        birthDateLabel.setBounds(30, 250, 80, 30);
        panel.add(birthDateLabel);

        // Initialize JDateChooser
        dBirthDate = new JDateChooser();
        dBirthDate.setBounds(140, 250, 200, 30);
        panel.add(dBirthDate);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setBounds(30, 170, 80, 30);
        panel.add(passwordLabel);

        password = new JPasswordField();
        password.setBounds(140, 170, 200, 30);
        panel.add(password);

        JLabel passwordRLabel = new JLabel("Repeat Password:");
        passwordRLabel.setForeground(Color.WHITE);
        passwordRLabel.setBounds(30, 210, 110, 30);
        panel.add(passwordRLabel);

        passwordR = new JPasswordField();
        passwordR.setBounds(140, 210, 200, 30);
        panel.add(passwordR);

        JButton bRegister = new JButton("Register");
        bRegister.setBounds(90, 300, 160, 30);
        panel.add(bRegister);
        bRegister.addMouseListener(new MouseAdapter() {
            Pattern pEmail = Pattern.compile("^[A-Z0-9+_.-]+@[A-Z0-9.-]+$");
            Pattern pPassword = Pattern.compile("#");
            
            @Override
            public void mouseClicked(MouseEvent e) {
                String password1 = String.valueOf(password.getPassword()); 
                String password2 = String.valueOf(passwordR.getPassword()); 	
                String emailCheck = tEmail.getText();
                boolean b1 = pEmail.matcher(emailCheck).find();
                            
                if(tName.getText().equals("") || tSurname.getText().equals("")||dBirthDate.getDate() == null || tEmail.getText().equals("")){
                    JOptionPane.showMessageDialog(null, "Name, Surname, Birthdate must be filled to register correctly");
                    if(!b1) {
                        JOptionPane.showMessageDialog(null, "Email format is not valid");	
                    }					

                }else if (password1.equals(password2) && password1.length()>0) {
                    boolean b2 = pPassword.matcher(password1).find();
                    if(!b2) {
                        
                        String name = tName.getText();
                        String surname = tSurname.getText();
                        String email = tEmail.getText();
                        String userPassword = new String(password.getPassword());
                        //
                        if (true) {
                            JOptionPane.showMessageDialog(null,"User has been created","",JOptionPane.DEFAULT_OPTION);		
                            dispose();
                            new LoginWindow(serverGateway);
                        }						
                    }else {
                        JOptionPane.showMessageDialog(null,"The password contains an unvalid character","",JOptionPane.ERROR_MESSAGE);		
                    }
                }
                else {
                    JOptionPane.showMessageDialog(null,"Passwords do not match","",JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
        JTextField loginText = new JTextField("Already have an account?");
        loginText.setBounds(100, 330, 290, 20);
        loginText.setForeground(Color.WHITE);
        loginText.setBorder(null);
        loginText.setOpaque(false);
        loginText.setEditable(false);
        panel.add(loginText);

        // Add mouse listener to the login text field
        loginText.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                dispose();
                new LoginWindow(serverGateway);
            }
        });
        this.setVisible(true);
    }

    public static void main(String[] args) {
        new RegistrationWindow();
    }
}
