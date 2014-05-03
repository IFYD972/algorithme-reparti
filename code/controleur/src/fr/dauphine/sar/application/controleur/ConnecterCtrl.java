package fr.dauphine.sar.application.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.common.Utils;
import fr.dauphine.sar.application.vue.composant.MenuDroite;
import fr.dauphine.sar.reseau.Constantes;

public class ConnecterCtrl implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final JButton connecter = (JButton) e.getSource();
		
		connecter.setText("Connexion");
		connecter.setEnabled(false);
		

    	/*L'utilisation de SwingUtilities permet de lancer les opérations dans un Thread géré par le système
    	afin de ne pas freezer l'interface graphique*/
		new Thread(new Runnable(){
            public void run() {
				try {
					Main.service.connecterServeur(new CallbackListener() {

						@Override
						public void onReturn(boolean success) {
							
							if(success) {
								try { Thread.sleep(500); }
								catch (InterruptedException e) { e.printStackTrace(); }
								
								connecter.setText("Se deconnecter");

								connecter.setEnabled(true);
								MenuDroite.btnAugmenterVitesse.setEnabled(true);
								MenuDroite.btnReduireVitesse.setEnabled(true);
								MenuDroite.slctDestination.setEnabled(true);
								//MenuDroite.btnRafraichir.setEnabled(true);
								
								Utils.removeAllListeners(connecter);
								
								connecter.addActionListener(new DeconnecterCtrl());
							}
							else {
								connecter.setText("Se connecter");
								connecter.setEnabled(true);
							}
						}
					});
				}
				catch (IOException e) { e.printStackTrace(); System.out.println("COUCOU");}
            }
       }).start();
	}

}
