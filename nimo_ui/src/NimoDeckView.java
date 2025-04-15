import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;
import java.io.File;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * NimoDeckView component for displaying draw and discard piles in the Uno game.
 * Shows visual representation of card piles with the ability to interact with them.
 * Enhanced with animations, better performance, and improved visual effects.
 */
public class NimoDeckView extends NimoComponent {
    
    public static final int DECK_WIDTH = 120;
    public static final int DECK_HEIGHT = 180;
    
    private static final String CARD_BACK_IMAGE_PATH = "uno_card_back.png";
    private static final int MAX_VISIBLE_CARDS = 5;
    private static final int CARD_STACK_OFFSET = 3;
    private static final int ANIMATION_DURATION = 300; // ms
    
    private BufferedImage cardBackImage;
    private int cardCount;
    private boolean isDrawPile;
    private DeckType deckType;
    private Random random = new Random();
    
    // Last few cards in discard pile (top is last)
    private Color[] discardPileTopColors;
    private String[] discardPileTopValues; // Store card values (numbers, skip, etc.)
    private int discardPileSize;
    
    // Animation properties
    private boolean isAnimating = false;
    private long animationStartTime;
    private Point animationStartPoint;
    private Point animationEndPoint;
    private Color animatingCardColor;
    private String animatingCardValue;
    private Timer animationTimer;
    
    // Card hover effect
    private boolean isHovering = false;
    
    // Listener for card draw/discard actions
    private DeckActionListener actionListener;
    
    // For double-buffering and performance
    private BufferedImage bufferImage;
    private boolean needsRedraw = true;
    
    /**
     * Type of deck this view represents
     */
    public enum DeckType {
        DRAW_PILE,
        DISCARD_PILE
    }
    
    /**
     * Interface for listening to deck actions
     */
    public interface DeckActionListener {
        void onDrawCard();
        void onDiscardPileClicked();
        void onAnimationComplete(boolean isDrawAnimation);
    }
    
    /**
     * Creates a new deck view with the specified type
     * 
     * @param deckType The type of deck (draw pile or discard pile)
     */
    public NimoDeckView(DeckType deckType) {
        super();
        this.deckType = deckType;
        this.isDrawPile = (deckType == DeckType.DRAW_PILE);
        this.cardCount = isDrawPile ? 108 : 0; // Standard Uno deck size for draw pile
        this.discardPileTopColors = new Color[MAX_VISIBLE_CARDS];
        this.discardPileTopValues = new String[MAX_VISIBLE_CARDS];
        this.discardPileSize = 0;
        
        // Set preferred size
        setPreferredSize(new Dimension(DECK_WIDTH, DECK_HEIGHT));
        
        // Load card back image
        loadCardBackImage();
        
        // Add mouse listeners for interaction
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (contains(e.getPoint()) && !isAnimating) {
                    handleDeckClick();
                }
            }
            
            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true;
                needsRedraw = true;
                repaint();
            }
            
            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false;
                needsRedraw = true;
                repaint();
            }
        });
        
        // Add component listener to handle resizing
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                bufferImage = null; // Force recreation of buffer
                needsRedraw = true;
            }
        });
        
        // Setup animation timer
        animationTimer = new Timer(16, e -> {
            if (isAnimating) {
                long elapsed = System.currentTimeMillis() - animationStartTime;
                if (elapsed >= ANIMATION_DURATION) {
                    isAnimating = false;
                    if (actionListener != null) {
                        actionListener.onAnimationComplete(isDrawPile);
                    }
                }
                needsRedraw = true;
                repaint();
            }
        });
        animationTimer.setRepeats(true);
        animationTimer.start();
    }
    
    /**
     * Loads the card back image
     */
   /* private void loadCardBackImage() {
        try {
            File file = new File(CARD_BACK_IMAGE_PATH);
            if (file.exists()) {
                cardBackImage = ImageIO.read(file);
            } else {
                System.err.println("Card back image not found: " + CARD_BACK_IMAGE_PATH);
                cardBackImage = createPlaceholderImage();
            }
        } catch (IOException e) {
            System.err.println("Error reading card back image: " + e.getMessage());
            cardBackImage = createPlaceholderImage();
        }
    }*/

    private void loadCardBackImage() {
        try {
            cardBackImage = ImageIO.read(getClass().getResourceAsStream("/uno_card_back.png"));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading card back image from classpath: " + e.getMessage());
            cardBackImage = createPlaceholderImage();
        }
    }

    
    /**
     * Creates a placeholder image when the card back image cannot be loaded
     */
    private BufferedImage createPlaceholderImage() {
        BufferedImage placeholder = new BufferedImage(DECK_WIDTH, DECK_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = placeholder.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setColor(Color.GRAY);
        g2.fillRoundRect(0, 0, DECK_WIDTH, DECK_HEIGHT, 20, 20);
        g2.setColor(Color.BLACK);
        g2.drawRoundRect(0, 0, DECK_WIDTH - 1, DECK_HEIGHT - 1, 20, 20);
        
        // Draw Uno logo placeholder
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 24));
        FontMetrics fm = g2.getFontMetrics();
        String text = "UNO";
        int textWidth = fm.stringWidth(text);
        g2.drawString(text, (DECK_WIDTH - textWidth) / 2, DECK_HEIGHT / 2);
        
        g2.dispose();
        return placeholder;
    }
    
    /**
     * Handles clicks on the deck
     */
    private void handleDeckClick() {
        if (actionListener != null) {
            if (isDrawPile) {
                if (cardCount > 0) {
                    actionListener.onDrawCard();
                }
            } else {
                actionListener.onDiscardPileClicked();
            }
        }
    }
    
    /**
     * Sets the action listener for deck interactions
     * 
     * @param listener The listener to be notified of deck actions
     */
    public void setDeckActionListener(DeckActionListener listener) {
        this.actionListener = listener;
    }
    
    /**
     * Updates the card count in the deck
     * 
     * @param count The new card count
     */
    public void setCardCount(int count) {
        if (this.cardCount != count) {
            this.cardCount = Math.max(0, count);
            needsRedraw = true;
            repaint();
        }
    }
    
    /**
     * Adds a card to the discard pile
     * 
     * @param cardColor The color of the card being discarded
     * @param cardValue The value of the card (number, skip, etc.)
     */
    public void addDiscardCard(Color cardColor, String cardValue) {
        if (!isDrawPile) {
            // Shift colors and values to make room for the new one
            for (int i = 0; i < discardPileTopColors.length - 1; i++) {
                discardPileTopColors[i] = discardPileTopColors[i + 1];
                discardPileTopValues[i] = discardPileTopValues[i + 1];
            }
            // Add new card at the top
            discardPileTopColors[discardPileTopColors.length - 1] = cardColor;
            discardPileTopValues[discardPileTopValues.length - 1] = cardValue;
            discardPileSize++;
            needsRedraw = true;
            repaint();
        }
    }
    
    /**
     * Adds a card to the discard pile with animation
     * 
     * @param cardColor The color of the card being discarded
     * @param cardValue The value of the card (number, skip, etc.)
     * @param startPoint The starting point for the animation
     */
    public void addDiscardCardWithAnimation(Color cardColor, String cardValue, Point startPoint) {
        if (!isDrawPile && !isAnimating) {
            animatingCardColor = cardColor;
            animatingCardValue = cardValue;
            animationStartPoint = startPoint;
            animationEndPoint = new Point(getWidth() / 2, getHeight() / 2);
            animationStartTime = System.currentTimeMillis();
            isAnimating = true;
            
            // The actual card will be added to the pile when animation completes
            Timer addCardTimer = new Timer(ANIMATION_DURATION, e -> {
                addDiscardCard(cardColor, cardValue);
                ((Timer)e.getSource()).stop();
            });
            addCardTimer.setRepeats(false);
            addCardTimer.start();
            
            needsRedraw = true;
            repaint();
        } else {
            // If already animating, just add the card without animation
            addDiscardCard(cardColor, cardValue);
        }
    }
    
    /**
     * Clears the discard pile
     */
    public void clearDiscardPile() {
        if (!isDrawPile) {
            discardPileSize = 0;
            for (int i = 0; i < discardPileTopColors.length; i++) {
                discardPileTopColors[i] = null;
                discardPileTopValues[i] = null;
            }
            needsRedraw = true;
            repaint();
        }
    }
    
    /**
     * Gets the current card count
     * 
     * @return The number of cards in the deck
     */
    public int getCardCount() {
        return cardCount;
    }
    
    /**
     * Simulates drawing a card from the pile visually with animation
     * 
     * @param targetPoint The end point for the animation
     * @return true if cards are available, false otherwise
     */
    public boolean drawCardWithAnimation(Point targetPoint) {
        if (isDrawPile && cardCount > 0 && !isAnimating) {
            cardCount--;
            
            animationStartPoint = new Point(getWidth() / 2, getHeight() / 2);
            animationEndPoint = targetPoint;
            animationStartTime = System.currentTimeMillis();
            isAnimating = true;
            
            needsRedraw = true;
            repaint();
            return true;
        }
        return false;
    }
    
    /**
     * Simulates drawing a card from the pile visually
     * 
     * @return true if cards are available, false otherwise
     */
    public boolean drawCard() {
        if (isDrawPile && cardCount > 0) {
            cardCount--;
            needsRedraw = true;
            repaint();
            return true;
        }
        return false;
    }
    
    /**
     * Returns the top card color from the discard pile
     * 
     * @return The color of the top card or null if pile is empty
     */
    public Color getTopCardColor() {
        if (!isDrawPile && discardPileSize > 0) {
            return discardPileTopColors[discardPileTopColors.length - 1];
        }
        return null;
    }
    
    /**
     * Returns the top card value from the discard pile
     * 
     * @return The value of the top card or null if pile is empty
     */
    public String getTopCardValue() {
        if (!isDrawPile && discardPileSize > 0) {
            return discardPileTopValues[discardPileTopValues.length - 1];
        }
        return null;
    }
    
    @Override
    protected void paintContent(Graphics2D g2) {
        // Use double buffering for smoother rendering
        if (bufferImage == null || bufferImage.getWidth() != getWidth() || 
            bufferImage.getHeight() != getHeight()) {
            bufferImage = new BufferedImage(Math.max(1, getWidth()), 
                                           Math.max(1, getHeight()), 
                                           BufferedImage.TYPE_INT_ARGB);
            needsRedraw = true;
        }
        
        if (needsRedraw) {
            Graphics2D bufferG2 = bufferImage.createGraphics();
            bufferG2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            bufferG2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
            bufferG2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            
            // Clear the buffer
            bufferG2.setComposite(AlphaComposite.Clear);
            bufferG2.fillRect(0, 0, getWidth(), getHeight());
            bufferG2.setComposite(AlphaComposite.SrcOver);
            
            if (isDrawPile) {
                paintDrawPile(bufferG2);
            } else {
                paintDiscardPile(bufferG2);
            }
            
            // Draw card count if needed
            if (isDrawPile && cardCount > 0) {
                String countText = String.valueOf(cardCount);
                bufferG2.setColor(new Color(0, 0, 0, 80));
                bufferG2.fillRoundRect(getWidth() - 42, getHeight() - 27, 37, 22, 10, 10);
                bufferG2.setColor(Color.WHITE);
                bufferG2.fillRoundRect(getWidth() - 40, getHeight() - 25, 35, 20, 10, 10);
                bufferG2.setColor(Color.BLACK);
                bufferG2.setFont(new Font("Arial", Font.BOLD, 14));
                FontMetrics fm = bufferG2.getFontMetrics();
                int textWidth = fm.stringWidth(countText);
                bufferG2.drawString(countText, getWidth() - 22 - textWidth/2, getHeight() - 10);
            }
            
            // Draw hover effect
            if (isHovering && !isAnimating) {
                bufferG2.setColor(new Color(255, 255, 255, 50));
                bufferG2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                
                // Draw a subtle glow
                bufferG2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
                for (int i = 0; i < 5; i++) {
                    bufferG2.setColor(new Color(255, 255, 200, 10 - i * 2));
                    bufferG2.setStroke(new BasicStroke(i * 2 + 1));
                    bufferG2.drawRoundRect(i, i, getWidth() - i * 2, getHeight() - i * 2, 15, 15);
                }
                bufferG2.setComposite(AlphaComposite.SrcOver);
            }
            
            // Draw animation if active
            if (isAnimating) {
                paintCardAnimation(bufferG2);
            }
            
            bufferG2.dispose();
            needsRedraw = false;
        }
        
        // Draw the buffer to the screen
        g2.drawImage(bufferImage, 0, 0, null);
    }
    
    /**
     * Paints the card animation
     */
    private void paintCardAnimation(Graphics2D g2) {
        long elapsed = System.currentTimeMillis() - animationStartTime;
        float progress = Math.min(1.0f, (float)elapsed / ANIMATION_DURATION);
        
        // Use easing function for smoother animation
        float easedProgress = easeOutQuad(progress);
        
        // Calculate current position
        int currentX = (int)(animationStartPoint.x + (animationEndPoint.x - animationStartPoint.x) * easedProgress);
        int currentY = (int)(animationStartPoint.y + (animationEndPoint.y - animationStartPoint.y) * easedProgress);
        
        // Calculate scale and rotation based on progress
        float scale = 1.0f - 0.2f * (1.0f - easedProgress);
        float rotation = (float)(Math.PI * 2 * easedProgress);
        
        // Save original transform
        AffineTransform originalTransform = g2.getTransform();
        
        // Apply transformations
        g2.translate(currentX, currentY);
        g2.rotate(rotation, DECK_WIDTH / 2 * scale, DECK_HEIGHT / 2 * scale);
        g2.scale(scale, scale);
        
        // Draw the animating card
        if (isDrawPile) {
            // Draw card back for draw pile
            g2.drawImage(cardBackImage, -DECK_WIDTH / 2, -DECK_HEIGHT / 2, DECK_WIDTH, DECK_HEIGHT, null);
        } else {
            // Draw colored card for discard pile
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(-DECK_WIDTH / 2 + 3, -DECK_HEIGHT / 2 + 3, DECK_WIDTH - 6, DECK_HEIGHT - 6, 10, 10);
            
            g2.setColor(animatingCardColor != null ? animatingCardColor : Color.GRAY);
            g2.fillRoundRect(-DECK_WIDTH / 2, -DECK_HEIGHT / 2, DECK_WIDTH - 6, DECK_HEIGHT - 6, 10, 10);
            
            g2.setColor(Color.WHITE);
            g2.setStroke(new BasicStroke(2));
            g2.drawRoundRect(-DECK_WIDTH / 2, -DECK_HEIGHT / 2, DECK_WIDTH - 6, DECK_HEIGHT - 6, 10, 10);
            
            // Draw card value if available
            if (animatingCardValue != null) {
                g2.setColor(Color.WHITE);
                g2.setFont(new Font("Arial", Font.BOLD, 24));
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(animatingCardValue);
                g2.drawString(animatingCardValue, -textWidth / 2, 10);
            }
        }
        
        // Restore original transform
        g2.setTransform(originalTransform);
    }
    
    /**
     * Easing function for smoother animations
     */
    private float easeOutQuad(float t) {
        return t * (2 - t);
    }
    
    /**
     * Paints the draw pile
     */
    private void paintDrawPile(Graphics2D g2) {
        if (cardCount <= 0) {
            // Draw empty pile outline
            g2.setColor(new Color(200, 200, 200, 100));
            g2.fillRoundRect(5, 5, DECK_WIDTH - 10, DECK_HEIGHT - 10, 15, 15);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0));
            g2.drawRoundRect(5, 5, DECK_WIDTH - 10, DECK_HEIGHT - 10, 15, 15);
            
            // Draw "Empty" text
            g2.setColor(new Color(100, 100, 100, 150));
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2.getFontMetrics();
            String emptyText = "EMPTY";
            int textWidth = fm.stringWidth(emptyText);
            g2.drawString(emptyText, (DECK_WIDTH - textWidth) / 2, DECK_HEIGHT / 2 + 5);
            return;
        }
        
        // Draw stacked cards effect
        int stackSize = Math.min(5, cardCount);
        for (int i = 0; i < stackSize; i++) {
            // Apply slight random rotation and offset for realism
            AffineTransform original = g2.getTransform();
            
            double angle = (random.nextDouble() - 0.5) * 0.05; // Small random angle
            int xOffset = random.nextInt(3) - 1;
            int yOffset = random.nextInt(3) - 1;
            
            g2.translate(xOffset + i, yOffset + i);
            g2.rotate(angle, DECK_WIDTH / 2, DECK_HEIGHT / 2);
            
            // Draw card with shadow
            g2.setColor(new Color(0, 0, 0, 50));
            g2.fillRoundRect(3, 3, DECK_WIDTH - 6, DECK_HEIGHT - 6, 15, 15);
            
            // Draw actual card
            g2.drawImage(cardBackImage, 0, 0, DECK_WIDTH, DECK_HEIGHT, null);
            
            g2.setTransform(original);
        }
    }
    
    /**
     * Paints the discard pile
     */
    private void paintDiscardPile(Graphics2D g2) {
        if (discardPileSize <= 0) {
            // Draw empty pile indicator
            g2.setColor(new Color(200, 200, 200, 100));
            g2.fillRoundRect(5, 5, DECK_WIDTH - 10, DECK_HEIGHT - 10, 15, 15);
            g2.setColor(Color.GRAY);
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 0, new float[]{5, 5}, 0));
            g2.drawRoundRect(5, 5, DECK_WIDTH - 10, DECK_HEIGHT - 10, 15, 15);
            
            // Draw "Empty" text
            g2.setColor(new Color(100, 100, 100, 150));
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            FontMetrics fm = g2.getFontMetrics();
            String emptyText = "EMPTY";
            int textWidth = fm.stringWidth(emptyText);
            g2.drawString(emptyText, (DECK_WIDTH - textWidth) / 2, DECK_HEIGHT / 2 + 5);
            return;
        }
        
        // Draw visible cards in discard pile with offset
        int visibleCards = Math.min(MAX_VISIBLE_CARDS, discardPileSize);
        for (int i = 0; i < visibleCards; i++) {
            int index = i - (visibleCards - Math.min(discardPileTopColors.length, discardPileSize));
            if (index >= 0) {
                Color cardColor = discardPileTopColors[index];
                String cardValue = discardPileTopValues[index];
                if (cardColor != null) {
                    // Calculate offset position
                    int xOffset = (visibleCards - i - 1) * CARD_STACK_OFFSET;
                    int yOffset = (visibleCards - i - 1) * CARD_STACK_OFFSET;
                    
                    // Apply slight random rotation for realism
                    AffineTransform original = g2.getTransform();
                    double angle = (random.nextDouble() - 0.5) * 0.1;
                    g2.rotate(angle, DECK_WIDTH / 2 + xOffset, DECK_HEIGHT / 2 + yOffset);
                    
                    // Draw card with shadow
                    g2.setColor(new Color(0, 0, 0, 50));
                    g2.fillRoundRect(xOffset + 3, yOffset + 3, DECK_WIDTH - 6, DECK_HEIGHT - 6, 15, 15);
                    
                    // Draw card with color
                    g2.setColor(cardColor);
                    g2.fillRoundRect(xOffset, yOffset, DECK_WIDTH - 6, DECK_HEIGHT - 6, 15, 15);
                    
                    // Draw card outline
                    g2.setColor(Color.WHITE);
                    g2.setStroke(new BasicStroke(2));
                    g2.drawRoundRect(xOffset, yOffset, DECK_WIDTH - 6, DECK_HEIGHT - 6, 15, 15);
                    
                    // Draw card value if available
                    if (cardValue != null) {
                        g2.setColor(Color.WHITE);
                        g2.setFont(new Font("Arial", Font.BOLD, 24));
                        FontMetrics fm = g2.getFontMetrics();
                        int textWidth = fm.stringWidth(cardValue);
                        g2.drawString(cardValue, xOffset + (DECK_WIDTH - 6 - textWidth) / 2, 
                                     yOffset + DECK_HEIGHT / 2 + 8);
                    }
                    
                    g2.setTransform(original);
                } else {
                    // If no color information, draw a generic card
                    drawGenericCard(g2, i, visibleCards);
                }
            } else {
                // For cards that don't have color information yet
                drawGenericCard(g2, i, visibleCards);
            }
        }
    }
    
    /**
     * Draws a generic card for the discard pile when no color information is available
     */
    private void drawGenericCard(Graphics2D g2, int cardIndex, int totalVisibleCards) {
        int xOffset = (totalVisibleCards - cardIndex - 1) * CARD_STACK_OFFSET;
        int yOffset = (totalVisibleCards - cardIndex - 1) * CARD_STACK_OFFSET;
        
        AffineTransform original = g2.getTransform();
        double angle = (random.nextDouble() - 0.5) * 0.1;
        g2.rotate(angle, DECK_WIDTH / 2 + xOffset, DECK_HEIGHT / 2 + yOffset);
        
        // Draw card shadow
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(xOffset + 3, yOffset + 3, DECK_WIDTH - 6, DECK_HEIGHT - 6, 15, 15);
        
        // Draw card face
        g2.setColor(Color.LIGHT_GRAY);
        g2.fillRoundRect(xOffset, yOffset, DECK_WIDTH - 6, DECK_HEIGHT - 6, 15, 15);
        g2.setColor(Color.DARK_GRAY);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(xOffset, yOffset, DECK_WIDTH - 6, DECK_HEIGHT - 6, 15, 15);
        
        g2.setTransform(original);
    }
    
    /**
     * Shuffles the discard pile back into the draw pile with animation
     * 
     * @param newDrawCount The new count for the draw pile after shuffling
     */
    public void shuffleDiscardToDraw(int newDrawCount) {
        if (!isDrawPile && discardPileSize > 0) {
            // Create a shuffling animation
            Timer shuffleTimer = new Timer(50, null);
            final int[] counter = {0};
            final int totalFrames = 10;
            
            shuffleTimer.addActionListener(e -> {
                counter[0]++;
                needsRedraw = true;
                repaint();
                
                if (counter[0] >= totalFrames) {
                    shuffleTimer.stop();
                    clearDiscardPile();
                    if (actionListener != null) {
                        actionListener.onAnimationComplete(false);
                    }
                }
            });
            
            shuffleTimer.start();
        }
    }
    
    /**
     * Cleans up resources when this component is no longer needed
     */
    public void dispose() {
        if (animationTimer != null) {
            animationTimer.stop();
        }
        bufferImage = null;
    }
    
    /**
     * Checks if the deck is currently animating
     * 
     * @return true if an animation is in progress
     */
    public boolean isAnimating() {
        return isAnimating;
    }
}