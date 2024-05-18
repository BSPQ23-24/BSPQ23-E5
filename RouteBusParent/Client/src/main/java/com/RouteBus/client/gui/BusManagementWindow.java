package com.RouteBus.client.gui;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashSet;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Set;

import com.RouteBus.client.dto.BusDTO;
//
import com.RouteBus.client.dto.RouteDTO;

public class BusManagementWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    // Panels
    private JPanel contentPanel;
    private JPanel bussPanel;
    private JPanel addBusPanel;

    // Menu
    private JMenuBar menuBar;
    private JMenu busesMenu;
    private JMenuItem addBusMenuItem;
    private JMenuItem viewBusesMenuItem;

    // Components for adding bus
    private JTextField busLicensePlateField;
    private JTextField makeField;
    private JTextField modelField;
    private JTextField capacityField;
    private JButton addbusButton;
    private JList routeList;

    // Set to hold buss
    private Set<BusDTO> busSet;

    // ResourceBundle for internationalization
    private ResourceBundle messages;

    public BusManagementWindow(String languageToLoad) {
        Locale currentLocale;
        if (languageToLoad == null) {
            currentLocale = Locale.getDefault();
        } else {
            currentLocale = new Locale(languageToLoad);
        }
        messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
        this.setTitle(messages.getString("windowTitle1"));
        this.setLayout(null);
        this.setBounds(500, 100, 850, 600);
        this.setResizable(false);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(contentPanel);

        bussPanel = new JPanel(null);
        bussPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        bussPanel.setBackground(Color.WHITE);

        addBusPanel = new JPanel(null);
        addBusPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        addBusPanel.setBackground(Color.WHITE);

        contentPanel.add(bussPanel);

        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));

        busesMenu = new JMenu(messages.getString("busesMenu"));
        addBusMenuItem = new JMenuItem(messages.getString("addBusMenuItem"));
        viewBusesMenuItem = new JMenuItem(messages.getString("viewBusesMenuItem"));

        busesMenu.add(addBusMenuItem);
        busesMenu.add(viewBusesMenuItem);

        menuBar.add(busesMenu);

        this.setJMenuBar(menuBar);

        // Initialize bus set
       busSet = new HashSet<>();

        // Components for adding a new bus
        JLabel busLicensePlateLabel = new JLabel(messages.getString("busLicensePlateLabel"));
        busLicensePlateLabel.setBounds(50, 50, 120, 30);
        addBusPanel.add(busLicensePlateLabel);

        busLicensePlateField = new JTextField();
        busLicensePlateField.setBounds(200, 50, 200, 30);
        addBusPanel.add(busLicensePlateField);

        JLabel makeLabel = new JLabel(messages.getString("makeLabel"));
        makeLabel.setBounds(50, 100, 120, 30);
        addBusPanel.add(makeLabel);

        makeField = new JTextField();
        makeField.setBounds(200, 100, 200, 30);
        addBusPanel.add(makeField);

        JLabel modelLabel = new JLabel(messages.getString("modelLabel"));
        modelLabel.setBounds(50, 150, 120, 30);
        addBusPanel.add(modelLabel);

        modelField = new JTextField();
        modelField.setBounds(200, 150, 200, 30);
        addBusPanel.add(modelField);

        JLabel capacityLabel = new JLabel(messages.getString("capacityLabel"));
        capacityLabel.setBounds(50, 200, 120, 30);
        addBusPanel.add(capacityLabel);

        capacityField = new JTextField();
        capacityField.setBounds(200, 200, 200, 30);
        addBusPanel.add(capacityField);

        JLabel routeLabel = new JLabel(messages.getString("routeLabel"));
        capacityLabel.setBounds(50, 250, 120, 30);
        addBusPanel.add(routeLabel);

        routeList = new JList<RouteDTO>();
        routeList.setBounds(200,250,120,30);
        routeList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        routeList.setVisibleRowCount(8);
        add(new JScrollPane(routeList));
        addBusPanel.add(routeList);

        addbusButton = new JButton(messages.getString("addbusButton"));
        addbusButton.setBounds(150, 300, 150, 30);
        addBusPanel.add(addbusButton);

        // Action Listeners
        addBusMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(addBusPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        addbusButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addBus();
            }
        });

        viewBusesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewBus();
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
            
         // Agrega el JLabel al panel de agregar bus
            addBusPanel.add(imageLabel);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.setVisible(true);
    }

    private void addBus() {
        String license = busLicensePlateField.getText();
        int capacity = Integer.parseInt(capacityField.getText());
        String make = makeField.getText();
        String model = modelField.getText();
        /* !!!!!!! */
        //No clue about how to get this working.
        //Set<RouteDTO> routeSet = routeSet.add(routeList.getSelectedValue());
        /* !!!!!!! */
        BusDTO bus = new BusDTO(license,capacity,make,model);
        busSet.add(bus);

        JOptionPane.showMessageDialog(this, messages.getString("busAddedMessage"));
    }

    private void viewBus() {
        // Crear el modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("License");
        tableModel.addColumn("Capacity");
        tableModel.addColumn("Make");
        tableModel.addColumn("Model");
        //Routes might be troublesome. Will figure out later.
        //tableModel.addColumn("Routes");

        // Llenar el modelo con las rutas existentes
        for (BusDTO bus : busSet) {
            tableModel.addRow(new Object[]{
                    bus.getLicensePlate(),
                    bus.getCapacity(),
                    bus.getMake(),
                    bus.getModel(),
                    //bus.getRoutes()
            });
        }

        // Crear el JTable con el modelo
        JTable busTable = new JTable(tableModel);
        busTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Personalizar el renderizador de cabeceras
        JTableHeader header = busTable.getTableHeader();
        header.setBackground(new Color(204, 153, 255));

        // Crear un JScrollPane para el JTable
        JScrollPane scrollPane = new JScrollPane(busTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        //  Images
        try {     
            BufferedImage iconImage = ImageIO.read(getClass().getResource("/images/iconBus.png"));

            if (iconImage != null) {
                ImageIcon icon = new ImageIcon(iconImage);

                JFrame frame = new JFrame(messages.getString("viewBusTitle"));
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
}
