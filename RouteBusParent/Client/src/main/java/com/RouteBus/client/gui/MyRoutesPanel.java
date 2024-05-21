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
public class MyRoutesPanel extends JPanel {
    private JTable routesTable;
    private UserDTO user;
    private JLabel routesLabel;
    private JTextField routeNameField;
    private JTextField startPointField;
    private JTextField endPointField;
    private JTextField totalDistanceField;
    private JTextArea stationsArea;
    private JTextArea busesArea;
    private JTextField totalPassengersField;
    private Color colorBackground;

    public MyRoutesPanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorPrimary, Color colorSecondary, Color colorTertiary) {
        this.user = user;
        this.setLayout(new BorderLayout());
        this.setBackground(colorBackground);
        this.colorBackground = colorBackground;

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(colorBackground);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        routesLabel = new JLabel(messages.getString("routesLabel"));
        routesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        routesLabel.setForeground(colorPrimary);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        headerPanel.add(routesLabel, gbc);

        this.add(headerPanel, BorderLayout.NORTH);

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

        JTableHeader header = routesTable.getTableHeader();
        header.setBackground(colorPrimary);
        header.setForeground(Color.WHITE);
        routesTable.setBackground(colorBackground);
        routesTable.setForeground(Color.BLACK);
        routesTable.setSelectionBackground(colorSecondary);
        routesTable.setSelectionForeground(Color.WHITE);

        JScrollPane tablePane = new JScrollPane(routesTable);
        tablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(tablePane, BorderLayout.CENTER);

        JPanel detailsPanel = new JPanel(new GridBagLayout());
        detailsPanel.setBackground(colorBackground);
        detailsPanel.setBorder(BorderFactory.createLineBorder(colorTertiary, 1));
        detailsPanel.setPreferredSize(new Dimension(300, 400));
        this.add(detailsPanel, BorderLayout.EAST);

        GridBagConstraints detailsGbc = new GridBagConstraints();
        detailsGbc.insets = new Insets(10, 10, 10, 10);
        detailsGbc.fill = GridBagConstraints.HORIZONTAL;
        detailsGbc.gridx = 0;
        detailsGbc.gridy = GridBagConstraints.RELATIVE;
        detailsGbc.anchor = GridBagConstraints.NORTH;

        JLabel detailsLabel = new JLabel(messages.getString("routeDetails"));
        detailsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        detailsLabel.setHorizontalAlignment(SwingConstants.CENTER);
        detailsLabel.setForeground(colorPrimary);
        detailsGbc.gridwidth = 2;
        detailsPanel.add(detailsLabel, detailsGbc);
        detailsGbc.gridwidth = 1;

        routeNameField = createNonEditableTextField();
        startPointField = createNonEditableTextField();
        endPointField = createNonEditableTextField();
        totalDistanceField = createNonEditableTextField();
        stationsArea = createNonEditableTextArea();
        busesArea = createNonEditableTextArea();
        totalPassengersField = createNonEditableTextField();

        addDetailField(detailsPanel, messages.getString("routeName"), routeNameField, detailsGbc);
        addDetailField(detailsPanel, messages.getString("startPoint"), startPointField, detailsGbc);
        addDetailField(detailsPanel, messages.getString("endPoint"), endPointField, detailsGbc);
        addDetailField(detailsPanel, messages.getString("totalDistance"), totalDistanceField, detailsGbc);
        addDetailArea(detailsPanel, messages.getString("stations"), stationsArea, detailsGbc);
        addDetailArea(detailsPanel, messages.getString("buses"), busesArea, detailsGbc);
        addDetailField(detailsPanel, messages.getString("totalPassengers"), totalPassengersField, detailsGbc);

        routesTable.getSelectionModel().addListSelectionListener(e -> {
            int selectedRow = routesTable.getSelectedRow();
            if (selectedRow >= 0) {
                String routeName = (String) routesTable.getValueAt(selectedRow, 0);
                RouteDTO selectedRoute = RouteController.getInstance().getRouteById(routeName);
                if (selectedRoute != null) {
                    updateRouteDetails(selectedRoute, messages);
                }
            }
        });

        loadRoutesData(model);
    }

    private JTextField createNonEditableTextField() {
        JTextField textField = new JTextField();
        textField.setEditable(false);
        textField.setBackground(colorBackground);
        textField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textField;
    }

    private JTextArea createNonEditableTextArea() {
        JTextArea textArea = new JTextArea(3, 20);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setBackground(colorBackground);
        textArea.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        return textArea;
    }

    private void addDetailField(JPanel panel, String label, JTextField field, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private void addDetailArea(JPanel panel, String label, JTextArea area, GridBagConstraints gbc) {
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        panel.add(new JLabel(label), gbc);
        gbc.gridx = 1;
        panel.add(new JScrollPane(area), gbc);
    }

    private void updateRouteDetails(RouteDTO route, ResourceBundle messages) {
        routeNameField.setText(route.getName());
        startPointField.setText(route.getStartPoint());
        endPointField.setText(route.getEndPoint());
        totalDistanceField.setText(String.valueOf(route.getTotalDistance()));

        StringBuilder stations = new StringBuilder();
        for (StationDTO station : route.getStations()) {
            stations.append(station.getName()).append(", ");
        }
        stationsArea.setText(stations.toString());

        StringBuilder buses = new StringBuilder();
        for (BusDTO bus : route.getBuses()) {
            buses.append(bus.getLicensePlate()).append(", ");
        }
        busesArea.setText(buses.toString());

        totalPassengersField.setText(String.valueOf(route.getTotalPassengers()));
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
