package om;

import javax.swing.*;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
public class NimoTextField extends JTextField {
	
	 public NimoTextField () {
		 
		 Font unoFont = new Font("Segoe UI", Font.BOLD, 18);		 
		 setFont(unoFont);  
		 setBackground(Color.black);  
		 setCaretColor(Color.white);
		
		 setForeground(Color.white); 
		 setPreferredSize(new Dimension(90, 40)); 
		 requestFocusInWindow();  

		
		 setBorder(BorderFactory.createCompoundBorder(
				    BorderFactory.createLineBorder(Color.RED, 2),  
				    BorderFactory.createEmptyBorder(5, 10, 5, 10)  
				));
		 addKeyListener(new KeyAdapter() {
	            @Override
	            public void keyTyped(KeyEvent e) {
	                // Empêche la saisie de tout caractère qui n'est pas un chiffre
	                char c = e.getKeyChar();
	                if (!Character.isDigit(c)) {
	                    e.consume();  // Ignorer les entrées non numériques
	                }

	                // Empêche l'utilisateur d'entrer un nombre supérieur ou égal à 4
	                String text = getText() + c;
	                try {
	                    int value = Integer.parseInt(text);
	                    if (value >4 ||value <1) {
	                        e.consume();  // 
	                    }
	                } catch (NumberFormatException ex) {
	                    // Si c'est un autre caractère non numérique, on ignore
	                }
	            }
	        });
		 SwingUtilities.invokeLater(() -> requestFocusInWindow());  
	 
}
}

   