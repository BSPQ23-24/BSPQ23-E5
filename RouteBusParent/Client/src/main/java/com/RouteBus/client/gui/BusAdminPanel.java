package com.RouteBus.client.gui;

import com.RouteBus.client.controller.BusController;
import com.RouteBus.client.dto.BusDTO;
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
public class BusAdminPanel extends AdminPanel<BusDTO> {

    private JTextField licensePlateField;
    private JTextField modelField;
    private JTextField capacityField;

    public BusAdminPanel(Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground) {
        super(colorPrimary, colorSecondary, colorTertiary, colorBackground);
    }

    @Override
    protected JPanel createEditingPanel() {
        JPanel editingPanel = new JPanel(new GridBagLayout());
        editingPanel.setBackground(colorBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel licensePlateLabel = new JLabel("License Plate:");
        licensePlateLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 0;
        editingPanel.add(licensePlateLabel, gbc);

        licensePlateField = new JTextField(20);
        licensePlateField.setEditable(false);
        gbc.gridx = 1;
        editingPanel.add(licensePlateField, gbc);

        JLabel modelLabel = new JLabel("Model:");
        modelLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 1;
        editingPanel.add(modelLabel, gbc);

        modelField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(modelField, gbc);

        JLabel capacityLabel = new JLabel("Capacity:");
        capacityLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 2;
        editingPanel.add(capacityLabel, gbc);

        capacityField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(capacityField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 1, 10, 0));
        buttonPanel.setBackground(colorBackground);
        gbc.gridx = 1;
        gbc.gridy = 3;

        JButton editButton = new JButton("Edit");
        editButton.setBackground(colorSecondary);
        buttonPanel.add(editButton);

        editingPanel.add(buttonPanel, gbc);

        editButton.addActionListener(e -> editEntity());

        return editingPanel;
    }

    @Override
    protected JTable createTable() {
        String[] columns = {"License Plate", "Model", "Capacity"};
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
        List<BusDTO> buses = BusController.getInstance().getAllBuses();
        updateTableModel(buses);
    }

    private void updateTableModel(List<BusDTO> buses) {
        tableModel.setRowCount(0);
        for (BusDTO bus : buses) {
            tableModel.addRow(new Object[]{bus.getLicensePlate(), bus.getModel(), bus.getCapacity()});
        }
    }

    @Override
    protected void filterEntities(String query) {
        List<BusDTO> buses = BusController.getInstance().getAllBuses().stream()
                .filter(bus -> bus.getLicensePlate().toLowerCase().contains(query.toLowerCase()) ||
                        bus.getModel().toLowerCase().contains(query.toLowerCase()) ||
                        String.valueOf(bus.getCapacity()).contains(query))
                .collect(Collectors.toList());
        updateTableModel(buses);
    }

    @Override
    protected void populateFieldsFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String licensePlate = (String) tableModel.getValueAt(selectedRow, 0);
            try {
                selectedEntity = BusController.getInstance().getBusById(URLEncoder.encode(licensePlate, StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }

            if (selectedEntity != null) {
                licensePlateField.setText(selectedEntity.getLicensePlate());
                modelField.setText(selectedEntity.getModel());
                capacityField.setText(String.valueOf(selectedEntity.getCapacity()));
            } else {
                clearFields();
            }
        }
    }

    @Override
    protected void clearFields() {
        licensePlateField.setText("");
        modelField.setText("");
        capacityField.setText("");
    }

    @Override
    protected void editEntity() {
        if (selectedEntity != null) {
            selectedEntity.setModel(modelField.getText());
            selectedEntity.setCapacity(Integer.parseInt(capacityField.getText()));

            boolean success = BusController.getInstance().updateBus(selectedEntity);
            if (success) {
                loadTableData();
                JOptionPane.showMessageDialog(this, "Bus updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update bus", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteEntity() {
    }
}
