package com.RouteBus.client.gui.user;

import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.gui.InitialWindow;
import com.RouteBus.client.gui.MultilingualLoadingWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@SuppressWarnings("serial")
public class MainWindow extends MultilingualLoadingWindow {

    private JPanel leftPanel;
    private NewsPanel newsPanel;
    private MyInfoPanel infoPanel;
    private MyTicketsPanel ticketPanel;
    private PurchaseTicketsPanel purchaseTicketsPanel;

    private JMenuItem busInfoMenuItem;
    private JMenuItem informationMenuItem;
    private JMenuItem ticketsMenuItem;
    private JMenuItem buyTicketsMenuItem;
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
        ticketsMenuItem = new JMenuItem(messages.getString("myBusTicketsMenu"));
        buyTicketsMenuItem = new JMenuItem(messages.getString("buyTickets"));
        logoutMenuItem = new JMenuItem(messages.getString("Logout"));

        menuBar.add(busInfoMenuItem);
        menuBar.add(informationMenuItem);
        menuBar.add(ticketsMenuItem);
        menuBar.add(buyTicketsMenuItem);
        menuBar.add(logoutMenuItem);

        busInfoMenuItem.addActionListener(e -> showPanel("newsPanel"));
        informationMenuItem.addActionListener(e -> showPanel("infoPanel"));
        ticketsMenuItem.addActionListener(e -> showPanel("ticketPanel"));
        buyTicketsMenuItem.addActionListener(e -> showPanel("ticketPurchasePanel"));
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

        newsPanel = new NewsPanel(messages, colorPrimary, colorSecondary, colorTertiary, colorBackground);
        infoPanel = new MyInfoPanel(messages, user, colorBackground, colorPrimary);
        ticketPanel = new MyTicketsPanel(messages, user, colorBackground, colorPrimary);
        purchaseTicketsPanel = new PurchaseTicketsPanel(messages, user, colorBackground, colorPrimary, colorSecondary);

        leftPanel.add(newsPanel, "newsPanel");
        leftPanel.add(infoPanel, "infoPanel");
        leftPanel.add(ticketPanel, "ticketPanel");
        leftPanel.add(purchaseTicketsPanel, "ticketPurchasePanel");

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
        ticketsMenuItem.setBackground(activePanel.equals("ticketPanel") ? colorPrimary : colorSecondary);
        buyTicketsMenuItem.setBackground(activePanel.equals("ticketPurchasePanel") ? colorPrimary : colorSecondary);
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
    	super.updateTexts();
    	
        setTitle(messages.getString("windowTitle"));

        newsPanel.updateTexts(messages);
        infoPanel.updateTexts(messages);
        ticketPanel.updateTexts(messages);
        purchaseTicketsPanel.updateTexts(messages);

        busInfoMenuItem.setText(messages.getString("BusRouteInfo"));
        informationMenuItem.setText(messages.getString("myInformationMenu"));
        ticketsMenuItem.setText(messages.getString("myBusTicketsMenu"));
        buyTicketsMenuItem.setText(messages.getString("buyTickets"));
        logoutMenuItem.setText(messages.getString("Logout"));
    }
}
