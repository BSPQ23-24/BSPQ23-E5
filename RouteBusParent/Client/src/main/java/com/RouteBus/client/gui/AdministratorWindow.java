package com.RouteBus.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.RouteBus.client.gateway.ServerGateway;


public class AdministratorWindow extends JFrame{

    private static final long serialVersionUID = 1L;
	private ServerGateway serverGateway;
	private JLabel appNameLabel;

	//Subtitle bar
	private JMenuBar menuBar;
	private JMenu bizInt;
	private JMenu routeAdmin;
    private JMenu busAdmin;
    private JMenu ticketsAdmin;
	private JMenu logout;
	
	//Most used bus(es)
	private JLabel stat1Label;
	private JList stat1Info; //Turn into JTextField if all else fails
	private String busList[] = {"Bus 1", "Bus 2"};

	//Most purchased rout(es)
	private JLabel stat2Label;
	private JList stat2Info; //Ditto.
	private String routeList[] = {"Route 1", "Route 2"};
	
	//Total revenue
	private JLabel stat3Label;
	private JTextField stat3Info;
	
	public void AdministrativeWindow(ServerGateway sg) {
		//Idea: For 'main' showing, most used buses, most purchased routes, and total revenue (all obtained from Ticket data)
		//Buses for maintenance, routes and revenue for BI.
		this.setTitle("RouteBus Administrative Window");
		this.setLayout(null);
		this.setBounds(500, 500, 800, 600);
		this.setResizable(false);
		this.setBackground(new Color(204, 153, 255));

		//MenuBar
		menuBar = new JMenuBar();
		bizInt = new JMenu("Administrative View");
		routeAdmin = new JMenu("Route Administration");
		busAdmin = new JMenu("Bus Administration");
		ticketsAdmin = new JMenu("Tickets Administration");
		logout = new JMenu("Log Out");
		
		menuBar.add(bizInt);
		menuBar.add(routeAdmin);
		menuBar.add(busAdmin);
		menuBar.add(ticketsAdmin);
		menuBar.add(logout);

		this.setJMenuBar(menuBar);
		
		// ELEMENT CREATION
		appNameLabel = new JLabel("RouteBus - Integrated Business Intelligence Module (IBIM)");
		appNameLabel.setFont(new Font("Arial", Font.BOLD, 30));
		appNameLabel.setForeground(Color.WHITE);
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Most used buses
		stat1Label = new JLabel("Most used buses:");
		stat1Label.setForeground(Color.WHITE);
		stat1Info = new JList(busList); //List of buses goes in
		
		//Most purchased routes
		stat2Label = new JLabel("Most purchased routes:");
		stat2Label.setForeground(Color.WHITE);
		stat2Info = new JList(routeList); //List of routes goes in
		
		
		//Total revenue;
		stat3Label = new JLabel("Total Revenue:");
		stat3Label.setForeground(Color.WHITE);
		stat3Info = new JTextField();
		stat3Info.setEditable(false);
		stat3Info.setText("$1");

		//Adding things...
        appNameLabel.setBounds(0, 100, getWidth(), 30);
        this.add(appNameLabel);
        
		stat1Label.setBounds(140, 180, 150, 30);
		this.add(stat1Label);

		stat2Label.setBounds(140, 250, 150, 30);
		this.add(stat2Label);

		stat3Label.setBounds(140, 320, 150, 30);
		this.add(stat3Label);

		stat1Info.setBounds(240, 180, 150, 30);
		this.add(stat1Label);

		stat2Info.setBounds(240, 250, 150, 30);
		this.add(stat2Label);

		stat3Info.setBounds(240, 320, 30, 30);
		this.add(stat3Label);

		this.revalidate();
		
		this.setVisible(true);
		}

}
