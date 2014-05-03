package fr.dauphine.sar.reseau;

import fr.dauphine.sar.application.modele.Controleur;
import fr.dauphine.sar.service.InterfaceService;

/**
 * 
 * Interface regroupant les différentes interfaces nécessairent au programme
 *
 */
public interface Constantes {
	//public final String IPDistante = "127.0.0.1";
	//public final String IPLocale = "127.0.0.1";
    public final String IPDistante = "192.168.43.131";
	
	public final int portDistantTCP = 2525;
	public final int portDistantUDP = 2525;
	
	public final int tailleTamponUdp = 1024;
}