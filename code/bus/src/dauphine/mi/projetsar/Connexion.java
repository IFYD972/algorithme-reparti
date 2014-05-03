package dauphine.mi.projetsar;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

public class Connexion extends Thread{
	Socket s;
	PrintWriter pf;
	BufferedReader bf;
	
	private boolean debug = true;
	
	/** Creates a new instance of Connection 
	 * @throws IOException 
	 * @throws UnknownHostException */
	public Connexion(String adresse, int port) throws UnknownHostException, IOException {
		s = new Socket(adresse,port);
	}
	
	public Connexion(Socket s) {
		this.s = s;
	}
	
	public void ouvrirLecture(){
		try{
			bf = new BufferedReader(new InputStreamReader(s.getInputStream()));
		}catch(IOException io){
			System.out.println("echec de l'ouverture en lecture");
			io.printStackTrace();
		}
	}
	
	public void ouvrirEcriture(){
		try{
			// PrintWriter avec un auto flush sinon bloquant.
			// directement le flux logique.
			pf = new PrintWriter(s.getOutputStream(),true);
			// alternative 
			// PrintStream ps = new PrintStream(s.getOutputStream());
			
		}catch(IOException io){
			System.out.println("echec de l'ouverture en ecriture");
			io.printStackTrace();
		}
	}
	
	public void ecrire(String s){
		if(debug) {System.out.println("Ecriture TCP: " + s);}
		pf.println(s);
	}
	
	public String lire(){
		String t="";
		try{
			t = bf.readLine();
		}catch(IOException io){
			System.out.println("echec de lecture");
			io.printStackTrace();
		}
		
		if(debug) { if(!t.equals("")) {System.out.println("Lecture TCP: " + t);} }
		return t;
	}
	
	public void openSession(){
		ouvrirLecture();
		ouvrirEcriture();
	} 
	
	public void closeSession(){
		try{ 
			bf.close();
			pf.close();
			s.close();
		}catch(IOException io){
			System.out.println("echec de fermeture");
			io.printStackTrace();		
		}
	}
	
	public boolean isConnected(){
		return s.isConnected();
	}
	
}