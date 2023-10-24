package Practica00;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Manager {
	public static void main(String[] args) {
		VentanaJuego ven1 = new VentanaJuego();
		ven1.setExtendedState(JFrame.MAXIMIZED_BOTH);
		//ven1.setBounds(0,0,1800, 1600);
		JPanel panelblanco = new JPanel();
		panelblanco.setLayout(new BorderLayout());
		JPanel botonera = new JPanel();
		botonera.setBackground(Color.GRAY);
		JButton botonacelerar = new JButton("Acelera");
		JButton botonfrenar = new JButton("Frena");
		JButton botongiroizq = new JButton("Gira Izq.");
		JButton botongiroder = new JButton("Giro der.");
		botonera.setLayout(new FlowLayout());
		botonera.add(botonacelerar);
		Coche car = new Coche();
		boolean ejecutar = true;
		
		botonacelerar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (car.getMiVelocidad()<0) {
					car.setMiVelocidad(car.getMiVelocidad() - 5);
				}
				else {
					car.setMiVelocidad(car.getMiVelocidad() + 5);
				}
			}
		});
		botonfrenar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (car.getMiVelocidad() == 0) {
					
				}
				else if(car.getMiVelocidad()<0) {
					car.setMiVelocidad(car.getMiVelocidad() + 5);
				}
				else {
					car.setMiVelocidad(car.getMiVelocidad() - 5);
				}
			}
		});
		botongiroizq.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				car.setMiDireccionActual(car.getMiDireccionActual()-10);	
				System.out.print(car.getMiDireccionActual());
				if (car.getMiDireccionActual() == 0) {
					car.setMiDireccionActual(360);
				}
			}
		});
		botongiroder.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				car.setMiDireccionActual(car.getMiDireccionActual()+10);	
				if (car.getMiDireccionActual() == 360) {
					car.setMiDireccionActual(0);
				}
			}
		});
		botonera.add(botonfrenar);
		botonera.add(botongiroizq);
		botonera.add(botongiroder);
		panelblanco.add(botonera, BorderLayout.SOUTH);
		ven1.add(panelblanco);
		JPanel panelconducir = new JPanel();
		panelconducir.setLayout(null);
		panelblanco.add(panelconducir);

		ImageIcon icon = new ImageIcon("Practicas/Practica00/coche.png");
		ImageIcon imagen = new ImageIcon(icon.getImage().getScaledInstance(100,100,Image.SCALE_SMOOTH));
		Image i2 = imagen.getImage();
		ven1.setVisible(true);
		
		
		Runnable correr = new Runnable() {
			public void run() {
				
				while (ejecutar == true) {
					//PARTE DE LA ACTUALIZACIÓN DE GIRO DEL COCHE
					AffineTransform transformacion = new AffineTransform();
			        transformacion.rotate(Math.toRadians(car.getMiDireccionActual()), i2.getWidth(null) / 2, i2.getHeight(null) / 2);
			        BufferedImage imagenRotada = new BufferedImage(i2.getHeight(null), i2.getWidth(null), BufferedImage.TYPE_INT_ARGB);
			        Graphics2D g2d = imagenRotada.createGraphics();
			        g2d.setTransform(transformacion);
			        g2d.drawImage(i2, 0, 0, null);
			        g2d.dispose();
			        ImageIcon imagenFinal = new ImageIcon(imagenRotada);
			        JLabel coche = new JLabel(imagenFinal);
			        //PARTE DE LA ACTUALIZACIÓN DE POSICIÓN DEL COCHE, TANTO DEL OBJETO COMO DE LA JLABEL
			        panelconducir.add(coche); 
					coche.setVisible(true);
					car.setPosX(car.getPosX() + (car.getMiVelocidad()*Math.sin(car.getMiDireccionActual()*(2*Math.PI)/360)));
					car.setPosY(car.getPosY() - (car.getMiVelocidad()*Math.cos(car.getMiDireccionActual()*(2*Math.PI)/360)));		
					coche.setBounds((int)car.getPosX(),(int) car.getPosY(), 100, 100);
					//PARTE DEL CONTROLADOR DE REBOTES EN LOS LADOS
					if ( car.getPosY() < 10|| car.getPosY() > 650) {	
							car.setMiDireccionActual(180-car.getMiDireccionActual());
							if (car.getMiDireccionActual() > 360) {
								car.setMiDireccionActual(car.getMiDireccionActual()-360);
							}
					} 
					if ( car.getPosX() < 10|| car.getPosX() > 1430) {	
						car.setMiDireccionActual(360-car.getMiDireccionActual());
						if (car.getMiDireccionActual() < 0) {
							car.setMiDireccionActual(car.getMiDireccionActual() + 90);
						}
					}
					//ESPERA DE 40ms ENTRE ACTUALIZACIONES PARA GENERAR ESE EFECTO DE MOVIMIENTO
					try {
						Thread.sleep(40);
						coche.setVisible(false);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		};
		Thread hilo = new Thread(correr);
		hilo.start();
		ven1.addWindowListener(new WindowAdapter() {		
			public void windowClosed(WindowEvent e) {
				ven1.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			}			
		});
		
	}
}
