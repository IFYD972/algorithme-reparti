package dauphine.mi.projetsar;

import org.json.simple.JSONObject;

public class Bus extends Thread {
	
	private int numero;
	private Station stationCourante;
	private Station destination;
	private int vitesseCourante;
	
	private boolean debug = true;
	
	public Bus (int unNumero, Station stationDepart, Station stationDestination, int uneVitesse) {
		this.numero = unNumero;
		this.stationCourante = stationDepart;
		this.destination = stationDestination;
		this.vitesseCourante = uneVitesse;
	}
	
	public int getNumero() {
		return this.numero;
	}
	
	public void setDestination (Station uneDestination) {
		this.destination = uneDestination;
		synchronized(this) {
			this.notify();
		}
	}
	public Station getDestination () {
		return this.destination;
	}
	
	public void setVitesseCourante (int uneVitesseCourante) {
		this.vitesseCourante = uneVitesseCourante;
		synchronized(this) {
			this.notify();
		}
	}
	public double getVitesseCourante () {
		return this.vitesseCourante;
	}
	
	public void setStationCourante (Station uneStationCourante) {
		this.stationCourante = uneStationCourante;
	}
	public Station getStationCourante () {
		return this.stationCourante;
	}
	
	public void run() {
		while (this.stationCourante != this.destination) {
			synchronized(this) {
				try {		
					this.wait(new Long ((1*100000)/this.vitesseCourante));
				} catch (InterruptedException e) { e.printStackTrace(); }
			}
			this.stationCourante = this.stationCourante.getProchaineStation();
			if(debug) { if(this.stationCourante == this.destination) { System.out.println("Le bus " + this.numero + " est arrivé à destination");} }
		}	
	}
	
	public String toJSONString() {
		JSONObject dataInJsonFormat = new JSONObject();
		
		dataInJsonFormat.put("stationCourante", this.stationCourante.getLibelle());
		dataInJsonFormat.put("stationDestination", this.destination.getLibelle());
		dataInJsonFormat.put("vitesseCourante", this.vitesseCourante);
	 
		return dataInJsonFormat.toJSONString();
	}
}
