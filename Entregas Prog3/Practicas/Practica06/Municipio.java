package Practica06;

/** Permite crear objetos municipio con información de población, provincia y comunidad autónoma
 */
public class Municipio {
	private int codigo;
	private String nombre;
	private int habitantes;
	private String provincia;
	private String autonomia;
	private int densidad;
	private int lluvia;
	
	/** Crea un municipio
	 * @param codigo	Código único del municipio (1-n)
	 * @param nombre	Nombre oficial
	 * @param habitantes	Número de habitantes
	 * @param provincia	Nombre de su provincia
	 * @param autonomia	Nombre de su comunidad autónoma
	 */
	public Municipio(int codigo, String nombre, int habitantes, String provincia, String autonomia, int densidad, int lluvia) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.habitantes = habitantes;
		this.provincia = provincia;
		this.autonomia = autonomia;
		this.densidad = densidad;
		this.lluvia = lluvia;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getHabitantes() {
		return habitantes;
	}

	public void setHabitantes(int habitantes) {
		this.habitantes = habitantes;
	}

	public String getProvincia() {
		return provincia;
	}

	public void setProvincia(String provincia) {
		this.provincia = provincia;
	}

	public String getAutonomia() {
		return autonomia;
	}

	public void setAutonomia(String autonomia) {
		this.autonomia = autonomia;
	}

	
	protected int getDensidad() {
		return densidad;
	}

	protected void setDensidad(int densidad) {
		this.densidad = densidad;
	}

	protected int getLluvia() {
		return lluvia;
	}

	protected void setLluvia(int lluvia) {
		this.lluvia = lluvia;
	}

	@Override
	public String toString() {
		return "Municipio [codigo=" + codigo + ", nombre=" + nombre + ", habitantes=" + habitantes + ", provincia="
				+ provincia + ", autonomia=" + autonomia + ", densidad=" + densidad + ", lluvia=" + lluvia + "]";
	}

	

	
	
}
