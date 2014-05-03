package dauphine.mi.projetsar;

import java.util.Iterator;
import java.util.LinkedList;

public class Ligne {
	private int numero;
	private LinkedList<Station> stations = new LinkedList<Station>();
	
	public Ligne(int unNumeroDeLigne,  LinkedList<Station> lesStations) {
		this.numero = unNumeroDeLigne;
		this.stations = lesStations;
		
		// affectation des prochaine stations pour chaque station a part la dernière
		for (int i=0; i<this.stations.size()-1; i++) {
			Station uneStation = this.stations.get(i);
			uneStation.setProchaineStation(this.stations.get(i+1));
		}
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public Station getFirstStation() {
		return this.stations.getFirst();
	}
	public Station getLastStation() {
		return this.stations.getLast();
	}
	
	public Station getUneStation(String unLibelleStation) {
		Station uneStation = new Station(unLibelleStation);
		Iterator<Station> iter = this.stations.iterator();
		while(iter.hasNext()) {
			Station station = iter.next();
			if (station.getLibelle().equals(unLibelleStation))
				return station;
		}
		return uneStation;
	}
}
