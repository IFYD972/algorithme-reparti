package dauphine.projet.sar.reseaux;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.DatagramSocket;
import java.net.Socket;

import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;

public interface InterfaceConnexion {

		/*UDP*/
		public void envoyerUDP_a(String g, DatagramSocket socket)throws IOException;
		public void envoyerUDP_a(String g, DatagramSocket socket, int port)throws IOException;
		public boolean recevoirUDP(DatagramSocket socket,JSONObject jsonObject) throws IOException, ParseException;
		public String recevoirUDP(DatagramSocket soc) throws IOException;
		/*TCP*/
		public Boolean sur_receprion_de(Socket socket,JSONObject jsonObject) 
				throws ParseException, IOException;
		public String sur_reception_de_init(Socket socket) throws IOException;
		public void envoyer_a(String s, Socket socket)throws IOException;
}
