package com.RouteBus.client.util;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

@SuppressWarnings("serial")
public class TableUtil {

	public static JTable createConfiguredTable(DefaultTableModel model, Color background, Color headerBackground, Color headerForeground) {
        JTable table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Disable cell editing
            }
        };

        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Single row selection
        table.setBackground(background);
        table.setForeground(Color.BLACK);
        table.setFillsViewportHeight(true);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setBackground(headerBackground);
        header.setForeground(headerForeground);

        return table;
    }
}
