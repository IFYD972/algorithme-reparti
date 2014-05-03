package fr.dauphine.sar.application.modele;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Ligne {
	private Vector<Bus> listeBus;
	private Vector<String> listeStations;
	private Vector<JLabel> labelBus;
	private int id;
	private String nom;
	private boolean controlee;
	private String traffic;
	
	public Ligne(int id) {
		this.id = id;
		this.controlee = false;
		this.labelBus = new Vector<JLabel>();
	}
	
	public Ligne(String nom, int id) {
		this.nom = nom;
		this.id = id;
		this.controlee = false;
		this.labelBus = new Vector<JLabel>();
	}
	
	public int getId() { return this.id; }
	public void setId(int ligne) { this.id = ligne; }
	
	public String getNom() { return this.nom; }
	public void setNom(String name) { this.nom = name; }
	
	public boolean estControlee() { return this.controlee; }
	public void setControlee(boolean contr) { this.controlee = contr; }
	
	public void ajouterBus(Vector<Bus> bus) { this.listeBus = bus; }
	public Vector<Bus> getBus() { return this.listeBus; }
	public Vector<JLabel> getLabelBus() { return this.labelBus; }
	
	public void genererLabel() {
		Iterator it = listeBus.iterator();
		int compteurCritique = 0;
		int compteurInte = 0;
		
		while(it.hasNext()) {
			Bus myBus = (Bus) it.next();
			
			if(myBus.getVitesse() < 10)
				compteurCritique++;
			else if(myBus.getVitesse() < 30)
				compteurInte++;
			
			JLabel lab = new JLabel(new ImageIcon("res/icon_autobus.gif"));
			lab.setSize(50,50);
			lab.setLocation((myBus.getPosition()[0])-(lab.getWidth()/2), (myBus.getPosition()[1])-(lab.getHeight()/2));
			labelBus.add(lab);
		}
		
		if(compteurCritique >= 2)
			this.setEtatTraffic("Critique");
		else if(compteurInte >= 2)
			this.setEtatTraffic("Intermediaire");
		else
			this.setEtatTraffic("Normal");
		
		System.out.println("nb Bus : "+labelBus.size());
	}
	
	public String getEtatTraffic() { return this.traffic; }
	public void setEtatTraffic(String traf) { this.traffic = traf; }
	
	public String toString() { return this.nom+"/"+this.id; }
}
