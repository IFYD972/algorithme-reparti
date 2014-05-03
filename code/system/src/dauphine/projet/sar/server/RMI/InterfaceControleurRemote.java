package dauphine.projet.sar.server.RMI;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface InterfaceControleurRemote extends Remote{
	
	public void vitessePlus (int ID_Bus)  throws RemoteException;
	
	public void vitesseMoins (int ID_bus)  throws RemoteException;

}
