package fr.dauphine.sar.reseau;

import java.io.IOException;
import java.net.Socket;

import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.modele.Controleur;

public class InterfaceReseau {
	private Connexion connexion;
	private Protocole protocole;
	private String reponse;
	private String adresseIP;
	private int portLocal;
	private int portDistant;
	
	public InterfaceReseau(Protocole proto, String adresseIP, int portLocal, int portDistant) {
		protocole = proto;
		
	    switch(protocole) {
	        case UDP:
	            connexion = new ConnexionUDP(adresseIP, portLocal, portDistant);
	            break;
	        case TCP:
	            try { connexion = new ConnexionTCP(new Socket(adresseIP, portDistant)); }
	            catch (IOException e) { e.printStackTrace(); }
	            break;
	        case RMI:
	        	break;
	    }
	}
	
	public String recevoir() throws IOException {
		return connexion.recevoir();
	}
	
	public void envoyer(String msg) throws IOException {
		connexion.envoyer(msg);
	}
	
	public void connexionServeur() throws IOException {
		connexion.envoyer("INIT");
		if((reponse = connexion.recevoir()).equals("200 READY")) {//200 READY

			reponse = connexion.recevoir();//ID CONTROLEUR + JSON LIGNES
			int idControleur = 0;
			Main.controleur.setId(idControleur);
		}
	}
	
	public void deconnexionServeur() throws IOException {
	    connexion.envoyer("QUIT");
	    
	    try { Thread.sleep(2000); }
	    catch (InterruptedException e) { e.printStackTrace(); }
	    
	    connexion.fermer();
	}
	
	public boolean choisirLigne(int id) throws IOException {
		connexion.envoyer("CHOIX "+id+" "+Main.controleur.getId());
		reponse = connexion.recevoir();
		
		return (reponse.equals("OK") ? true : false);
	}
	
	public String recevoirData() throws IOException {
		return connexion.recevoir();
	}
	
	public void envoyerOrdre(int idLigne, String data) throws IOException {
		connexion.envoyer("ORDRE "+idLigne+" "+data);
	}
}