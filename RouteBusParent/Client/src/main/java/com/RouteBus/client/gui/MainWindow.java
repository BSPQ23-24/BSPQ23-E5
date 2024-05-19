package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.RouteBus.client.dto.NationalityDTO;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class MainWindow extends ParentWindow {

    // Panels
    private JPanel contentPanel;
    private JPanel newsPanel;
    private JPanel infoPanel;
    private JPanel ticketPanel;

    // ScrollPane
    private JScrollPane tablePane;

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
    private JLabel ticketTitle;
    private JLabel lUserPic;
    private JLabel lName;
    private JLabel lSurname;
    private JLabel lEmail;
    private JLabel lBirthDay;
    private JLabel lNationality;
    private JLabel lOrigin;
    private JLabel lDestination;

    private JTextField tName;
    private JTextField tSurname;
    private JTextField tEmail;

    private JButton bEdit;

    private JDateChooser dBirthDate;

    private JComboBox<NationalityDTO> comboNationality;
    private JComboBox<String> comboOrigin;
    private JComboBox<String> comboDestination;

    private JTable tableJourneys;

    private ResourceBundle messages;

    public MainWindow(String languageToLoad) {
        super();
        Locale currentLocale;
        if (languageToLoad == null) {
            currentLocale = Locale.getDefault();
        } else {
            currentLocale = new Locale(languageToLoad);
        }
        messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
        this.setTitle(messages.getString("windowTitle"));
        this.setLayout(null);
        this.setBounds(500, 100, 850, 600);
        this.setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(contentPanel);

        newsPanel = new JPanel(null);
        newsPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        newsPanel.setBackground(Color.WHITE);

        infoPanel = new JPanel(null);
        infoPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        infoPanel.setBackground(Color.WHITE);

        ticketPanel = new JPanel(null);
        ticketPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        ticketPanel.setBackground(Color.WHITE);

        contentPanel.add(newsPanel);

        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));

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
        // Initialize text areas for news
        titleLabel = new JLabel(messages.getString("welcome"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
        titleLabel.setForeground(Color.BLACK); // Set text color
        titleLabel.setBounds(300, 50, 300, 30); // Adjust position and size as needed
        newsPanel.add(titleLabel);

        noticia1 = new JTextArea(messages.getString("new1Content"));
        noticia1.setEditable(false); // Make it read-only
        noticia1.setLineWrap(true); // Wrap text to next line if necessary
        noticia1.setBackground(new Color(223, 169, 245));

        noticia2 = new JTextArea(messages.getString("new2Content"));
        noticia2.setEditable(false); // Make it read-only
        noticia2.setLineWrap(true); // Wrap text to next line if necessary
        noticia2.setBackground(new Color(223, 169, 245));

        JScrollPane scrollPane1 = new JScrollPane(noticia1);
        scrollPane1.setBounds(200, 180, 600, 100);
        scrollPane1.setBorder(null);

        JScrollPane scrollPane2 = new JScrollPane(noticia2);
        scrollPane2.setBounds(200, 340, 600, 100);
        scrollPane2.setBorder(null);

        newsPanel.add(scrollPane1);
        newsPanel.add(scrollPane2);

        // Information Panel
        // title
        infotitle = new JLabel(messages.getString("persInfoWelcome"));
        infotitle.setFont(new Font("Arial", Font.BOLD, 20));
        infotitle.setForeground(Color.BLACK);
        infotitle.setBounds(300, 50, 250, 50);
        infoPanel.add(infotitle);

        // labels
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

        // fields
        tName = new JTextField();
        tName.setBounds(350, 140, 150, 30);
        tSurname = new JTextField();
        tSurname.setBounds(350, 200, 150, 30);
        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
        dBirthDate.setBounds(350, 260, 150, 30);
        comboNationality = new JComboBox<>();
        comboNationality.setBounds(350, 320, 150, 30);
        //loadNationalities();

        tEmail = new JTextField();
        tEmail.setBounds(350, 380, 150, 30);

        infoPanel.add(tName);
        infoPanel.add(tSurname);
        infoPanel.add(dBirthDate);
        infoPanel.add(comboNationality);
        infoPanel.add(tEmail);

        // button
        bEdit = new JButton(messages.getString("editButton"));
        bEdit.setBackground(new Color(204, 153, 255));
        bEdit.setBorder(null);
        bEdit.setBounds(330, 440, 100, 30);
        infoPanel.add(bEdit);

        // Tickets Panel
        // title
        ticketTitle = new JLabel(messages.getString("ticketWelcome"));
        ticketTitle.setFont(new Font("Arial", Font.BOLD, 20));
        ticketTitle.setForeground(Color.BLACK);
        ticketTitle.setBounds(300, 50, 250, 50);
        ticketPanel.add(ticketTitle);

        // UI Components
        lOrigin = new JLabel(messages.getString("origin"));
        lOrigin.setBounds(200, 120, 100, 100);

        lDestination = new JLabel(messages.getString("destination"));
        lDestination.setBounds(500, 120, 100, 100);

        comboOrigin = new JComboBox<>();
        comboOrigin.setBounds(200, 190, 150, 30);

        comboDestination = new JComboBox<>();
        comboDestination.setBounds(500, 190, 150, 30);

        ticketPanel.add(lOrigin);
        ticketPanel.add(lDestination);
        ticketPanel.add(comboOrigin);
        ticketPanel.add(comboDestination);

        // Table
        // Column names
        String[] columnNames = {messages.getString("departuretime"), messages.getString("arrivaltime"), messages.getString("price")};

        // Table model
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        tableJourneys = new JTable(model);

        JTableHeader header = tableJourneys.getTableHeader();
        header.setBackground(new Color(204, 153, 255));
        tablePane = new JScrollPane(tableJourneys);
        tablePane.setBounds(180, 270, 500, 150);

        ticketPanel.add(tablePane);

        // Images
        JLabel imageLabel = loadImage("/images/icon.jpg", 200, 300);
        JLabel imageLabel2 = loadImage("/images/icon.jpg", 200, 300);
        JLabel imageLabel3 = loadImage("/images/imag1.jpg", 140, 100);
        JLabel imageLabel4 = loadImage("/images/imag2.jpg", 140, 100);
        JLabel imageLabel5 = loadImage("/images/icon.jpg", 200, 300);
        JLabel imageLabelU = loadImage("/images/imagUser.jpg", 200, 200);
        JLabel imageLabelDel = loadImage("/images/deleteIcon.png", 100, 100);

        imageLabel.setBounds(590, -70, 200, 300);
        imageLabel2.setBounds(590, -70, 200, 300);
        imageLabel3.setBounds(30, 180, 140, 100);
        imageLabel4.setBounds(30, 340, 140, 100);
        imageLabel5.setBounds(590, -70, 200, 300);
        imageLabelU.setBounds(50, 160, 200, 200);
        imageLabelDel.setBounds(450, 405, 100, 100);

        newsPanel.add(imageLabel);
        newsPanel.add(imageLabel3);
        newsPanel.add(imageLabel4);

        infoPanel.add(imageLabel2);
        infoPanel.add(imageLabelU);
        infoPanel.add(imageLabelDel);

        ticketPanel.add(imageLabel5);

        // Actions
        // Language Actions
        spanish.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLanguage("es");
            }
        });

        english.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changeLanguage("en_US");
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

        // Menu Actions
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

        tickets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getContentPane().removeAll();
                contentPanel.add(ticketPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        out.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                SwingUtilities.invokeLater(() -> {
                    InitialWindow initialWindow = new InitialWindow();
                    initialWindow.setVisible(true);
                });
            }
        });

        // Info window actions
        bEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Function to edit user data
            }
        });

        this.setVisible(true);
    }

    private void changeLanguage(String selectedLocale) {
        Locale locale = new Locale(selectedLocale);
        messages = ResourceBundle.getBundle("multilingual/messages", locale);

        // Update UI components with new language messages
        this.setTitle(messages.getString("windowTitle"));

        // Titles
        titleLabel.setText(messages.getString("welcome"));
        infotitle.setText(messages.getString("persInfoWelcome"));

        // Menu
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

        // Update labels, buttons, etc. with new language messages
        lUserPic.setText(messages.getString("userPic"));
        lName.setText(messages.getString("nameLabel"));
        lSurname.setText(messages.getString("surnameLabel"));
        lBirthDay.setText(messages.getString("birthDateLabel"));
        lNationality.setText(messages.getString("nationalityLabel"));
        lEmail.setText(messages.getString("email"));
        bEdit.setText(messages.getString("editButton"));

        lOrigin.setText(messages.getString("origin"));
        lDestination.setText(messages.getString("destination"));

        // News
        noticia1.setText(messages.getString("new1Content"));
        noticia2.setText(messages.getString("new2Content"));

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    public static void main(String[] args) {
        new MainWindow("en_US");
    }
}