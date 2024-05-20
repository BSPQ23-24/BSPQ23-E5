package com.RouteBus.client.gui;

import javax.swing.*;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.BusDTO;
import com.RouteBus.client.controller.RouteController;
import com.RouteBus.client.controller.TicketController;
import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

@SuppressWarnings("serial")
public class RoutesPanel extends JPanel {
    private JTable routesTable;
    private UserDTO user;
    private JLabel routesLabel;

    public RoutesPanel(ResourceBundle messages, UserDTO user, Color colorBackground) {
        this.user = user;
        this.setLayout(null);
        this.setBackground(colorBackground);

        routesLabel = new JLabel(messages.getString("routesLabel"));
        routesLabel.setFont(new Font("Arial", Font.BOLD, 20));
        routesLabel.setForeground(Color.BLACK);
        routesLabel.setBounds(300, 20, 200, 30);
        this.add(routesLabel);

        String[] columnNames = {messages.getString("routeName"), messages.getString("startPoint"),
                messages.getString("endPoint"), messages.getString("totalDistance")};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        routesTable = new JTable(model);

        JTableHeader header = routesTable.getTableHeader();
        header.setBackground(new Color(204, 153, 255));
        JScrollPane tablePane = new JScrollPane(routesTable);
        tablePane.setBounds(100, 70, 600, 150);
        this.add(tablePane);

        JLabel imageLabel2 = new JLabel(new ImageIcon("images/icon.jpg"));
        imageLabel2.setBounds(750, 70, 100, 100);
        this.add(imageLabel2);

        loadRoutesData(model);

        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BorderLayout());
        detailsPanel.setBackground(Color.LIGHT_GRAY);
        detailsPanel.setBounds(720, 70, 300, 310);

        JLabel detailsLabel = new JLabel("Route Details");
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsPanel.add(detailsLabel, BorderLayout.NORTH);

        JTextArea detailsTextArea = new JTextArea();
        detailsTextArea.setEditable(false);
        detailsTextArea.setFont(new Font("Arial", Font.PLAIN, 14));
        detailsPanel.add(new JScrollPane(detailsTextArea), BorderLayout.CENTER);

        this.add(detailsPanel);
        routesTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = routesTable.getSelectedRow();
            if (selectedRow >= 0) {
                String routeName = (String) routesTable.getValueAt(selectedRow, 0);
                RouteDTO selectedRoute = RouteController.getInstance().getRouteById(routeName);

                StringBuilder details = new StringBuilder();
                details.append("Route Name: ").append(selectedRoute.getName()).append("\n");
                details.append("Start Point: ").append(selectedRoute.getStartPoint()).append("\n");
                details.append("End Point: ").append(selectedRoute.getEndPoint()).append("\n");
                details.append("Total Distance: ").append(selectedRoute.getTotalDistance()).append("\n");
                details.append("Stations: ");
                for (StationDTO station : selectedRoute.getStations()) {
                    details.append(station.getName()).append(", ");
                }
                details.append("\nBuses: ");
                for (BusDTO bus : selectedRoute.getBuses()) {
                    details.append(bus.getLicensePlate()).append(", ");
                }
                details.append("\nTotal Passengers: ").append(selectedRoute.getTotalPassengers()).append("\n");

                detailsTextArea.setText(details.toString());
            }
        });
    }

    private void loadRoutesData(DefaultTableModel model) {
        List<TicketDTO> tickets = TicketController.getInstance().getTicketByUser(user.getEmail());
        Set<RouteDTO> uniqueRoutes = new HashSet<>();
        for (TicketDTO ticket : tickets) {
            RouteDTO route = ticket.getSchedule().getRoute();
            uniqueRoutes.add(route);
        }
        for (RouteDTO route : uniqueRoutes) {
            Object[] rowData = {route.getName(), route.getStartPoint(), route.getEndPoint(), route.getTotalDistance()};
            model.addRow(rowData);
        }
    }

    public void updateTexts(ResourceBundle messages) {
        routesLabel.setText(messages.getString("routesLabel"));

        String[] columnNames = {messages.getString("routeName"), messages.getString("startPoint"),
                messages.getString("endPoint"), messages.getString("totalDistance")};
        DefaultTableModel model = (DefaultTableModel) routesTable.getModel();
        model.setColumnIdentifiers(columnNames);
    }
}
