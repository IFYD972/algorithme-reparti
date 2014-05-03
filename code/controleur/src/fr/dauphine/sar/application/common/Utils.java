package fr.dauphine.sar.application.common;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;

import javax.swing.JButton;
import javax.swing.JComboBox;

public class Utils {
	
    public static void removeAllListeners(JButton bouton) {
        for( ActionListener al : bouton.getActionListeners() ) {
            bouton.removeActionListener( al );
        }
    }
    public static void removeAllListeners(JComboBox box) {
        for( ItemListener al : box.getItemListeners() ) {
        	box.removeItemListener(al);
        }
    }
}