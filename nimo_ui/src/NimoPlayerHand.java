import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NimoPlayerHand extends JPanel {
    
    private List<CardView> playerCards; // List of CardView objects
    private static final int CARD_SPACING = 10; // Space between each card in the hand
    
 
    public NimoPlayerHand() {
        this.playerCards = new ArrayList<>();
        setLayout(new FlowLayout(FlowLayout.LEFT, CARD_SPACING, 0)); // Cards are displayed from left to right
        
        setPreferredSize(new Dimension(800, 150)); // Adjust size as needed
    }
    
 
    public void addCard(CardView cardView) {
        playerCards.add(cardView);
        add(cardView); // Add the visual CardView component to the panel
        revalidate(); // Re-layout the panel to reflect the change
        repaint();    // Repaint the panel to update the display
    }
    
    public void removeCard(CardView cardView) {
        playerCards.remove(cardView);
        remove(cardView); // Remove the CardView component from the panel
        revalidate();     // Re-layout the panel to reflect the change
        repaint();        
    }

    public void clearHand() {
        playerCards.clear();
        removeAll(); // Remove all components from the panel
        revalidate();
        repaint();
    }
    
    public List<CardView> getPlayerCards() {
        return playerCards;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Custom painting logic can go here, but the default layout handles card placement.
    }
    
    public boolean containsCard(CardView card) {
        return playerCards.contains(card);
    }
}
