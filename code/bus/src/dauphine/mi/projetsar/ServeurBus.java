package dauphine.mi.projetsar;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

public class ServeurBus extends Thread {
	private final String IP_DISTANTE = "192.168.43.131"; // a modifier en fonction
	private final int PORT_ORDRES_DISTANT = 4141;
	private final int PORT_CARACTERISTIQUES_DISTANT = 2525;
	
	private DatagramSocket socket;
	private DatagramPacket packetAEnvoyer 	= null;
	private DatagramPacket packetRecu 	= null;
	private byte[] tamponEnvoi 		= new byte[1024];
	private byte[] tamponReception 	= new byte[1024];

	Connexion connexionOrdres;
	
	private int numero;
	private Ligne ligne = null;
	private List<Bus> lesBus = new ArrayList<Bus>();
	
	private boolean continuer = true;
	
	private boolean debug = true;
	
	public static void main(String[] args) {		
		ServeurBus serveurLigneOne;
		try {
			serveurLigneOne = new ServeurBus();
			if(serveurLigneOne.ligne != null)
				serveurLigneOne.run();
		} catch (IOException e) { e.printStackTrace(); }
	}

	public ServeurBus () throws IOException {
		this.socket = new DatagramSocket();
		
		// ### Ouverture de la connexion TCP pour la réception des ordres 
		try {
			this.connexionOrdres = new Connexion(this.IP_DISTANTE, this.PORT_ORDRES_DISTANT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.connexionOrdres.openSession();
		this.connexionOrdres.ecrire("INIT");
		if(this.connexionOrdres.lire().equals("220 READY")) {
			// création de la ligne
			JSONObject donnesLigne = (JSONObject) JSONValue.parse(this.connexionOrdres.lire());
			Long numLigne = (Long) donnesLigne.get("numero");
			this.numero = numLigne.intValue();
			
			LinkedList<Station> lesStations = new LinkedList<Station>();
			JSONArray libStationsToJsonArray = (JSONArray) donnesLigne.get("stations");
			for (int i=0; i < libStationsToJsonArray.size(); i++) {
				lesStations.add(new Station((String)libStationsToJsonArray.get(i)));
			}
			
			this.ligne = new Ligne(this.numero, lesStations);
			
			Random randomGenerator = new Random();
			// création des bus sur la ligne
			for (int i=0; i<3; i++) {
				int vitesse = randomGenerator.nextInt(49)+1;
				Bus unBus = new Bus(i, ligne.getFirstStation(), ligne.getLastStation(), vitesse);
				unBus.start();
				this.lesBus.add(unBus);
			}
		}
		// ### Fin de l'ouverture de la connexion TCP pour la réception des ordres 
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public List<Bus> getLesBus() {
		return this.lesBus;
	}
	
	public void ajouterBus(Bus unBus) {
		this.lesBus.add(unBus);
	}
	
	public boolean areBusEnTrajet() {
		Iterator<Bus> iter = this.lesBus.iterator();
		while(iter.hasNext()) {
			Bus unBus = iter.next();
			if (unBus.isAlive()) {
				//if(debug) {System.out.println("Le bus " + unBus.getNumero() + " est encore en route");}
				return true;
			}
		}
		return false;
	}
	
	public void run() {
		TimerTask envoyerCaracteristiques = new TimerTask()
		{
			public void run() 
			{
				try {	
					envoyerCaracteristiquesDesBus();
				} catch (IOException e) { e.printStackTrace(); }
			}	
		};
		
		Timer timerEnvoiCaracteristiques = new Timer();
		timerEnvoiCaracteristiques.scheduleAtFixedRate(envoyerCaracteristiques, 0, 5000);

		Thread threadTraitementOrdres = new Thread(new Runnable(){
            public void run() {
            	while(continuer) {
            		traitementOrdres();
            	}
            		
            }
        });
		threadTraitementOrdres.start();

		while(this.continuer) {
			this.continuer = this.areBusEnTrajet();
			
			if (!this.continuer) {
				timerEnvoiCaracteristiques.cancel();
				
				try {	
					this.envoyerCaracteristiquesDesBus();
				} catch (IOException e) { e.printStackTrace(); }
				
				this.socket.close();
				
				this.connexionOrdres.ecrire("QUIT");
			}
		}	
	}
	
	public void envoyerUDP(String msg) {		
		if(debug) {System.out.println("Envoi UDP: " + msg);}
		
		this.tamponEnvoi = msg.getBytes();
		
		try {
			this.packetAEnvoyer = new DatagramPacket(this.tamponEnvoi, this.tamponEnvoi.length, InetAddress.getByName(this.IP_DISTANTE), this.PORT_CARACTERISTIQUES_DISTANT);
		} catch (UnknownHostException e) {	e.printStackTrace(); }

		try {
			this.socket.send(this.packetAEnvoyer);
		} catch (IOException e1) { e1.printStackTrace();}
	}
	
	public String recevoirUDP() throws IOException {
		this.packetRecu = new DatagramPacket(this.tamponReception, this.tamponReception.length);
		socket.receive(this.packetRecu);
		String msg = new String(this.packetRecu.getData());
		if(debug) {System.out.println("Reception UDP: " + msg);}
		return msg;
	}
	
	public void traitementOrdres() {
		String ordre = this.connexionOrdres.lire();		
		
		if(!ordre.equals("")) {
			if(ordre.equals("221 closing connexion")) {
				this.connexionOrdres.closeSession();
			} else {
				JSONObject donnesOrdre = (JSONObject) JSONValue.parse(ordre);
				int typeOrdre = 0;
				String valeurTypeOrdre = (String) donnesOrdre.get("type_ordre");
				if(valeurTypeOrdre.equals("destination")) {
					typeOrdre = 1;
				}
				else if(valeurTypeOrdre.equals("vitesse")) {
					typeOrdre = 2;
				}
				
				Long numBus = (Long) donnesOrdre.get("bus");
				int numBusInt = numBus.intValue();
				
				switch(typeOrdre) {
					case 0 : System.out.println("Error in order format");
						break;
					case 1 : lesBus.get(numBusInt).setDestination(this.ligne.getUneStation((String)donnesOrdre.get("valeur_ordre")));
						break;
					case 2 :
						Long vitesseBus = (Long) donnesOrdre.get("valeur_ordre");
						int vitesseBusInt = vitesseBus.intValue();
						lesBus.get(numBusInt).setVitesseCourante(vitesseBusInt);
						break;
				}
				
				this.connexionOrdres.ecrire("ORDRE OK "+this.numero);
			}
		}
	}
	
	public void envoyerCaracteristiquesDesBus () throws IOException { 
		this.envoyerUDP(this.lesBusToJSONString());		
	}
	
	private String lesBusToJSONString() {
		JSONObject dataInJsonFormat = new JSONObject();
		
		dataInJsonFormat.put("numero", this.numero);
		
		Iterator<Bus> iter = this.lesBus.iterator();
		while(iter.hasNext()) {
			Bus unBus = iter.next();
			dataInJsonFormat.put("bus"+unBus.getNumero(), unBus.toJSONString());
		}
	 
		return dataInJsonFormat.toJSONString();
	}
}
