package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import com.RouteBus.client.controller.RouteController;
import com.RouteBus.client.controller.ScheduleController;
import com.RouteBus.client.dto.RouteDTO;
import com.RouteBus.client.util.TableUtil;

import java.awt.*;
import java.net.URL;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
public class HomeAdminPanel extends JPanel {

    private Color colorPrimary;
    private Color colorSecondary;
    private Color colorTertiary;
    private Color colorBackground;

    public HomeAdminPanel(Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground) {
        this.colorPrimary = colorPrimary;
        this.colorSecondary = colorSecondary;
        this.colorTertiary = colorTertiary;
        this.colorBackground = colorBackground;
        setLayout(new GridBagLayout());
        setBackground(colorBackground);
        initializeComponents();
    }

    private void initializeComponents() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        // Adding the BusRoute image
        JLabel imageLabel = loadImage("/images/busroute.jpg", 400, 300);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        gbc.weighty = 0.5;
        add(imageLabel, gbc);

        // Adding the line chart
        JFreeChart lineChart = createLineChart();
        ChartPanel lineChartPanel = new ChartPanel(lineChart);
        lineChartPanel.setBackground(colorBackground);
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        add(lineChartPanel, gbc);

        // Adding the pie chart
        JFreeChart pieChart = createPieChart();
        ChartPanel pieChartPanel = new ChartPanel(pieChart);
        pieChartPanel.setBackground(colorBackground);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(pieChartPanel, gbc);

        // Creating the top routes panel
        JPanel topRoutesPanel = new JPanel(new BorderLayout());
        topRoutesPanel.setBackground(colorBackground);

        JLabel topRoutesLabel = new JLabel("Top Routes", JLabel.CENTER);
        topRoutesLabel.setForeground(colorPrimary);
        topRoutesLabel.setFont(pieChart.getTitle().getFont());
        topRoutesPanel.add(topRoutesLabel, BorderLayout.NORTH);

        JScrollPane tableScrollPane = new JScrollPane(createTopRoutesTable());
        topRoutesPanel.add(tableScrollPane, BorderLayout.CENTER);
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        add(topRoutesPanel, gbc);
    }

    private JFreeChart createLineChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        Map<String, Long> weeklyTravellers = ScheduleController.getInstance().getWeeklyTravellersData();

        weeklyTravellers.forEach((week, travellers) -> dataset.addValue(travellers, "Travellers", week));

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
        plot.getDomainAxis().setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        return lineChart;
    }

    private JFreeChart createPieChart() {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();

        List<RouteDTO> routes = RouteController.getInstance().getAllRoutes();
        for (RouteDTO route : routes) {
            int totalPassengers = route.getTotalPassengers();
            dataset.setValue(route.getName(), totalPassengers);
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

        int colorIndex = 0;
        Color[] pieColors = {colorPrimary, colorSecondary, colorTertiary};
        for (String key : dataset.getKeys()) {
            plot.setSectionPaint(key, pieColors[colorIndex % pieColors.length]);
            colorIndex++;
        }

        plot.setLabelBackgroundPaint(colorTertiary);
        plot.setLabelPaint(Color.BLACK);

        return pieChart;
    }

    private JTable createTopRoutesTable() {
        String[] columns = {"Popularity", "Route", "Travellers"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        List<RouteDTO> routes = RouteController.getInstance().getRoutesOrderedByPopularity();

        for (int i = 0; i < routes.size(); i++) {
            RouteDTO route = routes.get(i);
            int totalPassengers = route.getTotalPassengers();
            model.addRow(new Object[]{i + 1, route.getName(), totalPassengers});
        }

        return TableUtil.createConfiguredTable(model, colorBackground, colorSecondary, Color.WHITE);
    }

    private JLabel loadImage(String imagePath, int width, int height) {
        URL imageUrl = getClass().getResource(imagePath);
        if (imageUrl != null) {
            ImageIcon icon = new ImageIcon(imageUrl);
            JLabel imageLabel = new JLabel(icon);
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            imageLabel.setVerticalAlignment(JLabel.CENTER);
            imageLabel.setOpaque(true);
            imageLabel.setBackground(colorBackground);
            imageLabel.setIcon(resizeIcon(icon, width, height));
            return imageLabel;
        } else {
            JLabel imageLabel = new JLabel("Image Not Found");
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            return imageLabel;
        }
    }

    private Icon resizeIcon(ImageIcon icon, int resizedWidth, int resizedHeight) {
        Image img = icon.getImage();
        Image resizedImage = img.getScaledInstance(resizedWidth, resizedHeight, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }
}
