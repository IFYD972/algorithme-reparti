package fr.dauphine.sar.application.controleur;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JPanel;

public class StationCtrl implements MouseListener {
	JButton bouton;
	
	public StationCtrl(JButton btn) {
		bouton = btn;
	}
	private static int counter=0;
	public static ArrayList<int[]> positions = new ArrayList<int[]>();
	
	@Override
	public void mouseClicked(MouseEvent e) {
		JPanel pane = (JPanel) e.getSource();
		
		bouton.setLocation(pane.getMousePosition().x-(bouton.getWidth()/2), pane.getMousePosition().y-(bouton.getHeight()/2));
		System.out.println(pane.getMousePosition().x+":"+pane.getMousePosition().y);
		positions.add(new int[]{pane.getMousePosition().x, pane.getMousePosition().y});

	}
	

	@Override
	public void mousePressed(MouseEvent e) { }

	@Override
	public void mouseReleased(MouseEvent e) { }

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

}
