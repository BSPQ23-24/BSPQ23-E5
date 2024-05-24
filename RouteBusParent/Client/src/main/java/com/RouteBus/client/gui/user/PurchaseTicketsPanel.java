package com.RouteBus.client.gui.user;

import javax.swing.*;
import com.RouteBus.client.dto.UserDTO;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.ScheduleDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.controller.RouteController;
import com.RouteBus.client.controller.TicketController;
import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.controller.ScheduleController;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.ListSelectionModel;

@SuppressWarnings("serial")
public class PurchaseTicketsPanel extends UserPanel {
    private JTable combinedTable;
    private JComboBox<String> comboOrigin;
    private JComboBox<String> comboDestination;
    private JComboBox<String> comboOriginStation;
    private JComboBox<String> comboDestinationStation;
    private JButton buyButton;
    private UserDTO user;

    public PurchaseTicketsPanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorPrimary, Color colorSecondary) {
        super(messages, colorBackground, colorPrimary, colorSecondary, null);
        this.user = user;
        this.setLayout(new BorderLayout());
        this.setBackground(colorBackground);

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(colorBackground);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel lOrigin = new JLabel(messages.getString("origin"));
        lOrigin.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridwidth = 1;
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(lOrigin, gbc);

        comboOrigin = new JComboBox<>();
        gbc.gridx = 1;
        filterPanel.add(comboOrigin, gbc);

        JLabel lDestination = new JLabel(messages.getString("destination"));
        lDestination.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        filterPanel.add(lDestination, gbc);

        comboDestination = new JComboBox<>();
        gbc.gridx = 1;
        filterPanel.add(comboDestination, gbc);

        JLabel lOriginStation = new JLabel(messages.getString("originStation"));
        lOriginStation.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        filterPanel.add(lOriginStation, gbc);

        comboOriginStation = new JComboBox<>();
        gbc.gridx = 1;
        filterPanel.add(comboOriginStation, gbc);

        JLabel lDestinationStation = new JLabel(messages.getString("destinationStation"));
        lDestinationStation.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 3;
        filterPanel.add(lDestinationStation, gbc);

        comboDestinationStation = new JComboBox<>();
        gbc.gridx = 1;
        filterPanel.add(comboDestinationStation, gbc);

        this.add(filterPanel, BorderLayout.NORTH);

        String[] columnNames = {
            messages.getString("startPoint"), 
            messages.getString("endPoint"), 
            messages.getString("totalDistance"), 
            messages.getString("departureDate"), 
            messages.getString("departureTime"), 
            messages.getString("arrivalDate"), 
            messages.getString("arrivalTime")
        };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        combinedTable = new JTable(model);
        combinedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JTableHeader header = combinedTable.getTableHeader();
        header.setBackground(colorPrimary);
        header.setForeground(Color.WHITE);
        combinedTable.setBackground(colorBackground);
        combinedTable.setForeground(Color.BLACK);
        combinedTable.setSelectionBackground(colorSecondary);
        combinedTable.setSelectionForeground(Color.WHITE);

        JScrollPane tablePane = new JScrollPane(combinedTable);
        tablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        this.add(tablePane, BorderLayout.CENTER);

        buyButton = new JButton(messages.getString("buyButton"));
        buyButton.setBackground(colorPrimary);
        buyButton.setForeground(Color.WHITE);
        this.add(buyButton, BorderLayout.SOUTH);

        comboOrigin.addActionListener(e -> loadStations(comboOrigin, comboOriginStation));
        comboDestination.addActionListener(e -> loadStations(comboDestination, comboDestinationStation));
        comboOriginStation.addActionListener(e -> loadRoutesAndSchedulesData(model));
        comboDestinationStation.addActionListener(e -> loadRoutesAndSchedulesData(model));
        buyButton.addActionListener(e -> buyTicket());

        setupComboBoxes();
    }

    private void setupComboBoxes() {
        List<StationDTO> stations = StationController.getInstance().getAllStations();
        for (StationDTO station : stations) {
            comboOrigin.addItem(station.getName());
            comboDestination.addItem(station.getName());
        }
    }

    private void loadStations(JComboBox<String> comboBox, JComboBox<String> stationComboBox) {
        stationComboBox.removeAllItems();
        String selectedStation = (String) comboBox.getSelectedItem();
        if (selectedStation != null) {
            List<StationDTO> stations = StationController.getInstance().getAllStations();
            for (StationDTO station : stations) {
                if (station.getName().equals(selectedStation)) {
                    stationComboBox.addItem(station.getLocation());
                }
            }
        }
    }

    private void loadRoutesAndSchedulesData(DefaultTableModel model) {
        model.setRowCount(0);
        String origin = (String) comboOrigin.getSelectedItem();
        String destination = (String) comboDestination.getSelectedItem();
        String originStation = (String) comboOriginStation.getSelectedItem();
        String destinationStation = (String) comboDestinationStation.getSelectedItem();

        if (origin == null || destination == null || originStation == null || destinationStation == null) {
            return;
        }

        List<RouteDTO> routes = RouteController.getInstance().getRoutesByStations(origin, destination);
        if (routes == null || routes.isEmpty()) {
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");

        for (RouteDTO route : routes) {
            List<ScheduleDTO> schedules = ScheduleController.getInstance().getSchedulesByRoute(route);
            for (ScheduleDTO schedule : schedules) {
                Object[] rowData = {
                    originStation,
                    destinationStation,
                    route.getTotalDistance(),
                    dateFormat.format(schedule.getDepartureTime()),
                    timeFormat.format(schedule.getDepartureTime()),
                    dateFormat.format(schedule.getArrivalTime()),
                    timeFormat.format(schedule.getArrivalTime())
                };
                model.addRow(rowData);
            }
        }
    }

    private void buyTicket() {
        int selectedRow = combinedTable.getSelectedRow();
        if (selectedRow >= 0) {
            String origin = (String) comboOrigin.getSelectedItem();
            String destination = (String) comboDestination.getSelectedItem();
            String departureDate = (String) combinedTable.getValueAt(selectedRow, 3);
            String departureTime = (String) combinedTable.getValueAt(selectedRow, 4);
            String arrivalDate = (String) combinedTable.getValueAt(selectedRow, 5);
            String arrivalTime = (String) combinedTable.getValueAt(selectedRow, 6);
            
            SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            SimpleDateFormat idDateFormat = new SimpleDateFormat("yyyyMMddHHmm");

            try {
                RouteDTO selectedRoute = RouteController.getInstance().getRoutesByStations(origin, destination).get(0);
                Date departureDateTime = dateTimeFormat.parse(departureDate + " " + departureTime);
                Date arrivalDateTime = dateTimeFormat.parse(arrivalDate + " " + arrivalTime);

                String scheduleId = selectedRoute.getName().replaceAll("\\s+", "").toLowerCase() + "-" + idDateFormat.format(departureDateTime) + "-" + idDateFormat.format(arrivalDateTime);
                ScheduleDTO selectedSchedule = ScheduleController.getInstance().getScheduleById(scheduleId);

                if (selectedSchedule != null) {
                    int seatNumber = ThreadLocalRandom.current().nextInt(1, selectedRoute.getBuses().iterator().next().getCapacity() + 1);
                    double price = ThreadLocalRandom.current().nextDouble(50, 101);
                    TicketDTO newTicket = new TicketDTO(user, seatNumber, price, TicketDTO.TicketStatus.PURCHASED, selectedSchedule);
                    boolean success = TicketController.getInstance().createTicket(newTicket);

                    if (success) {
                        JOptionPane.showMessageDialog(this, messages.getString("ticketPurchaseSuccess"), messages.getString("successTitle"), JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(this, messages.getString("ticketPurchaseError"), messages.getString("errorTitle"), JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, messages.getString("noRoutesError"), messages.getString("errorTitle"), JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @Override
    public void updateTexts(ResourceBundle messages) {
        this.messages = messages;

        String[] columnNames = {
            messages.getString("startPoint"), 
            messages.getString("endPoint"), 
            messages.getString("totalDistance"), 
            messages.getString("departureDate"), 
            messages.getString("departureTime"), 
            messages.getString("arrivalDate"), 
            messages.getString("arrivalTime")
        };
        DefaultTableModel model = (DefaultTableModel) combinedTable.getModel();
        model.setColumnIdentifiers(columnNames);
        loadRoutesAndSchedulesData(model);

        buyButton.setText(messages.getString("buyButton"));
    }
}
