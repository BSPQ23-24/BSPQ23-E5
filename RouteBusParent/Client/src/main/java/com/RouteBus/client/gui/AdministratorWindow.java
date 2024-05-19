package com.RouteBus.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.concurrent.ExecutionException;

@SuppressWarnings("serial")
public class AdministratorWindow extends ParentWindow {

    private JPanel mainPanel;
    private JMenuItem homeMenuItem, routeAdminMenuItem, busAdminMenuItem, ticketsAdminMenuItem, stationAdminMenuItem, logoutMenuItem;
    private InitialWindow initialWindow;
    private JProgressBar progressBar;
    private JLabel loadingLabel;

    public AdministratorWindow(InitialWindow initialWindow) {
        super();
        this.initialWindow = initialWindow;
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

        homeMenuItem.setBackground(colorSecondary);
        routeAdminMenuItem.setBackground(colorSecondary);
        busAdminMenuItem.setBackground(colorSecondary);
        ticketsAdminMenuItem.setBackground(colorSecondary);
        stationAdminMenuItem.setBackground(colorSecondary);
        logoutMenuItem.setBackground(colorSecondary);

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

        // Loading panel with progress bar
        JPanel loadingPanel = new JPanel(new BorderLayout());
        loadingLabel = new JLabel("Loading...", SwingConstants.CENTER);
        loadingLabel.setFont(new Font("Arial", Font.BOLD, 30));
        loadingPanel.add(loadingLabel, BorderLayout.NORTH);

        progressBar = new JProgressBar(0, 100);
        progressBar.setIndeterminate(true);
        loadingPanel.add(progressBar, BorderLayout.CENTER);
        add(loadingPanel, BorderLayout.CENTER);

        // Main panel
        mainPanel = new JPanel(new CardLayout());
        mainPanel.setBackground(colorBackground);

        // Load panels asynchronously
        loadPanelsAsync();
    }

    private void loadPanelsAsync() {
        SwingWorker<Void, Integer> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() throws Exception {
                publish(0);
                mainPanel.add(new HomePanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "Home");
                publish(20);
                Thread.sleep(200); // Simulating load time

                mainPanel.add(new RouteAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "RouteAdmin");
                publish(40);
                Thread.sleep(200); // Simulating load time

                mainPanel.add(new BusAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "BusAdmin");
                publish(60);
                Thread.sleep(200); // Simulating load time

                mainPanel.add(new TicketsAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "TicketsAdmin");
                publish(80);
                Thread.sleep(200); // Simulating load time

                mainPanel.add(new StationAdminPanel(colorPrimary, colorSecondary, colorTertiary, colorBackground), "StationAdmin");
                publish(100);
                Thread.sleep(200); // Simulating load time

                return null;
            }

            @Override
            protected void process(List<Integer> chunks) {
                for (int progress : chunks) {
                    progressBar.setValue(progress);
                }
            }

            @Override
            protected void done() {
                try {
                    get();
                    remove(progressBar.getParent());
                    add(mainPanel, BorderLayout.CENTER);
                    showPanel("Home");
                    revalidate();
                    repaint();
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }
        };
        worker.execute();
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
        SwingUtilities.invokeLater(() -> initialWindow.setVisible(true));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            InitialWindow initialWindow = new InitialWindow();
            AdministratorWindow window = new AdministratorWindow(initialWindow);
            initialWindow.setVisible(true);
            window.setVisible(true);
        });
    }
}
