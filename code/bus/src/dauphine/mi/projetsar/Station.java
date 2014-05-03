package dauphine.mi.projetsar;

public class Station {
	private String libelle;
	private Station prochaineStation;
	
	public Station(String unLibelleStation) {
		this.libelle = unLibelleStation;
	}
	
	public String getLibelle() {
		return this.libelle;
	}
	public void setProchaineStation(Station uneStation) {
		this.prochaineStation = uneStation; 
	}
	public Station getProchaineStation() {
		return this.prochaineStation; 
	}
}
