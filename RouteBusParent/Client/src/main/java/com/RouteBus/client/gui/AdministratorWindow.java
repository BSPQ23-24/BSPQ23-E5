package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import java.util.List;
import java.util.stream.Collectors;
import com.RouteBus.client.controller.*;
import com.RouteBus.client.dto.*;

@SuppressWarnings("serial")
public class AdministratorWindow extends ParentWindow {

    public AdministratorWindow() {
        super();
        setTitle("Administration Window");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu bar
        JMenuBar menuBar = new JMenuBar();
        menuBar.setBackground(colorSecondary);
        JMenu routeAdmin = new JMenu("Route administration");
        JMenu busAdmin = new JMenu("Bus administration");
        JMenu ticketsAdmin = new JMenu("Tickets administration");
        JMenu logout = new JMenu("Log out");
        routeAdmin.setForeground(Color.WHITE);
        busAdmin.setForeground(Color.WHITE);
        ticketsAdmin.setForeground(Color.WHITE);
        logout.setForeground(Color.WHITE);
        menuBar.add(routeAdmin);
        menuBar.add(busAdmin);
        menuBar.add(ticketsAdmin);
        menuBar.add(logout);
        setJMenuBar(menuBar);

        // Main panel
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 2));
        mainPanel.setBackground(colorBackground);

        // Adding the BusRoute image
        JLabel imageLabel = loadImage("/images/busroute.jpg", 400, 300);
        mainPanel.add(imageLabel);

        // Adding the line chart
        JFreeChart lineChart = createLineChart();
        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        lineChartPanel.setBackground(colorBackground);
        mainPanel.add(lineChartPanel);

        // Adding the pie chart
        JFreeChart pieChart = createPieChart();
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setBackground(colorBackground);
        mainPanel.add(pieChartPanel);

        // Creating the top routes panel
        JPanel topRoutesPanel = new JPanel(new BorderLayout());
        topRoutesPanel.setBackground(colorBackground);

        JLabel topRoutesLabel = new JLabel("Top Routes", JLabel.CENTER);
        topRoutesLabel.setForeground(colorPrimary);
        topRoutesLabel.setFont(pieChart.getTitle().getFont()); // Set the font to match "Routes distribution"
        topRoutesPanel.add(topRoutesLabel, BorderLayout.NORTH);

        JScrollPane tableScrollPane = new JScrollPane(createTopRoutesTable());
        topRoutesPanel.add(tableScrollPane, BorderLayout.CENTER);
        mainPanel.add(topRoutesPanel);

        add(mainPanel, BorderLayout.CENTER);
    }

    private JFreeChart createLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        List<ScheduleDTO> schedules = ScheduleController.getInstance().getAllSchedules().stream().collect(Collectors.toList());
        int week = 1;
        for (ScheduleDTO schedule : schedules) {
            dataset.addValue(schedule.getTickets().size(), "Travellers", "Week " + week);
            week++;
        }

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Nº travellers per week",
                "Week",
                "Travellers",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false);

        lineChart.setBackgroundPaint(colorBackground);
        lineChart.getTitle().setPaint(colorPrimary);

        CategoryPlot plot = lineChart.getCategoryPlot();
        plot.setBackgroundPaint(colorBackground);
        plot.setDomainGridlinePaint(colorPrimary);
        plot.setRangeGridlinePaint(colorPrimary);
        plot.getDomainAxis().setLabelPaint(colorPrimary);
        plot.getRangeAxis().setLabelPaint(colorPrimary);
        plot.getRenderer().setSeriesPaint(0, colorPrimary);

        return lineChart;
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        List<RouteDTO> routes = RouteController.getInstance().getAllRoutes();
        for (RouteDTO route : routes) {
            dataset.setValue(route.getName(), route.getBuses().size());
        }

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Routes distribution",
                dataset,
                true, true, false);

        @SuppressWarnings("unchecked")
        PiePlot<String> plot = (PiePlot<String>) pieChart.getPlot();
        plot.setBackgroundPaint(colorBackground);
        pieChart.setBackgroundPaint(colorBackground);
        pieChart.getTitle().setPaint(colorPrimary);

        // Customize pie chart colors
        int colorIndex = 0;
        Color[] pieColors = {colorPrimary, colorSecondary, colorTertiary};
        for (String key : dataset.getKeys()) {
            plot.setSectionPaint(key, pieColors[colorIndex % pieColors.length]);
            colorIndex++;
        }

        // Set label colors
        plot.setLabelBackgroundPaint(colorTertiary);
        plot.setLabelPaint(Color.BLACK);

        return pieChart;
    }

    private JTable createTopRoutesTable() {
        String[] columns = {"Popularity", "Route", "Travellers"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        List<RouteDTO> routes = RouteController.getInstance().getAllRoutes();
        routes.sort((r1, r2) -> Integer.compare(r2.getBuses().size(), r1.getBuses().size()));

        for (int i = 0; i < Math.min(3, routes.size()); i++) {
            RouteDTO route = routes.get(i);
            model.addRow(new Object[]{i + 1, route.getName(), route.getBuses().size()});
        }

        JTable table = new JTable(model);
        table.setBackground(colorBackground);
        table.setForeground(Color.BLACK);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(colorSecondary);
        header.setForeground(Color.WHITE);

        return table;
    }

}
