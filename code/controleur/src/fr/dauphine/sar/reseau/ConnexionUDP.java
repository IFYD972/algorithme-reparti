package fr.dauphine.sar.reseau;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

public class ConnexionUDP implements Connexion {
	private DatagramSocket socket 	= null;
	DatagramPacket PacketAEnvoyer 	= null;
	private byte[] tamponEnvoi 		= new byte[Constantes.tailleTamponUdp];
	private byte[] tamponReception 	= new byte[Constantes.tailleTamponUdp];
	private int portEcoute 			= 0;
	private int portEnvoi 			= 0;
	private String adresseIP 		= "";
	
	public ConnexionUDP(String adresse, int portEcoute, int portEnvoi) {
		this.portEcoute = portEcoute;
		this.portEnvoi = portEnvoi;
		this.adresseIP = adresse;
		
		try { socket = new DatagramSocket(portEcoute); }
		catch (SocketException e) { e.printStackTrace(); }
	}

	@Override
	public String recevoir() throws IOException {
		DatagramPacket packetRecu = new DatagramPacket(tamponReception, tamponReception.length);
		
		socket.receive(packetRecu);

		System.out.println(new String(packetRecu.getData()));
		
		return new String(packetRecu.getData());
	}
	
	@Override
	public String envoyer(String msg) throws IOException {		
		tamponEnvoi = msg.getBytes();
		PacketAEnvoyer = new DatagramPacket (tamponEnvoi,tamponEnvoi.length,InetAddress.getByName(adresseIP),portEnvoi);
		socket.send(PacketAEnvoyer);
		System.out.println(new String(tamponEnvoi));
		return new String(tamponEnvoi);
	}
	
	@Override
	public void fermer() {
		socket.close();
	}
}
