package fr.dauphine.sar.reseau;

import java.io.IOException;

/**
 * 
 * Interface utilisée par les différentes classes qui implémentent un protocole de communication
 *
 */
public interface Connexion {
	public String envoyer(String msg) throws IOException;
	public String recevoir() throws IOException;
	public void fermer();
}
