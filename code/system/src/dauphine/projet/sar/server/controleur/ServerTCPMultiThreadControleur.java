package dauphine.projet.sar.server.controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.text.html.HTMLDocument.HTMLReader.SpecialAction;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import dauphine.projet.sar.dao.Controleur;
import dauphine.projet.sar.dao.LigneBus;
import dauphine.projet.sar.reseaux.ConnexionType;
import dauphine.projet.sar.server.Server;
import dauphine.projet.sar.server.bus.ServerTCPMultithreadBus;

public class ServerTCPMultiThreadControleur extends Thread{
	
	private Server server;
	private Socket socket;
	private JButton but;
	private PrintWriter sortie;
	private BufferedReader entree;
	private Controleur controleur;
	private boolean RUN;
	private ArrayList<LigneBus> ligne = new ArrayList<LigneBus>();
	
	private int ID_Controleur;
	
	public ServerTCPMultiThreadControleur(Socket s, Server server) {
		super("Server Tcp Multi Controleur");
		this.server=server;
		
		this.socket=s;
		this.controleur = new Controleur(ID_Controleur, server, s);
		server.getPanControleur().add(controleur);
		server.getPanControleur().repaint();
		server.getPanControleur().revalidate();
	}
	
	@Override
	public void run() {
		super.run();
		RUN=true;
		
		try {
			String s = sur_reception_de();
			String[] init=s.split(" ");
			if(init[0].equals("INIT"))
			{
				controleur.setPort(new Integer(init[1]));
				this.server.console("INIT", "Controleur >");
				/*Activation de l'envoie de données*/
				envoyer_a("200 READY");
				this.ID_Controleur=this.server.getID_Controleur();
				controleur.setID(ID_Controleur);
				JSONObject ligneUne = new JSONObject();
				String st[] = server.getLigneDispo().split(",");
				LinkedList stationsLigneUne = new LinkedList();
				for (int i = 0; i < st.length; i++) {
					stationsLigneUne.add(st[i]);
				}
				ligneUne.put("id_controleur", ID_Controleur);
				ligneUne.put("lignes",stationsLigneUne);
					JSONParser jsonParser = new JSONParser();
				envoyer_a("DATA;"+ligneUne.toJSONString());
				s = sur_reception_de();
				String [] val = s.split(" ");
				if(val[0].equals("QUIT")){
						 RUN=false;
						controleur.disconect(server.getPanControleur());
						this.server.setIndexControleurFree(ID_Controleur);
				 }else{
//				val = s.split(" ");
							if(val[0].equals("CHOIX")){
								LigneBus l = this.server.getLigneID(new Integer(val[1].trim())).getLigne();
								if(l.isLibre()
										&& (new Integer(val[2].trim()))== this.ID_Controleur){
									l.addControleur(controleur);
									ligne.add(l);
									controleur.addLigne(l);
									System.out.println("Ligne attribué a "+ID_Controleur);
									envoyer_a("OK");	
									while(RUN){
										s = sur_reception_de();
										if(s==null) {
											RUN=false;
											controleur.disconect(server.getPanControleur());
											this.server.setIndexControleurFree(ID_Controleur);
										}else{
										val = s.split(" ");
										if(val[0].equals("CHOIX")){
											l = this.server.getLigneID(new Integer(val[1].trim())).getLigne();
											if(l.isLibre()
													&& (new Integer(val[2].trim()))== this.ID_Controleur){
												l.addControleur(controleur);
												ligne.add(l);
												envoyer_a("OK");
												}
											else{
												envoyer_a("NOT");
											}
										}
										 if(s.contains("QUIT"))
										{
											RUN=false;
											controleur.disconect(server.getPanControleur());
											this.server.setIndexControleurFree(ID_Controleur);
											
										}
										else if(s.contains("ORDRE")){
											controleur.envoyer_ordre_a(new Integer(val[1].trim()
													),val[2]);
										}
										else if (s.equals("REFRESH")){
											envoyer_a("DATA;"+ligneUne.toJSONString());
										}
								}
									}
						}
								else{
									envoyer_a("NOT");
								}
			}
			
		}}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		catch (NullPointerException e) {
			// TODO Auto-generated catch block
			try {
				RUN=false;
				controleur.disconect(server.getPanControleur());
				this.server.setIndexControleurFree(ID_Controleur);
				server.console("deconnexion malveillante observé ---> Controleur "+ID_Controleur);
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void envoyer_a(String s) throws IOException{
		ConnexionType.CONTROLEUR.envoyer_a(s, this.socket);
	}
	
	public String sur_reception_de() throws IOException{
		return ConnexionType.CONTROLEUR.sur_reception_de_init(socket);
	}
	
	
}
