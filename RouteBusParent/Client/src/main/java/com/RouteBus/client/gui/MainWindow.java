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
    
    private JMenuBar menuBar;
    
    private JMenu myInformation;
    private JMenu myRoutes;
    private JMenu myBusTickets;
    private JMenu language;
    private JMenu logout;
    
    private JMenuItem spanish;
    private JMenuItem english;
    private JMenuItem basque;
    private JMenuItem german;
    
    private JTextArea noticia1;
    private JTextArea noticia2;
	
	
    private JPanel contentPanel;
    private JPanel newsPanel;
    private JPanel infoPanel;
    
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
        infoPanel.setBackground(Color.BLACK);
        
        contentPanel.add(newsPanel);
        
        menuBar = new JMenuBar();
        menuBar.setBackground(new Color(204, 153, 255));
        
        new JMenu(messages.getString("userMenu"));
        myInformation = new JMenu(messages.getString("myInformationMenu"));
        myRoutes = new JMenu(messages.getString("myRoutesMenu"));
        myBusTickets = new JMenu(messages.getString("myBusTicketsMenu"));
        language = new JMenu(messages.getString("languageMenu"));
        logout = new JMenu(messages.getString("logoutMenu"));
        
        spanish = new JMenuItem(messages.getString("spanish"));
        english = new JMenuItem(messages.getString("english"));
        basque = new JMenuItem(messages.getString("basque"));
        german = new JMenuItem(messages.getString("german")); 
        // Add menu items to the user menu
        menuBar.add(myInformation);
        menuBar.add(myRoutes);
        menuBar.add(myBusTickets);
        menuBar.add(language);
        menuBar.add(logout);
        
        language.add(spanish);
        language.add(english);
        language.add(basque);
        language.add(german);
        this.setJMenuBar(menuBar);
                
        // Initialize text areas for news
        
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
        
        myInformation.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
                // Remove the news panel
                contentPanel.remove(newsPanel);
                // Add the user info panel
                contentPanel.add(infoPanel);
                // Repaint the content panel
                contentPanel.revalidate();
                contentPanel.repaint();
				
			}
		});
        
        JLabel titleLabel = new JLabel("Welcome back!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20)); // Set font and size
        titleLabel.setForeground(Color.BLACK); // Set text color
        titleLabel.setBounds(350, 50, 200, 30); // Adjust position and size as needed
        newsPanel.add(titleLabel);
        
        try {
            BufferedImage logo = ImageIO.read(getClass().getResource("/images/icon.jpg"));
            BufferedImage imag1 = ImageIO.read(getClass().getResource("/images/imag1.jpg"));
            BufferedImage imag2 = ImageIO.read(getClass().getResource("/images/imag2.jpg"));
            
            ImageIcon icon = new ImageIcon(logo);
            ImageIcon icon2 = new ImageIcon(logo);
            ImageIcon imagIcon1 = new ImageIcon(imag1);
            ImageIcon imagIcon2 = new ImageIcon(imag2);
            
            JLabel imageLabel = new JLabel(icon);
            JLabel imageLabel2 = new JLabel(icon2);
            JLabel imageLabel3 = new JLabel(imagIcon1);
            JLabel imageLabel4 = new JLabel(imagIcon2);
            
            imageLabel.setBounds(590, -70, 200, 300); // Adjust position and size as needed
            imageLabel2.setBounds(590,-70, 200, 300);
            imageLabel3.setBounds(30,180,140,100);
            imageLabel4.setBounds(30,340,140,100);
            
            newsPanel.add(imageLabel);
            newsPanel.add(imageLabel3);
            newsPanel.add(imageLabel4);
            infoPanel.add(imageLabel2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        

        
        this.setVisible(true);
    }
}
