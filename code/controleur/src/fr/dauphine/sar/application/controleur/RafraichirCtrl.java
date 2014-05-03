package fr.dauphine.sar.application.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.common.Utils;
import fr.dauphine.sar.reseau.Constantes;

public class RafraichirCtrl implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final JButton rafraichir = (JButton) e.getSource();
		
		new Thread(new Runnable(){
            public void run() {
				try { Main.service.rafraichirListeLignes(); }
				catch (IOException e) { e.printStackTrace(); }
			}
       }).start();
	}

}
