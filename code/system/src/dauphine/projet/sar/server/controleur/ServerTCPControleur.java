package dauphine.projet.sar.server.controleur;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JButton;
import javax.swing.JLabel;

import dauphine.projet.sar.server.Server;
import utilitaire.Constantes;

public class ServerTCPControleur extends Thread {

	private Server server;
	private ServerSocket serverSocket;
	
	public ServerTCPControleur(Server server){
		super("Server Tcp Controleur");
		this.server=server;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			serverSocket=new ServerSocket(Constantes.TCPSystem);
			while(true){
				server.console("Attente de connexion ... Tcp Controleur ok");
				Socket s = serverSocket.accept();
				ServerTCPMultiThreadControleur th = new ServerTCPMultiThreadControleur(s, server);
				th.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
