package com.RouteBus.client.gui;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.net.URL;

@SuppressWarnings("serial")
public abstract class AdminPanel<T> extends JPanel {

    protected Color colorPrimary;
    protected Color colorSecondary;
    protected Color colorTertiary;
    protected Color colorBackground;
    protected JTable table;
    protected DefaultTableModel tableModel;
    protected T selectedEntity;
    private JTextField searchField;

    public AdminPanel(Color colorPrimary, Color colorSecondary, Color colorTertiary, Color colorBackground) {
        this.colorPrimary = colorPrimary;
        this.colorSecondary = colorSecondary;
        this.colorTertiary = colorTertiary;
        this.colorBackground = colorBackground;
        setLayout(new BorderLayout());
        setBackground(colorBackground);
        initializeComponents();
    }

    private void initializeComponents() {
        JPanel upperPanel = new JPanel(new BorderLayout());
        upperPanel.setBackground(colorBackground);

        JPanel logoPanel = new JPanel(new GridBagLayout());
        logoPanel.setBackground(colorBackground);
        JLabel logoLabel = loadImage("/images/busroute.jpg", 325, 250);
        logoPanel.add(logoLabel);

        JPanel editingPanel = createEditingPanel();
        editingPanel.setBackground(colorBackground);

        JPanel centralPanel = new JPanel(new GridBagLayout());
        centralPanel.setBackground(colorBackground);
        GridBagConstraints centralGbc = new GridBagConstraints();
        centralGbc.insets = new Insets(0, 20, 0, 20);

        centralGbc.gridx = 0;
        centralGbc.gridy = 0;
        centralGbc.weightx = 0.5;
        centralGbc.anchor = GridBagConstraints.CENTER;
        centralPanel.add(logoPanel, centralGbc);

        JSeparator separator = new JSeparator(SwingConstants.VERTICAL);
        separator.setBackground(colorTertiary);
        centralGbc.gridx = 1;
        centralGbc.gridy = 0;
        centralGbc.weightx = 0;
        centralGbc.fill = GridBagConstraints.VERTICAL;
        centralPanel.add(separator, centralGbc);

        centralGbc.gridx = 2;
        centralGbc.gridy = 0;
        centralGbc.weightx = 0.5;
        centralGbc.fill = GridBagConstraints.NONE;
        centralPanel.add(editingPanel, centralGbc);

        upperPanel.add(centralPanel, BorderLayout.CENTER);
        add(upperPanel, BorderLayout.NORTH);

        JPanel lowerPanel = new JPanel(new BorderLayout());
        lowerPanel.setBackground(colorBackground);
        lowerPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));

        searchField = new JTextField(20);
        searchField.setForeground(colorPrimary);
        searchField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filterEntities(searchField.getText());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filterEntities(searchField.getText());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filterEntities(searchField.getText());
            }
        });
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBackground(colorBackground);
        GridBagConstraints gbcSearch = new GridBagConstraints();
        gbcSearch.insets = new Insets(5, 5, 5, 5);
        gbcSearch.gridx = 0;
        gbcSearch.gridy = 0;
        gbcSearch.anchor = GridBagConstraints.CENTER;

        JLabel searchLabel = new JLabel("Search: ");
        searchLabel.setForeground(colorSecondary);
        searchPanel.add(searchLabel, gbcSearch);

        gbcSearch.gridy = 1;
        searchPanel.add(searchField, gbcSearch);

        JPanel centeredSearchPanel = new JPanel(new GridBagLayout());
        centeredSearchPanel.setBackground(colorBackground);
        GridBagConstraints centeredGbc = new GridBagConstraints();
        centeredGbc.gridx = 0;
        centeredGbc.gridy = 0;
        centeredGbc.insets = new Insets(0, 0, 20, 0);
        centeredGbc.anchor = GridBagConstraints.CENTER;
        centeredSearchPanel.add(searchPanel, centeredGbc);

        lowerPanel.add(centeredSearchPanel, BorderLayout.NORTH);

        table = createTable();
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent event) {
                if (!event.getValueIsAdjusting() && table.getSelectedRow() != -1) {
                    populateFieldsFromSelectedRow();
                }
            }
        });
        JScrollPane tableScrollPane = new JScrollPane(table);
        lowerPanel.add(tableScrollPane, BorderLayout.CENTER);

        add(lowerPanel, BorderLayout.CENTER);

        loadTableData();
    }

    protected abstract JPanel createEditingPanel();

    protected abstract JTable createTable();

    protected abstract void loadTableData();

    protected abstract void filterEntities(String query);

    protected abstract void populateFieldsFromSelectedRow();

    protected abstract void clearFields();

    protected abstract void editEntity();

    protected abstract void deleteEntity();

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
