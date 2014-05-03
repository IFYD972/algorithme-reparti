package fr.dauphine.sar.application.controleur;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.IOException;

import javax.swing.JComboBox;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.modele.Bus;

public class DestinationCtrl implements ItemListener {
	private static int counter=0;
	private Bus bus;
	
	public DestinationCtrl(Bus bu) {
		this.bus = bu;
	}
	
	@Override
	public void itemStateChanged(ItemEvent e) {
		// TODO Auto-generated method stub
		counter++;
		
		if(counter == 2) {
			JComboBox box = (JComboBox) e.getSource();
			final String str = (String) box.getSelectedItem();
			
			System.out.println("Changer destination");
			
			new Thread(new Runnable() {
				
				public void run() {
					try { Main.service.changerDestination(bus, str); }
					catch (IOException e) { e.printStackTrace(); }
				}
			}).start();
			
			counter = 0;
		}
	}

}
