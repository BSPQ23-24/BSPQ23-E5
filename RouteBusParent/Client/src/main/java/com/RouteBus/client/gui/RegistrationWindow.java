package com.RouteBus.client.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import com.RouteBus.client.controller.UserController;
import com.RouteBus.client.dto.NationalityDTO;
import com.RouteBus.client.dto.UserDTO;
import com.toedter.calendar.JDateChooser;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class RegistrationWindow extends JFrame {

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

    private JComboBox<NationalityDTO> comboNationality;

    private JPasswordField password;
    private JPasswordField passwordR;

    private JButton bRegister;

    private ResourceBundle messages;

    public RegistrationWindow() {
        Locale currentLocale = Locale.getDefault();
        messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
        this.setTitle(messages.getString("windowTitle"));
        this.setLayout(null);
        this.setBounds(300, 100, 750, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel contentPane = new JPanel(null);
        contentPane.setBackground(Color.WHITE);
        this.setContentPane(contentPane);

        lName = new JLabel(messages.getString("nameLabel"));
        lSurname = new JLabel(messages.getString("surnameLabel"));
        lEmail = new JLabel(messages.getString("email"));
        lBirthDay = new JLabel(messages.getString("birthDateLabel"));
        lNationality = new JLabel(messages.getString("nationalityLabel"));
        lPassword = new JLabel(messages.getString("password"));
        lPasswordR = new JLabel(messages.getString("repeatPasswordLabel"));

        tName = new JTextField();
        tSurname = new JTextField();
        tEmail = new JTextField();

        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');

        comboNationality = new JComboBox<>();
        loadNationalities();
        comboNationality.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NationalityDTO selectedNationality = (NationalityDTO) comboNationality.getSelectedItem();
                if (selectedNationality != null) {
                    Locale locale = new Locale(selectedNationality.getLanguage());
                    messages = ResourceBundle.getBundle("multilingual/messages", locale);
                    updateLabels();
                }
            }
        });

        password = new JPasswordField();
        passwordR = new JPasswordField();

        bRegister = new JButton(messages.getString("registerButton"));
        bRegister.setBackground(new Color(204, 153, 255));
        bRegister.setBorder(null);

        // POSITIONING
        lName.setBounds(50, 210, 150, 30);
        contentPane.add(lName);

        tName.setBounds(50, 240, 150, 30);
        contentPane.add(tName);

        lSurname.setBounds(230, 210, 150, 30);
        contentPane.add(lSurname);

        tSurname.setBounds(230, 240, 150, 30);
        contentPane.add(tSurname);

        lEmail.setBounds(60, 270, 150, 30);
        contentPane.add(lEmail);

        tEmail.setBounds(60, 300, 310, 30);
        contentPane.add(tEmail);

        lNationality.setBounds(50, 330, 150, 30);
        contentPane.add(lNationality);

        comboNationality.setBounds(50, 360, 150, 30);
        contentPane.add(comboNationality);

        lBirthDay.setBounds(230, 330, 150, 30);
        contentPane.add(lBirthDay);

        dBirthDate.setBounds(230, 360, 150, 30);
        contentPane.add(dBirthDate);

        lPassword.setBounds(500, 210, 150, 30);
        contentPane.add(lPassword);

        password.setBounds(500, 240, 150, 30);
        contentPane.add(password);

        lPasswordR.setBounds(500, 290, 150, 30);
        contentPane.add(lPasswordR);

        passwordR.setBounds(500, 320, 150, 30);
        contentPane.add(passwordR);

        bRegister.setBounds(320, 450, 150, 30);
        contentPane.add(bRegister);

        bRegister.addActionListener(this::performRegistration);

        // Draw vertical line
        VerticalLinePanel linePanel = new VerticalLinePanel();
        linePanel.setBounds(450, 200, 1, 220);
        contentPane.add(linePanel);

        try {
            BufferedImage image = ImageIO.read(getClass().getResource("/images/icon.jpg"));
            BufferedImage iconImage = ImageIO.read(getClass().getResource("/images/iconBus.png"));
            
            ImageIcon icon = new ImageIcon(image);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setBounds(300, -40, 200, 300);
            contentPane.add(imageLabel);
            
            this.setIconImage(iconImage);
        } catch (IOException | IllegalArgumentException e) {
            e.printStackTrace();
        }

        this.setVisible(true);

    }

    private void performRegistration(ActionEvent e) {
        String name = tName.getText().trim();
        String surname = tSurname.getText().trim();
        String email = tEmail.getText().trim();
        Date birthDate = dBirthDate.getDate();
        String nationality = ((NationalityDTO) comboNationality.getSelectedItem()).getName();
        String passwordText = new String(password.getPassword());
        String passwordRText = new String(passwordR.getPassword());

        if (name.isEmpty() || surname.isEmpty() || email.isEmpty() || birthDate == null || nationality.isEmpty() || passwordText.isEmpty() || passwordRText.isEmpty()) {
            JOptionPane.showMessageDialog(this, messages.getString("fillFieldsError"), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!passwordText.equals(passwordRText)) {
            JOptionPane.showMessageDialog(this, messages.getString("passwordMismatchError"), "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        UserDTO newUser = new UserDTO(name, surname, email, passwordText, new NationalityDTO(nationality), birthDate);
        boolean created = UserController.getInstance().createUser(newUser);
        if (created) {
            JOptionPane.showMessageDialog(this, messages.getString("registrationSuccess"), "Success", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();
            new MainWindow(newUser.getNationality().getLanguage());
        } else {
            JOptionPane.showMessageDialog(this, messages.getString("registrationFailError"), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static class VerticalLinePanel extends JPanel {
        private static final long serialVersionUID = 1L;

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(Color.BLACK);
            g.drawLine(0, 0, 0, getHeight());
        }
    }

    private void loadNationalities() {
        List<NationalityDTO> nationalities = UserController.getInstance().getNationalities();
        for (NationalityDTO nationality : nationalities) {
            comboNationality.addItem(nationality);
        }
        comboNationality.setRenderer(new DefaultListCellRenderer() {
			private static final long serialVersionUID = 1L;

			@Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof NationalityDTO) {
                    setText(((NationalityDTO) value).getName());
                }
                return this;
            }
        });
    }

    private void updateLabels() {
        lName.setText(messages.getString("nameLabel"));
        lSurname.setText(messages.getString("surnameLabel"));
        lEmail.setText(messages.getString("email"));
        lBirthDay.setText(messages.getString("birthDateLabel"));
        lNationality.setText(messages.getString("nationalityLabel"));
        lPassword.setText(messages.getString("password"));
        lPasswordR.setText(messages.getString("repeatPasswordLabel"));
        bRegister.setText(messages.getString("registerButton"));
    }
}
