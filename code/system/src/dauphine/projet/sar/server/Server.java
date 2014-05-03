package dauphine.projet.sar.server;

/*STANDARD IMPORT*/
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

import org.json.simple.JSONObject;

import dauphine.projet.sar.dao.LigneBus;
import dauphine.projet.sar.reseaux.ConnexionType;
import dauphine.projet.sar.server.RMI.ControleurRemoteImp;
import dauphine.projet.sar.server.bus.ServerTCPBus;
import dauphine.projet.sar.server.bus.ServerTCPMultithreadBus;
import dauphine.projet.sar.server.bus.ServerUDPBus;
import dauphine.projet.sar.server.controleur.ServerTCPControleur;
/*IMPORT LOCAL*/
import utilitaire.Constantes;
import utilitaire.Utile;

/*LIBRARY IMPORT*/


/*
 * Le server
 */
public class Server extends JFrame implements Runnable{
		
	/*Variable configuration réseaux*/
	private DatagramSocket socketServerUdp;
	private DatagramSocket socketServerUdpBus;
	
	
	
	/*Variable affichage*/
	private JPanel container = new JPanel(new BorderLayout());
	private JPanel panControleur = new JPanel();
	private JPanel panBus =  new JPanel();
	private JTextArea console = new JTextArea();
	private int rowConsole = 5;
	private String stConsole = " CONSOLE SERVER  \n -----------------------------";
	private JScrollPane jscroll = new JScrollPane(console);
	private ArrayList<ServerTCPMultithreadBus> Lignes = new ArrayList<ServerTCPMultithreadBus>();
	
	private int NBLingnes = 8;
	private int ID_BUS = 1;
//	private int ID_Controleur = 1;
	private JSONObject[] InfoLigne;
	private boolean[] TabLigne = new boolean[Constantes.MAXLIGNE+1];
	private boolean[] ID_controleur = new boolean[Constantes.MAXLIGNE+1];
	
		public Server() {
			super("SERVER");
			
			/*init ligne */
			try {
				InfoLigne = Utile.getStation();
				TabLigne[0]=true;
				ID_controleur[0]=true;
				for (int i = 1; i < TabLigne.length; i++) {
					TabLigne[i]=false;
					ID_controleur[i]=false;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*init fenêtre*/
			this.setSize(840, 640);
			this.setLocationRelativeTo(null);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			this.container.add(panBus, BorderLayout.WEST);
			this.container.add(panControleur, BorderLayout.EAST);
			this.container.add(jscroll, BorderLayout.SOUTH);
			
			this.panBus.setPreferredSize(new Dimension(515, 400));
			this.panBus.setToolTipText("Panneau de Serveur BUS");
			this.panBus.setAutoscrolls(true);
			
			jscroll.setBounds(100,30,250,100);
			jscroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			jscroll.setAutoscrolls(true);
			jscroll.setPreferredSize(new Dimension(830, 90));
			
			this.console.setPreferredSize(new Dimension(830, 100));
			this.console.setToolTipText("Console");
			this.console.setAutoscrolls(true);
			
			this.panControleur.setPreferredSize(new Dimension(315, 400));
			this.panControleur.setToolTipText("Panneau Controleur");
			this.panBus.setBackground(Color.GRAY);
			this.console.setBackground(Color.black);
			console.setForeground(Color.cyan);
			console.setBackground(Color.black);
			console.setAutoscrolls(true);
			console.setRows(rowConsole);
			console.setLineWrap(true);
			console.setFocusable(true);
			console.setEditable(false);
			console.setText(stConsole);
			
			this.getContentPane().add(container);
			this.setVisible(true);
		}
		
		@Override
		public void run() {
		try {
			
		/*Lancement la connexion udp controleur*/
		socketServerUdp =  new DatagramSocket(Constantes.PortUDPSystem);
		
		new ServerUDPBus(socketServerUdp, this).start();
		
		/*Lancement du TCP bus*/
		new ServerTCPBus(this).start();
		
		/*Lancement TCP controleur*/
		new ServerTCPControleur(this).start();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		
		public DatagramSocket getUDPServer(){
			return this.socketServerUdp;
		}
		
		
		public JPanel getPanBus(){
			return panBus;
		}
		
		public JPanel getPanControleur(){
			return panControleur;
		}
		
		public synchronized void console(String s){
			rowConsole++;
			this.console.setRows(rowConsole);
			this.console.insert("\n Server > "+s, this.stConsole.length());
			console.setSelectionStart(rowConsole);
		}
		
		public synchronized void console(String s, String ss){
			rowConsole++;
			this.console.setRows(rowConsole);
			this.console.insert("\n "+ss+" > "+s, this.stConsole.length());
			console.setSelectionStart(rowConsole);
		}
		
		public void addLignes(ServerTCPMultithreadBus stm){
			Lignes.add(stm);
		}
		
		
		public int getID_Bus(){
			int val =ID_BUS;
			ID_BUS=(ID_BUS+1)%NBLingnes;
			return val;
		}
		
		public int getID_Controleur(){
			int val = 0;
			int nb = ID_controleur.length;
			boolean t = true;
			for (int i = 0; i < nb && t; i++) {
				if(ID_controleur[i]==false){
					val=i;
					ID_controleur[i]=true;
					t=false;
					break;
				}
			}
			return val;
		}
		
		public void setIndexControleurFree(int i){
			ID_controleur[i]=false;
		}
		
		public boolean getIndexControleurFree(int i){
			return ID_controleur[i];
		}
		
		
		public synchronized ArrayList<ServerTCPMultithreadBus> getLigneALL(){
			return Lignes;
		}
		
		public synchronized ServerTCPMultithreadBus getLigneID(int ID){
			ServerTCPMultithreadBus val= null;
			for (ServerTCPMultithreadBus l : Lignes) {
				if(l.getID()==ID) val=l;
			}
			return val;
		}
		
		/*Main de du serveur*/
		public static void main(String[] args) throws RemoteException, MalformedURLException {
			Server server = new Server();
			Thread th = new Thread(server);
			th.start();
		}
		
		public JSONObject getInfoLigne(int i){
			return InfoLigne[i];
		}
		
		public String getLigneDispo(){
			String s="";
			for (int i = 1; i < TabLigne.length; i++) {
				if(TabLigne[i]==true){
					s+="LIGNE "+i+",";
				}
			}
			
			return s;
		}
		
		public int getIndexLigneFree(){
			int val = 0;
			int nb = TabLigne.length;
			boolean t = true;
			for (int i = 0; i< nb && t; i++) {
				if(TabLigne[i]==false){
					val=i;
					t=false;
					TabLigne[i]=true;
					break;
				}
			}
			return val;
		}
		
		public void setIndexLigneFree(int i){
			TabLigne[i]=false;
		}
		
		public boolean getIndexLigneFree(int i){
			System.out.println("ligne "+i+ " demandé");
			System.out.println(TabLigne[i]);
			return TabLigne[i];
		}
}
