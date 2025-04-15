import javax.swing.*;
import java.awt.*;
import java.io.IOException;


public class Main {

    static  Panel panel = new Panel();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                NimoFrame frame = new NimoFrame("UNO", new Color(200, 230, 255));

                // Create the start panel and add it to the frame
                NimoStartPanel startPanel = null;
                try {
                    startPanel = new NimoStartPanel();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                frame.getContentPane().add(startPanel, BorderLayout.CENTER);

                // Display the frame
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });
    }
    
}

/*import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
	    JFrame frame = new JFrame("Card Test");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    frame.setLayout(new FlowLayout());
	    
	    // Create cards using the factory method
	    Card redSkip = Card.create("red", "skip");
	    Card blue7 = Card.create("blue", "7");
	    Card greenReverse = Card.create("green", "reverse");
	    Card wildDrawFour = Card.create("wild", "wild draw four");
	    Card red9 = Card.create("red", "9");
	    Card blue4 = Card.create("blue", "4");
	    Card blue9 = Card.create("blue", "1");


	    // Create card views
	    CardView redSkipView = new CardView(redSkip);
	    CardView blue7View = new CardView(blue7);
	    CardView greenReverseView = new CardView(greenReverse);
	    CardView wildDrawFourView = new CardView(wildDrawFour);
	    CardView red9View = new CardView(red9);
	    CardView blue4View = new CardView(blue4);
	    CardView blue9View = new CardView(blue9);

	    
            
	    NimoDeckView drawPile = new NimoDeckView(NimoDeckView.DeckType.DRAW_PILE);

        // Add it to the center
        JPanel centerPanel = new JPanel(); // optional wrapper panel
        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, 0));
        centerPanel.add(drawPile);

        frame.add(centerPanel, BorderLayout.CENTER);
        NimoPlayerHand p = new NimoPlayerHand();
        Panel p1 = new Panel();
        p1.setLayout(null);
        p1.setPreferredSize(new Dimension(100,100));
       // NimoPlayerH
        NimoPlayerHand h = new NimoPlayerHand();
        h.addCard(redSkipView);
        
      //  p1.add(p)
        // Add to frame
   	    frame.add(redSkipView);
   	    frame.add(blue7View);
   	    frame.add(greenReverseView);
   	    frame.add(wildDrawFourView);
   	    frame.add(red9View);
   	    frame.add(blue4View);

   	    frame.add(p1);
   	    frame.add(h);
   	    
   	    frame.pack();
   	    frame.setVisible(true);
        
        
	}
	
}*/
        
        