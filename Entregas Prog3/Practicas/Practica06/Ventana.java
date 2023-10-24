package Practica06;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;

public class Ventana extends JFrame{
	public JLabel label;
	public JTree treeizq;
	public JScrollPane barratree;
	public JTable tablacen;
	public JScrollPane barratable;
	public JPanel panelder;
	public JPanel botonera;
	public JButton botoninsercion;
	public JButton botonborrado;
	public JButton botonorden;
	
	public Ventana(DataSetMunicipios dsmunicipios) {
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		label = new JLabel();
		this.add(label, BorderLayout.NORTH);
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("Municipios");
		List<Municipio> lMunicipios = dsmunicipios.getListaMunicipios();
		Set<String> listacomunidadesnorepetidas = new HashSet<String>();
		for(Municipio municipio : lMunicipios) {
			listacomunidadesnorepetidas.add(municipio.getAutonomia());
		}
		for (String elemento : listacomunidadesnorepetidas) {
			DefaultMutableTreeNode subnodo = new DefaultMutableTreeNode(elemento);
			root.add(subnodo);
		}
		for (Municipio municipio : lMunicipios) {
			for(int i = 0; i<root.getChildCount();i++) {
				DefaultMutableTreeNode subnodo = (DefaultMutableTreeNode) root.getChildAt(i);
				if(subnodo.getUserObject().equals(municipio.getAutonomia())) {
					boolean duplicado = false;
					for (int e = 0; e <subnodo.getChildCount();e++) {
						DefaultMutableTreeNode subsubnodo = (DefaultMutableTreeNode) subnodo.getChildAt(e);
						if(subsubnodo.getUserObject().equals(municipio.getProvincia())) {
							duplicado = true;
						}
					}
					if(duplicado == false) {
						subnodo.add(new DefaultMutableTreeNode(municipio.getProvincia()));
					}
					
				}
			}
		}
		treeizq = new JTree(root);
		
		
		
		
		
		
		
		barratree = new JScrollPane(treeizq);
		this.add(barratree, BorderLayout.WEST);
		tablacen = new JTable();
		barratable = new JScrollPane(tablacen);
		this.add(barratable, BorderLayout.CENTER);
		panelder = new JPanel();
		this.add(panelder, BorderLayout.EAST);
		botonera = new JPanel();
		botoninsercion = new JButton("INSERTAR");
		botonborrado = new JButton("BORRAR");
		botonorden = new JButton("ORDEN");
		botonera.setLayout(new FlowLayout());
		botonera.add(botoninsercion);
		botonera.add(botonborrado);
		botonera.add(botonorden);
		this.add(botonera, BorderLayout.SOUTH);
	}
}
