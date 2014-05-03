package utilitaire;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.LinkedList;

import javax.swing.JEditorPane;

import org.json.simple.JSONObject;

/*
 * Class utilitaire
 */
public class Utile {
	
	public final static String file = "stations.txt";
	
	public static JSONObject[] getStation() throws IOException{
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		JSONObject[] jsonTab = new JSONObject[Constantes.MAXLIGNE+1];
		JSONObject json = null;
		String s = "";
		String[] tabS = null;
		int num = 0;
		while ((s=br.readLine())!= null) {
			num++;
			tabS = s.split(",");
			json = new JSONObject();
			json.put("numero", new Integer(num));
			LinkedList stationsLigneUne = new LinkedList();
			for (int i = 0; i < tabS.length; i++) {
				stationsLigneUne.add(tabS[i]);
			}
			json.put("stations", stationsLigneUne);
			jsonTab[num] = json;
		}
		
		for (int i = 1; i < tabS.length; i++) {
			System.out.println(jsonTab[i].toJSONString());
		}
		
		return jsonTab;
	}
	
}
