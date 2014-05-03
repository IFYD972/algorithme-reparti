package dauphine.projet.sar.dao;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

import dauphine.projet.sar.reseaux.ConnexionType;
import dauphine.projet.sar.server.Server;
import utilitaire.Constantes;

/**
 * 
 * Objet métier du contrôleur
 *
 */
public class Controleur extends JButton{
	private int ID;
	private int port;
	private InetAddress adress;
	private ArrayList<LigneBus> ligne = new ArrayList<LigneBus>();
	private Server server;
	private Socket socket;
	
	public Controleur(int id, Server server, Socket s){
		super();
		setText("Controleur n°"+id);
		this.socket=s;
		this.ID=id;
		this.server=server;
		setPreferredSize(new Dimension(310, 30));
		setBackground(Color.LIGHT_GRAY);
		setEnabled(false);
	}
	
	
	public void disconect(JPanel jPanel) throws IOException {
		jPanel.remove(this);
		jPanel.repaint();
		jPanel.revalidate();
	}
	
	public int getPort() {
		return port;
	}
	
	public InetAddress getIP(){
		return adress;
	}
	public void setPort(int p) {
		port=p;
	}
	
	public void setIP(InetAddress adr){
		adress=adr;
	}
	public void attributionLigne(LigneBus ligne){
		this.ligne.add(ligne);
	}
	
	public ArrayList<LigneBus> getLigneAtt() {
		return ligne;
	}
	
	public LigneBus getLigne(int id) {
		LigneBus val = null;
		for (LigneBus l : ligne) {
			if(l.getNumero()==id){
				val=l;
			}
		}
		return val;
	}
	
	public void envoyer_a(String s, Socket socketBus) throws IOException{
		ConnexionType.CONTROLEUR.envoyer_a(s, socketBus);
		this.server.console(s);
	}
	
	public void envoyer_ordre_a(int i,String s) throws IOException{
		LigneBus val = null;
		for (LigneBus l : ligne) {
			if(l.getNumero()==i) val = l;
		}
		ConnexionType.CONTROLEUR.envoyer_a(s, val.getSocket());
		this.server.console("Controleur n°"+ID+" envoi ordre --> "+s);
	}

	public void setID(int iD_Controleur) {
		this.ID=iD_Controleur;
		this.setText("Controleur n°"+ID);
		this.repaint();
		this.revalidate();
	}
	
	public void addLigne(LigneBus ligneBus){
		ligne.add(ligneBus);
	}
}
