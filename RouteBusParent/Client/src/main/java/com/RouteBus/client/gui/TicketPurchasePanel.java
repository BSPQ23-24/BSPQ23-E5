package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import com.RouteBus.client.controller.RouteController;
import com.RouteBus.client.controller.TicketController;
import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.UserDTO;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class TicketPurchasePanel extends JPanel {
    private JTable routesTable;
    private JComboBox<String> comboOrigin;
    private JComboBox<String> comboDestination;
    private JButton buyButton;
    private UserDTO user;

    public TicketPurchasePanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorPrimary, Color colorSecondary) {
        this.user = user;
        this.setLayout(new BorderLayout());
        this.setBackground(colorBackground);

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(colorBackground);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel routesLabel = new JLabel(messages.getString("availableRoutes"));
        routesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        routesLabel.setForeground(colorPrimary);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        headerPanel.add(routesLabel, gbc);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(colorBackground);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gbc.gridwidth = 1;

        JLabel lOrigin = new JLabel(messages.getString("origin"));
        lOrigin.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(lOrigin, gbc);

        comboOrigin = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        filterPanel.add(comboOrigin, gbc);

        JLabel lDestination = new JLabel(messages.getString("destination"));
        lDestination.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        filterPanel.add(lDestination, gbc);

        comboDestination = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        filterPanel.add(comboDestination, gbc);

        this.add(filterPanel, BorderLayout.WEST);

        String[] columnNames = {messages.getString("routeName"), messages.getString("startPoint"),
                messages.getString("endPoint"), messages.getString("totalDistance")};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        routesTable = new JTable(model);
        routesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane tablePane = new JScrollPane(routesTable);
        tablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(tablePane, BorderLayout.CENTER);

        buyButton = new JButton(messages.getString("buyTicket"));
        buyButton.setBackground(colorSecondary);
        this.add(buyButton, BorderLayout.SOUTH);

        comboOrigin.addActionListener(e -> loadRoutesData((DefaultTableModel) routesTable.getModel()));
        comboDestination.addActionListener(e -> loadRoutesData((DefaultTableModel) routesTable.getModel()));
        buyButton.addActionListener(this::performPurchase);

        setupComboBoxes();
        loadRoutesData(model);
    }

    private void setupComboBoxes() {
        List<StationDTO> stations = StationController.getInstance().getAllStations();
        for (StationDTO station : stations) {
            comboDestination.addItem(station.getName());
            comboOrigin.addItem(station.getName());
        }
    }

    private void loadRoutesData(DefaultTableModel model) {
        model.setRowCount(0);

        List<RouteDTO> routes = RouteController.getInstance().getAllRoutes();

        String selectedOrigin = (String) comboOrigin.getSelectedItem();
        String selectedDestination = (String) comboDestination.getSelectedItem();

        List<TicketDTO> userTickets = TicketController.getInstance().getTicketByUser(user.getEmail());
        List<RouteDTO> userRoutes = userTickets.stream()
                .map(ticket -> ticket.getSchedule().getRoute())
                .collect(Collectors.toList());

        routes = routes.stream()
                .filter(route -> !userRoutes.contains(route))
                .filter(route -> selectedOrigin == null || route.getStartPoint().equals(selectedOrigin))
                .filter(route -> selectedDestination == null || route.getEndPoint().equals(selectedDestination))
                .collect(Collectors.toList());

        for (RouteDTO route : routes) {
            model.addRow(new Object[]{route.getName(), route.getStartPoint(), route.getEndPoint(), route.getTotalDistance()});
        }
    }

    private void performPurchase(ActionEvent e) {
        int selectedRow = routesTable.getSelectedRow();
        if (selectedRow >= 0) {
            String routeName = (String) routesTable.getValueAt(selectedRow, 0);
            RouteDTO selectedRoute = RouteController.getInstance().getRouteById(routeName);
            if (selectedRoute != null) {
                boolean success = TicketController.getInstance().createTicketForUser(user, selectedRoute);
                if (success) {
                    JOptionPane.showMessageDialog(this, "ticketPurchasedSuccess", "successTitle", JOptionPane.INFORMATION_MESSAGE);
                    loadRoutesData((DefaultTableModel) routesTable.getModel());
                } else {
                    JOptionPane.showMessageDialog(this, "ticketPurchasedError", "errorTitle", JOptionPane.ERROR_MESSAGE);
                }
            }
        }
    }
}
