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
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import com.RouteBus.client.gateway.ServerGateway;


public class AdministratorWindow extends JFrame{

    private static final long serialVersionUID = 1L;
	private ServerGateway serverGateway;
	private JLabel appNameLabel;
	
	//Most used bus(es)
	private JLabel stat1Label;
	private JList stat1Info; //Turn into JTextField if all else fails

	//Most purchased rout(es)
	private JLabel stat2Label;
	private JList stat2Info; //Ditto.
	
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
		
		URL urlBackGround;
		
		try {
			urlBackGround = new URL("https://www.caf.com/media/6860/las-carreteras-de-america-latina-no-estan-suficientemente-preparadas-para-enfrentar-el-cambio-climatico.jpg");
			Image imageBackground = ImageIO.read(urlBackGround).getScaledInstance(400, 600, java.awt.Image.SCALE_SMOOTH);
			this.setContentPane(new JLabel(new ImageIcon(imageBackground)));
		} catch (Exception e) {
			System.out.println("# Problem while setting the background: " + e);
		}
		
		// ELEMENT CREATION
		appNameLabel = new JLabel("RouteBus - Integrated Business Intelligence Module (IBIM)");
		appNameLabel.setFont(new Font("Arial", Font.BOLD, 30));
		appNameLabel.setForeground(Color.WHITE);
        appNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		//Most 
		stat1Label = new JLabel("Most used buses:");
		stat1Label.setForeground(Color.WHITE);
		
		//Most purchased routes
		stat2Label = new JLabel("Most purchased routes:");
		stat2Label.setForeground(Color.WHITE);
		
		//Total revenue;
		stat3Label = new JLabel("Total Revenue:");
		stat3Label.setForeground(Color.WHITE);
		stat3Info = new JTextField();
		stat3Info.setEditable(false);
		stat3Info.setText("$1");

        appNameLabel.setBounds(0, 100, getWidth(), 30);
        this.add(appNameLabel);
        
		stat1Label.setBounds(140, 180, 150, 30);
		this.add(stat1Label);

		stat2Label.setBounds(140, 250, 150, 30);
		this.add(stat2Label);

		stat3Label.setBounds(140, 320, 150, 30);
		this.add(stat3Label);

		this.revalidate();
		
		this.setVisible(true);
		}

}
