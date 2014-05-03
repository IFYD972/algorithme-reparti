package fr.dauphine.sar.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.swing.SwingUtilities;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import fr.dauphine.sar.application.InterfaceApplication;
import fr.dauphine.sar.application.Main;
import fr.dauphine.sar.application.modele.Bus;
import fr.dauphine.sar.application.modele.Ligne;
import fr.dauphine.sar.application.vue.composant.MenuDroite;
import fr.dauphine.sar.reseau.Constantes;
import fr.dauphine.sar.reseau.InterfaceReseau;
import fr.dauphine.sar.reseau.Protocole;

/**
 * 
 * Service lancé en arrière plan qui permet de recevoir les données recuent en UDP du serveur de ligne afin
 * de mettre à jour les données des modèles de données.
 *
 */
public class ServiceReceptionData extends Thread {
    private InterfaceReseau coucheReseau;
    private InterfaceApplication coucheApplication;
    private boolean continuer = true;
    
	@Override
	public void run() {
        coucheReseau = new InterfaceReseau(Protocole.UDP, Constantes.IPDistante, Main.portLocal, Constantes.portDistantUDP);
        coucheApplication = new InterfaceApplication();
        
        System.out.println("CONNEXION UDP : "+Main.portLocal);
        //Tant qu'il n'y a pas de problème avec la couche réseau, on continue
        while(continuer) {
        	System.out.println("ATTENTE");
        	try {
        		String reponse = coucheReseau.recevoir();
        		JSONObject jsonObject = (JSONObject) JSONValue.parse(reponse.toString().trim());
        		//System.out.println(jsonObject.keySet());
        		
        		if(jsonObject == null) System.out.println("JSON OBJECT NULL");
        		else {
        			System.out.println("JSON OBJECT GOOD");
	        		
	        		//System.out.println(jsonObject.keySet().getClass());
	        		Set<String> keys = jsonObject.keySet();
	        		
	        		Vector<Bus> listeBus = new Vector<Bus>();
	        		int idLigne = ((Long)jsonObject.get("numero")).intValue();
	        		
	        		//{"vitesseCourante":6,"stationCourante":"84:177","stationDestination":"485:225"}
	        		for(String key : keys) {
	        			System.out.println("KEY:"+key);
	        			if(jsonObject.get(key) instanceof String){
		        			String jsonString = (String) jsonObject.get(key);
		        			JSONObject busInfo = (JSONObject) JSONValue.parse(jsonString.trim());
		        			
		        			Bus bus = new Bus();
		        			bus.setVitesse(((Long)busInfo.get("vitesseCourante")).intValue());
		        			bus.setDestination((String)busInfo.get("stationDestination"));
		        			
		        			String[] positions = ((String)busInfo.get("stationCourante")).split(":");
		        			bus.setPosition(Integer.parseInt(positions[0]), Integer.parseInt(positions[1]));
		        			
		        			
		        			int idBus = Integer.parseInt(key.substring(key.length() - 1));
		        			bus.setId(idBus);
		        			bus.setLigneId(idLigne);
	
		        			listeBus.add(bus);
	        			}
	        		}
	        		
	        		final Ligne updateLigne = new Ligne("LIGNE "+idLigne,idLigne);
	        		updateLigne.ajouterBus(listeBus);
	        		updateLigne.genererLabel();
	        		
	        		
	        		new Thread(new Runnable(){
	        			public void run() {

	    	        		synchronized(Main.lignes) {
	    	        			Main.lignes.put(updateLigne.getNom(), updateLigne);
		        				coucheApplication.mettreAJourAffichageLigne();
	    	        		}
	        			}
	        		}).start();
        		}
    			//for()
        		
    			//JSONArray jsonArray = (JSONArray) jsonObject.get("lignes");
    			//int idControleur = Integer.parseInt(""+jsonObject.get("id_controleur"));
        	}
        	catch(IOException e) { e.printStackTrace(); continuer=false; }
        }
        
        try { coucheReseau.deconnexionServeur(); }
        catch (IOException e) { e.printStackTrace(); }
	}
}
