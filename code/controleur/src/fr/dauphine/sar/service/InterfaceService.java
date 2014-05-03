package fr.dauphine.sar.service;

/* LOCAL IMPORT */
import java.io.IOException;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import fr.dauphine.sar.application.InterfaceApplication;
import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.controleur.CallbackListener;
import fr.dauphine.sar.application.modele.Bus;
import fr.dauphine.sar.application.modele.Ligne;
import fr.dauphine.sar.reseau.Constantes;
import fr.dauphine.sar.reseau.InterfaceReseau;
import fr.dauphine.sar.reseau.Protocole;


/**
 * 
 * Classe qui fournit les différentes fonctionnalités de traitement des données.
 *
 */
public class InterfaceService {
        private InterfaceReseau coucheReseau;
        private String reponse;
        private int portLocal;
        private JSONObject jsonObject;
        private InterfaceApplication coucheApplication;
        
        public InterfaceService(int port) {
        	portLocal = port;
        	jsonObject = new JSONObject();
        	coucheApplication = new InterfaceApplication();
        }
        
        /**
         * Méthode qui permet d'établir une connexion TCP avec le serveur.
         * @param callback fonction définie au niveau de la couche application appellée une fois la connexion établit
         */
        public void connecterServeur(CallbackListener callback) throws IOException {
        	
        	coucheReseau = new InterfaceReseau(Protocole.TCP, Constantes.IPDistante, portLocal, Constantes.portDistantTCP);
        	
			coucheReseau.envoyer("INIT "+portLocal);
			
    		if((reponse = coucheReseau.recevoir()).equals("200 READY")) {
    			String[] reponseParsed;
    			int idControleur;
    			
    			reponse = coucheReseau.recevoir();//ID CONTROLEUR + JSON LIGNES
    			reponseParsed = reponse.split(";");
    			
    			//{"id_controleur":2, "lignes":["ligne1", "ligne2", "ligne3"]}
    			jsonObject = (JSONObject) JSONValue.parse(reponseParsed[1]);
    			JSONArray jsonArray = (JSONArray) jsonObject.get("lignes");
    			idControleur = Integer.parseInt(""+jsonObject.get("id_controleur"));

    			//traitement JSON
    			if(jsonArray.size() > 0) {
        			synchronized(Main.controleur) {
        				Main.controleur.setId(idControleur);
        			}
        			
        			//S'il y a des lignes de disponibles
        			if(jsonArray.size() > 0 && !jsonArray.get(0).equals("")) {
	        			synchronized(Main.lignes) {
	        				for (int i = 0; i < jsonArray.size(); i++) {
	        					String nomLigne = (String)jsonArray.get(i);
	        					int idLigne = Integer.parseInt(nomLigne.substring(nomLigne.length() - 1));
	        					Ligne newLigne = new Ligne(nomLigne,idLigne);
	        					
	        					((HashMap) Main.lignes).put(nomLigne, newLigne);
	        				}
		        			coucheApplication.mettreAJourLignes();
	        			}
        			}
    			}
    		}
    		
			callback.onReturn(true);
        }
        
        public void rafraichirListeLignes() throws IOException {
			String[] reponseParsed;
			int idControleur;
			
			coucheReseau.envoyer("REFRESH");
			
			reponse = coucheReseau.recevoir();//ID CONTROLEUR + JSON LIGNES
			reponseParsed = reponse.split(";");
			
			//{"id_controleur":2, "lignes":["ligne1", "ligne2", "ligne3"]}
			jsonObject = (JSONObject) JSONValue.parse(reponseParsed[1]);
			JSONArray jsonArray = (JSONArray) jsonObject.get("lignes");
			idControleur = Integer.parseInt(""+jsonObject.get("id_controleur"));

			//traitement JSON
			if(jsonArray.size() > 0) {
    			synchronized(Main.controleur) {
    				Main.controleur.setId(idControleur);
    			}
    			
    			//S'il y a des lignes de disponibles
    			if(jsonArray.size() > 0 && !jsonArray.get(0).equals("")) {
        			synchronized(Main.lignes) {
        				for (int i = 0; i < jsonArray.size(); i++) {
        					String nomLigne = (String)jsonArray.get(i);
        					int idLigne = Integer.parseInt(nomLigne.substring(nomLigne.length() - 1));
        					Ligne newLigne = new Ligne(nomLigne,idLigne);
        					
        					((HashMap) Main.lignes).put(nomLigne, newLigne);
        				}
	        			coucheApplication.mettreAJourLignes();
        			}
    			}
			}
        }
        
        /**
         * Méthode qui permet de déconnecter le controleur du serveur.
         * @param callback fonction définie au niveau de la couche application appellée une fois la connexion établit
         * @throws IOException 
         */
        public void deconnecterServeur(CallbackListener callback) throws IOException {
            coucheReseau.deconnexionServeur();
            callback.onReturn(true);
        }
        
        /**
         * Permet de choisir la ligne désirée
         * @param id identifiant de la ligne
         * @param callback fonction définie au niveau de la couche application appellée une fois la connexion établit
         * @throws IOException 
         */
        public void choisirLigne(int id, CallbackListener callback) throws IOException {
            callback.onReturn(coucheReseau.choisirLigne(id));
        }
        
        /**
         * Envoi l'ordre d'augmenter la vitesse
         * @param callback fonction définie au niveau de la couche application appellée une fois la connexion établit
         * @throws IOException 
         */
        public void augmenterVitesse(Bus bus) throws IOException {
        	JSONObject ligneUne = new JSONObject();
    		ligneUne.put("bus",bus.getId());
    		ligneUne.put("type_ordre","vitesse");
    		ligneUne.put("valeur_ordre", bus.getVitesse()+1);
    		
        	coucheReseau.envoyerOrdre(bus.getLigneId(), ligneUne.toString());
        }
        
        /**
         * 
         * @param callback fonction définie au niveau de la couche application appellée une fois la connexion établit
         * @throws IOException 
         */
        public void diminuerVitesse(Bus bus) throws IOException {
        	JSONObject ligneUne = new JSONObject();
    		ligneUne.put("bus",bus.getId());
    		ligneUne.put("type_ordre","vitesse");
    		ligneUne.put("valeur_ordre", bus.getVitesse()-1);
    		
        	coucheReseau.envoyerOrdre(bus.getLigneId(), ligneUne.toString());
        }
        
        public void changerDestination(Bus bus, String dest) throws IOException {
	    	JSONObject ligneUne = new JSONObject();
			ligneUne.put("bus",bus.getId());
			ligneUne.put("type_ordre","destination");
			ligneUne.put("valeur_ordre", dest);
			
	    	coucheReseau.envoyerOrdre(bus.getLigneId(), ligneUne.toString());
        }
}