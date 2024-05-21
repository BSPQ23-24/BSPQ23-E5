package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.List;
import java.util.ResourceBundle;

import com.RouteBus.client.controller.StationController;
import com.RouteBus.client.controller.TicketController;
import com.RouteBus.client.dto.StationDTO;
import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.UserDTO;

@SuppressWarnings("serial")
public class MyTicketsPanel extends JPanel {

    private JLabel ticketTitle;
    private JLabel lOrigin;
    private JLabel lDestination;
    private JComboBox<StationDTO> comboOrigin;
    private JComboBox<StationDTO> comboDestination;
    private JTable tableJourneys;
    private DefaultTableModel tableModel;
    private UserDTO user;
    private ResourceBundle messages;
    private Color colorBackground;
    private Color colorSecondary;

    public MyTicketsPanel(ResourceBundle messages, UserDTO user, Color colorBackground, Color colorSecondary) {
        this.user = user;
        this.messages = messages;
        this.colorBackground = colorBackground;
        this.colorSecondary = colorSecondary;

        initializeComponents();
        layoutComponents();
        setupListeners();
        setupComboBoxes();
        loadTicketsData();
    }

    private void initializeComponents() {
        ticketTitle = createLabel(messages.getString("ticketWelcome"), new Font("Arial", Font.BOLD, 24), Color.BLACK);
        lOrigin = createLabel(messages.getString("origin"), new Font("Arial", Font.BOLD, 18), null);
        lDestination = createLabel(messages.getString("destination"), new Font("Arial", Font.BOLD, 18), null);

        comboOrigin = new JComboBox<>();
        comboDestination = new JComboBox<>();

        String[] columnNames = {messages.getString("departuretime"), messages.getString("arrivaltime"), messages.getString("price")};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableJourneys = new JTable(tableModel);
        configureTableAppearance();
    }

    private void layoutComponents() {
        setLayout(new BorderLayout());
        setBackground(colorBackground);

        JPanel headerPanel = new JPanel(new GridBagLayout());
        headerPanel.setBackground(colorBackground);
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(headerPanel, BorderLayout.NORTH);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridwidth = 2;

        addToPanel(headerPanel, ticketTitle, gbc, 0, 0);

        gbc.gridwidth = 1;
        addToPanel(headerPanel, lOrigin, gbc, 0, 1);
        addToPanel(headerPanel, comboOrigin, gbc, 1, 1);
        addToPanel(headerPanel, lDestination, gbc, 0, 2);
        addToPanel(headerPanel, comboDestination, gbc, 1, 2);

        JScrollPane tablePane = new JScrollPane(tableJourneys);
        tablePane.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(tablePane, BorderLayout.CENTER);
    }

    private void setupListeners() {
        comboOrigin.addActionListener(e -> loadTicketsData());
        comboDestination.addActionListener(e -> loadTicketsData());
    }

    private void setupComboBoxes() {
        List<StationDTO> stations = StationController.getInstance().getAllStations();
        for (StationDTO station : stations) {
            comboOrigin.addItem(station);
            comboDestination.addItem(station);
        }
        comboOrigin.setRenderer(new StationListCellRenderer());
        comboDestination.setRenderer(new StationListCellRenderer());
    }

    private void loadTicketsData() {
        tableModel.setRowCount(0);

        List<TicketDTO> tickets = TicketController.getInstance().getTicketByUser(user.getEmail());
        StationDTO selectedOrigin = (StationDTO) comboOrigin.getSelectedItem();
        StationDTO selectedDestination = (StationDTO) comboDestination.getSelectedItem();

        if (selectedOrigin == null || selectedDestination == null) {
            return;
        }

        tickets.stream()
                .filter(ticket -> ticketMatchesRoute(ticket, selectedOrigin, selectedDestination))
                .forEach(this::addTicketToTable);
    }

    private boolean ticketMatchesRoute(TicketDTO ticket, StationDTO origin, StationDTO destination) {
        return ticket.getSchedule().getRoute().getStations().containsAll(List.of(origin, destination));
    }

    private void addTicketToTable(TicketDTO ticket) {
        Object[] rowData = {
                ticket.getSchedule().getDepartureTime(),
                ticket.getSchedule().getArrivalTime(),
                ticket.getPrice()
        };
        tableModel.addRow(rowData);
    }

    public void updateTexts(ResourceBundle messages) {
        ticketTitle.setText(messages.getString("ticketWelcome"));
        lOrigin.setText(messages.getString("origin"));
        lDestination.setText(messages.getString("destination"));

        String[] columnNames = {messages.getString("departuretime"), messages.getString("arrivaltime"), messages.getString("price")};
        tableModel.setColumnIdentifiers(columnNames);
    }

    private JLabel createLabel(String text, Font font, Color color) {
        JLabel label = new JLabel(text);
        if (font != null) {
            label.setFont(font);
        }
        if (color != null) {
            label.setForeground(color);
        }
        return label;
    }

    private void configureTableAppearance() {
        tableJourneys.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tableJourneys.setBackground(colorBackground);
        tableJourneys.setForeground(Color.BLACK);
        tableJourneys.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tableJourneys.getColumnCount(); i++) {
            tableJourneys.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = tableJourneys.getTableHeader();
        header.setBackground(colorSecondary);
        header.setForeground(Color.WHITE);
    }

    private void addToPanel(JPanel panel, Component component, GridBagConstraints gbc, int x, int y) {
        gbc.gridx = x;
        gbc.gridy = y;
        panel.add(component, gbc);
    }

    private static class StationListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Component renderer = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof StationDTO) {
                setText(((StationDTO) value).getName());
            }
            return renderer;
        }
    }
}
