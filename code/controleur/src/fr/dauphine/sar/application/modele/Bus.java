package fr.dauphine.sar.application.modele;

public class Bus {
	private int id;
	private int ligneId;
	private int vitesse;
	private String destination;
	private int position_x;
	private int position_y;
	
	public void setId(int id) { this.id = id; }
	public int getId() { return this.id; }
	
	public void setLigneId(int idl) { this.ligneId = idl; }
	public int getLigneId() { return ligneId; }
	
	public void setVitesse(int vit) { this.vitesse = vit;	}
	public int getVitesse() { return this.vitesse;	}
	
	public void setDestination(String dest) { this.destination = dest; }
	public String getDestination() { return this.destination; }
	
	public void setPosition(int x, int y) { this.position_x = x; this.position_y = y; }
	public int[] getPosition() { return new int[]{position_x, position_y}; }
}
