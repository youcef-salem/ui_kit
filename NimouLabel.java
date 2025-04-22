package om;
import java.awt.Color;
import java.awt.Font;
import javax.swing.*;

public class NimouLabel extends JLabel {
public  NimouLabel (String a) {
	super(a);
	 Font unoFont = new Font("Segoe UI", Font.BOLD, 13);		 
	 setFont(unoFont); 
	 setForeground(Color.white);
}
}
