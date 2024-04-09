package com.RouteBus.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.RouteBus.client.gateway.ServerGateway;



public class LoginWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	private ServerGateway serverGateway;
	private JLabel appNameLabel;
	
	private JLabel emailLabel;
	private JLabel labelPassword;
	
	private JTextField emailTField;
	private JPasswordField passwordField;
	
	private JButton loginButton;
	
	private JLabel registerLabel;
	private JButton registerButton;
	
	public LoginWindow(ServerGateway sg) {
		
		this.setTitle("LOGIN");
		this.setLayout(null);
		this.setBounds(500, 100, 420, 600);
		this.setResizable(false);
		
		URL urlBackGround;
		
		try {
			urlBackGround = new URL("https://www.caf.com/media/6860/las-carreteras-de-america-latina-no-estan-suficientemente-preparadas-para-enfrentar-el-cambio-climatico.jpg");
			Image imageBackground = ImageIO.read(urlBackGround).getScaledInstance(400, 600, java.awt.Image.SCALE_SMOOTH);
			this.setContentPane(new JLabel(new ImageIcon(imageBackground)));
		} catch (Exception e) {
			System.out.println("# Problem while setting the background: " + e);
		}
		
		// ELEMENT CREATION
		appNameLabel = new JLabel("RouteBus");
		appNameLabel.setFont(new Font("Arial", Font.BOLD, 30));
		appNameLabel.setForeground(Color.WHITE);
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		emailLabel = new JLabel("Email: ");
		emailLabel.setForeground(Color.WHITE);
		
		emailTField = new JTextField();
		
		labelPassword = new JLabel("Password:");
		labelPassword.setForeground(Color.WHITE);
		
		passwordField = new JPasswordField();
		
		loginButton = new JButton("LOG IN");
		loginButton.setToolTipText("Log in");
		
		registerLabel = new JLabel("Not registered yet?");
		registerLabel.setForeground(Color.WHITE);
		
		registerButton = new JButton("REGISTER");
		registerButton.setToolTipText("Register");
		
		// POSITIONING
        appNameLabel.setBounds(0, 100, getWidth(), 30);
        this.add(appNameLabel);
        
		emailLabel.setBounds(140, 180, 150, 30);
		this.add(emailLabel);

		emailTField.setBounds(140, 210, 150, 30);
		this.add(emailTField);

		labelPassword.setBounds(140, 250, 150, 30);
		this.add(labelPassword);

		passwordField.setBounds(140, 280, 150, 30);
		this.add(passwordField);

		loginButton.setBounds(140, 320, 150, 30);
		this.add(loginButton);
		this.revalidate();

		registerLabel.setBounds(140, 360, 150, 30);
		this.add(registerLabel);

		registerButton.setBounds(140, 390, 150, 30);
		this.add(registerButton);
		
		this.setVisible(true);
		
		loginButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String password = String.valueOf(passwordField.getPassword());
				String password_example = "SPQ23%24";
				if(sg.checkPassword(emailTField.getText(),String.valueOf(passwordField.getPassword()))) {
					System.out.println("login correct");
				};
			}
		});
		
		registerButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				new RegistrationWindow();
			}
		});
		
			this.addWindowListener(new WindowAdapter() {
					
				@Override
				public void windowClosing(WindowEvent e) {
					System.exit(0);
				}
				
			});
			
			this.setVisible(true);
		}
}