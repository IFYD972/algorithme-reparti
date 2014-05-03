package fr.dauphine.sar.application.vue.composant;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import fr.dauphine.sar.application.controleur.StationCtrl;

public class ComposantCentral extends JPanel {

	public static JLabel busIcon;
	Image bg = new ImageIcon("res/map.jpg").getImage();
	
	public ComposantCentral() {
		this.setLayout(null);
		JButton test = new JButton("COUCOU");
		busIcon = new JLabel(new ImageIcon("res/icon_autobus.gif"));
		this.add(busIcon);
		busIcon.setSize(100,100);
		
		busIcon.setLocation(100,100);
		
		this.add(test);
		//this.setPreferredSize(new Dimension(1000,1000));
		test.setLocation(200, 200);
		test.setSize(15,15);
		test.addActionListener(new positionSettings());
		
		this.setSize(1000, 1000);
		System.out.println(this.getHeight());
		System.out.println(this.getWidth());
		this.addMouseListener(new StationCtrl(test));
	}
	
    @Override
    public void paintComponent(Graphics g) {
        g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        
		ArrayList<int[]> posi = new ArrayList<int[]>();
		posi.add(new int[]{84,177});
		posi.add(new int[]{83,148});
		posi.add(new int[]{84,122});
		posi.add(new int[]{122,119});
		posi.add(new int[]{160,118});
		posi.add(new int[]{195,148});
		posi.add(new int[]{226,173});
		posi.add(new int[]{257,195});
		posi.add(new int[]{294,225});
		posi.add(new int[]{331,222});
		posi.add(new int[]{378,222});
		posi.add(new int[]{428,222});
		posi.add(new int[]{485,225});
		/*
		try { Thread.sleep(1000); }
		catch (InterruptedException e) { e.printStackTrace(); }
		
		Iterator it = posi.iterator();
		
		while(it.hasNext()) {
			System.out.println("COUCOU");
			int[] coord = (int[]) it.next();
			busIcon.setLocation(coord[0],coord[1]);

			try { Thread.sleep(1000); }
			catch (InterruptedException e) { e.printStackTrace(); }
		}*/
    }
    
    private class positionSettings implements ActionListener {
    	
    	@Override
    	public void actionPerformed(ActionEvent e) {
    		Iterator it = new StationCtrl((JButton) e.getSource()).positions.iterator();
			
			while(it.hasNext()) {
				int[] coord = (int[]) it.next();
				System.out.print(coord[0]+":"+coord[1]+",");
			}
			System.out.print("\r\n");
			new StationCtrl((JButton) e.getSource()).positions = new ArrayList<int[]>();
    	}
    }

}
