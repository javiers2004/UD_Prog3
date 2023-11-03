package Practica05;

import java.io.File;
import java.util.HashMap;

public class GestionTwitter {
	protected static HashMap<String, UsuarioTwitter> mapausuarios = new HashMap<String, UsuarioTwitter>();  //ORDENADOS POR ID
	protected static HashMap<String, UsuarioTwitter> mapausuariosnick = new HashMap<String, UsuarioTwitter>();  //ORDENADOS POR NICK
	
	public static void main(String[] args) {
		try {
			// TODO Configurar el path y ruta del fichero a cargar
			String fileName = "Practicas/Practica05/data.csv";
			CSV.processCSV( new File( fileName ),  mapausuarios, mapausuariosnick);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
