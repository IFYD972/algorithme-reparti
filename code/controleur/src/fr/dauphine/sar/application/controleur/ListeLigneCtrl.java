package fr.dauphine.sar.application.controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JList;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.modele.Ligne;
import fr.dauphine.sar.application.vue.composant.MenuDroite;
import fr.dauphine.sar.reseau.Constantes;

public class ListeLigneCtrl implements MouseListener {

	@Override
	public void mouseClicked(MouseEvent e) {

       JList liste = (JList)e.getSource();
       
        if (e.getClickCount() == 2) {
        	synchronized(Main.lignes) {
        		final Ligne ligne = Main.lignes.get(liste.getSelectedValue());

	       		System.out.println("DOUBLE CLICK " +ligne.getId());
	       		
	       		new Thread(new Runnable() {
	       			public void run() {
			       		try {
							Main.service.choisirLigne(ligne.getId(), new CallbackListener() {
								
								@Override
								public void onReturn(boolean success) {
									// TODO Auto-generated method stub
									if(success) {
										System.out.println("LIGNE DISPONIBLE");
										ligne.setControlee(true);
									}
									else
										System.out.println("LIGNE INDISPONIBLE");
								}
							});
						}
			       		catch (IOException e) { e.printStackTrace(); }
	       			}
	       		}).start();
        	}
        }
	}

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }
}


