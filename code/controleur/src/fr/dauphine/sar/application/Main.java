package fr.dauphine.sar.application;

/* LIBRARY IMPORT */

/* STANDARD IMPORT */
import java.awt.Dimension;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import fr.dauphine.sar.application.controleur.BusClickCtrl;
import fr.dauphine.sar.application.controleur.StationCtrl;
import fr.dauphine.sar.application.modele.Bus;
import fr.dauphine.sar.application.modele.Controleur;
import fr.dauphine.sar.application.modele.Ligne;
import fr.dauphine.sar.application.vue.composant.ComposantCentral;
import fr.dauphine.sar.application.vue.layout.ConteneurPrincipal;
import fr.dauphine.sar.application.vue.layout.Fenetre;
import fr.dauphine.sar.reseau.Constantes;
import fr.dauphine.sar.service.InterfaceService;
import fr.dauphine.sar.service.ServiceReceptionData;

/* LOCAL IMPORT */


public class Main {
	private static Fenetre fenetre;
	public static ConteneurPrincipal contenuPrincipal;
	
	public static InterfaceService service;
	public static ServiceReceptionData serviceUDP;
	
	public static HashMap<String, Ligne> lignes;
	public static Controleur controleur;
	
	public static int portLocal;
	
	public static HashMap<String, ArrayList<String>> stations;
	
	public static void main(String[] args) {
		if(args.length > 0) {

			portLocal = Integer.parseInt(args[0]);
			System.out.println("TAILLE : "+args.length+";ARGS:"+args[0]/*+";ARGS:"+args[1]*/);
			
			controleur = new Controleur();
			service = new InterfaceService(portLocal);
			
			lignes = new HashMap<String, Ligne>();
			
			serviceUDP = new ServiceReceptionData();
			serviceUDP.start();

			fenetre = new Fenetre();
			contenuPrincipal = new ConteneurPrincipal();
			
			fenetre.add(contenuPrincipal);
			fenetre.setPreferredSize(new Dimension(1270,800));
			
			fenetre.pack();
			fenetre.setVisible(true);
			
			stations = new HashMap<String, ArrayList<String>>();
			
			stations.put("1", new ArrayList<String>(){{ add("84:177"); add("83:148"); add("84:122"); add("122:119"); add("160:118"); add("195:148"); add("226:173"); add("257:195"); add("294:225"); add("331:222"); add("378:222"); add("428:222"); add("485:225"); }});
			stations.put("2", new ArrayList<String>(){{	add("136:76"); add("233:76"); add("328:77"); add("329:112"); add("333:141"); add("329:181"); add("331:210"); add("380:208"); add("427:210"); add("482:210"); }});
			stations.put("3", new ArrayList<String>(){{	add("331:555"); add("330:481"); add("331:377"); add("330:300"); add("327:237"); }});
			stations.put("4", new ArrayList<String>(){{	add("484:235"); add("481:271"); add("482:305"); add("482:331"); }});
			stations.put("5", new ArrayList<String>(){{	add("447:117"); add("447:167"); add("446:210"); add("481:270"); add("580:299"); add("661:323"); add("733:343"); add("767:351"); add("806:362"); add("848:379"); add("870:414"); add("870:452"); add("873:492"); add("874:530"); }});
			stations.put("6", new ArrayList<String>(){{	add("579:57"); add("579:88"); add("578:119"); add("579:152"); add("578:187"); }});
			stations.put("7", new ArrayList<String>(){{	add("947:233");	add("945:187");	add("915:178");	add("880:169");	add("842:162");	add("811:149");	add("774:143");	add("707:158");	add("664:173");	add("580:193");	add("579:247");	add("579:300");	add("580:339");	add("579:379");}});
			stations.put("8", new ArrayList<String>(){{	add("553:581");	add("697:531");	add("698:473");	add("693:456");	add("697:416");	add("697:287");	add("737:255");	add("739:182");	add("772:150");}});
		}
		else
			System.out.println("Il manque des arguments");
	}
	
	public static void mettreAJourCarte() {
		contenuPrincipal.centre.setVisible(false); 
		contenuPrincipal.centre.removeAll(); 
		
		Iterator ite = lignes.entrySet().iterator();
		
		while(ite.hasNext()) {
			Map.Entry item = (Map.Entry)ite.next();
			Ligne maLigne = (Ligne) item.getValue();
			Vector<JLabel> labels = maLigne.getLabelBus();
			Vector<Bus> bus = maLigne.getBus();
			
			Iterator it = labels.iterator();
			
			if(bus != null) {
				Iterator ito = bus.iterator();
				while(it.hasNext() && ito.hasNext()) {
					JLabel label = (JLabel) it.next();
					Bus buso = (Bus) ito.next();
					label.addMouseListener(new BusClickCtrl(buso));
					contenuPrincipal.centre.add(label);
				}
			}
		}
		
		contenuPrincipal.centre.validate();
		contenuPrincipal.centre.invalidate();
		contenuPrincipal.centre.revalidate();
		contenuPrincipal.centre.repaint();
		contenuPrincipal.centre.setVisible(true);
	}
}
