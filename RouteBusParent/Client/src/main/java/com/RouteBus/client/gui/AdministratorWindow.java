package com.RouteBus.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdministratorWindow extends ParentWindow {

    private JPanel mainPanel;
    private JMenuItem homeMenuItem, routeAdminMenuItem, busAdminMenuItem, ticketsAdminMenuItem, stationAdminMenuItem, logoutMenuItem;

    public AdministratorWindow() {
        super();
        setTitle("Administration Window");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorSecondary);

        homeMenuItem = new JMenuItem("Home");
        routeAdminMenuItem = new JMenuItem("Route administration");
        busAdminMenuItem = new JMenuItem("Bus administration");
        ticketsAdminMenuItem = new JMenuItem("Tickets administration");
        stationAdminMenuItem = new JMenuItem("Station administration");
        logoutMenuItem = new JMenuItem("Log out");

        homeMenuItem.addActionListener(e -> showPanel("Home"));
        routeAdminMenuItem.addActionListener(e -> showPanel("RouteAdmin"));
        busAdminMenuItem.addActionListener(e -> showPanel("BusAdmin"));
        ticketsAdminMenuItem.addActionListener(e -> showPanel("TicketsAdmin"));
        stationAdminMenuItem.addActionListener(e -> showPanel("StationAdmin"));
        logoutMenuItem.addActionListener(this::logoutActionPerformed);

        menuBar.add(homeMenuItem);
        menuBar.add(routeAdminMenuItem);
        menuBar.add(busAdminMenuItem);
        menuBar.add(ticketsAdminMenuItem);
        menuBar.add(stationAdminMenuItem);
        menuBar.add(logoutMenuItem);
        setJMenuBar(menuBar);

        // Main panel
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(colorBackground);
        add(mainPanel, BorderLayout.CENTER);

        // Add panels
        mainPanel.add(new HomePanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "Home");
        mainPanel.add(new RouteAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "RouteAdmin");
        mainPanel.add(new BusAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "BusAdmin");
        mainPanel.add(new TicketsAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "TicketsAdmin");
        mainPanel.add(new StationAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "StationAdmin");

        // Show initial panel
        showPanel("Home");
    }

    private void showPanel(String panelName) {
        CardLayout cl = (CardLayout) (mainPanel.getLayout());
        cl.show(mainPanel, panelName);
        updateMenuColors(panelName);
    }

    private void updateMenuColors(String activePanel) {
        homeMenuItem.setBackground(activePanel.equals("Home") ? colorPrimary : colorSecondary);
        routeAdminMenuItem.setBackground(activePanel.equals("RouteAdmin") ? colorPrimary : colorSecondary);
        busAdminMenuItem.setBackground(activePanel.equals("BusAdmin") ? colorPrimary : colorSecondary);
        ticketsAdminMenuItem.setBackground(activePanel.equals("TicketsAdmin") ? colorPrimary : colorSecondary);
        stationAdminMenuItem.setBackground(activePanel.equals("StationAdmin") ? colorPrimary : colorSecondary);
        logoutMenuItem.setBackground(colorSecondary);
    }

    private void logoutActionPerformed(ActionEvent evt) {
        this.dispose();
        SwingUtilities.invokeLater(() -> {
            LoginWindow loginWindow = new LoginWindow();
            loginWindow.setVisible(true);
        });
    }

}
