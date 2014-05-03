package dauphine.projet.sar.server.bus;

/*STANDARD IMPORT*/
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.Console;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;


















import javax.swing.JPanel;



import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;














/*LIBRARY IMPORT*/
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utilitaire.Utile;
import dauphine.projet.sar.dao.LigneBus;
import dauphine.projet.sar.reseaux.ConnexionType;
import dauphine.projet.sar.server.Server;


public class ServerTCPMultithreadBus extends Thread {
		/*Variable serveur*/
		private Socket socket;
		private Server server;
		private JSONObject jsonC; 
		
		/*Variables Algo conso*/
		JSONObject jsonObject;
		private int nbMessage = 0;
		private int in = 0;
		private int out = 0;
		private int N = 20;
		
		/*Variable Graphique*/
		private JPanel oneBus = new JPanel();
		private JTextArea text = new JTextArea();
		private int rowConsole = 10;
		private JScrollPane jscroll = new JScrollPane(text);
		private int ID_LIGNE;
		
		private LigneBus ligneBus;
		
		public ServerTCPMultithreadBus(Socket socket, Server server){
			super("Server Tcp Multi Bus");
			/**/
			this.socket=socket;
			this.server = server;
			this.oneBus.setBackground(Color.GRAY);
			this.oneBus.setForeground(Color.cyan);
			this.text.setEditable(false);
			this.oneBus.setPreferredSize(new Dimension(515, 80));
			
			this.server.getPanBus().add(oneBus);
			this.server.getPanBus().repaint();
			this.server.getPanBus().revalidate();
		}
		
		@Override
		public void run() {
		super.run();
		try {
			
			String s = ConnexionType.BUS.sur_reception_de_init(socket);
		if(s.equals("INIT"));
		{
			ID_LIGNE = server.getIndexLigneFree();
			if(ID_LIGNE == 0){ConnexionType.BUS.envoyer_a("REJET", socket);}
			else{
				server.console("Ligne "+ID_LIGNE +" activé");
				ligneBus = new LigneBus(socket, ID_LIGNE);
				oneBus.add(ligneBus);
				server.addLignes(this);
				server.getPanBus().repaint();
				server.getPanBus().revalidate();
			boolean RUN = true;
			ConnexionType.BUS.envoyer_a("220 READY", socket);
			server.console(server.getInfoLigne(ID_LIGNE).toJSONString());
			ConnexionType.BUS.envoyer_a(server.getInfoLigne(ID_LIGNE).toJSONString(), socket);
//			ConnexionType.BUS.envoyer_a("HAaaaaaaaaaaaaaaaaa", socket);
			String data ="";
			while(RUN){
				data=ConnexionType.BUS.sur_reception_de_init(socket);
				if(data!=null){
				if(data.equals("QUIT"))
					{
					 ConnexionType.BUS.envoyer_a("221 closing connexion", socket);
					 ligneBus.disconect(oneBus);
					 server.getLigneALL().remove(this);
					 server.getPanBus().remove(oneBus);
					 this.server.setIndexLigneFree(ID_LIGNE);
					 RUN=false;	
//					 oneBus.repaint();oneBus.revalidate();
					 server.getPanBus().repaint();server.getPanBus().revalidate();
					}
				else{
//					ConnexionType.BUS.envoyer_a("HAaaaaaaaaaaaaaaaaa", socket);
					Thread.sleep(2000);
				}
				}else{
				 RUN=false;
				 ligneBus.disconect(oneBus);
				 server.getLigneALL().remove(this);
				 server.getPanBus().remove(oneBus);
				 ConnexionType.BUS.envoyer_a("221 closing connexion", socket);
				 this.server.setIndexLigneFree(ID_LIGNE);
				 RUN=false;	
//				 oneBus.repaint();oneBus.revalidate();
				 server.getPanBus().repaint();server.getPanBus().revalidate();
				}
		}
		}
			
			socket.close();
		}	
			} catch (IOException e) {
				// TODO Auto-generated catch block
				 try {
					ligneBus.disconect(oneBus);
					 server.getLigneALL().remove(this);
					 server.getPanBus().remove(oneBus);
					 this.server.setIndexLigneFree(ID_LIGNE);
					 server.getPanBus().repaint();server.getPanBus().revalidate();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		public LigneBus getLigne(){
			return ligneBus;
		}
		
		public int getID(){
			return ID_LIGNE;
		}
}



