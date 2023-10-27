package Practica06;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class customTableCellRenderer extends DefaultTableCellRenderer{

	private JProgressBar progressBar = new JProgressBar(50000, 5000000);  

    public customTableCellRenderer() {
        super();
        setOpaque(true);
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        if (value instanceof Integer) {
            int progress = (int) value;
            progressBar.setValue(progress);
            progressBar.setForeground(getColorForProgress((int)value));

        }

        return progressBar;
    }

    private Color getColorForProgress(int progress) {		//PARA CONFIGURAR EL COLOR DE LA PROGRESSBAR
        if (progress < 2500000) {
            // Verde a amarillo para el progreso del 0% al 50%
            int red = (int) (255 * (progress / 2500000));		// A MÁS HABITANTES, MÁS ROJO -> MÁS AMARILLO
            int green = 255;									//VERDE AL MAXIMO
            int blue = 0;
            return new Color(red, green, blue);
        } else {
            // Amarillo a rojo para el progreso del 51% al 100%
            int red = 255;																//ROJO AL MÁXIMO
            int green = (int) (255 - 255 * ((progress - 2500000) / 2500000));			//A MÁS HABITANTES, MENOS VERDE -> MÁS ROJO
            int blue = 0;
            return new Color(red, green, blue);
        }
    }
	
	
	
	
	
}
