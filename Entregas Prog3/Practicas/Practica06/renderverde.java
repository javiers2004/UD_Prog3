package Practica06;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class renderverde extends DefaultTableCellRenderer{
	private int targetRow;

    public renderverde(int targetRow) {		//DEBE PONER LA FILA EN VERDE
        this.targetRow = targetRow;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        if (row == targetRow) {
            c.setBackground(Color.GREEN);
        }
        return c;
    }
}
