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

import com.RouteBus.client.dto.TicketDTO;
import com.RouteBus.client.dto.RouteDTO;



public class TicketManagementWindow extends JFrame{
    private static final long serialVersionUID = 1L;

    // Panels
    private JPanel contentPanel;
    private JPanel ticketsPanel;
    private JPanel addTicketPanel;

    // Menu
    private JMenuBar menuBar;
    private JMenu ticketMenu;
    private JMenuItem addTicketMenuItem;
    private JMenuItem viewTicketMenuItem;

    // Components for adding ticket
    private JTextField ticketPriceField;

    private JButton addTicketButton;

    // Set to hold Tickets
    private Set<TicketDTO> ticketSet;

    // ResourceBundle for internationalization
    private ResourceBundle messages;

    public TicketManagementWindow(String languageToLoad) {
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

        ticketsPanel = new JPanel(null);
        ticketsPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        ticketsPanel.setBackground(Color.WHITE);

        addTicketPanel = new JPanel(null);
        addTicketPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        addTicketPanel.setBackground(Color.WHITE);

        contentPanel.add(ticketsPanel);

        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));

        ticketMenu = new JMenu(messages.getString("ticketMenu"));
        addTicketMenuItem = new JMenuItem(messages.getString("addTicketMenuItem"));
        viewTicketMenuItem = new JMenuItem(messages.getString("viewTicketMenuItem"));

        ticketMenu.add(addTicketMenuItem);
        ticketMenu.add(viewTicketMenuItem);

        menuBar.add(ticketMenu);

        this.setJMenuBar(menuBar);

        // Initialize ticket set
       ticketSet = new HashSet<>();

        // Components for adding a new ticket
        JLabel ticketPriceLabel = new JLabel(messages.getString("ticketPriceLabel"));
        ticketPriceLabel.setBounds(50, 50, 120, 30);
        addTicketPanel.add(ticketPriceLabel);

        ticketPriceField = new JTextField();
        ticketPriceField.setBounds(200, 50, 200, 30);
        addTicketPanel.add(ticketPriceField);

        addTicketButton = new JButton(messages.getString("addTicketButton"));
        addTicketButton.setBounds(150, 300, 150, 30);
        addTicketPanel.add(addTicketButton);

        // Action Listeners
        addTicketMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                contentPanel.removeAll();
                contentPanel.add(addTicketPanel);
                contentPanel.revalidate();
                contentPanel.repaint();
            }
        });

        addTicketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTicket();
            }
        });

        viewTicketMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewTicket();
            }
        });

        //  Images
        try {
            BufferedImage iconImage = ImageIO.read(getClass().getResource("/images/iconticket.png"));
            BufferedImage ticketLogoImage = ImageIO.read(getClass().getResource("/images/icon.jpg"));

            ImageIcon icon = new ImageIcon(iconImage);
            ImageIcon ticketLogo = new ImageIcon(ticketLogoImage);

            JLabel imageLabel = new JLabel(ticketLogo);            

            this.setIconImage(icon.getImage());

            imageLabel.setBounds(590, -70, 200, 300); // Adjust position and size as needed

         // Agrega el JLabel al panel de agregar ticket
            addTicketPanel.add(imageLabel);

        } catch (IOException e) {
            e.printStackTrace();
        }

        this.setVisible(true);
    }

    private void addTicket() {
        Double price = Double.parseDouble(ticketPriceField.getText());
        //No such window in the design pages...
        TicketDTO ticket = new TicketDTO();
        ticket.setPrice(price);
        ticketSet.add(ticket);

        JOptionPane.showMessageDialog(this, messages.getString("ticketAddedMessage"));
    }

    private void viewTicket() {
        // Crear el modelo de tabla
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Price");
        tableModel.addColumn("Status");
        tableModel.addColumn("User");
        tableModel.addColumn("Seat Number");
        tableModel.addColumn("Schedule");


        // Llenar el modelo con las rutas existentes
        for (TicketDTO ticket : ticketSet) {
            tableModel.addRow(new Object[]{
                    ticket.getId(),
                    ticket.getPrice(),
                    ticket.getStatus(),
                    ticket.getUser(),
                    ticket.getSeatNumber(),
                    ticket.getSchedule()
            });
        }

        // Crear el JTable con el modelo
        JTable ticketTable = new JTable(tableModel);
        ticketTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

        // Personalizar el renderizador de cabeceras
        JTableHeader header = ticketTable.getTableHeader();
        header.setBackground(new Color(204, 153, 255));

        // Crear un JScrollPane para el JTable
        JScrollPane scrollPane = new JScrollPane(ticketTable);
        scrollPane.setPreferredSize(new Dimension(800, 400));

        //  Images
        try {     
            BufferedImage iconImage = ImageIO.read(getClass().getResource("/images/iconticket.png"));

            if (iconImage != null) {
                ImageIcon icon = new ImageIcon(iconImage);

                JFrame frame = new JFrame(messages.getString("viewTicketTitle"));
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
