package fr.dauphine.sar.application.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JLabel;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.modele.Bus;
import fr.dauphine.sar.reseau.Constantes;

public class AugmenterCtrl implements ActionListener {
	private JLabel lblVitesse;
	private Bus bus;
	
	public AugmenterCtrl(Bus bu) {
		//lblVitesse = vitesse;
		this.bus = bu;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		JButton augmenter = (JButton) e.getSource();
		System.out.println("Augmenter vitesse");
		
		new Thread(new Runnable() {
			public void run() {
				try { Main.service.augmenterVitesse(bus); }
				catch (IOException e) { e.printStackTrace(); }
			}
		}).start();
	}

}
