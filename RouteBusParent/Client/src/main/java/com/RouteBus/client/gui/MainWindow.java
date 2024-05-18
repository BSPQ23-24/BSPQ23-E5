package com.RouteBus.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;
import com.toedter.calendar.JDateChooser;

public class MainWindow extends ParentWindow {
    private static final long serialVersionUID = 1L;

    // Panels
    private JPanel contentPanel;
    private JPanel newsPanel;
    private JPanel infoPanel;

    // Menu
    private JMenuBar menuBar;

    private JMenu busRouteInfo;
    private JMenu myInformation;
    private JMenu myRoutes;
    private JMenu myBusTickets;
    private JMenu language;
    private JMenu logout;

    private JMenuItem information;
    private JMenuItem busInfo;
    private JMenuItem routes;
    private JMenuItem tickets;
    private JMenuItem out;
    private JMenuItem spanish;
    private JMenuItem english;
    private JMenuItem basque;
    private JMenuItem german;

    private JTextArea noticia1;
    private JTextArea noticia2;

    private JLabel titleLabel;
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

    private JComboBox<String> comboNationality;

    private ResourceBundle messages;

    public MainWindow(String languageToLoad) {
        super();

        Locale currentLocale;
        if (languageToLoad == null) {
            currentLocale = Locale.getDefault();
            System.out.println(currentLocale);
        } else {
            currentLocale = new Locale(languageToLoad);
        }
        messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);

        this.setTitle(messages.getString("windowTitle"));
        this.setBounds(500, 100, 850, 600);
        this.setResizable(false);

        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        contentPanel.setBackground(colorBackground);
        this.setContentPane(contentPanel);

        newsPanel = new JPanel(null);
        newsPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        newsPanel.setBackground(colorBackground);

        infoPanel = new JPanel(null);
        infoPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        infoPanel.setBackground(colorBackground);

        contentPanel.add(newsPanel);

        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(colorSecondary);

        busRouteInfo = new JMenu(messages.getString("BusRouteInfo"));
        myInformation = new JMenu(messages.getString("myInformationMenu"));
        myRoutes = new JMenu(messages.getString("myRoutesMenu"));
        myBusTickets = new JMenu(messages.getString("myBusTicketsMenu"));
        language = new JMenu(messages.getString("languageMenu"));
        logout = new JMenu(messages.getString("OptionsMenu"));

        busInfo = new JMenuItem(messages.getString("News"));
        information = new JMenuItem(messages.getString("MyInformation"));
        routes = new JMenuItem(messages.getString("MyRoutes"));
        tickets = new JMenuItem(messages.getString("MyTickets"));
        out = new JMenuItem(messages.getString("Logout"));

        spanish = new JMenuItem(messages.getString("spanish"));
        english = new JMenuItem(messages.getString("english"));
        basque = new JMenuItem(messages.getString("basque"));
        german = new JMenuItem(messages.getString("german"));

        // Add menu items to the user menu
        busRouteInfo.add(busInfo);
        myInformation.add(information);
        myRoutes.add(routes);
        myBusTickets.add(tickets);
        logout.add(out);

        // Add languages
        language.add(basque);
        language.add(english);
        language.add(german);
        language.add(spanish);

        menuBar.add(busRouteInfo);
        menuBar.add(myInformation);
        menuBar.add(myRoutes);
        menuBar.add(myBusTickets);
        menuBar.add(language);
        menuBar.add(logout);

        this.setJMenuBar(menuBar);

        // News Panel
        titleLabel = new JLabel(messages.getString("welcome"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(colorPrimary);
        titleLabel.setBounds(300, 50, 300, 30);
        newsPanel.add(titleLabel);

        noticia1 = new JTextArea(messages.getString("new1Content"));
        noticia1.setEditable(false);
        noticia1.setLineWrap(true);
        noticia1.setBackground(colorTertiary);

        noticia2 = new JTextArea(messages.getString("new2Content"));
        noticia2.setEditable(false);
        noticia2.setLineWrap(true);
        noticia2.setBackground(colorTertiary);

        JScrollPane scrollPane1 = new JScrollPane(noticia1);
        scrollPane1.setBounds(200, 180, 600, 100);
        scrollPane1.setBorder(null);

        JScrollPane scrollPane2 = new JScrollPane(noticia2);
        scrollPane2.setBounds(200, 340, 600, 100);
        scrollPane2.setBorder(null);

        newsPanel.add(scrollPane1);
        newsPanel.add(scrollPane2);

        // Information Panel
        infotitle = new JLabel(messages.getString("persInfoWelcome"));
        infotitle.setFont(new Font("Arial", Font.BOLD, 20));
        infotitle.setForeground(colorPrimary);
        infotitle.setBounds(300, 50, 250, 50);
        infoPanel.add(infotitle);

        lUserPic = new JLabel(messages.getString("userPic"));
        lUserPic.setBounds(90, 370, 150, 30);

        lName = new JLabel(messages.getString("nameLabel"));
        lName.setBounds(350, 110, 150, 30);
        lSurname = new JLabel(messages.getString("surnameLabel"));
        lSurname.setBounds(350, 170, 150, 30);
        lBirthDay = new JLabel(messages.getString("birthDateLabel"));
        lBirthDay.setBounds(350, 230, 150, 30);
        lNationality = new JLabel(messages.getString("nationalityLabel"));
        lNationality.setBounds(350, 290, 150, 30);
        lEmail = new JLabel(messages.getString("email"));
        lEmail.setBounds(350, 350, 150, 30);

        infoPanel.add(lUserPic);
        infoPanel.add(lName);
        infoPanel.add(lSurname);
        infoPanel.add(lBirthDay);
        infoPanel.add(lNationality);
        infoPanel.add(lEmail);

        tName = new JTextField();
        tName.setBounds(350, 140, 150, 30);
        tSurname = new JTextField();
        tSurname.setBounds(350, 200, 150, 30);
        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
        dBirthDate.setBounds(350, 260, 150, 30);
        comboNationality = new JComboBox<>();
        comboNationality.setBounds(350, 320, 150, 30);
        tEmail = new JTextField();
        tEmail.setBounds(350, 380, 150, 30);

        infoPanel.add(tName);
        infoPanel.add(tSurname);
        infoPanel.add(dBirthDate);
        infoPanel.add(comboNationality);
        infoPanel.add(tEmail);

        bEdit = new JButton("Edit");
        bEdit.setBackground(colorSecondary);
        bEdit.setBorder(null);
        bEdit.setBounds(370, 440, 100, 30);
        infoPanel.add(bEdit);

        // Actions
        information.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                contentPanel.add(infoPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        busInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                contentPanel.add(newsPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        spanish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLanguage("es");
            }
        });

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLanguage("es_US");
            }
        });

        basque.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLanguage("eus");
            }
        });

        german.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLanguage("de");
            }
        });

        out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new LoginWindow();
            }
        });

        // Images
        JLabel imageLabel1 = loadImage("/images/icon.jpg", 200, 300);
        JLabel imageLabel2 = loadImage("/images/imag1.jpg", 140, 100);
        imageLabel2.setBounds(30, 180, 140, 100);
        JLabel imageLabel3 = loadImage("/images/imag2.jpg", 140, 100);
        imageLabel3.setBounds(30, 340, 140, 100);
        JLabel imageLabel4 = loadImage("/images/icon.jpg", 200, 300);
        JLabel imageLabel5 = loadImage("/images/imagUser.jpg", 200, 200);
        imageLabel5.setBounds(50, 160, 200, 200);

        newsPanel.add(imageLabel1);
        newsPanel.add(imageLabel2);
        newsPanel.add(imageLabel3);
        infoPanel.add(imageLabel4);
        infoPanel.add(imageLabel5);
    }

    private void changeLanguage(String selectedLocale) {
        Locale locale = new Locale(selectedLocale);
        messages = ResourceBundle.getBundle("multilingual/messages", locale);

        this.setTitle(messages.getString("windowTitle"));

        titleLabel.setText(messages.getString("welcome"));
        infotitle.setText(messages.getString("persInfoWelcome"));

        busRouteInfo.setText(messages.getString("BusRouteInfo"));
        myInformation.setText(messages.getString("myInformationMenu"));
        myRoutes.setText(messages.getString("myRoutesMenu"));
        myBusTickets.setText(messages.getString("myBusTicketsMenu"));
        language.setText(messages.getString("languageMenu"));
        logout.setText(messages.getString("OptionsMenu"));
        busInfo.setText(messages.getString("News"));
        information.setText(messages.getString("MyInformation"));
        routes.setText(messages.getString("MyRoutes"));
        tickets.setText(messages.getString("MyTickets"));
        out.setText(messages.getString("Logout"));

        lUserPic.setText(messages.getString("userPic"));
        lName.setText(messages.getString("nameLabel"));
        lSurname.setText(messages.getString("surnameLabel"));
        lBirthDay.setText(messages.getString("birthDateLabel"));
        lNationality.setText(messages.getString("nationalityLabel"));
        lEmail.setText(messages.getString("email"));
        bEdit.setText(messages.getString("editButton"));

        noticia1.setText(messages.getString("new1Content"));
        noticia2.setText(messages.getString("new2Content"));

        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
