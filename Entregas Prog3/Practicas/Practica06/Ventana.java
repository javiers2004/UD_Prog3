package Practica06;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.EventObject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.DefaultCellEditor;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;

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
	public boolean ordenalfabetico = true;
	public DefaultTableModel modelo;
	public int totalhabitantesestado = 0;
	public HashMap<String, Integer> mapapoblacionporprovincia;
	
	
	
	protected HashMap<String, Integer> getMapapoblacionporprovincia() {
		return mapapoblacionporprovincia;
	}
	protected void setMapapoblacionporprovincia(HashMap<String, Integer> mapapoblacionporprovincia) {
		this.mapapoblacionporprovincia = mapapoblacionporprovincia;
	}
	protected int getTotalhabitantesestado() {
		return totalhabitantesestado;
	}
	protected void setTotalhabitantesestado(int totalhabitantesestado) {
		this.totalhabitantesestado = totalhabitantesestado;
	}
	protected boolean isOrdenalfabetico() {
		return ordenalfabetico;
	}
	protected void setOrdenalfabetico(boolean ordenalfabetico) {
		this.ordenalfabetico = ordenalfabetico;
	}

	public Ventana(DataSetMunicipios dsmunicipios) {
		this.setLayout(new BorderLayout());
		this.setLocationRelativeTo(null);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		panelder = new JPanel();
		this.add(panelder, BorderLayout.EAST);
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
		// TODAS LAS PROVINCIAS SIN REPETIR
		mapapoblacionporprovincia = new HashMap<String, Integer>();
		for (Municipio municipio : lMunicipios) {
			if(mapapoblacionporprovincia.get(municipio.getProvincia()) == null) {
				mapapoblacionporprovincia.put(municipio.getProvincia(), municipio.getHabitantes());
			}
			else {
			mapapoblacionporprovincia.put(municipio.getProvincia(), mapapoblacionporprovincia.get(municipio.getProvincia())+ municipio.getHabitantes());
			}
		}
		// TODAS LAS COMUNIDADES AUTÓNOMAS SIN REPETIR
		HashMap<String, Integer> mapapoblacionporcomunidad = new HashMap<String, Integer>();
		for (Municipio municipio : lMunicipios) {
			if(mapapoblacionporcomunidad.get(municipio.getAutonomia()) == null) {
				mapapoblacionporcomunidad.put(municipio.getAutonomia(), municipio.getHabitantes());
			}
			else {
			mapapoblacionporcomunidad.put(municipio.getAutonomia(), mapapoblacionporcomunidad.get(municipio.getAutonomia())+ municipio.getHabitantes());
			}
		}
		
		//PARA AÑADIR LAS PROVINCIAS A LA COMUNIDAD A LA QUE PERTENECEN PERO SOLO UNA VEZ(NO REPETIDAS)
		//PRIMERO DETECTA CUÁL ES SU COMUNIDAD, LUEGO RECORRE TODOS SUS NODOS HIJOS Y EN EL CASO DE QUE NO SE HAYA REPETIDO SE AÑADE
		for (Municipio municipio : lMunicipios) {
			for(int i = 0; i<root.getChildCount();i++) {
				DefaultMutableTreeNode subnodo = (DefaultMutableTreeNode) root.getChildAt(i);
				if(subnodo.getUserObject().equals(municipio.getAutonomia())) {                	//localizar su comunidad
					boolean duplicado = false;
					for (int e = 0; e <subnodo.getChildCount();e++) {							//para ver si su provincia ya está añadida a su comunidad
						DefaultMutableTreeNode subsubnodo = (DefaultMutableTreeNode) subnodo.getChildAt(e);
						JPanel panelsubsubnodo = (JPanel)subsubnodo.getUserObject();
						Component[] components = panelsubsubnodo.getComponents();
						JLabel labelconname = new JLabel();
						for(Component com : components) {
							if (com instanceof JLabel) {
								labelconname = (JLabel) com;
							}
						}
						if(labelconname.getText().equals(municipio.getProvincia())) {
							duplicado = true;
						}
					}
					if(duplicado == false) {													//añadir nuevas provincias
						DefaultMutableTreeNode subsubnodoconbarra = new DefaultMutableTreeNode(municipio.getProvincia());
			            JPanel progressBarPanel = new JPanel(new BorderLayout());													//PANEL QUE CONTIENE EL PROGRESSBAR Y LA LABEL CON EL NOMBRE
			            JProgressBar progressBar = new JProgressBar(0, mapapoblacionporcomunidad.get(municipio.getAutonomia()));
			            progressBar.setValue(mapapoblacionporprovincia.get(municipio.getProvincia()));
			            progressBarPanel.add(progressBar, BorderLayout.CENTER);
			            JLabel name = new JLabel(municipio.getProvincia());
			            progressBarPanel.add(name, BorderLayout.WEST);
			            subsubnodoconbarra.setUserObject(progressBarPanel);
						subnodo.add(subsubnodoconbarra);
					}
					
				}
			}
		}

		
		System.out.print(mapapoblacionporprovincia);
		modelo = new DefaultTableModel();
		modelo.addColumn("Nombre");
		modelo.addColumn("Habitantes");
		modelo.addColumn("Provincia");
		modelo.addColumn("Comunidad");
		modelo.addColumn("Densidad");
		modelo.addColumn("Luvia");
		modelo.addColumn("Población");
		modelo.addColumn("Indice");
		tablacen = new JTable(modelo);
		treeizq = new JTree(root);
		
		
		//RENDER PARA VER LOS PROGRESS BAR DEL TREE
		TreeCellRenderer renderer = new DefaultTreeCellRenderer() {
            @Override
            public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
                Component component = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

                if (value instanceof DefaultMutableTreeNode) {
                    Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
                    if (userObject instanceof Component) {
                        component = (Component) userObject;
                    }
                }
                return component;
            }
        };
        for(Municipio municipio : lMunicipios) {
        	totalhabitantesestado += municipio.getHabitantes();
        }
        //LISTENER PARA QUE AL SELECCIONAR UNA PROVINCIA, TODOS LOS MUNICIPIOS QUE PERTENECEN A ELLA SE CARGEN EN LA TABLA
		treeizq.setCellRenderer(renderer);
		treeizq.addTreeSelectionListener(new TreeSelectionListener() {
            public void valueChanged(TreeSelectionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) treeizq.getLastSelectedPathComponent();
                if (selectedNode != null) {
                	modelo.setRowCount(0);
                	
                	JPanel panelsubsubnodo = (JPanel)selectedNode.getUserObject();
					Component[] components = panelsubsubnodo.getComponents();
					JLabel labelconname = new JLabel();
					for(Component com : components) {
						if (com instanceof JLabel) {
							labelconname = (JLabel) com;
						}
					}
                	
                    for(Municipio municipio : lMunicipios) {
                    	if(labelconname.getText().equals(municipio.getProvincia())) {
                    		JProgressBar progressBar = new JProgressBar(50000, 5000000);
                    		progressBar.setStringPainted(true); // Esto muestra el valor en la barra de progreso
                    		progressBar.setValue(municipio.getHabitantes());
                    		modelo.addRow( new Object[] {municipio.getNombre(), municipio.getHabitantes(), municipio.getProvincia(), municipio.getAutonomia(), municipio.getDensidad(), municipio.getLluvia(), municipio.getHabitantes(), municipio.getCodigo()});
                    		//tablacen.getColumnModel().getColumn(1).setCellRenderer(new customTableCellRenderer());
                    	}	
                    } 
                panelder.removeAll();
                JProgressBar progressBarestado = new JProgressBar(JProgressBar.VERTICAL, 0, Ventana.this.getTotalhabitantesestado());   //el máximo va a ser la catidadtotal, por lo que esta barra estará llena 
                progressBarestado.setValue(Ventana.this.getTotalhabitantesestado());
                JProgressBar progressBarprovincia = new JProgressBar(JProgressBar.VERTICAL, 0, Ventana.this.getTotalhabitantesestado());
                progressBarprovincia.setValue(Ventana.this.getMapapoblacionporprovincia().get(labelconname.getText()));
                panelder.add(progressBarprovincia);
                panelder.add(progressBarestado);
                panelder.setVisible(true);
                Ventana.this.revalidate();
                Ventana.this.repaint();
                }
            }
        });
		
		barratree = new JScrollPane(treeizq);
		this.add(barratree, BorderLayout.WEST);
		barratable = new JScrollPane(tablacen);
		
		//VOLVER LAS COLUMNAS 2-7 NO EDITABLES
        tablacen.getColumnModel().getColumn(2).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        });
        tablacen.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        });
        tablacen.getColumnModel().getColumn(4).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        });
        tablacen.getColumnModel().getColumn(5).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        });
        tablacen.getColumnModel().getColumn(6).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        });
        tablacen.getColumnModel().getColumn(7).setCellEditor(new DefaultCellEditor(new JTextField()) {
            @Override
            public boolean isCellEditable(EventObject e) {
                return false;
            }
        });
        // modelo.addTableModelListener(new TableModelListener() {						//para actualizar cambios en la tabla(NO USAR)
        //     @Override
        //     public void tableChanged(TableModelEvent e) {
        //     	 if (e.getType() == TableModelEvent.UPDATE) {
        //              int filacambio = tablacen.getSelectedRow();
        //             Municipio nuevomun = new Municipio((int)tablacen.getValueAt(filacambio, 6), tablacen.getValueAt(filacambio, 0).toString(), (int)tablacen.getValueAt(filacambio, 1), tablacen.getValueAt(filacambio, 2).toString(), tablacen.getValueAt(filacambio, 3).toString(),(int) tablacen.getValueAt(filacambio, 4),(int) tablacen.getValueAt(filacambio, 5));
        //             dsmunicipios.anyadir(nuevomun);
        //             int indice = 9999;
        //				for (int i = 0; i<dsmunicipios.getListaMunicipios().size(); i++) {
        //					if (tablacen.getValueAt( tablacen.getSelectedRow(), 6).equals(dsmunicipios.getListaMunicipios().get(i).getCodigo())) {
		//						indice = i;
		//					}
		//				}
		//				if (indice != 9999) {
		//					System.out.print("aaaaaaaaaaa");
		//					dsmunicipios.quitar(indice);
		//					
		//				}
        //         }
        //    	
        //    }
        //});

		this.add(barratable, BorderLayout.CENTER);
		botonera = new JPanel();
		botoninsercion = new JButton("INSERTAR");
		botoninsercion.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {	
				if(tablacen.getSelectedRow() >= 0) {
				Municipio nuevomun = new Municipio((int)dsmunicipios.getListaMunicipios().size() + 1, "", 50000, tablacen.getValueAt(tablacen.getSelectedRow(),2).toString(), tablacen.getValueAt(tablacen.getSelectedRow(), 3).toString() , 0, 0);
				dsmunicipios.anyadir(nuevomun);
				modelo.addRow( new Object[] {"", 50000, tablacen.getValueAt(tablacen.getSelectedRow(),2).toString(), tablacen.getValueAt(tablacen.getSelectedRow(),3).toString(), 0, 0, new JProgressBar(50000, 5000000)});
			
				}
				else {
					System.out.println("Comunidad y autonomía en blanco por no seleccionar otra ciudad");
				}
			}	
		});
		tablacen.getColumnModel().getColumn(6).setCellRenderer(new customTableCellRenderer());
		//ListSelectionModel selectionModel = tablacen.getSelectionModel();
		//selectionModel.addListSelectionListener(new ListSelectionListener() {
		// 		@Override
		// 		public void valueChanged(ListSelectionEvent e) {
		// 			if (!e.getValueIsAdjusting()) {
		// 				int selectedRow = tablacen.getSelectedRow();
		// 				if (selectedRow >= 0) {
		// 					for(int i = 0;i<tablacen.getRowCount();i++) {
		// 						if ((int) tablacen.getValueAt(selectedRow, 2) > (int)tablacen.getValueAt(i, 2)) {
		// 							tablacen.setDefaultRenderer(Object.class, new renderverde(i)); //NO ME FUNCIONA ESTE RENDER
		// 						}
		// 					}
		// 				}
		// 			}
		// 		}
		//});


		
		botonborrado = new JButton("BORRAR");
		botonborrado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(tablacen.getSelectedRow() >= 0) {
					int respuesta = JOptionPane.showConfirmDialog(null, "¿Estás seguro de que deseas realizar esta acción?", "Confirmación", JOptionPane.YES_NO_OPTION);
					
					if (respuesta == JOptionPane.YES_OPTION) {
						int indice = 9999;
						for (int i = 0; i<dsmunicipios.getListaMunicipios().size(); i++) {
							System.out.println(tablacen.getValueAt(tablacen.getSelectedRow(), 0));
							System.out.println(dsmunicipios.getListaMunicipios().get(i).getNombre());
							if (tablacen.getValueAt( tablacen.getSelectedRow(), 0).equals(dsmunicipios.getListaMunicipios().get(i).getNombre())) {
								indice = i;
							}
						}
						if (indice != 9999) {
							System.out.print(dsmunicipios.getListaMunicipios().get(indice));
							dsmunicipios.quitar(indice);
							
						}
						
						modelo.removeRow(tablacen.getSelectedRow());
					    System.out.print("SI se ha borrado el municipio");
					} else {
						System.out.println("NO se ha borrado el municipio");
					}
				}
			}
		});
		botonorden = new JButton("ORDEN");
		 Comparator<Vector<Object>> comparador = new Comparator<Vector<Object>>() {
	            @Override
	            public int compare(Vector<Object> fila1, Vector<Object> fila2) {
	                Object valor1 = fila1.get(0);
	                Object valor2 = fila2.get(0);

	                if (valor1 == null) {
	                    return -1;
	                }
	                if (valor2 == null) {
	                    return 1;
	                }

	                return valor1.toString().compareTo(valor2.toString());
	            }
	        };
	        Comparator<Vector<Object>> comparador2 = new Comparator<Vector<Object>>() {
	            @Override
	            public int compare(Vector<Object> fila1, Vector<Object> fila2) {
	                Object valor1 = fila1.get(1);
	                Object valor2 = fila2.get(1);

	                if (valor1 == null) {
	                    return -1;
	                }
	                if (valor2 == null) {
	                    return 1;
	                }

	                return valor1.toString().compareTo(valor2.toString());
	            }
	        };
		botonorden.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent a) {
		        Vector<Vector<Object>> data = new Vector<Vector<Object>>();
		        Vector<Vector> rawData = modelo.getDataVector();

		        for (Vector fila : rawData) {
		            Vector<Object> filaObject = new Vector<Object>(fila);
		            data.add(filaObject);
		        }
				if(Ventana.this.isOrdenalfabetico() == true) {
					Collections.sort(data, comparador);
					Ventana.this.setOrdenalfabetico(false);
				}
				else {
					Collections.sort(data, comparador2);
					Ventana.this.setOrdenalfabetico(true);
				}
				modelo.setRowCount(0);
				for (Vector<Object> fila : data) {
		            modelo.addRow(fila);
		        }
			}
		});
		botonera.setLayout(new FlowLayout());
		botonera.add(botoninsercion);
		botonera.add(botonborrado);
		botonera.add(botonorden);
		this.add(botonera, BorderLayout.SOUTH);
		

	}
}
