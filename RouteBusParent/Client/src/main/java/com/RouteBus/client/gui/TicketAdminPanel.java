package com.RouteBus.client.gui;

import com.RouteBus.client.controller.TicketController;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.util.TableUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.List;

@SuppressWarnings("serial")
public class TicketAdminPanel extends AdminPanel<TicketDTO> {

    private JTextField idField;
    private JTextField seatNumberField;
    private JTextField priceField;
    private JTextField statusField;
    private JTextField userField;
    private JTextField departureTimeField;
    private JTextField arrivalTimeField;

    public TicketAdminPanel(Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground) {
        super(colorPrimary, colorSecondary, colorTertiary, colorBackground);
    }

    @Override
    protected JPanel createEditingPanel() {
        JPanel editingPanel = new JPanel(new GridBagLayout());
        editingPanel.setBackground(colorBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel("Ticket ID:");
        idLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 0;
        editingPanel.add(idLabel, gbc);

        idField = new JTextField(20);
        idField.setEditable(false);
        gbc.gridx = 1;
        editingPanel.add(idField, gbc);

        JLabel seatNumberLabel = new JLabel("Seat Number:");
        seatNumberLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 1;
        editingPanel.add(seatNumberLabel, gbc);

        seatNumberField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(seatNumberField, gbc);

        JLabel priceLabel = new JLabel("Price:");
        priceLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 2;
        editingPanel.add(priceLabel, gbc);

        priceField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(priceField, gbc);

        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 3;
        editingPanel.add(statusLabel, gbc);

        statusField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(statusField, gbc);

        JLabel userLabel = new JLabel("User:");
        userLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 4;
        editingPanel.add(userLabel, gbc);

        userField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(userField, gbc);

        JLabel departureTimeLabel = new JLabel("Departure Time:");
        departureTimeLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 5;
        editingPanel.add(departureTimeLabel, gbc);

        departureTimeField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(departureTimeField, gbc);

        JLabel arrivalTimeLabel = new JLabel("Arrival Time:");
        arrivalTimeLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 6;
        editingPanel.add(arrivalTimeLabel, gbc);

        arrivalTimeField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(arrivalTimeField, gbc);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setBackground(colorBackground);
        gbc.gridx = 1;
        gbc.gridy = 7;

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
        String[] columns = {"Ticket ID", "Seat Number", "Price", "Status", "User", "Departure Time", "Arrival Time"};
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
        List<TicketDTO> tickets = TicketController.getInstance().getAllTickets();
        updateTableModel(tickets);
    }

    private void updateTableModel(List<TicketDTO> tickets) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tableModel.setRowCount(0);
        if (tickets != null) {
            for (TicketDTO ticket : tickets) {
                tableModel.addRow(new Object[]{
                        ticket.getId(),
                        ticket.getSeatNumber(),
                        ticket.getPrice(),
                        ticket.getStatus(),
                        ticket.getUser().getFirstName(),
                        sdf.format(ticket.getSchedule().getDepartureTime()),
                        sdf.format(ticket.getSchedule().getArrivalTime())
                });
            }
        }
    }

    @Override
    protected void filterEntities(String query) {
        List<TicketDTO> tickets = TicketController.getInstance().filterTickets(query);
        updateTableModel(tickets);
    }

    @Override
    protected void populateFieldsFromSelectedRow() {
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            String id = (String) tableModel.getValueAt(selectedRow, 0);
            try {
                selectedEntity = TicketController.getInstance().getTicketById(URLEncoder.encode(id, StandardCharsets.UTF_8.toString()));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return;
            }

            if (selectedEntity != null) {
                idField.setText(selectedEntity.getId());
                seatNumberField.setText(String.valueOf(selectedEntity.getSeatNumber()));
                priceField.setText(String.valueOf(selectedEntity.getPrice()));
                statusField.setText(selectedEntity.getStatus().toString());
                userField.setText(selectedEntity.getUser().getFirstName());
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                departureTimeField.setText(sdf.format(selectedEntity.getSchedule().getDepartureTime()));
                arrivalTimeField.setText(sdf.format(selectedEntity.getSchedule().getArrivalTime()));
            } else {
                clearFields();
            }
        }
    }

    @Override
    protected void clearFields() {
        idField.setText("");
        seatNumberField.setText("");
        priceField.setText("");
        statusField.setText("");
        userField.setText("");
        departureTimeField.setText("");
        arrivalTimeField.setText("");
    }

    @Override
    protected void editEntity() {
        if (selectedEntity != null) {
            selectedEntity.setSeatNumber(Integer.parseInt(seatNumberField.getText()));
            selectedEntity.setPrice(Double.parseDouble(priceField.getText()));
            selectedEntity.setStatus(TicketDTO.TicketStatus.valueOf(statusField.getText().toUpperCase()));

            boolean success = TicketController.getInstance().updateTicket(selectedEntity);
            if (success) {
                loadTableData();
                JOptionPane.showMessageDialog(this, "Ticket updated successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update ticket", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    protected void deleteEntity() {
        if (selectedEntity != null) {
            boolean success = TicketController.getInstance().deleteTicket(selectedEntity.getId());
            if (success) {
                selectedEntity = null;
                loadTableData();
                clearFields();
                JOptionPane.showMessageDialog(this, "Ticket deleted successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete ticket", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
