package dauphine.projet.sar.dao;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;

import javax.swing.JButton;


/**
 * 
 * Element graphique des bus
 *
 */
public class Bus extends JButton{
	private int numero;
	private String station;
	private String destination;
	private String vitesse;
	
	public Bus(int numero, String station, String destination, String string){
		this.destination=destination;
		this.numero=numero;
		this.station=station;
		this.vitesse= string;
		setText("Bus n°"+numero+" à destination de "+destination+" avec une vitesse "+", station : "+station);
		this.setPreferredSize(new Dimension(515,15));
		this.setBackground(Color.DARK_GRAY);
		this.setEnabled(false);
		this.setAutoscrolls(true);
		this.setForeground(Color.white);
	}
}
