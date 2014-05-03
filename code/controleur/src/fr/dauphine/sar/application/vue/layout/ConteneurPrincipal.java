package fr.dauphine.sar.application.vue.layout;

/* STANDARD IMPORT */
import java.awt.BorderLayout;
import javax.swing.JPanel;

/* LOCAL IMPORT */
import fr.dauphine.sar.application.vue.composant.ComposantCentral;
import fr.dauphine.sar.application.vue.composant.MenuDroite;



public class ConteneurPrincipal extends JPanel{
	public ComposantCentral centre = new ComposantCentral();
	public MenuDroite menuDroite;
	
	public ConteneurPrincipal() {
		menuDroite = new MenuDroite();
		
	    this.setLayout(new BorderLayout());
	    
	    this.add(centre, BorderLayout.CENTER);
	    this.add(menuDroite, BorderLayout.EAST);
	}
	
}
