package com.RouteBus.client.gui;

import com.RouteBus.client.controller.RouteController;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.util.TableUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class RouteAdminPanel extends AdminPanel<RouteDTO> {

    private JTextField routeIdField;
    private JTextField startPointField;
    private JTextField endPointField;
    private JTextField totalDistanceField;

    public RouteAdminPanel(Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground) {
        super(colorPrimary, colorSecondary, colorTertiary, colorBackground);
    }

    @Override
    protected JPanel createEditingPanel() {
        JPanel editingPanel = new JPanel(new GridBagLayout());
        editingPanel.setBackground(colorBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel routeIdLabel = new JLabel("Route ID:");
        routeIdLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 0;
        editingPanel.add(routeIdLabel, gbc);

        routeIdField = new JTextField(20);
        routeIdField.setEditable(false);
        gbc.gridx = 1;
        editingPanel.add(routeIdField, gbc);

        JLabel startPointLabel = new JLabel("Start Point:");
        startPointLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 1;
        editingPanel.add(startPointLabel, gbc);

        startPointField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(startPointField, gbc);

        JLabel endPointLabel = new JLabel("End Point:");
        endPointLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 2;
        editingPanel.add(endPointLabel, gbc);

        endPointField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(endPointField, gbc);

        JLabel totalDistanceLabel = new JLabel("Total Distance:");
        totalDistanceLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 3;
        editingPanel.add(totalDistanceLabel, gbc);

        totalDistanceField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(totalDistanceField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(colorBackground);
        gbc.gridx = 1;
        gbc.gridy = 4;

        JButton editButton = new JButton("Edit");
        editButton.setBackground(colorSecondary);
        buttonPanel.add(editButton);

        JButton removeButton = new JButton("Remove");
        removeButton.setBackground(colorSecondary);
        buttonPanel.add(removeButton);

        editingPanel.add(buttonPanel, gbc);

        editButton.addActionListener(e -> editEntity());
        removeButton.addActionListener(e -> deleteEntity());

        return editingPanel;
    }

    @Override
    protected JTable createTable() {
        String[] columns = {"Route ID", "Start Point", "End Point", "Total Distance", "Stations", "Buses"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        return TableUtil.createConfiguredTable(tableModel, colorBackground, colorSecondary, Color.WHITE);
    }

    @Override
    protected void loadTableData() {
        List<RouteDTO> routes = RouteController.getInstance().getAllRoutes();
        updateTableModel(routes);
    }

    private void updateTableModel(List<RouteDTO> routes) {
        tableModel.setRowCount(0);
        for (RouteDTO route : routes) {
            String stations = route.getStations().stream()
                    .map(station -> station.getLocation())
                    .collect(Collectors.joining(", "));
            String buses = route.getBuses().stream()
                    .map(bus -> bus.getLicensePlate())
                    .collect(Collectors.joining(", "));
            tableModel.addRow(new Object[]{route.getName(), route.getStartPoint(), route.getEndPoint(), route.getTotalDistance() + " km", stations, buses});
        }
    }

    @Override
    protected void filterEntities(String query) {
        List<RouteDTO> routes = RouteController.getInstance().getAllRoutes().stream()
                .filter(route -> route.getName().toLowerCase().contains(query.toLowerCase()) ||
                        route.getStartPoint().toLowerCase().contains(query.toLowerCase()) ||
                        route.getEndPoint().toLowerCase().contains(query.toLowerCase()) ||
                        route.getStations().stream().anyMatch(station -> station.getLocation().toLowerCase().contains(query.toLowerCase())) ||
                        route.getBuses().stream().anyMatch(bus -> bus.getLicensePlate().toLowerCase().contains(query.toLowerCase())) ||
                        String.valueOf(route.getTotalDistance()).contains(query))
                .collect(Collectors.toList());
        updateTableModel(routes);
    }

    @Override
    protected void populateFieldsFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String routeId = (String) tableModel.getValueAt(selectedRow, 0);
            try {
                selectedEntity = RouteController.getInstance().getRouteById(URLEncoder.encode(routeId, StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }

            if (selectedEntity != null) {
                routeIdField.setText(selectedEntity.getName());
                startPointField.setText(selectedEntity.getStartPoint());
                endPointField.setText(selectedEntity.getEndPoint());
                totalDistanceField.setText(String.valueOf(selectedEntity.getTotalDistance()));
            } else {
                clearFields();
            }
        }
    }

    @Override
    protected void clearFields() {
        routeIdField.setText("");
        startPointField.setText("");
        endPointField.setText("");
        totalDistanceField.setText("");
    }

    @Override
    protected void editEntity() {
        if (selectedEntity != null) {
            selectedEntity.setStartPoint(startPointField.getText());
            selectedEntity.setEndPoint(endPointField.getText());
            selectedEntity.setTotalDistance(Double.parseDouble(totalDistanceField.getText()));

            boolean success = RouteController.getInstance().updateRoute(selectedEntity);
            if (success) {
                loadTableData();
                JOptionPane.showMessageDialog(this, "Route updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update route", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteEntity() {
        if (selectedEntity != null) {
            boolean success = RouteController.getInstance().deleteRoute(selectedEntity.getName());
            if (success) {
                selectedEntity = null;
                loadTableData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Route deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete route", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
