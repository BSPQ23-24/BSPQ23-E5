package com.RouteBus.client.gui;

import com.RouteBus.client.controller.UserController;
import com.RouteBus.client.dto.UserDTO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainWindow extends MultilingualLoadingWindow {

    private JPanel leftPanel;
    private NewsPanel newsPanel;
    private InfoPanel infoPanel;
    private TicketPanel ticketPanel;
    private RoutesPanel routesPanel;

    private JMenuItem busInfoMenuItem;
    private JMenuItem informationMenuItem;
    private JMenuItem routesMenuItem;
    private JMenuItem ticketsMenuItem;
    private JMenuItem logoutMenuItem;

    private UserDTO user;

    public MainWindow(String languageToLoad, UserDTO user) {
        super(languageToLoad);
        this.setTitle(messages.getString("windowTitle"));
        this.user = user;
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        JMenuBar menuBar = getJMenuBar();

        busInfoMenuItem = new JMenuItem(messages.getString("BusRouteInfo"));
        informationMenuItem = new JMenuItem(messages.getString("myInformationMenu"));
        routesMenuItem = new JMenuItem(messages.getString("myRoutesMenu"));
        ticketsMenuItem = new JMenuItem(messages.getString("myBusTicketsMenu"));
        logoutMenuItem = new JMenuItem(messages.getString("Logout"));

        menuBar.add(busInfoMenuItem);
        menuBar.add(informationMenuItem);
        menuBar.add(routesMenuItem);
        menuBar.add(ticketsMenuItem);
        menuBar.add(logoutMenuItem);

        busInfoMenuItem.addActionListener(e -> showPanel("newsPanel"));
        informationMenuItem.addActionListener(e -> showPanel("infoPanel"));
        routesMenuItem.addActionListener(e -> showPanel("routesPanel"));
        ticketsMenuItem.addActionListener(e -> showPanel("ticketPanel"));
        logoutMenuItem.addActionListener(this::logoutActionPerformed);

        applyMenuColors();
    }

    @Override
    protected void loadPanels() {
        JPanel mainPanelStructure = new JPanel(new BorderLayout());
        mainPanelStructure.setBackground(colorBackground);
        mainPanel.add(mainPanelStructure, BorderLayout.CENTER);

        leftPanel = new JPanel(new CardLayout());
        leftPanel.setBorder(BorderFactory.createLineBorder(colorSecondary, 2));
        leftPanel.setPreferredSize(new Dimension(800, 0));
        mainPanelStructure.add(leftPanel, BorderLayout.CENTER);

        JPanel rightPanel = new JPanel(new GridBagLayout());
        rightPanel.setBackground(colorBackground);
        mainPanelStructure.add(rightPanel, BorderLayout.EAST);

        newsPanel = new NewsPanel(messages, colorPrimary, colorSecondary, colorSecondary, colorBackground);
        infoPanel = new InfoPanel(messages, user, colorBackground, colorSecondary);
        ticketPanel = new TicketPanel(messages, user, colorBackground, colorSecondary);
        routesPanel = new RoutesPanel(messages, user, colorBackground);

        leftPanel.add(newsPanel, "newsPanel");
        leftPanel.add(infoPanel, "infoPanel");
        leftPanel.add(routesPanel, "routesPanel");
        leftPanel.add(ticketPanel, "ticketPanel");

        JLabel imageLabel = loadImage("/images/busroute.jpg", 600, 800);
        rightPanel.add(imageLabel, new GridBagConstraints());

        showPanel("newsPanel");
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

    @Override
    protected void updateTexts() {
        this.setTitle(messages.getString("windowTitle"));

        newsPanel.updateTexts(messages);
        infoPanel.updateTexts(messages);
        ticketPanel.updateTexts(messages);
        routesPanel.updateTexts(messages);

        busInfoMenuItem.setText(messages.getString("BusRouteInfo"));
        informationMenuItem.setText(messages.getString("myInformationMenu"));
        routesMenuItem.setText(messages.getString("myRoutesMenu"));
        ticketsMenuItem.setText(messages.getString("myBusTicketsMenu"));
        logoutMenuItem.setText(messages.getString("Logout"));
    }

    public static void main(String[] args) {
        UserDTO user = UserController.getInstance().getUserByEmail("xabi.alonso@gmail.com");
        MainWindow v = new MainWindow("es", user);
        v.setVisible(true);
    }
}
