package com.RouteBus.client.gui.admin;

import javax.swing.*;

import com.RouteBus.client.gui.InitialWindow;
import com.RouteBus.client.gui.MultilingualLoadingWindow;

import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class AdministratorWindow extends MultilingualLoadingWindow {

    private JMenuItem homeMenuItem, routeAdminMenuItem, busAdminMenuItem, ticketsAdminMenuItem, stationAdminMenuItem, logoutMenuItem;
    private InitialWindow initialWindow;

    public AdministratorWindow(String languageToLoad, InitialWindow initialWindow) {
        super(languageToLoad);
        this.initialWindow = initialWindow;
        setTitle(messages.getString("adminWindowTitle"));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        initializeMenuItems();
    }

    private void initializeMenuItems() {
        JMenuBar menuBar = getJMenuBar();

        homeMenuItem = new JMenuItem(messages.getString("home"));
        routeAdminMenuItem = new JMenuItem(messages.getString("routeAdmin"));
        busAdminMenuItem = new JMenuItem(messages.getString("busAdmin"));
        ticketsAdminMenuItem = new JMenuItem(messages.getString("ticketsAdmin"));
        stationAdminMenuItem = new JMenuItem(messages.getString("stationAdmin"));
        logoutMenuItem = new JMenuItem(messages.getString("logout"));

        menuBar.add(homeMenuItem);
        menuBar.add(routeAdminMenuItem);
        menuBar.add(busAdminMenuItem);
        menuBar.add(ticketsAdminMenuItem);
        menuBar.add(stationAdminMenuItem);
        menuBar.add(logoutMenuItem);

        homeMenuItem.addActionListener(e -> showPanel("Home"));
        routeAdminMenuItem.addActionListener(e -> showPanel("RouteAdmin"));
        busAdminMenuItem.addActionListener(e -> showPanel("BusAdmin"));
        ticketsAdminMenuItem.addActionListener(e -> showPanel("TicketsAdmin"));
        stationAdminMenuItem.addActionListener(e -> showPanel("StationAdmin"));
        logoutMenuItem.addActionListener(this::logoutActionPerformed);

        applyMenuColors();
    }

    @Override
    protected void loadPanels() {
        mainPanel.add(new HomeAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground, messages), "Home");
        mainPanel.add(new RouteAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground, messages), "RouteAdmin");
        mainPanel.add(new BusesAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground, messages), "BusAdmin");
        mainPanel.add(new TicketsAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground, messages), "TicketsAdmin");
        mainPanel.add(new StationsAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground, messages), "StationAdmin");
        
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
        applyPanelBackgroundColor(activePanel);
    }

    private void applyPanelBackgroundColor(String activePanel) {
        mainPanel.setBackground(colorPrimary);
    }

    private void logoutActionPerformed(ActionEvent evt) {
        this.dispose();
        SwingUtilities.invokeLater(() -> initialWindow.setVisible(true));
    }

    @Override
    protected void updateTexts() {
    	super.updateTexts();
    	
        setTitle(messages.getString("adminWindowTitle"));
        homeMenuItem.setText(messages.getString("home"));
        
        routeAdminMenuItem.setText(messages.getString("routeAdmin"));
        busAdminMenuItem.setText(messages.getString("busAdmin"));
        ticketsAdminMenuItem.setText(messages.getString("ticketsAdmin"));
        stationAdminMenuItem.setText(messages.getString("stationAdmin"));
        logoutMenuItem.setText(messages.getString("logout"));
    }
}
