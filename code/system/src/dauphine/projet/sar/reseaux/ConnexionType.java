package dauphine.projet.sar.reseaux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import utilitaire.Constantes;

public enum ConnexionType implements InterfaceConnexion{
	
	/*Methodes des Controleurs*/
	CONTROLEUR{
		public void envoyerUDP_a(String msg, DatagramSocket socket, int port) throws IOException {
			byte[] sendtanmpon = msg.getBytes();
			DatagramPacket PacketAEnvoyer = new DatagramPacket (sendtanmpon,
					sendtanmpon.length,InetAddress.getByName(Constantes.IP),port);
			socket.send(PacketAEnvoyer);
		}

		public boolean recevoirUDP(DatagramSocket socket, JSONObject jsonObject)
				throws IOException, ParseException {
			byte[] reveivetanmpon = new byte[1024];
			DatagramPacket packetRecu = 
					new DatagramPacket(reveivetanmpon, 
							reveivetanmpon.length);
			socket.receive(packetRecu);
			String s = new String(packetRecu.getData());
			if(!s.contains("QUIT")){
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(s);
			return true;
			}
			else{
				return false;
				}
		}

		public Boolean sur_receprion_de(Socket socket, JSONObject jsonObject)
				throws ParseException, IOException {
			BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s = entree.readLine();
			if(!s.equals("QUIT")){
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(s);
//			entree.close();
			return true;
			}else{
				return false;
			}
		}

		public String sur_reception_de_init(Socket socket) throws IOException {
			BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s = entree.readLine();
			System.out.println(s);
//			entree.close();
			return s;
		}

		public void envoyer_a(String s, Socket socket) throws IOException {
			PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
			sortie.println(s);
//			sortie.close();
		}

		@Override
		public String recevoirUDP(DatagramSocket soc) throws IOException {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void envoyerUDP_a(String msg, DatagramSocket socket)
				throws IOException {
			byte[] sendtanmpon = msg.getBytes();
			DatagramPacket PacketAEnvoyer = new DatagramPacket (sendtanmpon,
					sendtanmpon.length,InetAddress.getByName(Constantes.IP),Constantes.portsenDataControleurUDP);
			socket.send(PacketAEnvoyer);
			
		}
	},
	
	/*Methodes dens Bus*/
	BUS{
		public void envoyerUDP_a(String msg, DatagramSocket socket) throws IOException {
			byte[] sendtanmpon = msg.getBytes();
			DatagramPacket PacketAEnvoyer = new DatagramPacket (sendtanmpon,
					sendtanmpon.length,InetAddress.getByName(Constantes.IP),Constantes.portsenDataControleurUDP);
			socket.send(PacketAEnvoyer);
		}

		public boolean recevoirUDP(DatagramSocket socket, JSONObject jsonObject)
				throws IOException, ParseException {
			byte[] reveivetanmpon = new byte[1024];
			DatagramPacket packetRecu = 
					new DatagramPacket(reveivetanmpon, 
							reveivetanmpon.length);
			socket.receive(packetRecu);
			String s = new String(packetRecu.getData());
			if(!s.contains("QUIT")){
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(s);
			return true;
			}
			else{
				return false;
				}
		}
		
		public String recevoirUDP(DatagramSocket soc) throws IOException{
			byte[] reveivetanmpon = new byte[1024];
			DatagramPacket packetRecu = 
					new DatagramPacket(reveivetanmpon, 
							reveivetanmpon.length);
			soc.receive(packetRecu);
			String s = new String(packetRecu.getData());
			return s;
		}
		
		public Boolean sur_receprion_de(Socket socket, JSONObject jsonObject)
				throws ParseException, IOException {
			BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s = entree.readLine();
			if(!s.equals("QUIT")){
			JSONParser jsonParser = new JSONParser();
			jsonObject = (JSONObject) jsonParser.parse(s);
//			entree.close();
			return true;
			}else{
				return false;
			}
		}

		public String sur_reception_de_init(Socket socket) throws IOException {
			BufferedReader entree = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			String s = entree.readLine();
			System.out.println(s);
//			entree.close();
			return s;
		}

		public void envoyer_a(String s, Socket socket) throws IOException {
			PrintWriter sortie = new PrintWriter(socket.getOutputStream(), true);
			sortie.println(s);
//			sortie.close();
		}

		@Override
		public void envoyerUDP_a(String g, DatagramSocket socket, int port)
				throws IOException {
			// TODO Auto-generated method stub
			
		}
	};
}
