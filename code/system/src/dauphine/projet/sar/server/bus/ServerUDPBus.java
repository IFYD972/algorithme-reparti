package dauphine.projet.sar.server.bus;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utilitaire.Constantes;
import dauphine.projet.sar.dao.Controleur;
import dauphine.projet.sar.dao.LigneBus;
import dauphine.projet.sar.reseaux.ConnexionType;
import dauphine.projet.sar.server.Server;

public class ServerUDPBus extends Thread {
	
	private byte[] sendtanmpon = new byte[1024];
	private byte[] reveivetanmpon = new byte[1024];
	
	private DatagramSocket socket;
	private boolean RUN = false;
	private Server server;
	private LigneBus ligneBus;
	private int PortC;
	private InetAddress addr;
	private JSONObject jsonObject;
	private int in = 0;
	private int out = 0;
	
	
	public ServerUDPBus(DatagramSocket socket, Server server) {
		super("Server UDP controleur");
		this.socket=socket;
		this.server=server;
		
	}
	
	@Override
	public void run() {
	super.run();
	
	
    RUN = true;
    
    String data ="";
	while(RUN){
		try {
			if(server.getLigneALL().size()>0)
			if((data=ConnexionType.BUS.recevoirUDP(socket)).equals("QUIT"))
				{
				
			}else{
				JSONObject json =  (JSONObject)JSONValue.parse(data.toString().trim());
				System.out.println("Etat du json recup --> "+json.get("numero").getClass());
				System.out.println(json.toJSONString());
				int id = ((Long)json.get("numero")).intValue();	
				LigneBus ligne = server.getLigneID(id).getLigne();
				System.out.println(ligne);
				if(!ligne.isLibre())
				{
					ligne.envoyer_data(socket, json);
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		}
    		socket.close();
	}
	
}