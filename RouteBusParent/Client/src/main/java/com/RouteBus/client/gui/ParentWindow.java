package com.RouteBus.client.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

@SuppressWarnings("serial")
public abstract class ParentWindow extends JFrame {

    private static final String ICON = "/images/icon.jpg";

    protected Color colorPrimary;
    protected Color colorSecondary;
    protected Color colorTertiary;
    protected Color colorBackground;

    public ParentWindow() {
        loadColors();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
        getContentPane().setBackground(colorBackground);
        setWindowIcon();
    }

    @Override
    public void setVisible(boolean visible) {
        if (visible) {
            centerWindow();
        }
        super.setVisible(visible);
    }

    private void centerWindow() {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int x = (screenSize.width - getWidth()) / 2;
        int y = (screenSize.height - getHeight()) / 2;
        setLocation(x, y);
    }

    private void loadColors() {
        Properties properties = new Properties();
        try {
            properties.load(getClass().getResourceAsStream("/properties/gui.properties"));
            colorPrimary = parseColor(properties.getProperty("color.primary"));
            colorSecondary = parseColor(properties.getProperty("color.secondary"));
            colorTertiary = parseColor(properties.getProperty("color.tertiary"));
            colorBackground = parseColor(properties.getProperty("color.background"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Color parseColor(String colorString) {
        String[] rgb = colorString.split(",\\s*");
        return new Color(Integer.parseInt(rgb[0]), Integer.parseInt(rgb[1]), Integer.parseInt(rgb[2]));
    }

    protected JLabel loadImage(String imagePath, int width, int height) {
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

    private void setWindowIcon() {
        URL iconUrl = getClass().getResource(ICON);
        if (iconUrl != null) {
            ImageIcon icon = new ImageIcon(iconUrl);
            setIconImage(icon.getImage());
        } else {
            System.err.println("Icon image not found at " + ICON);
        }
    }
}