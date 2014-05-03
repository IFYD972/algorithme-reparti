package dauphine.projet.sar.dao;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.LayoutManager;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.rmi.server.SocketSecurityException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;

import org.json.simple.JSONObject;

import dauphine.projet.sar.reseaux.ConnexionType;
import utilitaire.Constantes;

/*
 * Classe object métier
 */
public class LigneBus extends JPanel{
		
		private int NUMERO;
		private Socket socket;
		private int port;
		private InetAddress adress;
		private JLabel name;
		private JLabel donneBus= new JLabel();
		GridLayout monStyle = new GridLayout();						
		JPanel monContenu = new JPanel(monStyle);
		JScrollPane maFenetre = new JScrollPane(monContenu);
		private JLabel bus = new JLabel();
		private Controleur controleur;
		int in = 0;
		int out = 0;
		JTextArea jPanel;
		private ArrayList<JSONObject> data = new ArrayList<JSONObject>();
		private int TAILLE = 20;
		private int NBcell = 0;
		 
		public LigneBus(Socket socket, int ID){
			this.setBackground(Color.white);
			this.setPreferredSize(new Dimension(515, 100));
			this.socket = socket;
			this.NUMERO=ID;	
			Bus val = new Bus(1,"", "", "");
			name = new JLabel("Serveur n°"+NUMERO);
			name.setForeground(Color.red);
			name.setPreferredSize(new Dimension(100, 10));
			this.add(name);
			this.add(maFenetre);
			maFenetre.setPreferredSize(new Dimension(500,50));
			maFenetre.setBounds(100,30,250,10);
			maFenetre.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
			this.setAutoscrolls(true);
			maFenetre.setAutoscrolls(true);
			monContenu.setForeground(Color.white);
			monContenu.add(donneBus);
		}
		
		public void envoyer_a(String msg, DatagramSocket socketUdp) throws IOException{
			ConnexionType.BUS.envoyerUDP_a(msg, socketUdp);
		}
		
		public void envoyer_data(DatagramSocket socketUdp) throws IOException{
			System.out.println("Données envoyés : -->  "+data.get(out).toJSONString());
			System.out.println("Port controleur --> "+controleur.getPort());
			this.bus.removeAll();
			this.bus.setText(data.get(out).toJSONString());
			jPanel.repaint();
			this.getParent().revalidate();
			ConnexionType.CONTROLEUR.envoyerUDP_a(data.get(out).toJSONString(), socketUdp, controleur.getPort());	
			out=(out+1)%Constantes.MAXDATA;
			NBcell--;
		}
		
		public void envoyer_data(DatagramSocket socketUdp, JSONObject json) throws IOException{
			donneBus.removeAll();
			donneBus.setText(json.toJSONString());
			monContenu.repaint();
			monContenu.revalidate();
			ConnexionType.CONTROLEUR.envoyerUDP_a(json.toJSONString(), socketUdp, controleur.getPort());	
			out=(out+1)%Constantes.MAXDATA;
			NBcell--;
		}
		
		public void addData(JSONObject json){
			data.add(json);
			in=(in+1)%Constantes.MAXDATA;
			NBcell++;
		}
		public void disconect(JPanel jPanel) throws IOException {
			jPanel.remove(this);
			jPanel.repaint();
			jPanel.revalidate();
			jPanel.setBackground(Color.GRAY);
			this.socket.close();
		}
		
		public void addControleur(Controleur controleur){
			this.controleur= controleur;
		}
		
		public Controleur getControleur(){
			return this.controleur;
		}
		
		public boolean isLibre() {
			if(controleur==null) return true;
			else return false;
		}
		
		public Socket getSocket() {
			return socket;
		}
		
		public int getNumero(){
			return NUMERO;
		}
		
		public boolean isReceive(){
			if(NBcell<Constantes.MAXDATA) return true;
			else return false;
		}
}
