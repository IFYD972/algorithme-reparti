package fr.dauphine.sar.application;

import java.awt.Container;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.SwingUtilities;

import fr.dauphine.sar.application.modele.Bus;
import fr.dauphine.sar.application.modele.Ligne;
import fr.dauphine.sar.application.vue.composant.MenuDroite;
import fr.dauphine.sar.application.vue.layout.ConteneurPrincipal;

public class InterfaceApplication {
	
	public void mettreAJourLignes() {
		//synchronized(MenuDroite.modelListeLigne) {
			MenuDroite.modelListeLigne.clear();
			
			Iterator it = Main.lignes.entrySet().iterator();
			
			while(it.hasNext()) {
		        Map.Entry pairs = (Map.Entry)it.next();
		        System.out.println(pairs.getKey());
				MenuDroite.modelListeLigne.addElement(pairs.getKey());
			}
		//}
	}
	
	public void mettreAJourAffichageLigne() {
		Main.mettreAJourCarte();
	}
}