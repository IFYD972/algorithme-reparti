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

public class DeconnecterCtrl implements ActionListener {
	
	@Override
	public void actionPerformed(ActionEvent e) {
		final JButton deconnecter = (JButton) e.getSource();
		
		deconnecter.setText("Déconnexion");
		deconnecter.setEnabled(false);

        new Thread(new Runnable(){
            public void run() {
				try {
					Main.service.deconnecterServeur(new CallbackListener() {

						@Override
						public void onReturn(boolean success) {				
							deconnecter.setText("Se connecter");

							deconnecter.setEnabled(true);
							MenuDroite.btnAugmenterVitesse.setEnabled(false);
							MenuDroite.btnReduireVitesse.setEnabled(false);
							MenuDroite.slctDestination.setEnabled(false);
							//MenuDroite.btnRafraichir.setEnabled(false);
							
							Utils.removeAllListeners(deconnecter);
							
							deconnecter.addActionListener(new ConnecterCtrl());
						}
					});
				}
				catch (IOException e) { e.printStackTrace(); }
            }
        }).start();
	}

}
