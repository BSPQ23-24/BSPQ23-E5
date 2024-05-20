package com.RouteBus.client.gui;

import javax.swing.*;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.controller.TicketController;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class TicketPanel extends JPanel {
    private JLabel ticketTitle;
    private JLabel lOrigin;
    private JLabel lDestination;
    private JComboBox<String> comboOrigin;
    private JComboBox<String> comboDestination;
    private JTable tableJourneys;
    private UserDTO user;

    public TicketPanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorSecondary) {
        this.user = user;
        this.setLayout(null);
        this.setBackground(Color.WHITE);

        ticketTitle = new JLabel(messages.getString("ticketWelcome"));
        ticketTitle.setFont(new Font("Arial", Font.BOLD, 24));
        ticketTitle.setForeground(Color.BLACK);
        ticketTitle.setBounds(300, 50, 250, 50);
        this.add(ticketTitle);

        lOrigin = new JLabel(messages.getString("origin"));
        lOrigin.setBounds(200, 120, 100, 100);
        lOrigin.setFont(new Font("Arial", Font.BOLD, 18));

        lDestination = new JLabel(messages.getString("destination"));
        lDestination.setBounds(500, 120, 100, 100);
        lDestination.setFont(new Font("Arial", Font.BOLD, 18));

        comboOrigin = new JComboBox<>();
        comboOrigin.setBounds(200, 190, 200, 30);

        comboDestination = new JComboBox<>();
        comboDestination.setBounds(500, 190, 200, 30);
        loadStations();

        this.add(lOrigin);
        this.add(lDestination);
        this.add(comboOrigin);
        this.add(comboDestination);

        String[] columnNames = {messages.getString("departuretime"), messages.getString("arrivaltime"), messages.getString("price")};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tableJourneys = new JTable(model);

        JTableHeader header = tableJourneys.getTableHeader();
        header.setBackground(colorSecondary);
        JScrollPane tablePane = new JScrollPane(tableJourneys);
        tablePane.setBounds(180, 270, 600, 150);
        comboOrigin.addActionListener(e -> loadTicketsData((DefaultTableModel) tableJourneys.getModel()));
        comboDestination.addActionListener(e -> loadTicketsData((DefaultTableModel) tableJourneys.getModel()));
        this.add(tablePane);
        setupComboBoxes();
        loadTicketsData(model);
    }

    private void loadStations() {
        List<StationDTO> stations = StationController.getInstance().getAllStations();
        for (StationDTO station : stations) {
            comboDestination.addItem(station.getName());
            comboOrigin.addItem(station.getName());
        }
        comboDestination.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof StationDTO) {
                    setText(((StationDTO) value).getName());
                }
                return this;
            }
        });
        comboOrigin.setRenderer(new DefaultListCellRenderer() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof StationDTO) {
                    setText(((StationDTO) value).getName());
                }
                return this;
            }
        });
    }

    private void setupComboBoxes() {
        List<StationDTO> stations = StationController.getInstance().getAllStations();
        for (StationDTO station : stations) {
            comboOrigin.addItem(station.getName());
            comboDestination.addItem(station.getName());
        }

        comboOrigin.addActionListener(e -> loadTicketsData((DefaultTableModel) tableJourneys.getModel()));
        comboDestination.addActionListener(e -> loadTicketsData((DefaultTableModel) tableJourneys.getModel()));
    }

    private void loadTicketsData(DefaultTableModel model) {
        model.setRowCount(0);

        List<TicketDTO> tickets = TicketController.getInstance().getTicketByUser(user.getEmail());

        StationDTO selectedOrigin = StationController.getInstance().getStationById((String) comboOrigin.getSelectedItem());
        StationDTO selectedDestination = StationController.getInstance().getStationById((String) comboDestination.getSelectedItem());

        if (selectedOrigin == null || selectedDestination == null) {
            return;
        }

        for (TicketDTO ticket : tickets) {
            RouteDTO route = ticket.getSchedule().getRoute();
            if (route.getStations().contains(selectedOrigin) && route.getStations().contains(selectedDestination)) {
                Object[] rowData = {
                    ticket.getSchedule().getDepartureTime(),
                    ticket.getSchedule().getArrivalTime(),
                    ticket.getPrice()
                };
                model.addRow(rowData);
            }
        }
    }

    public void updateTexts(ResourceBundle messages) {
        ticketTitle.setText(messages.getString("ticketWelcome"));
        lOrigin.setText(messages.getString("origin"));
        lDestination.setText(messages.getString("destination"));

        String[] columnNames = {messages.getString("departuretime"), messages.getString("arrivaltime"), messages.getString("price")};
        DefaultTableModel model = (DefaultTableModel) tableJourneys.getModel();
        model.setColumnIdentifiers(columnNames);
    }
}
