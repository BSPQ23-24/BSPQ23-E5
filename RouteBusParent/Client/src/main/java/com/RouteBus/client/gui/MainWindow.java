package com.RouteBus.client.gui;


import javax.swing.*;

import com.RouteBus.client.controller.UserController;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.imageio.ImageIO;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import com.toedter.calendar.JDateChooser;

public class MainWindow extends JFrame {
    
    private static final long serialVersionUID = 1L;
    
    // Panels
    private JPanel contentPanel;
    private JPanel newsPanel;
    private JPanel infoPanel;
    
    // Menu
    private JMenuBar menuBar;
    
    private JMenu busRouteInfo;
    private JMenu myInformation;
    private JMenu myRoutes;
    private JMenu myBusTickets;
    private JMenu language;
    private JMenu logout;
    
	private JMenuItem information;
	private JMenuItem busInfo;
	private JMenuItem routes;
	private JMenuItem tickets;
	private JMenuItem out;
    private JMenuItem spanish;
    private JMenuItem english;
    private JMenuItem basque;
    private JMenuItem german;
    
    private JTextArea noticia1;
    private JTextArea noticia2;
    
    private JLabel lUserPic;
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
	
	private ResourceBundle messages;
    
    public MainWindow() {
		Locale currentLocale = Locale.getDefault();
		messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
        this.setTitle(messages.getString("windowTitle"));
        this.setLayout(null);
        this.setBounds(500, 100, 850, 600);
        this.setResizable(false);
        
        contentPanel = new JPanel(null);
        contentPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        this.setContentPane(contentPanel);

        newsPanel = new JPanel(null);
        newsPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        newsPanel.setBackground(Color.WHITE);
        
        infoPanel = new JPanel(null);
        infoPanel.setBounds(0, 0, this.getWidth(), this.getHeight());
        infoPanel.setBackground(Color.WHITE);
        
        contentPanel.add(newsPanel);
        
        // Menu
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));
        
        //new JMenu(messages.getString("userMenu"));
        busRouteInfo = new JMenu(messages.getString("BusRouteInfo"));
        myInformation = new JMenu(messages.getString("myInformationMenu"));
        myRoutes = new JMenu(messages.getString("myRoutesMenu"));
        myBusTickets = new JMenu(messages.getString("myBusTicketsMenu"));
        language = new JMenu(messages.getString("languageMenu"));
        logout = new JMenu(messages.getString("OptionsMenu"));
        
        busInfo = new JMenuItem(messages.getString("News"));
        information = new JMenuItem(messages.getString("MyInformation"));
        routes = new JMenuItem(messages.getString("MyRoutes"));
        tickets = new JMenuItem(messages.getString("MyTickets"));
        out = new JMenuItem(messages.getString("Logout"));
        
        spanish = new JMenuItem(messages.getString("spanish"));
        english = new JMenuItem(messages.getString("english"));
        basque = new JMenuItem(messages.getString("basque"));
        german = new JMenuItem(messages.getString("german")); 
        
        // Add menu items to the user menu
        busRouteInfo.add(busInfo);
        myInformation.add(information);
        myRoutes.add(routes);
        myBusTickets.add(tickets);
        logout.add(out);
        
        // Add languages
        language.add(basque);
        language.add(english);
        language.add(german);
        language.add(spanish);
        
        menuBar.add(busRouteInfo);
        menuBar.add(myInformation);
        menuBar.add(myRoutes);
        menuBar.add(myBusTickets);
        menuBar.add(language);
        menuBar.add(logout);
        
        this.setJMenuBar(menuBar);
        
        // News Panel
        // Initialize text areas for news
        JLabel titleLabel = new JLabel(messages.getString("welcome"));
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
        titleLabel.setForeground(Color.BLACK); // Set text color
        titleLabel.setBounds(300, 50, 300, 30); // Adjust position and size as needed
        newsPanel.add(titleLabel);
        
        noticia1 = new JTextArea(messages.getString("new1Content"));
        noticia1.setEditable(false); // Make it read-only
        noticia1.setLineWrap(true); // Wrap text to next line if necessary
        noticia1.setBackground(new Color(223, 169, 245));
        
        noticia2 = new JTextArea(messages.getString("new2Content"));
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
        
        // Information Panel
        // title
        JLabel infotitle = new JLabel(messages.getString("persInfoWelcome"));
        infotitle.setFont(new Font("Arial", Font.BOLD, 20)); 
        infotitle.setForeground(Color.BLACK); 
        infotitle.setBounds(300, 50, 250, 50); 
        infoPanel.add(infotitle);
        
        // labels
        lUserPic = new JLabel(messages.getString("userPic"));
        lUserPic.setBounds(90, 370, 150,30);

        lName = new JLabel(messages.getString("nameLabel"));
        lName.setBounds(350, 110, 150, 30);
        lSurname = new JLabel(messages.getString("surnameLabel"));
        lSurname.setBounds(350, 170, 150, 30);
        lBirthDay = new JLabel(messages.getString("birthDateLabel"));
        lBirthDay.setBounds(350, 230, 150,30);
        lNationality = new JLabel(messages.getString("nationalityLabel"));
        lNationality.setBounds(350, 290, 150,30);
        lEmail = new JLabel(messages.getString("email"));
        lEmail.setBounds(350,350, 150,30);
        
        infoPanel.add(lUserPic);
        infoPanel.add(lName);
        infoPanel.add(lSurname);
        infoPanel.add(lBirthDay);
        infoPanel.add(lNationality);
        infoPanel.add(lEmail);
        
        // fields
        tName = new JTextField();
        tName.setBounds(350, 140, 150, 30);
        tSurname = new JTextField();
        tSurname.setBounds(350,200,150,30);
        dBirthDate = new JDateChooser("yyyy/MM/dd", "####/##/##", '_');
        dBirthDate.setBounds(350, 260, 150, 30);
        comboNationality = new JComboBox<String>();
        comboNationality.setBounds(350, 320, 150,30);
        //loadNationalities();
        tEmail = new JTextField();
        tEmail.setBounds(350, 380, 150, 30);
        
        infoPanel.add(tName);
        infoPanel.add(tSurname);
        infoPanel.add(dBirthDate);
        infoPanel.add(comboNationality);
        infoPanel.add(tEmail);
        
        // button
        bEdit = new JButton("Edit");
        bEdit.setBackground(new Color(204, 153, 255));
        bEdit.setBorder(null);
        bEdit.setBounds(370, 440,100,30);
        infoPanel.add(bEdit);
        
        // Actions
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
        
        // Images
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
            imageLabelU.setBounds(50, 160, 200, 200);
            
            newsPanel.add(imageLabel);
            newsPanel.add(imageLabel3);
            newsPanel.add(imageLabel4);
            infoPanel.add(imageLabel2);
            infoPanel.add(imageLabelU);
            
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        this.setVisible(true);
    }
}
