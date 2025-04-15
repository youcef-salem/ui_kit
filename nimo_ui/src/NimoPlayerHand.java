import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * NimoPlayerHand represents a player's hand in the graphical UNO game.
 * It holds a collection of CardView objects, which visually represent each card.
 */
public class NimoPlayerHand extends JPanel {
    
    private List<CardView> playerCards; // List of CardView objects
    private static final int CARD_SPACING = 10; // Space between each card in the hand
    
    /**
     * Constructor for NimoPlayerHand. Initializes the list of player cards.
     */
    public NimoPlayerHand() {
        this.playerCards = new ArrayList<>();
        setLayout(new FlowLayout(FlowLayout.LEFT, CARD_SPACING, 0)); // Cards are displayed from left to right
        
        setPreferredSize(new Dimension(800, 150)); // Adjust size as needed
    }
    
    /**
     * Adds a card to the player's hand.
     *
     * @param cardView The CardView to be added
     */
    public void addCard(CardView cardView) {
        playerCards.add(cardView);
        add(cardView); // Add the visual CardView component to the panel
        revalidate(); // Re-layout the panel to reflect the change
        repaint();    // Repaint the panel to update the display
    }
    
    /**
     * Removes a card from the player's hand.
     *
     * @param cardView The CardView to be removed
     */
    public void removeCard(CardView cardView) {
        playerCards.remove(cardView);
        remove(cardView); // Remove the CardView component from the panel
        revalidate();     // Re-layout the panel to reflect the change
        repaint();        // Repaint the panel to update the display
    }
    
    /**
     * Clears the entire player's hand.
     */
    public void clearHand() {
        playerCards.clear();
        removeAll(); // Remove all components from the panel
        revalidate();
        repaint();
    }
    
    /**
     * Gets the list of all cards in the player's hand.
     * 
     * @return A list of CardView objects
     */
    public List<CardView> getPlayerCards() {
        return playerCards;
    }
    
    /**
     * Paints the player's hand (optional, but could be used for custom rendering).
     * 
     * @param g The graphics context
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Custom painting logic can go here, but the default layout handles card placement.
    }
    
    /**
     * Determines if a card is in the player's hand.
     *
     * @param card The CardView to check
     * @return true if the card is in the hand, false otherwise
     */
    public boolean containsCard(CardView card) {
        return playerCards.contains(card);
    }
}
