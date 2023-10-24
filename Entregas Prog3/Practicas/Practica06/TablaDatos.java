package Practica06;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

public class TablaDatos extends JTable{

	public TablaDatos(List<Municipio> dataset) {
        Object[][] data = new Object[dataset.size()][6];

        for (int i = 0; i < dataset.size(); i++) {
            data[i][0] = dataset.get(i).getNombre();
            data[i][1] = dataset.get(i).getHabitantes();
            data[i][2] = dataset.get(i).getProvincia();
            data[i][3] = dataset.get(i).getAutonomia();
            data[i][4] = dataset.get(i).getDensidad();
            data[i][5] = dataset.get(i).getLluvia();
        }
	    String[] columnNames = {"Nombre", "Habitantes", "Provincia", "Comunidad", "Densidad", "Lluvia" };
	    DefaultTableModel model = new DefaultTableModel(data, columnNames);
	    new JTable(model);

	    
	}
}
