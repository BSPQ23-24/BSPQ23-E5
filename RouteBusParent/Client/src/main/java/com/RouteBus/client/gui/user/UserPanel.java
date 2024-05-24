package com.RouteBus.client.gui.user;

import javax.swing.*;
import java.awt.*;
import java.util.ResourceBundle;

@SuppressWarnings("serial")
public abstract class UserPanel extends JPanel {
    protected ResourceBundle messages;
    protected Color colorBackground;
    protected Color colorPrimary;
    protected Color colorSecondary;
    protected Color colorTertiary;

    public UserPanel(ResourceBundle messages, Color colorBackground, Color colorPrimary, Color colorSecondary, Color colorTertiary) {
        this.messages = messages;
        this.colorBackground = colorBackground;
        this.colorPrimary = colorPrimary;
        this.colorSecondary = colorSecondary;
        this.colorTertiary = colorTertiary;
        this.setBackground(colorBackground);
        this.setLayout(new BorderLayout());
    }

    public abstract void updateTexts(ResourceBundle messages);

    protected JLabel loadImage(String path, int width, int height) {
        ImageIcon imageIcon = new ImageIcon(getClass().getResource(path));
        Image image = imageIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new JLabel(new ImageIcon(image));
    }
}
