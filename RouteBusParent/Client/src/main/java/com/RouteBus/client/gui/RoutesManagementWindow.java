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
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.dto.StationDTO;

public class RoutesManagementWindow extends JFrame {
    private static final long serialVersionUID = 1L;

    // Panels
    private JPanel contentPanel;
    private JPanel routesPanel;
    private JPanel addRoutePanel;

    // Menu
    private JMenuBar menuBar;
    private JMenu routesMenu;
    private JMenuItem addRouteMenuItem;
    private JMenuItem viewRoutesMenuItem;

    // Components for adding route
    private JTextField routeNameField;
    private JTextField startPointField;
    private JTextField endPointField;
    private JTextField totalDistanceField;
    private JButton addRouteButton;

    // Set to hold routes
    private Set<RouteDTO> routesSet;

    // ResourceBundle for internationalization
    private ResourceBundle messages;

    public RoutesManagementWindow(String languageToLoad) {
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

        routesPanel = new JPanel(null);
        routesPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        routesPanel.setBackground(Color.WHITE);

        addRoutePanel = new JPanel(null);
        addRoutePanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        addRoutePanel.setBackground(Color.WHITE);

        contentPanel.add(routesPanel);

        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));

        routesMenu = new JMenu(messages.getString("routesMenu"));
        addRouteMenuItem = new JMenuItem(messages.getString("addRouteMenuItem"));
        viewRoutesMenuItem = new JMenuItem(messages.getString("viewRoutesMenuItem"));

        routesMenu.add(addRouteMenuItem);
        routesMenu.add(viewRoutesMenuItem);

        menuBar.add(routesMenu);

        this.setJMenuBar(menuBar);

        // Initialize routes set
        routesSet = new HashSet<>();

        // Components for adding route
        JLabel routeNameLabel = new JLabel(messages.getString("routeNameLabel"));
        routeNameLabel.setBounds(50, 50, 120, 30);
        addRoutePanel.add(routeNameLabel);

        routeNameField = new JTextField();
        routeNameField.setBounds(200, 50, 200, 30);
        addRoutePanel.add(routeNameField);

        JLabel startPointLabel = new JLabel(messages.getString("startPointLabel"));
        startPointLabel.setBounds(50, 100, 120, 30);
        addRoutePanel.add(startPointLabel);

        startPointField = new JTextField();
        startPointField.setBounds(200, 100, 200, 30);
        addRoutePanel.add(startPointField);

        JLabel endPointLabel = new JLabel(messages.getString("endPointLabel"));
        endPointLabel.setBounds(50, 150, 120, 30);
        addRoutePanel.add(endPointLabel);

        endPointField = new JTextField();
        endPointField.setBounds(200, 150, 200, 30);
        addRoutePanel.add(endPointField);

        JLabel totalDistanceLabel = new JLabel(messages.getString("totalDistanceLabel"));
        totalDistanceLabel.setBounds(50, 200, 120, 30);
        addRoutePanel.add(totalDistanceLabel);

        totalDistanceField = new JTextField();
        totalDistanceField.setBounds(200, 200, 200, 30);
        addRoutePanel.add(totalDistanceField);

        addRouteButton = new JButton(messages.getString("addRouteButton"));
        addRouteButton.setBounds(150, 250, 150, 30);
        addRoutePanel.add(addRouteButton);

        // Action Listeners
        addRouteMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(addRoutePanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        addRouteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addRoute();
            }
        });

        viewRoutesMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewRoutes();
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
            addRoutePanel.add(imageLabel);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.setVisible(true);
    }

    private void addRoute() {
        String name = routeNameField.getText();
        String startPoint = startPointField.getText();
        String endPoint = endPointField.getText();
        double totalDistance = Double.parseDouble(totalDistanceField.getText());

        RouteDTO route = new RouteDTO(name, startPoint, endPoint, totalDistance);
        routesSet.add(route);

        JOptionPane.showMessageDialog(this, messages.getString("routeAddedMessage"));
    }

    private void viewRoutes() {
        // Crear el modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Name");
        tableModel.addColumn("Start Point");
        tableModel.addColumn("End Point");
        tableModel.addColumn("Total Distance (km)");

        // Llenar el modelo con las rutas existentes
        for (RouteDTO route : routesSet) {
            tableModel.addRow(new Object[]{
                    route.getName(),
                    route.getStartPoint(),
                    route.getEndPoint(),
                    route.getTotalDistance()
            });
        }

        // Crear el JTable con el modelo
        JTable routesTable = new JTable(tableModel);
        routesTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Personalizar el renderizador de cabeceras
        JTableHeader header = routesTable.getTableHeader();
        header.setBackground(new Color(204, 153, 255));

        // Crear un JScrollPane para el JTable
        JScrollPane scrollPane = new JScrollPane(routesTable);
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
        new RoutesManagementWindow("en_US");
    }
}
