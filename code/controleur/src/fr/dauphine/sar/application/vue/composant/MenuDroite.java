package fr.dauphine.sar.application.vue.composant;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSeparator;

import fr.dauphine.sar.application.controleur.CellRenderer;
import fr.dauphine.sar.application.controleur.ConnecterCtrl;
import fr.dauphine.sar.application.controleur.ListeLigneCtrl;
import fr.dauphine.sar.application.controleur.RafraichirCtrl;

public class MenuDroite extends JPanel {
	public static JList listeLigne;
	public static DefaultListModel modelListeLigne;
	
	public static JComboBox slctDestination;
	public static DefaultComboBoxModel modelSlctDestination;
	
	public static JLabel lblVitesse;
	public static JLabel lblLigne;
	public static JLabel lblBus;
	public static JLabel lblDestination;
	public static JLabel lblTraffic;
	
	public static JButton btnConnexion;
	public static JButton btnAugmenterVitesse;
	public static JButton btnReduireVitesse;
	//public static JButton btnRafraichir;
	
	public MenuDroite() {
		lblBus = new JLabel(new ImageIcon("res/mini.png"));
		lblVitesse = new JLabel("VITESSE");
		lblLigne = new JLabel("LIGNE");
		lblDestination = new JLabel("DESTINATION");
		lblTraffic = new JLabel("Etat traffic");
		
		lblBus.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		btnAugmenterVitesse = new JButton("+");
		btnReduireVitesse = new JButton("-");
		btnConnexion = new JButton("Se connecter");
		//btnRafraichir = new JButton("Rafraichir");
		
		modelSlctDestination = new DefaultComboBoxModel();
		
		slctDestination = new JComboBox(modelSlctDestination);
		slctDestination.setEnabled(false);
		
        modelListeLigne = new DefaultListModel();

		listeLigne = new JList(modelListeLigne);
		
		listeLigne.addMouseListener(new ListeLigneCtrl());
		listeLigne.setCellRenderer(new CellRenderer());
		
		btnReduireVitesse.setMaximumSize(new Dimension(10,10));
        btnConnexion.addActionListener(new ConnecterCtrl());
        //btnRafraichir.addActionListener(new RafraichirCtrl());
        btnAugmenterVitesse.setEnabled(false);
        btnReduireVitesse.setEnabled(false);
        //btnRafraichir.setEnabled(false);
		
		JPanel pane = new JPanel();
		pane.setLayout(new BorderLayout());
		
		pane.add(btnReduireVitesse, BorderLayout.WEST);
		pane.add(lblVitesse, BorderLayout.CENTER);
		pane.add(btnAugmenterVitesse, BorderLayout.EAST);
		
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(lblBus);
        this.add(Box.createRigidArea(new Dimension(0,5)));
        this.add(lblTraffic);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(pane);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        //this.add(new Box.Filler(new Dimension(0,0), new Dimension(0,200), new Dimension(0,200)));
        this.add(slctDestination);
        this.add(Box.createRigidArea(new Dimension(0,10)));
        this.add(lblLigne);
        this.add(Box.createRigidArea(new Dimension(0,15)));
        this.add(new JSeparator());
        //this.add(Box.createRigidArea(new Dimension(0,15)));
        //this.add(btnRafraichir);
        this.add(Box.createRigidArea(new Dimension(0,15)));
        this.add(btnConnexion);
        this.add(Box.createRigidArea(new Dimension(0,15)));
        this.add(new JSeparator());
        this.add(Box.createRigidArea(new Dimension(0,15)));
        this.add(listeLigne);
        this.add(Box.createRigidArea(new Dimension(0,650)));
	}

}
