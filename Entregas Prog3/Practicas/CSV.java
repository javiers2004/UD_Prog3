package Practica05;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeSet;

/** Clase para proceso básico de ficheros csv
 * @author andoni.eguiluz @ ingenieria.deusto.es
 */
public class CSV { 
	
	private static boolean LOG_CONSOLE_CSV = false;  // Log a consola de lo que se va leyendo en el CSV
	/** Procesa un fichero csv
	 * @param file	Fichero del csv
	 * @throws IOException
	 */
	public static void processCSV( File file, HashMap<String, UsuarioTwitter> mapausuarios, HashMap<String, UsuarioTwitter> mapausuariosnick) 
	throws IOException // Error de I/O
	{
		processCSV( file.toURI().toURL(), mapausuarios, mapausuariosnick);
	}
	
	/** Procesa un fichero csv
	 * @param urlCompleta	URL del csv
	 * @throws IOException
	 * @throws UnknownHostException
	 * @throws FileNotFoundException
	 * @throws ConnectException
	 */
	public static void processCSV( URL url, HashMap<String, UsuarioTwitter> mapausuarios, HashMap<String, UsuarioTwitter> mapausuariosnick) 
	throws MalformedURLException,  // URL incorrecta 
	 IOException, // Error al abrir conexión
	 UnknownHostException, // servidor web no existente
	 FileNotFoundException, // En algunos servidores, acceso a p�gina inexistente
	 ConnectException // Error de timeout
	{
		BufferedReader input = null;
		InputStream inStream = null;
		try {
		    URLConnection connection = url.openConnection();
		    connection.setDoInput(true);
		    inStream = connection.getInputStream();
		    input = new BufferedReader(new InputStreamReader( inStream, "UTF-8" ));  // Supone utf-8 en la codificación de texto
		    String line = "";
		    int numLine = 0;
		    while ((line = input.readLine()) != null) {
		    	numLine++;
		    	if (LOG_CONSOLE_CSV) System.out.println( numLine + "\t" + line );
		    	try {
			    	ArrayList<Object> l = processCSVLine( input, line, numLine );
			    	if (LOG_CONSOLE_CSV) System.out.println( "\t" + l.size() + "\t" + l );
			    	if (numLine==1) {
			    		procesaCabeceras( l );
			    	} else {
			    		if (!l.isEmpty())		    			
			    			procesaLineaDatos(l);
			    			UsuarioTwitter usuario = new UsuarioTwitter((String)l.get(0),(String)l.get(1),(ArrayList<String>) l.get(2),(String) l.get(3),(Long) l.get(4),(Long)l.get(5),(String)l.get(6),(Long)l.get(7),(String) l.get(8),(ArrayList<String>)l.get(9));
			    			if(!mapausuarios.containsValue(usuario)) {
			    				mapausuarios.put(usuario.getId(), usuario);
			    			}
			    	}
		    	} catch (StringIndexOutOfBoundsException e) {
		    		/* if (LOG_CONSOLE_CSV) */ System.err.println( "\tError: " + e.getMessage() );
		    	}
		    }
		    
		    mapaidTOMapausuarios( mapausuarios, mapausuariosnick); //FUNCIÓN PARA CREAR EL MAPA DE USUARIOS CUYA CLAVE ES SU PROPIO NICK
		    //IMPRIME MAPA CON CLAVES DE ENTEROS 0-40000
		    Set<String> claves = mapausuarios.keySet();
	        for (String clave : claves) {
	            //System.out.println("Clave: " + clave + ", Valor: " + mapausuarios.get(clave));
	        }
	        //IMPRIME MAPA DE CLAVES DE NICK
	        Set<String> nicks = mapausuariosnick.keySet(); // NUEVO SET QUE CONTIENE TODOS LOS NICKS DE LOS USUARIOS
	        for (String nick : nicks) {
	            //System.out.println("Clave: " + nick + ", Valor: " + mapausuariosnick.get(nick));
	        }   
	        	        
	        TreeSet<UsuarioTwitter> treeamigos = new TreeSet<UsuarioTwitter>();
	        for (String clave : claves) {
	        	int amigosregistrados = 0;
	        	int amigosnoregistrados = 0;
	        	UsuarioTwitter usuario = mapausuarios.get(clave);
	        	for(int e = 0; e< usuario.getFriends().size(); e++) {
	        		if (mapausuarios.containsKey(usuario.getFriends().get(e))) {
	        			amigosregistrados ++;
	        		}
	        		else {
	        			amigosnoregistrados ++;
	        		}
	        	}
	        	if (amigosregistrados > 0) {
		        	System.out.println(usuario.getScreenName() +"  Amigos dentro de nuestro sistema: " + amigosregistrados + "  Amigos fuera de nuestro sistema:  " + amigosnoregistrados);
		        	usuario.setAmigosenelsistema(amigosregistrados);
		        	treeamigos.add(usuario);
	        	}
	        }
	        //System.out.print(treeamigos.size());
	        //FUNCIÓN QUE PASA POR CONSOLA TODOS LOS DATOS DEL TREE DE LOS USUARIOS ORDENADOS POR NºAMIGOS Y POR NICK(POR LO TANTO, YA ESTÁ ORDENADO)
	        for(UsuarioTwitter user: treeamigos) {
	        	imprimirUsuariosConAmigos(user);
	        }
	        
	        
	        
		} finally {
			try {
				inStream.close();
				input.close();
			} catch (Exception e2) {
			}
		}
	}
	
		/** Procesa una línea de entrada de csv	
		 * @param input	Stream de entrada ya abierto
		 * @param line	La línea YA LEÍDA desde input
		 * @param numLine	Número de línea ya leída
		 * @return	Lista de objetos procesados en el csv. Si hay algún string sin acabar en la línea actual, lee más líneas del input hasta que se acaben los strings o el input
		 * @throws StringIndexOutOfBoundsException
		 */
		public static ArrayList<Object> processCSVLine( BufferedReader input, String line, int numLine ) throws StringIndexOutOfBoundsException {
			ArrayList<Object> ret = new ArrayList<>();
			ArrayList<Object> lista = null; // Para posibles listas internas
			int posCar = 0;
			boolean inString = false; // Marca de cuando se está leyendo un string
			boolean lastString = false;  // Marca que el último leído era un string
			boolean inList = false; // Marca de cuando se está leyendo una lista (entre corchetes, separada por comas)
			boolean finString = false;
			String stringActual = "";
			char separador = 0;
			while (line!=null && (posCar<line.length() || line.isEmpty() && posCar==0)) {
				if (line.isEmpty() && posCar==0) {
					if (!inString) return ret;  // Línea vacía
				} else {
					char car = line.charAt( posCar );
					if (car=='"') {
						if (inString) {
							if (nextCar(line,posCar)=='"') {  // Doble "" es un "
								posCar++;
								stringActual += "\"";
							} else {  // " de cierre
								inString = false;
								finString = true;
								lastString = true;
							}
						} else {  // !inString
							if (stringActual.isEmpty()) {  // " de apertura
								inString = true;
							} else {  // " después de valor - error
								throw new StringIndexOutOfBoundsException( "\" after data in char " + posCar + " of line [" + line + "]" );
							}
						}
					} else if (!inString && (car==' ' || car=='\t')) {  // separador fuera de string
						// Nada que hacer
					} else if (car==',' || car==';') {
						if (inString) {  // separador dentro de string
							stringActual += car;
						} else {  // separador que separa valores
							if (separador==0) { // Si no se había encontrado separador hasta ahora
								separador = car;
								if (inList)
									lista.add( getDato( stringActual, lastString ) );
								else if (lista!=null) {
									ret.add( lista );
									lista = null;
								} else 
									ret.add( getDato( stringActual, lastString ) );
								stringActual = "";
								lastString = false;
								finString = false;
							} else { // Si se había encontrado, solo vale el mismo (, o ;)
								if (separador==car) {  // Es un separador
									if (inList)
										lista.add( getDato( stringActual, lastString ) );
									else if (lista!=null) {
										ret.add( lista );
										lista = null;
									} else 
										ret.add( getDato( stringActual, lastString ) );
									stringActual = "";
									lastString = false;
									finString = false;
								} else {  // Es un carácter normal
									if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor después de string - error
									stringActual += car;
								}
							}
						}
					} else if (!inString && car=='[') {  // Inicio de lista
						if (inList) throw new StringIndexOutOfBoundsException( "Nested lists not allowed in this process in line " + numLine + ": [" + line + "]");
						inList = true;
						lista = new ArrayList<>();
					} else if (!inString && car==']') {  // Posible fin de lista
						if (!inList) throw new StringIndexOutOfBoundsException( "Closing list not opened in line " + numLine + ": [" + line + "]");
						if (!stringActual.isEmpty()) lista.add( getDato( stringActual, lastString ) );
						stringActual = "";
						inList = false;
					} else {  // Carácter dentro de valor
						if (finString) throw new StringIndexOutOfBoundsException( "Data after string in char " + posCar + " of line [" + line + "]");  // valor después de string - error
						stringActual += car;
					}
					posCar++;
				}
				if (posCar>=line.length() && inString) {  // Se ha acabado la línea sin acabarse el string. Eso es porque algún string incluye salto de línea. Se sigue con la siguiente línea
					line = null;
				    try {
						line = input.readLine();
				    	if (LOG_CONSOLE_CSV) System.out.println( "  " + numLine + " (add)\t" + line );
						posCar = 0;
						stringActual += "\n";
					} catch (IOException e) {}  // Si la línea es null es que el fichero se ha acabado ya o hay un error de I/O
				}
			}
			if (inString) throw new StringIndexOutOfBoundsException( "String not closed in line " + numLine + ": [" + line + "]");
			if (lista!=null)
				ret.add( lista );
			else if (!stringActual.isEmpty())
				ret.add( getDato( stringActual, lastString ) );
			return ret;
		}

			// Devuelve el siguiente carácter (car 0 si no existe el siguiente carácter)
			private static char nextCar( String line, int posCar ) {
				if (posCar+1<line.length()) return line.charAt( posCar + 1 );
				else return Character.MIN_VALUE;
			}
			
			// Devuelve el objeto que corresponde a un dato (por defecto String. Si es entero o doble válido, se devuelve ese tipo)
			private static Object getDato( String valor, boolean esString ) {
				if (esString) return valor;
				try {
					long entero = Long.parseLong( valor );
					return new Long( entero );
				} catch (Exception e) {}
				try {
					double doble = Double.parseDouble( valor );
					return new Double( doble );
				} catch (Exception e) {}
				return valor;
			}

	
	private static void procesaCabeceras( ArrayList<Object> cabs ) {
		// TODO Cambiar este proceso si se quiere hacer algo con las cabeceras
		System.err.println( cabs );  // Saca la cabecera por consola de error
	}

	private static int numLin = 0;
	private static void procesaLineaDatos( ArrayList<Object> datos) {
		// TODO Cambiar este proceso si se quiere hacer algo con las cabeceras
		numLin++;
		//System.out.println( numLin + "\t" + datos );  // Saca la cabecera por consola de error
	}
	//FUNCION mapausuarios -> mapausuariosnick
	private static void mapaidTOMapausuarios(HashMap<String, UsuarioTwitter> mapausuarios, HashMap<String, UsuarioTwitter> mapausuariosnick) {
	    Set<String> claves = mapausuarios.keySet();
		for (String clave : claves) {
        	UsuarioTwitter usuario = mapausuarios.get(clave);
        	if(!mapausuariosnick.containsValue(usuario))
        		mapausuariosnick.put(usuario.getScreenName(), usuario);
        }
	}
	private static void imprimirUsuariosConAmigos(UsuarioTwitter user) {
		System.out.println(user.getScreenName() + "  -  " + user.getAmigosenelsistema() + " amigos.");
	}
}
