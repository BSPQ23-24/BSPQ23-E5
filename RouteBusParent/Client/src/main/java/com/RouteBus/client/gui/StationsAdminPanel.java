package com.RouteBus.client.gui;

import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.util.TableUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class StationsAdminPanel extends AdminPanel<StationDTO> {

    private JTextField nameField;
    private JTextField locationField;

    public StationsAdminPanel(Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground, ResourceBundle messages) {
        super(colorPrimary, colorSecondary, colorTertiary, colorBackground, messages);
    }

    @Override
    protected JPanel createEditingPanel() {
        JPanel editingPanel = new JPanel(new GridBagLayout());
        editingPanel.setBackground(colorBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel(messages.getString("nameLabelStation"));
        nameLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 0;
        editingPanel.add(nameLabel, gbc);

        nameField = new JTextField(20);
        nameField.setEditable(false);
        gbc.gridx = 1;
        editingPanel.add(nameField, gbc);

        JLabel locationLabel = new JLabel(messages.getString("locationLabel"));
        locationLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 1;
        editingPanel.add(locationLabel, gbc);

        locationField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(locationField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 0));
        buttonPanel.setBackground(colorBackground);
        gbc.gridx = 1;
        gbc.gridy = 2;

        JButton editButton = new JButton(messages.getString("editButtonStation"));
        editButton.setBackground(colorSecondary);
        buttonPanel.add(editButton);

        editingPanel.add(buttonPanel, gbc);

        editButton.addActionListener(e -> editEntity());

        return editingPanel;
    }

    @Override
    protected JTable createTable() {
        String[] columns = messages.getString("tableColumnsStation").split(",");
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
        List<StationDTO> stations = StationController.getInstance().getAllStations();
        updateTableModel(stations);
    }

    private void updateTableModel(List<StationDTO> stations) {
        tableModel.setRowCount(0);
        for (StationDTO station : stations) {
            tableModel.addRow(new Object[]{station.getName(), station.getLocation()});
        }
    }

    @Override
    protected void filterEntities(String query) {
        List<StationDTO> stations = StationController.getInstance().getAllStations().stream()
                .filter(station -> station.getName().toLowerCase().contains(query.toLowerCase()) ||
                        station.getLocation().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
        updateTableModel(stations);
    }

    @Override
    protected void populateFieldsFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String name = (String) tableModel.getValueAt(selectedRow, 0);
            try {
                selectedEntity = StationController.getInstance().getStationById(URLEncoder.encode(name, StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }

            if (selectedEntity != null) {
                nameField.setText(selectedEntity.getName());
                locationField.setText(selectedEntity.getLocation());
            } else {
                clearFields();
            }
        }
    }

    @Override
    protected void clearFields() {
        nameField.setText("");
        locationField.setText("");
    }

    @Override
    protected void editEntity() {
        if (selectedEntity != null) {
            selectedEntity.setLocation(locationField.getText());

            boolean success = StationController.getInstance().updateStation(selectedEntity);
            if (success) {
                loadTableData();
                JOptionPane.showMessageDialog(this, messages.getString("stationUpdatedSuccess"), messages.getString("successTitle"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, messages.getString("stationUpdatedError"), messages.getString("errorTitle"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteEntity() {
    }
}
