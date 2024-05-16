package com.RouteBus.client.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import com.RouteBus.client.dto.StationDTO;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

public class StationsManagementWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    // Panels
    private JPanel contentPanel;
    private JPanel stationsPanel;
    
    // Menu
    private JMenuBar menuBar;
    private JMenu stationsMenu;
    private JMenuItem addStationMenuItem;
    private JMenuItem viewStationsMenuItem;

    // Components for adding station
    private JTextField stationNameField;
    private JTextField stationLocationField;
    private JButton addStationButton;

    // Set to hold stations
    private Set<StationDTO> stationsSet;

    // ResourceBundle for internationalization
    private ResourceBundle messages;

    public StationsManagementWindow(String languageToLoad) {
        Locale currentLocale;
        if (languageToLoad == null) {
            currentLocale = Locale.getDefault();
        } else {
            currentLocale = new Locale(languageToLoad);
        }
        messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
        this.setTitle(messages.getString("windowTitle2"));
        this.setLayout(null);
        this.setBounds(500, 100, 850, 600);
        this.setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(contentPanel);

        stationsPanel = new JPanel(null);
        stationsPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        stationsPanel.setBackground(Color.WHITE);
        
        contentPanel.add(stationsPanel);

        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));

        stationsMenu = new JMenu(messages.getString("stationsMenu"));
        addStationMenuItem = new JMenuItem(messages.getString("addStationMenuItem"));
        viewStationsMenuItem = new JMenuItem(messages.getString("viewStationsMenuItem"));

        stationsMenu.add(addStationMenuItem);
        stationsMenu.add(viewStationsMenuItem);

        menuBar.add(stationsMenu);

        this.setJMenuBar(menuBar);

        // Initialize stations set
        stationsSet = new HashSet<>();

        // Components for adding station
        JLabel stationNameLabel = new JLabel(messages.getString("stationNameLabel"));
        stationNameLabel.setBounds(50, 50, 120, 30);
        stationsPanel.add(stationNameLabel);

        stationNameField = new JTextField();
        stationNameField.setBounds(200, 50, 200, 30);
        stationsPanel.add(stationNameField);

        JLabel stationLocationLabel = new JLabel(messages.getString("stationLocationLabel"));
        stationLocationLabel.setBounds(50, 100, 120, 30);
        stationsPanel.add(stationLocationLabel);

        stationLocationField = new JTextField();
        stationLocationField.setBounds(200, 100, 200, 30);
        stationsPanel.add(stationLocationField);

        addStationButton = new JButton(messages.getString("addStationButton"));
        addStationButton.setBounds(150, 150, 150, 30);
        stationsPanel.add(addStationButton);

        // Action Listeners
        addStationMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(stationsPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        addStationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addStation();
            }
        });

        viewStationsMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewStations();
            }
        });

        //  Images
        try {
            BufferedImage iconImage = ImageIO.read(getClass().getResource("/images/iconBus.png"));
            BufferedImage busLogoImage = ImageIO.read(getClass().getResource("/images/icon.jpg"));
            
            ImageIcon icon = new ImageIcon(iconImage);
            ImageIcon busLogo = new ImageIcon(busLogoImage);
            
            JLabel imageLabel = new JLabel(busLogo);            
            
            this.setIconImage(icon.getImage());
            
            imageLabel.setBounds(590, -70, 200, 300); // Adjust position and size as needed
            
         // Agrega el JLabel al panel de agregar ruta
            stationsPanel.add(imageLabel);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.setVisible(true);
    }

    private void addStation() {
        String name = stationNameField.getText();
        String location = stationLocationField.getText();

        StationDTO station = new StationDTO(name, location);
        stationsSet.add(station);

        JOptionPane.showMessageDialog(this, messages.getString("stationAddedMessage"));
    }

    private void viewStations() {
        // Crear el modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Location");

        // Llenar el modelo con las estaciones existentes
        for (StationDTO station : stationsSet) {
            tableModel.addRow(new Object[]{
                    station.getName(),
                    station.getLocation()
            });
        }

        // Crear el JTable con el modelo
        JTable stationsTable = new JTable(tableModel);
        stationsTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Personalizar el renderizador de cabeceras
        JTableHeader header = stationsTable.getTableHeader();
        header.setBackground(new Color(204, 153, 255));

        // Crear un JScrollPane para el JTable
        JScrollPane scrollPane = new JScrollPane(stationsTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

    //  Images
        try {     
            BufferedImage iconImage = ImageIO.read(getClass().getResource("/images/iconBus.png"));

            if (iconImage != null) {
                ImageIcon icon = new ImageIcon(iconImage);

                JFrame frame = new JFrame(messages.getString("viewRoutesTitle"));
                frame.getContentPane().setBackground(Color.WHITE);

                frame.setIconImage(iconImage);

                frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                frame.add(scrollPane);
                frame.pack();
                frame.setLocationRelativeTo(this);
                frame.setVisible(true);
            } else {
                System.err.println("No se pudo cargar la imagen del icono de la ventana.");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new StationsManagementWindow("en_US");
    }
}
