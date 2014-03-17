/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package vn.edu.vttu.data;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author nhphuoc
 */
public class ColoredTableCellRenderer extends DefaultTableCellRenderer {

    public Component getTableCellRendererComponent(JTable table, Object value, boolean selected, boolean focused, int row, int column) {
        setEnabled(table == null || table.isEnabled()); // see question above

        if ((row % 2) == 0) {
            setBackground(Color.WHITE);
        } else {
            setBackground(Color.getHSBColor(153, 205, 155));
        }

        super.getTableCellRendererComponent(table, value, selected, focused, row, column);

        return this;
    }
}
