package utilitaire;

import java.net.InetAddress;

/*
 * Classe de variables constantes
 */
public interface Constantes {
//	public final String IP = "127.0.0.1";
	public final String IP = "192.168.43.240";
	
	/*Port used System*/
	public final int TCPSystem = 2525;
	public final int PortUDPSystem = 2525;
	
	
	/*Port used Controleur*/
	public final int TCPControleur = 2626;
	public final int portsenDataControleurUDP = 2626;
	
	/*Port used Bus*/
	public final int portUdpSendOrdrer =  4242;
    public final int portTcpBus = 4141;
//    public final String url = "rmi://127.0.0.1/ordre";
    public final String url = "rmi://192.168.43.131/ordre";
    
    /*Taille Tampon*/
    public final int tamponUDP = 100;
    
    /*Tampon reception UDP*/
    public final int TAMPON = 20;
    
    /*MAX LIGNE*/
    public final static int MAXLIGNE = 8;
    
    /*MAX DATA BUFFER*/
    public final static int MAXDATA = 20;
    
    
    
}