package Practica00;

public class Coche {
	private double miVelocidad;  
	protected double miDireccionActual;  
	protected double posX;  
	protected double posY;  
	private String piloto;
	
	protected double getMiVelocidad() {
		return miVelocidad;
	}

	protected void setMiVelocidad(double miVelocidad) {
		this.miVelocidad = miVelocidad;
	}

	protected double getMiDireccionActual() {
		return miDireccionActual;
	}

	protected void setMiDireccionActual(double miDireccionActual) {
		this.miDireccionActual = miDireccionActual;
	}

	protected double getPosX() {
		return posX;
	}

	protected void setPosX(double posX) {
		this.posX = posX;
	}


	protected double getPosY() {
		return posY;
	}

	protected void setPosY(double posY) {
		this.posY = posY;
	}

	protected String getPiloto() {
		return piloto;
	}

	protected void setPiloto(String piloto) {
		this.piloto = piloto;
	}

	public Coche() {
		super();
		this.miVelocidad = 0;
		this.miDireccionActual = 90;
		this.posX = 100;
		this.posY = 150;
		this.piloto = "";
	}
	
	@Override
	public String toString() {
		return "Coche [miVelocidad=" + miVelocidad + ", miDireccionActual=" + miDireccionActual + ", posX=" + posX
				+ ", posY=" + posY + ", piloto=" + piloto + "]";
	}  
	
	
}
