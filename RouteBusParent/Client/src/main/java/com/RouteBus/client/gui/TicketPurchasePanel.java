package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import com.RouteBus.client.controller.RouteController;
import com.RouteBus.client.controller.TicketController;
import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.ScheduleDTO;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.TicketDTO.TicketStatus;
import com.RouteBus.client.dto.UserDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@SuppressWarnings("serial")
public class TicketPurchasePanel extends JPanel {
    private JTable routesTable;
    private JTable schedulesTable;
    private JComboBox<String> comboOrigin;
    private JComboBox<String> comboDestination;
    private JButton buyButton;
    private UserDTO user;
    private RouteDTO selectedRoute;

    public TicketPurchasePanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorPrimary, Color colorSecondary) {
        this.user = user;
        this.setLayout(new BorderLayout());
        this.setBackground(colorBackground);

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(colorBackground);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel routesLabel = new JLabel(messages.getString("availableRoutes"));
        routesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        routesLabel.setForeground(colorPrimary);
        gbc.gridwidth = 2;
        gbc.gridx = 0;
        gbc.gridy = 0;
        headerPanel.add(routesLabel, gbc);

        this.add(headerPanel, BorderLayout.NORTH);

        JPanel filterPanel = new JPanel(new GridBagLayout());
        filterPanel.setBackground(colorBackground);
        filterPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        gbc.gridwidth = 1;

        JLabel lOrigin = new JLabel(messages.getString("origin"));
        lOrigin.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        filterPanel.add(lOrigin, gbc);

        comboOrigin = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 0;
        filterPanel.add(comboOrigin, gbc);
        comboOrigin.setSelectedItem("");
        JLabel lDestination = new JLabel(messages.getString("destination"));
        lDestination.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        filterPanel.add(lDestination, gbc);

        comboDestination = new JComboBox<>();
        gbc.gridx = 1;
        gbc.gridy = 1;
        filterPanel.add(comboDestination, gbc);
        comboDestination.setSelectedItem("");

        this.add(filterPanel, BorderLayout.WEST);

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
        routesTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int selectedRow = routesTable.getSelectedRow();
                    if (selectedRow != -1) {
                        loadSchedulesData();
                    }
                }
            }
        });
        String[] scheduleColumnNames = {"departureTime", "arrivalTime"};
        DefaultTableModel scheduleModel = new DefaultTableModel(scheduleColumnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        schedulesTable = new JTable(scheduleModel);
        schedulesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scheduleTablePane = new JScrollPane(schedulesTable);
        scheduleTablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JScrollPane tablePane = new JScrollPane(routesTable);
        tablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        JPanel centerPanel = new JPanel(new GridLayout(1, 2));
        centerPanel.add(tablePane);
        centerPanel.add(scheduleTablePane);
        this.add(centerPanel, BorderLayout.CENTER);

        buyButton = new JButton(messages.getString("buyTicket"));
        buyButton.setBackground(colorSecondary);
        this.add(buyButton, BorderLayout.SOUTH);

        comboOrigin.addActionListener(e -> loadRoutesDataFiltered((DefaultTableModel) routesTable.getModel()));
        comboDestination.addActionListener(e -> loadRoutesDataFiltered((DefaultTableModel) routesTable.getModel()));
        buyButton.addActionListener(this::performPurchase);

        setupComboBoxes();
        loadRoutesData(model);
    }

    private void setupComboBoxes() {
        List<StationDTO> stations = StationController.getInstance().getAllStations();
        for (StationDTO station : stations) {
            comboDestination.addItem(station.getName());
            comboOrigin.addItem(station.getName());
        }
    }

    private void loadRoutesDataFiltered(DefaultTableModel model) {
        model.setRowCount(0);

        List <RouteDTO> routes = RouteController.getInstance().getAllRoutes();
        String selectedOrigin = (String) comboOrigin.getSelectedItem();
        String selectedDestination = (String) comboDestination.getSelectedItem();
        StationDTO originStation = StationController.getInstance().getStationById(selectedOrigin);
        StationDTO destinationStation = StationController.getInstance().getStationById(selectedDestination);
        
        for (RouteDTO route : routes) {
        	if (route.getStations().contains(originStation) && route.getStations().contains(destinationStation)) {
        		model.addRow(new Object[]{route.getName(), route.getStartPoint(), route.getEndPoint(), route.getTotalDistance()});
        	}
        }
    }
    private void loadRoutesData(DefaultTableModel model) {
        model.setRowCount(0);

        List <RouteDTO> routes = RouteController.getInstance().getAllRoutes();
        
        
        for (RouteDTO route : routes) {
        		model.addRow(new Object[]{route.getName(), route.getStartPoint(), route.getEndPoint(), route.getTotalDistance()});
        }
    }
    private void loadSchedulesData() {
        DefaultTableModel scheduleModel = (DefaultTableModel) schedulesTable.getModel();
        scheduleModel.setRowCount(0);
        
        int selectedRow = routesTable.getSelectedRow();
        if (selectedRow >= 0) {
            String routeName = (String) routesTable.getValueAt(selectedRow, 0);
            selectedRoute = RouteController.getInstance().getRouteById(routeName);
            if (selectedRoute != null) {
                Set<ScheduleDTO> schedules = selectedRoute.getSchedules();
                for (ScheduleDTO schedule : schedules) {
                    scheduleModel.addRow(new Object[]{schedule.getDepartureTime(), schedule.getArrivalTime()});
                }
            }
        }
    }
    private void performPurchase(ActionEvent e) {
        int selectedRouteRow = routesTable.getSelectedRow();
        int selectedScheduleRow = schedulesTable.getSelectedRow();

        if (selectedRouteRow >= 0 && selectedScheduleRow >= 0) {
            // Obtener el nombre de la ruta seleccionada
            String routeName = (String) routesTable.getValueAt(selectedRouteRow, 0);
            RouteDTO selectedRoute = RouteController.getInstance().getRouteById(routeName);

            // Obtener el horario seleccionado
            Date departureTime = (Date) schedulesTable.getValueAt(selectedScheduleRow, 0);
            Date arrivalTime = (Date) schedulesTable.getValueAt(selectedScheduleRow, 1);

            ScheduleDTO selectedSchedule = null;
            for (ScheduleDTO schedule : selectedRoute.getSchedules()) {
                if (schedule.getDepartureTime().equals(departureTime) && schedule.getArrivalTime().equals(arrivalTime)) {
                    selectedSchedule = schedule;
                    break;
                }
            }

            if (selectedSchedule != null) {
                // Generar un número de asiento aleatorio
                Random random = new Random();
                int seatNumber = random.nextInt(100) + 1; // Genera un número entre 1 y 100

                // Crear el nuevo TicketDTO con valores válidos
                TicketDTO ticket = new TicketDTO();
                ticket.setUser(user);
                ticket.setSeatNumber(seatNumber);
                ticket.setPrice(55.0); // Asegúrate de que este sea el precio correcto
                ticket.setSchedule(selectedSchedule);
                ticket.setStatus(TicketStatus.PURCHASED);
                ticket.setId(ticket.getSchedule().getId() + "-" + String.format("%04d", ticket.getSeatNumber()) + "-" + ticket.getStatus().toString().toLowerCase());
                // Verificar el JSON que se enviará
                ObjectMapper mapper = new ObjectMapper();
                try {
                    String json = mapper.writeValueAsString(ticket);
                    System.out.println("JSON enviado: " + json);
                } catch (JsonProcessingException ex) {
                    ex.printStackTrace();
                }

                // Enviar el ticket al servidor
                boolean success = TicketController.getInstance().createTicket(ticket);
                if (success) {
                    JOptionPane.showMessageDialog(this, "Ticket purchased successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    loadRoutesData((DefaultTableModel) routesTable.getModel());
                } else {
                    JOptionPane.showMessageDialog(this, "Error purchasing ticket.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Selected schedule is invalid.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a route and a schedule.", "Warning", JOptionPane.WARNING_MESSAGE);
        }
    }
}
