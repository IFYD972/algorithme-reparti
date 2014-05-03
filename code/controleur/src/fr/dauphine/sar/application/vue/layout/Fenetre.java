package fr.dauphine.sar.application.vue.layout;

import javax.swing.JFrame;

public class Fenetre extends JFrame {
	
	/**
	 *	Constructeur par défaut pour la fenêtre principale
	 */
	public Fenetre() {
		setTitle("Controleur");
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(true);
		
		repaint();
	}
}