package com.RouteBus.client.gui;

import javax.swing.*;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.dto.NationalityDTO;
import com.RouteBus.client.controller.UserController;
import com.toedter.calendar.JDateChooser;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class MyInfoPanel extends JPanel {
    private JLabel infotitle;
    private JLabel lUserPic;
    private JLabel lName;
    private JLabel lSurname;
    private JLabel lEmail;
    private JLabel lBirthDay;
    private JLabel lNationality;
    private JTextField tName;
    private JTextField tSurname;
    private JTextField tEmail;
    private JButton bEdit;
    private JDateChooser dBirthDate;
    private JComboBox<NationalityDTO> comboNationality;
    private UserDTO user;

    public MyInfoPanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorSecondary) {
        this.user = user;
        this.setLayout(new BorderLayout());
        this.setBackground(colorBackground);

        // Panel principal centrado
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(colorBackground);
        this.add(mainPanel, BorderLayout.CENTER);

        // Constraints para el layout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.BOTH;

        // Foto del usuario
        lUserPic = new JLabel();
        lUserPic.setIcon(new ImageIcon(getClass().getResource("/images/imagUser.jpg")));
        lUserPic.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridheight = 6;
        mainPanel.add(lUserPic, gbc);

        // Panel del formulario
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(colorBackground);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridheight = 1;
        mainPanel.add(formPanel, gbc);

        GridBagConstraints formGbc = new GridBagConstraints();
        formGbc.insets = new Insets(5, 10, 5, 10);
        formGbc.fill = GridBagConstraints.HORIZONTAL;

        infotitle = new JLabel(messages.getString("persInfoWelcome"));
        infotitle.setFont(new Font("Arial", Font.BOLD, 30));
        infotitle.setForeground(Color.BLACK);
        formGbc.gridwidth = 2;
        formGbc.gridx = 0;
        formGbc.gridy = 0;
        formGbc.insets = new Insets(10, 10, 20, 10); // Add more space below the title
        formPanel.add(infotitle, formGbc);

        formGbc.insets = new Insets(5, 10, 5, 10); // Reset insets for other components

        lEmail = new JLabel(messages.getString("email"));
        lEmail.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridwidth = 1;
        formGbc.gridx = 0;
        formGbc.gridy = 1;
        formPanel.add(lEmail, formGbc);

        tEmail = new JTextField(user.getEmail());
        tEmail.setFont(new Font("Arial", Font.PLAIN, 20));
        tEmail.setPreferredSize(new Dimension(300, 30)); // Make text fields larger
        tEmail.setEditable(false); // Make email field non-editable
        formGbc.gridx = 0;
        formGbc.gridy = 2;
        formPanel.add(tEmail, formGbc);

        lName = new JLabel(messages.getString("nameLabel"));
        lName.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 3;
        formPanel.add(lName, formGbc);

        tName = new JTextField(user.getFirstName());
        tName.setFont(new Font("Arial", Font.PLAIN, 20));
        tName.setPreferredSize(new Dimension(300, 30));
        formGbc.gridx = 0;
        formGbc.gridy = 4;
        formPanel.add(tName, formGbc);

        lSurname = new JLabel(messages.getString("surnameLabel"));
        lSurname.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 5;
        formPanel.add(lSurname, formGbc);

        tSurname = new JTextField(user.getLastName());
        tSurname.setFont(new Font("Arial", Font.PLAIN, 20));
        tSurname.setPreferredSize(new Dimension(300, 30));
        formGbc.gridx = 0;
        formGbc.gridy = 6;
        formPanel.add(tSurname, formGbc);

        lBirthDay = new JLabel(messages.getString("birthDateLabel"));
        lBirthDay.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 7;
        formPanel.add(lBirthDay, formGbc);

        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
        dBirthDate.setFont(new Font("Arial", Font.PLAIN, 20));
        dBirthDate.setPreferredSize(new Dimension(300, 30));
        dBirthDate.setDate(user.getBirthDate());
        formGbc.gridx = 0;
        formGbc.gridy = 8;
        formPanel.add(dBirthDate, formGbc);

        lNationality = new JLabel(messages.getString("nationalityLabel"));
        lNationality.setFont(new Font("Arial", Font.PLAIN, 20));
        formGbc.gridx = 0;
        formGbc.gridy = 9;
        formPanel.add(lNationality, formGbc);

        comboNationality = new JComboBox<>();
        comboNationality.setFont(new Font("Arial", Font.PLAIN, 20));
        comboNationality.setPreferredSize(new Dimension(300, 30));
        loadNationalities();
        formGbc.gridx = 0;
        formGbc.gridy = 10;
        formPanel.add(comboNationality, formGbc);

        // Botón de editar
        bEdit = new JButton(messages.getString("editButton"));
        bEdit.setBackground(colorSecondary);
        bEdit.setBorder(null);
        bEdit.setFont(new Font("Arial", Font.PLAIN, 20));
        bEdit.setPreferredSize(new Dimension(200, 50)); // Increase height and width of the button
        bEdit.addActionListener(e -> {
            UserDTO updatedUser = new UserDTO(tName.getText(), tSurname.getText(), tEmail.getText(), user.getPassword(), (NationalityDTO) comboNationality.getSelectedItem(), dBirthDate.getDate());
            UserController.getInstance().updateUser(updatedUser);
        });
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 11;
        gbc.insets = new Insets(20, 10, 10, 10); // Add more space above the button
        mainPanel.add(bEdit, gbc);

        this.add(mainPanel, BorderLayout.CENTER);
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
        comboNationality.setSelectedItem(user.getNationality());
    }

    public void updateTexts(ResourceBundle messages) {
        infotitle.setText(messages.getString("persInfoWelcome"));
        lName.setText(messages.getString("nameLabel"));
        lSurname.setText(messages.getString("surnameLabel"));
        lBirthDay.setText(messages.getString("birthDateLabel"));
        lNationality.setText(messages.getString("nationalityLabel"));
        lEmail.setText(messages.getString("email"));
        bEdit.setText(messages.getString("editButton"));
    }
}
