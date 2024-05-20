package com.RouteBus.client.gui;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public class NewsPanel extends JPanel {
    private JLabel titleLabel;

    public NewsPanel(ResourceBundle messages, Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground) {
        this.setLayout(new BorderLayout());
        this.setBackground(colorBackground);

        titleLabel = new JLabel(messages.getString("welcome"));
        titleLabel.setFont(new Font("Serif", Font.BOLD, 28));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titleLabel, BorderLayout.NORTH);

        JPanel newsContentPanel = new JPanel();
        newsContentPanel.setLayout(new BoxLayout(newsContentPanel, BoxLayout.Y_AXIS));
        newsContentPanel.setBackground(colorBackground);

        Color[] borderColors = {colorPrimary, colorSecondary, colorTertiary};

        for (int i = 1; i <= 10; i++) {
            JPanel newsItem = new JPanel();
            newsItem.setBorder(BorderFactory.createLineBorder(borderColors[i % borderColors.length], 2));
            newsItem.setBackground(colorBackground);
            newsItem.setLayout(new BorderLayout(10, 10));

            JLabel newsImage = loadImage("/images/InitialImage" + ((i % 7) + 1) + ".jpg", 100, 100);
            newsImage.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));

            JPanel textPanel = new JPanel();
            textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
            textPanel.setBackground(colorBackground);

            JLabel newsTitle = new JLabel(messages.getString("new" + i + "Title"));
            newsTitle.setFont(new Font("Serif", Font.BOLD, 20));
            newsTitle.setAlignmentX(Component.LEFT_ALIGNMENT);

            JTextArea newsText = new JTextArea(messages.getString("new" + i + "Content"));
            newsText.setEditable(false);
            newsText.setLineWrap(true);
            newsText.setWrapStyleWord(true);
            newsText.setFont(new Font("SansSerif", Font.PLAIN, 16));
            newsText.setBackground(colorBackground);
            newsText.setAlignmentX(Component.LEFT_ALIGNMENT);

            textPanel.add(newsTitle);
            textPanel.add(Box.createVerticalStrut(10));
            textPanel.add(newsText);

            newsItem.add(newsImage, BorderLayout.WEST);
            newsItem.add(textPanel, BorderLayout.CENTER);

            newsContentPanel.add(newsItem);
            newsContentPanel.add(Box.createVerticalStrut(20));
        }

        JScrollPane scrollPane = new JScrollPane(newsContentPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    public void updateTexts(ResourceBundle messages) {
        titleLabel.setText(messages.getString("welcome"));

        Component[] components = ((JPanel) ((JScrollPane) this.getComponent(1)).getViewport().getView()).getComponents();
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof JPanel) {
                JPanel newsItem = (JPanel) components[i];
                JLabel newsTitle = (JLabel) ((JPanel) newsItem.getComponent(1)).getComponent(0);
                JTextArea newsText = (JTextArea) ((JPanel) newsItem.getComponent(1)).getComponent(2);

                newsTitle.setText(messages.getString("new" + (i + 1) + "Title"));
                newsText.setText(messages.getString("new" + (i + 1) + "Content"));
            }
        }
    }

    private JLabel loadImage(String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(path));
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(image));
    }
}
