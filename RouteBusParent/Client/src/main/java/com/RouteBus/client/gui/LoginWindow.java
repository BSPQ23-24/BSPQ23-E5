package com.RouteBus.client.gui;

import javax.swing.*;

import com.RouteBus.client.controller.UserController;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.dto.UserDTO.UserRole;

import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@SuppressWarnings("serial")
public class LoginWindow extends ParentWindow {

	private JLabel emailLabel;
	private JLabel passLabel;
	private JLabel registerLabel;

	private JTextField emailField;
	private JPasswordField passwordField;

	private JButton loginButton;
	private JButton registerButton;
	private ResourceBundle messages;

	public LoginWindow() {
		super();
		Locale currentLocale = Locale.getDefault();
		messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
		this.setTitle("Login");
		this.setLayout(null);
		this.setBounds(500, 100, 420, 600);
		this.setResizable(false);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel contentPane = new JPanel(null);
		contentPane.setBackground(colorBackground);
		this.setContentPane(contentPane);

		// ELEMENT CREATION
		emailLabel = new JLabel(messages.getString("email"));
		emailLabel.setForeground(Color.BLACK);

		passLabel = new JLabel(messages.getString("password"));
		passLabel.setForeground(Color.BLACK);

		registerLabel = new JLabel(messages.getString("registerLabel"));
		registerLabel.setForeground(Color.BLACK);

		emailField = new JTextField();
		passwordField = new JPasswordField();

		loginButton = new JButton(messages.getString("loginButton"));
		loginButton.setToolTipText("Log in");
		loginButton.setBackground(colorSecondary);
		loginButton.setBorder(null);

		registerButton = new JButton((messages.getString("registerButton")));
		registerButton.setToolTipText("Register");
		registerButton.setBackground(colorSecondary);
		registerButton.setBorder(null);

		// ACTION LISTENERS
		loginButton.addActionListener(e -> performLogin());

		registerButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				SwingUtilities.invokeLater(() -> {
					RegistrationWindow window = new RegistrationWindow();
					window.setVisible(true);
				});
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
		JLabel imageLabel = loadImage("/images/busroute.jpg", 200, 200);
		imageLabel.setBounds(110, -30, 200, 300);
		contentPane.add(imageLabel);

		this.setVisible(true);
	}

	private void performLogin() {
		String email = emailField.getText().trim();
		String password = new String(passwordField.getPassword());

		if (email.isEmpty() || password.isEmpty()) {
			JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
			return;
		}

		boolean validLogin = UserController.getInstance().checkPassword(email, password);
		if (validLogin) {
			UserDTO user = UserController.getInstance().getUserByEmail(email);
			this.dispose();
			if (user.getRole() == UserRole.ADMIN) {
				SwingUtilities.invokeLater(() -> {
					AdministratorWindow window = new AdministratorWindow();
					window.setVisible(true);
				});
			} else {
				SwingUtilities.invokeLater(() -> {
					MainWindow window = new MainWindow(user.getNationality().getLanguage());
					window.setVisible(true);
				});
			}
		} else {
			JOptionPane.showMessageDialog(this, "Invalid email or password.", "Login Failed",
					JOptionPane.ERROR_MESSAGE);
		}
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			LoginWindow window = new LoginWindow();
			window.setVisible(true);
		});
	}
}
