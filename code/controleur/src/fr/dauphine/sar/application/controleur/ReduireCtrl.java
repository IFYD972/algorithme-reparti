package fr.dauphine.sar.application.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

import org.json.simple.JSONObject;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.modele.Bus;
import fr.dauphine.sar.reseau.Constantes;

public class ReduireCtrl implements ActionListener {
	private JLabel lblVitesse;
	private Bus bus;
	
	public ReduireCtrl(Bus bu) {
		//lblVitesse = vitesse;
		this.bus = bu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton reduire = (JButton) e.getSource();
		System.out.println("Reduire vitesse");
		
		new Thread(new Runnable() {
			public void run() {
				try { Main.service.diminuerVitesse(bus); }
				catch (IOException e) { e.printStackTrace(); }
			}
		}).start();
	}

}
