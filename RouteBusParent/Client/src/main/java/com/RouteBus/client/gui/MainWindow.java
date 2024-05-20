package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.util.List;

import com.RouteBus.client.dto.NationalityDTO;
import com.RouteBus.client.controller.UserController;
import com.toedter.calendar.JDateChooser;

@SuppressWarnings("serial")
public class MainWindow extends ParentWindow {

    // Panels
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel rightPanel;
    private JPanel newsPanel;
    private JPanel infoPanel;
    private JPanel ticketPanel;

    // ScrollPane
    private JScrollPane tablePane;

    // Menu Items
    private JMenuItem busInfoMenuItem;
    private JMenuItem informationMenuItem;
    private JMenuItem routesMenuItem;
    private JMenuItem ticketsMenuItem;
    private JMenu languageMenu;
    private JMenuItem logoutMenuItem;

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
        this.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());

        // Main panel
        mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(colorBackground);
        this.add(mainPanel, BorderLayout.CENTER);

        // Left and right panels
        leftPanel = new JPanel(new CardLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(colorSecondary, 2));
        leftPanel.setPreferredSize(new Dimension(800, 0));
        mainPanel.add(leftPanel, BorderLayout.CENTER);

        rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(colorBackground);
        mainPanel.add(rightPanel, BorderLayout.EAST);

        // Panels
        newsPanel = createNewsPanel();
        infoPanel = createInfoPanel();
        ticketPanel = createTicketPanel();

        // Add panels to left panel
        leftPanel.add(newsPanel, "newsPanel");
        leftPanel.add(infoPanel, "infoPanel");
        leftPanel.add(ticketPanel, "ticketPanel");

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorSecondary);

        busInfoMenuItem = new JMenuItem(messages.getString("BusRouteInfo"));
        informationMenuItem = new JMenuItem(messages.getString("myInformationMenu"));
        routesMenuItem = new JMenuItem(messages.getString("myRoutesMenu"));
        ticketsMenuItem = new JMenuItem(messages.getString("myBusTicketsMenu"));
        languageMenu = new JMenu(messages.getString("languageMenu"));
        logoutMenuItem = new JMenuItem(messages.getString("Logout"));

        busInfoMenuItem.setBackground(colorSecondary);
        informationMenuItem.setBackground(colorSecondary);
        routesMenuItem.setBackground(colorSecondary);
        ticketsMenuItem.setBackground(colorSecondary);
        logoutMenuItem.setBackground(colorSecondary);

        busInfoMenuItem.addActionListener(e -> showPanel("newsPanel"));
        informationMenuItem.addActionListener(e -> showPanel("infoPanel"));
        routesMenuItem.addActionListener(e -> showPanel("routesPanel"));
        ticketsMenuItem.addActionListener(e -> showPanel("ticketPanel"));
        logoutMenuItem.addActionListener(this::logoutActionPerformed);

        menuBar.add(busInfoMenuItem);
        menuBar.add(informationMenuItem);
        menuBar.add(routesMenuItem);
        menuBar.add(ticketsMenuItem);
        menuBar.add(logoutMenuItem);
        menuBar.add(languageMenu);
        setJMenuBar(menuBar);

        // Load languages
        loadLanguages();

        // Add routebus image to the right panel
        JLabel imageLabel = loadImage("/images/busroute.jpg", 600, 800);
        rightPanel.add(imageLabel, new GridBagConstraints());

        // Show the initial panel
        showPanel("newsPanel");
    }

    private void loadLanguages() {
        List<NationalityDTO> nationalities = UserController.getInstance().getNationalities();
        for (NationalityDTO nationality : nationalities) {
            JMenuItem languageItem = new JMenuItem(nationality.getName());
            languageItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    changeLanguage(nationality.getLanguage());
                }
            });
            languageMenu.add(languageItem);
        }
    }

    private JPanel createNewsPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(colorBackground);

        titleLabel = new JLabel(messages.getString("welcome"));
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(titleLabel, BorderLayout.NORTH);

        JPanel newsContentPanel = new JPanel();
        newsContentPanel.setLayout(new BoxLayout(newsContentPanel, BoxLayout.Y_AXIS));
        newsContentPanel.setBackground(colorBackground);

        // Add multiple news items with different background colors and images
        Color[] borderColors = {colorPrimary, colorSecondary, colorTertiary};

        for (int i = 1; i <= 10; i++) {
            JPanel newsItem = new JPanel();
            newsItem.setBorder(BorderFactory.createLineBorder(borderColors[i % borderColors.length], 2));
            newsItem.setBackground(colorBackground);
            newsItem.setLayout(new BorderLayout(10, 10)); // Add horizontal and vertical gaps

            JLabel newsImage = loadImage("/images/InitialImage" + ((i % 7) + 1) + ".jpg", 100, 100);
            newsImage.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10)); // Add space to the right of the image

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBackground(colorBackground);

            JLabel newsTitle = new JLabel(messages.getString("new" + i + "Title"));
            newsTitle.setFont(new Font("Serif", Font.BOLD, 20));
            newsTitle.setAlignmentX(Component.LEFT_ALIGNMENT); // Align title to the left

            JTextArea newsText = new JTextArea(messages.getString("new" + i + "Content"));
            newsText.setEditable(false);
            newsText.setLineWrap(true);
            newsText.setWrapStyleWord(true);
            newsText.setFont(new Font("SansSerif", Font.PLAIN, 16));
            newsText.setBackground(colorBackground);
            newsText.setAlignmentX(Component.LEFT_ALIGNMENT); // Align text to the left

            textPanel.add(newsTitle);
            textPanel.add(Box.createVerticalStrut(10)); // Add vertical space between title and text
            textPanel.add(newsText);

            newsItem.add(newsImage, BorderLayout.WEST);
            newsItem.add(textPanel, BorderLayout.CENTER);

            newsContentPanel.add(newsItem);
            newsContentPanel.add(Box.createVerticalStrut(20)); // Add space between news items
        }

        JScrollPane scrollPane = new JScrollPane(newsContentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Set faster scroll speed
        scrollPane.getVerticalScrollBar().addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                JScrollBar scrollBar = (JScrollBar) e.getSource();
                int units = e.getUnitsToScroll() * scrollBar.getUnitIncrement();
                scrollBar.setValue(scrollBar.getValue() + units);
            }
        });
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        infotitle = new JLabel(messages.getString("persInfoWelcome"));
        infotitle.setFont(new Font("Arial", Font.BOLD, 24));
        infotitle.setForeground(Color.BLACK);
        infotitle.setBounds(50, 50, 300, 50);
        panel.add(infotitle);

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

        panel.add(lUserPic);
        panel.add(lName);
        panel.add(lSurname);
        panel.add(lBirthDay);
        panel.add(lNationality);
        panel.add(lEmail);

        tName = new JTextField();
        tName.setBounds(350, 140, 200, 30);
        tSurname = new JTextField();
        tSurname.setBounds(350, 200, 200, 30);
        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
        dBirthDate.setBounds(350, 260, 200, 30);
        comboNationality = new JComboBox<>();
        comboNationality.setBounds(350, 320, 200, 30);

        tEmail = new JTextField();
        tEmail.setBounds(350, 380, 200, 30);

        panel.add(tName);
        panel.add(tSurname);
        panel.add(dBirthDate);
        panel.add(comboNationality);
        panel.add(tEmail);

        bEdit = new JButton(messages.getString("editButton"));
        bEdit.setBackground(colorSecondary);
        bEdit.setBorder(null);
        bEdit.setBounds(350, 440, 100, 30);
        panel.add(bEdit);

        return panel;
    }

    private JPanel createTicketPanel() {
        JPanel panel = new JPanel(null);
        panel.setBackground(Color.WHITE);

        ticketTitle = new JLabel(messages.getString("ticketWelcome"));
        ticketTitle.setFont(new Font("Arial", Font.BOLD, 24));
        ticketTitle.setForeground(Color.BLACK);
        ticketTitle.setBounds(300, 50, 250, 50);
        panel.add(ticketTitle);

        lOrigin = new JLabel(messages.getString("origin"));
        lOrigin.setBounds(200, 120, 100, 100);
        lOrigin.setFont(new Font("Arial", Font.BOLD, 18));

        lDestination = new JLabel(messages.getString("destination"));
        lDestination.setBounds(500, 120, 100, 100);
        lDestination.setFont(new Font("Arial", Font.BOLD, 18));

        comboOrigin = new JComboBox<>();
        comboOrigin.setBounds(200, 190, 200, 30);

        comboDestination = new JComboBox<>();
        comboDestination.setBounds(500, 190, 200, 30);

        panel.add(lOrigin);
        panel.add(lDestination);
        panel.add(comboOrigin);
        panel.add(comboDestination);

        String[] columnNames = {messages.getString("departuretime"), messages.getString("arrivaltime"), messages.getString("price")};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tableJourneys = new JTable(model);

        JTableHeader header = tableJourneys.getTableHeader();
        header.setBackground(colorSecondary);
        tablePane = new JScrollPane(tableJourneys);
        tablePane.setBounds(180, 270, 600, 150);

        panel.add(tablePane);

        return panel;
    }

    private void showPanel(String panelName) {
        CardLayout cl = (CardLayout) (leftPanel.getLayout());
        cl.show(leftPanel, panelName);
        updateMenuColors(panelName);
    }

    private void updateMenuColors(String activePanel) {
        busInfoMenuItem.setBackground(activePanel.equals("newsPanel") ? colorPrimary : colorSecondary);
        informationMenuItem.setBackground(activePanel.equals("infoPanel") ? colorPrimary : colorSecondary);
        routesMenuItem.setBackground(activePanel.equals("routesPanel") ? colorPrimary : colorSecondary);
        ticketsMenuItem.setBackground(activePanel.equals("ticketPanel") ? colorPrimary : colorSecondary);
        logoutMenuItem.setBackground(colorSecondary);
    }

    private void logoutActionPerformed(ActionEvent evt) {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            InitialWindow initialWindow = new InitialWindow();
            initialWindow.setVisible(true);
        });
    }

    private void changeLanguage(String selectedLocale) {
        Locale locale = new Locale(selectedLocale);
        messages = ResourceBundle.getBundle("multilingual/messages", locale);

        this.setTitle(messages.getString("windowTitle"));

        titleLabel.setText(messages.getString("welcome"));
        infotitle.setText(messages.getString("persInfoWelcome"));

        busInfoMenuItem.setText(messages.getString("BusRouteInfo"));
        informationMenuItem.setText(messages.getString("myInformationMenu"));
        routesMenuItem.setText(messages.getString("myRoutesMenu"));
        ticketsMenuItem.setText(messages.getString("myBusTicketsMenu"));
        languageMenu.setText(messages.getString("languageMenu"));
        logoutMenuItem.setText(messages.getString("Logout"));

        lUserPic.setText(messages.getString("userPic"));
        lName.setText(messages.getString("nameLabel"));
        lSurname.setText(messages.getString("surnameLabel"));
        lBirthDay.setText(messages.getString("birthDateLabel"));
        lNationality.setText(messages.getString("nationalityLabel"));
        lEmail.setText(messages.getString("email"));
        bEdit.setText(messages.getString("editButton"));

        lOrigin.setText(messages.getString("origin"));
        lDestination.setText(messages.getString("destination"));

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}
