package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.ScheduleDTO;
import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.controller.TicketController;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class MyTicketsPanel extends JPanel {
    private JLabel ticketTitle;
    private JLabel lOrigin;
    private JLabel lDestination;
    private JComboBox<String> comboOrigin;
    private JComboBox<String> comboDestination;
    private JTable tableJourneys;
    private JButton buy;
    private UserDTO user;

    public MyTicketsPanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorSecondary) {
        this.user = user;
        this.setLayout(new BorderLayout());
        this.setBackground(colorBackground);

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(colorBackground);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        ticketTitle = new JLabel(messages.getString("ticketWelcome"));
        ticketTitle.setFont(new Font("Arial", Font.BOLD, 24));
        ticketTitle.setForeground(Color.BLACK);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        headerPanel.add(ticketTitle, gbc);

        lOrigin = new JLabel(messages.getString("origin"));
        lOrigin.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 1;
        headerPanel.add(lOrigin, gbc);

        comboOrigin = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        headerPanel.add(comboOrigin, gbc);

        lDestination = new JLabel(messages.getString("destination"));
        lDestination.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        headerPanel.add(lDestination, gbc);

        comboDestination = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 2;
        headerPanel.add(comboDestination, gbc);

        this.add(headerPanel, BorderLayout.NORTH);

        String[] columnNames = {messages.getString("departuretime"), messages.getString("arrivaltime"), messages.getString("price")};

        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        tableJourneys = new JTable(model);

        JTableHeader header = tableJourneys.getTableHeader();
        header.setBackground(colorSecondary);
        JScrollPane tablePane = new JScrollPane(tableJourneys);
        tablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(tablePane, BorderLayout.CENTER);
        tableJourneys.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableJourneys.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = tableJourneys.getSelectedRow();
                    if (selectedRow != -1) {
                        System.out.println("Selected Row: " + selectedRow);
                        // Aquí puedes manejar la fila seleccionada
                        // Por ejemplo, obtener los datos de la fila seleccionada
                        Object departureTime = tableJourneys.getValueAt(selectedRow, 0);
                        Object arrivalTime = tableJourneys.getValueAt(selectedRow, 1);
                        Object price = tableJourneys.getValueAt(selectedRow, 2);
                        System.out.println("Selected Ticket - Departure: " + departureTime + ", Arrival: " + arrivalTime + ", Price: " + price);
                    }
                }
            }
        });
        comboOrigin.addActionListener(e -> loadTicketsData((DefaultTableModel) tableJourneys.getModel()));
        comboDestination.addActionListener(e -> loadTicketsData((DefaultTableModel) tableJourneys.getModel()));
        setupComboBoxes();
        loadTicketsData(model);
        buy = new JButton("Buy");

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
        loadStations();
    }

    private void loadTicketsData(DefaultTableModel model) {
        model.setRowCount(0);

        List<TicketDTO> tickets = TicketController.getInstance().getAllTickets();

        String selectedOrigin = (String) comboOrigin.getSelectedItem();
        String selectedDestination = (String) comboDestination.getSelectedItem();

        if (selectedOrigin == null || selectedDestination == null) {
            return;
        }

        StationDTO originStation = StationController.getInstance().getStationById(selectedOrigin);
        StationDTO destinationStation = StationController.getInstance().getStationById(selectedDestination);

        if (originStation == null || destinationStation == null) {
            return;
        }

        for (TicketDTO ticket : tickets) {
            RouteDTO route = ticket.getSchedule().getRoute();
            if (route.getStations().contains(originStation) && route.getStations().contains(destinationStation)) {
                Object[] rowData = {
                    ticket.getId(),
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
