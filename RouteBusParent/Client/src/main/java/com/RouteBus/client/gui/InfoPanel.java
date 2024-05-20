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
public class InfoPanel extends JPanel {
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

    public InfoPanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorSecondary) {
        this.user = user;
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        infotitle = new JLabel(messages.getString("persInfoWelcome"));
        infotitle.setFont(new Font("Arial", Font.BOLD, 24));
        infotitle.setForeground(Color.BLACK);
        infotitle.setBounds(50, 50, 300, 50);
        this.add(infotitle);

        lUserPic = new JLabel(messages.getString("userPic"));
        lUserPic.setBounds(90, 370, 150, 30);

        lName = new JLabel(messages.getString("nameLabel"));
        lName.setBounds(350, 110, 200, 30);
        lName.setFont(new Font("Arial", Font.BOLD, 18));
        lSurname = new JLabel(messages.getString("surnameLabel"));
        lSurname.setBounds(350, 170, 200, 30);
        lSurname.setFont(new Font("Arial", Font.BOLD, 18));
        lBirthDay = new JLabel(messages.getString("birthDateLabel"));
        lBirthDay.setBounds(350, 230, 200, 30);
        lBirthDay.setFont(new Font("Arial", Font.BOLD, 18));
        lNationality = new JLabel(messages.getString("nationalityLabel"));
        lNationality.setBounds(350, 290, 200, 30);
        lNationality.setFont(new Font("Arial", Font.BOLD, 18));
        lEmail = new JLabel(messages.getString("email"));
        lEmail.setBounds(350, 350, 200, 30);
        lEmail.setFont(new Font("Arial", Font.BOLD, 18));

        this.add(lUserPic);
        this.add(lName);
        this.add(lSurname);
        this.add(lBirthDay);
        this.add(lNationality);
        this.add(lEmail);

        tName = new JTextField(user.getFirstName());
        tName.setBounds(350, 140, 200, 30);
        tSurname = new JTextField(user.getLastName());
        tSurname.setBounds(350, 200, 200, 30);
        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
        dBirthDate.setBounds(350, 260, 200, 30);
        dBirthDate.setDate(user.getBirthDate());
        comboNationality = new JComboBox<>();
        comboNationality.setBounds(350, 320, 200, 30);
        loadNationalities();
        
        tEmail = new JTextField(user.getEmail());
        tEmail.setBounds(350, 380, 200, 30);
        this.add(tName);
        this.add(tSurname);
        this.add(dBirthDate);
        this.add(comboNationality);
        this.add(tEmail);

        bEdit = new JButton(messages.getString("editButton"));
        bEdit.setBackground(colorSecondary);
        bEdit.setBorder(null);
        bEdit.setBounds(350, 440, 100, 30);
        bEdit.addActionListener(e -> {
            UserDTO updatedUser = new UserDTO(tName.getText(), tSurname.getText(), tEmail.getText(), user.getPassword(), (NationalityDTO) comboNationality.getSelectedItem(), dBirthDate.getDate());
            UserController.getInstance().updateUser(updatedUser);
        });
        this.add(bEdit);
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
        lUserPic.setText(messages.getString("userPic"));
        lName.setText(messages.getString("nameLabel"));
        lSurname.setText(messages.getString("surnameLabel"));
        lBirthDay.setText(messages.getString("birthDateLabel"));
        lNationality.setText(messages.getString("nationalityLabel"));
        lEmail.setText(messages.getString("email"));
        bEdit.setText(messages.getString("editButton"));
    }
}
