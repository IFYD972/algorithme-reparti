package fr.dauphine.sar.application.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComboBox;
import javax.swing.JLabel;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.common.Utils;
import fr.dauphine.sar.application.modele.Bus;
import fr.dauphine.sar.application.vue.composant.MenuDroite;

public class BusClickCtrl implements MouseListener {
	private Bus bus;
	
	public BusClickCtrl(Bus bu) {
		this.bus = bu;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		System.out.println("CLICK");
		new Thread(new Runnable(){
			public void run() {
				synchronized(Main.lignes) {
					MenuDroite.lblVitesse.setText(""+bus.getVitesse());// = new JLabel("OH YEAH "+bus.getId());
					Utils.removeAllListeners(MenuDroite.btnAugmenterVitesse);
					Utils.removeAllListeners(MenuDroite.btnReduireVitesse);
					Utils.removeAllListeners(MenuDroite.slctDestination);
			
					MenuDroite.btnReduireVitesse.addActionListener(new ReduireCtrl(bus));
					MenuDroite.btnAugmenterVitesse.addActionListener(new AugmenterCtrl(bus));
					
					MenuDroite.modelSlctDestination.removeAllElements();
					
					Iterator it = Main.stations.get(""+bus.getLigneId()).iterator();
					
					while(it.hasNext()) {
						MenuDroite.modelSlctDestination.addElement(it.next());
					}
			
					MenuDroite.slctDestination.addItemListener(new DestinationCtrl(bus));
					MenuDroite.lblLigne.setText("Ligne "+bus.getLigneId()+ "- Bus "+bus.getId());
					
					MenuDroite.lblTraffic.setText(Main.lignes.get("LIGNE bus.getLigneId()").getEtatTraffic());
					
					Main.contenuPrincipal.menuDroite.validate();
				}
		}}).start();
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
