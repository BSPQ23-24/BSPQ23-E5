package com.RouteBus.client.gui;


import javax.swing.*;

import com.RouteBus.client.controller.UserController;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.toedter.calendar.JDateChooser;

public class MainWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    private JMenuBar menuBar;
    
    private JMenu myInformation;
    private JMenu myRoutes;
    private JMenu myBusTickets;
    private JMenu language;
    private JMenu logout;
    
    private JMenuItem spanish;
    private JMenuItem english;
    private JMenuItem basque;
    
    private JTextArea noticia1;
    private JTextArea noticia2;
    
    
	private JLabel lName;
	private JLabel lSurname;
	private JLabel lEmail;
	private JLabel lBirthDay;
	private JLabel lNationality;

	
	private JTextField tName;
	private JTextField tSurname;
	private JTextField tEmail;
	private JTextField tID;
	
	private JButton bEdit;
	private JDateChooser dBirthDate;
	
	private JComboBox<String> comboNationality;
	
	
    private JPanel contentPanel;
    private JPanel newsPanel;
    private JPanel infoPanel;

	private JLabel lUserPic;

	private JMenuItem information;

	private JMenu busRouteInfo;

	private JMenuItem busInfo;

	private JMenuItem routes;

	private JMenuItem tickets;

	private JMenuItem out;
    
    public MainWindow() {
        this.setTitle("Client main window");
        this.setLayout(null);
        this.setBounds(500, 100, 850, 600);
        this.setResizable(false);
        
        // Panels
        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(contentPanel);

        newsPanel = new JPanel(null);
        newsPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        newsPanel.setBackground(Color.WHITE);
        
        infoPanel = new JPanel(null);
        infoPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        infoPanel.setBackground(Color.WHITE);
        
        //contentPanel.add(newsPanel);
        
        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));
        
        busRouteInfo = new JMenu("BusRoute Info");
        myInformation = new JMenu("Personal Info");
        myRoutes = new JMenu("Routes Info");
        myBusTickets = new JMenu("Tickets Info");
        language = new JMenu("Language");
        logout = new JMenu("Options");
        
        busInfo = new JMenuItem("News");
        busRouteInfo.add(busInfo);
        information = new JMenuItem("My Information");
        myInformation.add(information);
        routes = new JMenuItem("My Routes");
        myRoutes.add(routes);
        tickets = new JMenuItem("My Tickets");
        myBusTickets.add(tickets);
        out = new JMenuItem("Log out");
        logout.add(out);
        spanish = new JMenuItem("Spanish");
        english = new JMenuItem("English");
        basque = new JMenuItem("Basque");
        language.add(spanish);
        language.add(english);
        language.add(basque);
        
        // Add menu items to the user menu
        menuBar.add(busRouteInfo);
        menuBar.add(myInformation);
        menuBar.add(myRoutes);
        menuBar.add(myBusTickets);
        menuBar.add(language);
        menuBar.add(logout);
        
        this.setJMenuBar(menuBar);
        
        // News Panel
        noticia1 = new JTextArea("New routes available!\n\nExplore exciting destinations with our state-of-the-art bus services. Book your tickets now and embark on an unforgettable journey. Don't miss out on our limited-time offer!");
        noticia1.setEditable(false); // Make it read-only
        noticia1.setLineWrap(true); // Wrap text to next line if necessary
        noticia1.setBackground(new Color(223, 169, 245));
        
        noticia2 = new JTextArea("Spring special offer!\n\nBook your tickets now and get a 20% discount on all selected routes. Don't miss out on this limited-time deal and start planning your next adventure today! Explore new destinations and make unforgettable memories with RouteBus.");
        noticia2.setEditable(false); // Make it read-only
        noticia2.setLineWrap(true); // Wrap text to next line if necessary
        noticia2.setBackground(new Color(223, 169, 245));
        
        JScrollPane scrollPane1 = new JScrollPane(noticia1);
        scrollPane1.setBounds(200, 180, 600, 100);
        scrollPane1.setBorder(null);
        
        JScrollPane scrollPane2 = new JScrollPane(noticia2);
        scrollPane2.setBounds(200, 340, 600, 100);
        scrollPane2.setBorder(null);

        newsPanel.add(scrollPane1);
        newsPanel.add(scrollPane2);
        
        try {
            BufferedImage logo = ImageIO.read(getClass().getResource("/images/icon.jpg"));
            BufferedImage imag1 = ImageIO.read(getClass().getResource("/images/imag1.jpg"));
            BufferedImage imag2 = ImageIO.read(getClass().getResource("/images/imag2.jpg"));
            BufferedImage imagUser = ImageIO.read(getClass().getResource("/images/imagUser.jpg"));
            
            ImageIcon icon = new ImageIcon(logo);
            ImageIcon icon2 = new ImageIcon(logo);
            ImageIcon imagIcon1 = new ImageIcon(imag1);
            ImageIcon imagIcon2 = new ImageIcon(imag2);
            ImageIcon imagIconU = new ImageIcon(imagUser);
            
            JLabel imageLabel = new JLabel(icon);
            JLabel imageLabel2 = new JLabel(icon2);
            JLabel imageLabel3 = new JLabel(imagIcon1);
            JLabel imageLabel4 = new JLabel(imagIcon2);
            JLabel imageLabelU = new JLabel(imagIconU);
            
            imageLabel.setBounds(590, -70, 200, 300); // Adjust position and size as needed
            imageLabel2.setBounds(590,-70, 200, 300);
            imageLabel3.setBounds(30,180,140,100);
            imageLabel4.setBounds(30,340,140,100);
            imageLabelU.setBounds(50, 150, 200, 200);
            
            newsPanel.add(imageLabel);
            newsPanel.add(imageLabel3);
            newsPanel.add(imageLabel4);
            infoPanel.add(imageLabel2);
            infoPanel.add(imageLabelU);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        // Information Panel
        // title
        JLabel infotitle = new JLabel("Your Personal Information");
        infotitle.setFont(new Font("Arial", Font.BOLD, 20)); 
        infotitle.setForeground(Color.BLACK); 
        infotitle.setBounds(300, 50, 250, 50); 
        infoPanel.add(infotitle);
        
        // labels
        lUserPic = new JLabel("Your User Picture");
        lUserPic.setBounds(100, 350, 150,30);

        lName = new JLabel("Name:");
        lName.setBounds(350, 170, 150, 30);
        lSurname = new JLabel("Surname:");
        lSurname.setBounds(350, 230, 150, 30);
        lBirthDay = new JLabel("Birthdate:");
        lBirthDay.setBounds(350, 290, 150,30);
        lNationality = new JLabel("Nationality:");
        lNationality.setBounds(350, 350, 150,30);
        lEmail = new JLabel("Email");
        lEmail.setBounds(350,410, 150,30);
        
        infoPanel.add(lUserPic);
        infoPanel.add(lName);
        infoPanel.add(lSurname);
        infoPanel.add(lBirthDay);
        infoPanel.add(lNationality);
        infoPanel.add(lEmail);
        
        // fields
        tID = new JTextField();
        tID.setEditable(false);
        tID.setBounds(350, 140, 150,30);
        tName = new JTextField();
        tName.setBounds(350, 200, 150, 30);
        tSurname = new JTextField();
        tSurname.setBounds(350,260,150,30);
        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
        dBirthDate.setBounds(350, 320, 150, 30);
        comboNationality = new JComboBox<String>();
        comboNationality.setBounds(350, 380, 150,30);
        loadNationalities();
        tEmail = new JTextField();
        tEmail.setBounds(350, 440, 150, 30);
        
        infoPanel.add(tID);
        infoPanel.add(tName);
        infoPanel.add(tSurname);
        infoPanel.add(dBirthDate);
        infoPanel.add(comboNationality);
        infoPanel.add(tEmail);
        
        // button
        bEdit = new JButton("Edit");
        bEdit.setBackground(new Color(204, 153, 255));
        bEdit.setBorder(null);
        bEdit.setBounds(530, 480,100,30);
        
        infoPanel.add(bEdit);
        information.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				getContentPane().removeAll();
                // Add the user info panel
                contentPanel.add(infoPanel);
                // Repaint the content panel
                contentPanel.revalidate();
                contentPanel.repaint();
				
			}
		});
        
        busInfo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
                // Remove the news panel
                getContentPane().removeAll();
                // Add the user info panel
                contentPanel.add(newsPanel);
                // Repaint the content panel
                contentPanel.revalidate();
                contentPanel.repaint();
			}
		});
        JLabel newstitle = new JLabel("Welcome back!");
        newstitle.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
        newstitle.setForeground(Color.BLACK); // Set text color
        newstitle.setBounds(350, 50, 200, 30); // Adjust position and size as needed
        newsPanel.add(newstitle);
        
        contentPanel.add(newsPanel);
        this.setVisible(true);
    }
    
    private void loadNationalities() {
        List<String> nationalities = UserController.getInstance().getNationalities();
         for (String nationality : nationalities) {
             comboNationality.addItem(nationality);
         }
     }
}

