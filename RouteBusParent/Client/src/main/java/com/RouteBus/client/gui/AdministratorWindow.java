package com.RouteBus.client.gui;

import java.awt.Color;
import java.awt.Font;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class AdministratorWindow extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel appNameLabel;

	// Subtitle bar
	private JMenuBar menuBar;
	private JMenu bizInt;
	private JMenu routeAdmin;
	private JMenu busAdmin;
	private JMenu ticketsAdmin;
	private JMenu logout;

	// Most used bus(es)
	private JLabel stat1Label;
	private JList<String> stat1Info; // Turn into JTextField if all else fails
	private String busList[] = { "Bus 1", "Bus 2" };

	// Most purchased rout(es)
	private JLabel stat2Label;
	private JList<String> stat2Info; // Ditto.
	private String routeList[] = { "Route 1", "Route 2" };

	// Total revenue
	private JLabel stat3Label;
	private JTextField stat3Info;
	private ResourceBundle messages;
	public void AdministrativeWindow() {
		// Idea: For 'main' showing, most used buses, most purchased routes, and total
		// revenue (all obtained from Ticket data)
		// Buses for maintenance, routes and revenue for BI.
		Locale currentLocale = Locale.getDefault();
		messages = ResourceBundle.getBundle("multilingual/messages", currentLocale);
		this.setTitle(messages.getString("administrativeWindowTitle"));
		this.setLayout(null);
		this.setBounds(500, 500, 800, 600);
		this.setResizable(false);
		this.setBackground(new Color(204, 153, 255));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setLocationRelativeTo(null);
		JPanel contentPane = new JPanel(null);
		contentPane.setBackground(Color.WHITE);
		this.setContentPane(contentPane);
		// MenuBar
		menuBar = new JMenuBar();
		bizInt = new JMenu(messages.getString("administrativeMenu"));
		routeAdmin = new JMenu(messages.getString("routeAdministrationMenu"));
		busAdmin = new JMenu(messages.getString("busAdministrationMenu"));
		ticketsAdmin = new JMenu(messages.getString("ticketsAdministrationMenu"));
		logout = new JMenu(messages.getString("logoutMenu"));

		menuBar.add(bizInt); 
		menuBar.add(routeAdmin);
		menuBar.add(busAdmin);
		menuBar.add(ticketsAdmin);
		menuBar.add(logout);

		this.setJMenuBar(menuBar);

		// ELEMENT CREATION
		appNameLabel = new JLabel(messages.getString("appNameLabel"));
		appNameLabel.setFont(new Font("Arial", Font.BOLD, 30));
		appNameLabel.setForeground(Color.WHITE);
		appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);

		// Most used buses
		stat1Label = new JLabel(messages.getString("mostUsedBusesLabel"));
		stat1Label.setForeground(Color.WHITE);
		stat1Info = new JList<String>(busList); // List of buses goes in

		// Most purchased routes
		stat2Label = new JLabel(messages.getString("mostPurchasedRoutesLabel"));
		stat2Label.setForeground(Color.WHITE);
		stat2Info = new JList<String>(routeList); // List of routes goes in

		// Total revenue;
		stat3Label = new JLabel(messages.getString("tatalRevenueLabel"));
		stat3Label.setForeground(Color.WHITE);
		stat3Info = new JTextField();
		stat3Info.setEditable(false);
		stat3Info.setText("$1");

		// Adding things...
		appNameLabel.setBounds(0, 100, getWidth(), 30);
		contentPane.add(appNameLabel);

		stat1Label.setBounds(140, 180, 150, 30);
		contentPane.add(stat1Label);

		stat2Label.setBounds(140, 250, 150, 30);
		contentPane.add(stat2Label);

		stat3Label.setBounds(140, 320, 150, 30);
		contentPane.add(stat3Label);

		stat1Info.setBounds(240, 180, 150, 30);
		contentPane.add(stat1Label);

		stat2Info.setBounds(240, 250, 150, 30);
		contentPane.add(stat2Label);

		stat3Info.setBounds(240, 320, 30, 30);
		contentPane.add(stat3Label);

		contentPane.revalidate();

		contentPane.setVisible(true);
	}
}