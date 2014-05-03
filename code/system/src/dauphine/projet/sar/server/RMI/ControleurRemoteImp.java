package dauphine.projet.sar.server.RMI;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import dauphine.projet.sar.server.Server;

public class ControleurRemoteImp extends UnicastRemoteObject implements InterfaceControleurRemote {
	
	private Server server;

	public ControleurRemoteImp(Server server) throws RemoteException {
		super();
		this.server=server;
	}

	@Override
	public void vitessePlus (int ID_Bus) {
		server.console("Vitesse de "+ID_Bus+" augmentée");
	}

	@Override
	public void vitesseMoins(int ID_bus) {
		server.console("Vitesse de "+ID_bus+" diminuée");
	}

}
