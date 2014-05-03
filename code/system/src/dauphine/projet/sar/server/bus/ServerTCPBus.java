package dauphine.projet.sar.server.bus;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import utilitaire.Constantes;
import dauphine.projet.sar.server.Server;

public class ServerTCPBus extends Thread{
	private Server server;
	private ServerSocket serverSocket;
	
	public ServerTCPBus(Server server){
		super("Server Tcp Bus");
		this.server=server;
	}
	
	@Override
	public void run() {
		super.run();
		try {
			serverSocket = new ServerSocket(Constantes.portTcpBus);
			 while(true){
					
					// j'attends une connexion TCP
				 	server.console("Attente de connexion ... Tcp Bus");
					Socket socketTcp = serverSocket.accept();
					ServerTCPMultithreadBus stm = new ServerTCPMultithreadBus(socketTcp,server);
					server.addLignes(stm);
					stm.start();
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
