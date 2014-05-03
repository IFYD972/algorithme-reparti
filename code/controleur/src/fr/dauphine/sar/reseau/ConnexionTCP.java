package fr.dauphine.sar.reseau;

/* LIBRARY IMPORT */

/* STANDARD IMPORT */
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;



public class ConnexionTCP implements Connexion{
	private Socket socket;
	private PrintWriter writerSocket = null;
	private BufferedReader tamponSocket = null;
	
	public ConnexionTCP(Socket sock) {
		socket = sock;
	}

	private void ouvrirLecture(){
		if(tamponSocket == null) {
			try{ tamponSocket = new BufferedReader(new InputStreamReader(socket.getInputStream())); }
			catch(IOException io){ io.printStackTrace(); }
		}
	}
	
	private void ouvrirEcriture(){
		if(writerSocket == null) {
			try{ writerSocket = new PrintWriter(socket.getOutputStream(),true); }
			catch(IOException io){ io.printStackTrace(); }
		}
	}
	
	@Override
	public String envoyer(String msg){
		ouvrirEcriture();
		System.out.println(msg);
		writerSocket.println(msg);
		
		return msg;
	}
	
	@Override
	public String recevoir(){
		ouvrirLecture();
		
		String msgRecu = "";
		
		try{ msgRecu = tamponSocket.readLine(); }
		catch(IOException io){ io.printStackTrace(); }
		System.out.println(msgRecu);
		return msgRecu;
	}
	
	public void fermer(){
        try{
	       	if(tamponSocket != null)
                tamponSocket.close();
	        
	        if(writerSocket != null)
                writerSocket.close();
	                
	        if(socket != null)
                socket.close();
        }
        catch(IOException io){ io.printStackTrace(); }
	}
}
