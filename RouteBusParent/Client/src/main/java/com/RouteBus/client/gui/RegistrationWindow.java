package com.RouteBus.client.gui;
import javax.imageio.ImageIO;
import javax.swing.*;

//import com.RouteBus.client.model.User;
import com.toedter.calendar.JDateChooser;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

public class RegistrationWindow extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JLabel lName;
	private JLabel lSurname;
	private JLabel lEmail;
	private JLabel lBirthDay;
	private JLabel lNationality;
	private JLabel lPassword;
	private JLabel lPasswordR;
	
	private JTextField tName;
	private JTextField tSurname;
	private JTextField tEmail;
	
	private JDateChooser dBirthDate;
	
	private JComboBox<String> comboNationality;
	
	private JPasswordField password;
	private JPasswordField passwordR;
	
	private JButton bRegister;
	
	public RegistrationWindow() {
		this.setTitle("Registration Window");
		this.setLayout(null);
		this.setBounds(300, 100, 750, 600);
		this.setResizable(false);
		
		JPanel contentPane = new JPanel(null);
		contentPane.setBackground(Color.WHITE);
		this.setContentPane(contentPane);
		
		lName = new JLabel("Name: ");
		lSurname = new JLabel("Surname: ");
		lEmail = new JLabel("Email: ");
		lBirthDay = new JLabel("Birthdate: ");
		lNationality = new JLabel("Nationality: ");
		lPassword = new JLabel("Password: ");
		lPasswordR = new JLabel("Repeat password: ");
		
		tName = new JTextField();
		tSurname = new JTextField();
		tEmail = new JTextField();
		
		dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
		
		comboNationality = new JComboBox<String>();
        comboNationality.addItem("American");
        comboNationality.addItem("British");
        comboNationality.addItem("Chinese");
        comboNationality.addItem("French");
        comboNationality.addItem("German");
        comboNationality.addItem("Indian");
        comboNationality.addItem("Japanese");
        comboNationality.addItem("Russian");
        comboNationality.addItem("Spanish");
		
		password = new JPasswordField();
		passwordR = new JPasswordField();
		
		bRegister = new JButton("Register");
		bRegister.setBackground(new Color(204, 153, 255));
		bRegister.setBorder(null);
		
		// POSITIONING
		lName.setBounds(50,210,150,30);
		contentPane.add(lName);
		
		tName.setBounds(50,240, 150,30);
		contentPane.add(tName);
		
		lSurname.setBounds(230, 210, 150, 30);
		contentPane.add(lSurname);
		
		tSurname.setBounds(230, 240, 150, 30);
		contentPane.add(tSurname);
		
		lEmail.setBounds(60, 270, 150, 30);
		contentPane.add(lEmail);
		
		tEmail.setBounds(60, 300, 310, 30);
		contentPane.add(tEmail);
		
		lNationality.setBounds(50, 330, 150,30);
		contentPane.add(lNationality);
		
		comboNationality.setBounds(50, 360, 150, 30);
		contentPane.add(comboNationality);
		
		lBirthDay.setBounds(230, 330, 150, 30);
		contentPane.add(lBirthDay);
		
		dBirthDate.setBounds(230, 360, 150,30);
		contentPane.add(dBirthDate);
		
		lPassword.setBounds(500, 210, 150, 30);
		contentPane.add(lPassword);
		
		password.setBounds(500,240,150,30);
		contentPane.add(password);
		
		lPasswordR.setBounds(500, 290, 150, 30);
		contentPane.add(lPasswordR);
		
		passwordR.setBounds(500,320,150,30);
		contentPane.add(passwordR);
		
		bRegister.setBounds(320, 450, 150, 30);
		contentPane.add(bRegister);
		
		bRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
	            String name = tName.getText().trim();
	            String surname = tSurname.getText().trim();
	            String email = tEmail.getText().trim();
	            String nationality = (String) comboNationality.getSelectedItem();
	            String passwordText = new String(password.getPassword());
	            String passwordRText = new String(passwordR.getPassword());
	            // Convert birthdate to string
	            Date birthDate = dBirthDate.getDate();

	            // Check if all fields are filled
	            if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || birthDate == null || nationality.isEmpty() || passwordText.isEmpty() || passwordRText.isEmpty()) {
	                JOptionPane.showMessageDialog(RegistrationWindow.this, "Please fill in all fields.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            } else {
//	                User user = new User();
//	                user.setFirstName(name);
//	                user.setLastName(surname);
//	                user.setEmail(email);
//	                user.setBirthDate(birthDate);
//	                user.setNationality(nationality);
//	                user.setPassword(passwordText);
	            }

	            // Check if passwords match
	            if (!passwordText.equals(passwordRText)) {
	                JOptionPane.showMessageDialog(RegistrationWindow.this, "Passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
	                return;
	            }
				
			}
		});
		
        // Draw vertical line
        VerticalLinePanel linePanel = new VerticalLinePanel();
        linePanel.setBounds(450, 200, 1, 220); // Adjust position and size as needed
        contentPane.add(linePanel);
		
        try {
            BufferedImage image = ImageIO.read(new File("images/icon.png"));
            ImageIcon icon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(300, -40, 200, 300); // Adjust position and size as needed
            contentPane.add(imageLabel);
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		
		this.setVisible(true);
		
	}
	
	public static void main(String[] args) {
		new RegistrationWindow();
	}
	
	// Custom panel to draw a vertical line
    private static class VerticalLinePanel extends JPanel {
        /**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.drawLine(0, 0, 0, getHeight());
     }
    }

}
