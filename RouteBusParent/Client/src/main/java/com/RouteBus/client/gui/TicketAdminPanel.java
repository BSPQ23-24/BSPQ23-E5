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
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class TicketAdminPanel extends AdminPanel<TicketDTO> {

    private JTextField idField;
    private JTextField seatNumberField;
    private JTextField priceField;
    private JTextField statusField;
    private JTextField userField;
    private JTextField departureTimeField;
    private JTextField arrivalTimeField;

    public TicketAdminPanel(Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground, ResourceBundle messages) {
        super(colorPrimary, colorSecondary, colorTertiary, colorBackground, messages);
    }

    @Override
    protected JPanel createEditingPanel() {
        JPanel editingPanel = new JPanel(new GridBagLayout());
        editingPanel.setBackground(colorBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel idLabel = new JLabel(messages.getString("idLabel"));
        idLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 0;
        editingPanel.add(idLabel, gbc);

        idField = new JTextField(20);
        idField.setEditable(false);
        gbc.gridx = 1;
        editingPanel.add(idField, gbc);

        JLabel seatNumberLabel = new JLabel(messages.getString("seatNumberLabel"));
        seatNumberLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 1;
        editingPanel.add(seatNumberLabel, gbc);

        seatNumberField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(seatNumberField, gbc);

        JLabel priceLabel = new JLabel(messages.getString("priceLabel"));
        priceLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 2;
        editingPanel.add(priceLabel, gbc);

        priceField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(priceField, gbc);

        JLabel statusLabel = new JLabel(messages.getString("statusLabel"));
        statusLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 3;
        editingPanel.add(statusLabel, gbc);

        statusField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(statusField, gbc);

        JLabel userLabel = new JLabel(messages.getString("userLabel"));
        userLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 4;
        editingPanel.add(userLabel, gbc);

        userField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(userField, gbc);

        JLabel departureTimeLabel = new JLabel(messages.getString("departureTimeLabel"));
        departureTimeLabel.setForeground(colorPrimary);
        gbc.gridx = 0;
        gbc.gridy = 5;
        editingPanel.add(departureTimeLabel, gbc);

        departureTimeField = new JTextField(20);
        gbc.gridx = 1;
        editingPanel.add(departureTimeField, gbc);

        JLabel arrivalTimeLabel = new JLabel(messages.getString("arrivalTimeLabel"));
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

        JButton editButton = new JButton(messages.getString("editButtonTicket"));
        editButton.setBackground(colorSecondary);
        buttonPanel.add(editButton);

        JButton removeButton = new JButton(messages.getString("removeButtonTicket"));
        removeButton.setBackground(colorSecondary);
        buttonPanel.add(removeButton);

        editingPanel.add(buttonPanel, gbc);

        editButton.addActionListener(e -> editEntity());
        removeButton.addActionListener(e -> deleteEntity());

        return editingPanel;
    }

    @Override
    protected JTable createTable() {
        String[] columns = messages.getString("tableColumnsTicket").split(",");
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
                JOptionPane.showMessageDialog(this, messages.getString("ticketUpdatedSuccess"), messages.getString("successTitle"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, messages.getString("ticketUpdatedError"), messages.getString("errorTitle"), JOptionPane.ERROR_MESSAGE);
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
                JOptionPane.showMessageDialog(this, messages.getString("ticketDeletedSuccess"), messages.getString("successTitle"), JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, messages.getString("ticketDeletedError"), messages.getString("errorTitle"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}