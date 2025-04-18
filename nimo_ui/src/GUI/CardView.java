package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Card {
    String color;
    String type;
    String imagePath;
    
    // Image path constants for all card types
    // Green cards
    public static final String GREEN_0 = "green-0-card-clipart-sm.png";
    public static final String GREEN_1 = "green-1-card-clipart-sm.png";
    public static final String GREEN_2 = "green-2-card-clipart-sm.png";
    public static final String GREEN_3 = "green-3-card-clipart-sm.png";
    public static final String GREEN_4 = "green-4-card-clipart-sm.png";
    public static final String GREEN_5 = "green-5-card-clipart-sm.png";
    public static final String GREEN_6 = "green-6-card-clipart-sm.png";
    public static final String GREEN_7 = "green-7-card-clipart-sm.png";
    public static final String GREEN_8 = "green-8-card-clipart-sm.png";
    public static final String GREEN_9 = "green-9-card-clipart-sm.png";
    public static final String GREEN_SKIP = "green-skip-card-clipart-sm.png";
    public static final String GREEN_REVERSE = "green-reverse-card-clipart-sm.png";
    public static final String GREEN_DRAW_TWO = "green-draw-two-card-clipart-sm.png";
    
    // Yellow cards
    public static final String YELLOW_0 = "yellow-0-card-clipart-sm.png";
    public static final String YELLOW_1 = "yellow-1-card-clipart-sm.png";
    public static final String YELLOW_2 = "yellow-2-card-clipart-sm.png";
    public static final String YELLOW_3 = "yellow-3-card-clipart-sm.png";
    public static final String YELLOW_4 = "yellow-4-card-clipart-sm.png";
    public static final String YELLOW_5 = "yellow-5-card-clipart-sm.png";
    public static final String YELLOW_6 = "yellow-6-card-clipart-sm.png";
    public static final String YELLOW_7 = "yellow-7-card-clipart-sm.png";
    public static final String YELLOW_8 = "yellow-8-card-clipart-sm.png";
    public static final String YELLOW_9 = "yellow-9-card-clipart-sm.png";
    public static final String YELLOW_SKIP = "yellow-skip-card-clipart-sm.png";
    public static final String YELLOW_REVERSE = "yellow-reverse-card-clipart-sm.png";
    public static final String YELLOW_DRAW_TWO = "yellow-draw-two-card-clipart-sm.png";
    
    // Blue cards
    public static final String BLUE_0 = "blue-0-card-clipart-sm.png";
    public static final String BLUE_1 = "blue-1-card-clipart-sm.png";
    public static final String BLUE_2 = "blue-2-card-clipart-sm.png";
    public static final String BLUE_3 = "blue-3-card-clipart-sm.png";
    public static final String BLUE_4 = "blue-4-card-clipart-sm.png";
    public static final String BLUE_5 = "blue-5-card-clipart-sm.png";
    public static final String BLUE_6 = "blue-6-card-clipart-sm.png";
    public static final String BLUE_7 = "blue-7-card-clipart-sm.png";
    public static final String BLUE_8 = "blue-8-card-clipart-sm.png";
    public static final String BLUE_9 = "blue-9-card-clipart-sm.png";
    public static final String BLUE_SKIP = "blue-skip-card-clipart-sm.png";
    public static final String BLUE_REVERSE = "blue-reverse-card-clipart-sm.png";
    public static final String BLUE_DRAW_TWO = "blue-draw-two-card-clipart-sm.png";
    
    // Red cards
    public static final String RED_0 = "red-0-card-clipart-sm.png";
    public static final String RED_1 = "red-1-card-clipart-sm.png";
    public static final String RED_2 = "red-2-card-clipart-sm.png";
    public static final String RED_3 = "red-3-card-clipart-sm.png";
    public static final String RED_4 = "red-4-card-clipart-sm.png";
    public static final String RED_5 = "red-5-card-clipart-sm.png";
    public static final String RED_6 = "red-6-card-clipart-sm.png";
    public static final String RED_7 = "red-7-card-clipart-sm.png";
    public static final String RED_8 = "red-8-card-clipart-sm.png";
    public static final String RED_9 = "red-9-card-clipart-sm.png";
    public static final String RED_SKIP = "red-skip-card-clipart-sm.png";
    public static final String RED_REVERSE = "red-reverse-card-clipart-sm.png";
    public static final String RED_DRAW_TWO = "red-draw-two-card-clipart-sm.png";
    
    // Wild cards
    public static final String WILD = "wild-card-clipart-sm.png";
    public static final String WILD_DRAW_FOUR = "wild-draw-four-card-clipart-sm.png";
    
    // Static map to easily get card image path by color and type
    private static final Map<String, Map<String, String>> CARD_IMAGES = initCardImageMap();
    
    private static Map<String, Map<String, String>> initCardImageMap() {
        Map<String, Map<String, String>> imageMap = new HashMap<>();
        
        // Red cards
        Map<String, String> redCards = new HashMap<>();
        redCards.put("0", RED_0);
        redCards.put("1", RED_1);
        redCards.put("2", RED_2);
        redCards.put("3", RED_3);
        redCards.put("4", RED_4);
        redCards.put("5", RED_5);
        redCards.put("6", RED_6);
        redCards.put("7", RED_7);
        redCards.put("8", RED_8);
        redCards.put("9", RED_9);
        redCards.put("skip", RED_SKIP);
        redCards.put("reverse", RED_REVERSE);
        redCards.put("draw two", RED_DRAW_TWO);
        imageMap.put("red", redCards);
        
        // Blue cards
        Map<String, String> blueCards = new HashMap<>();
        blueCards.put("0", BLUE_0);
        blueCards.put("1", BLUE_1);
        blueCards.put("2", BLUE_2);
        blueCards.put("3", BLUE_3);
        blueCards.put("4", BLUE_4);
        blueCards.put("5", BLUE_5);
        blueCards.put("6", BLUE_6);
        blueCards.put("7", BLUE_7);
        blueCards.put("8", BLUE_8);
        blueCards.put("9", BLUE_9);
        blueCards.put("skip", BLUE_SKIP);
        blueCards.put("reverse", BLUE_REVERSE);
        blueCards.put("draw two", BLUE_DRAW_TWO);
        imageMap.put("blue", blueCards);
        
        // Green cards
        Map<String, String> greenCards = new HashMap<>();
        greenCards.put("0", GREEN_0);
        greenCards.put("1", GREEN_1);
        greenCards.put("2", GREEN_2);
        greenCards.put("3", GREEN_3);
        greenCards.put("4", GREEN_4);
        greenCards.put("5", GREEN_5);
        greenCards.put("6", GREEN_6);
        greenCards.put("7", GREEN_7);
        greenCards.put("8", GREEN_8);
        greenCards.put("9", GREEN_9);
        greenCards.put("skip", GREEN_SKIP);
        greenCards.put("reverse", GREEN_REVERSE);
        greenCards.put("draw two", GREEN_DRAW_TWO);
        imageMap.put("green", greenCards);
        
        // Yellow cards
        Map<String, String> yellowCards = new HashMap<>();
        yellowCards.put("0", YELLOW_0);
        yellowCards.put("1", YELLOW_1);
        yellowCards.put("2", YELLOW_2);
        yellowCards.put("3", YELLOW_3);
        yellowCards.put("4", YELLOW_4);
        yellowCards.put("5", YELLOW_5);
        yellowCards.put("6", YELLOW_6);
        yellowCards.put("7", YELLOW_7);
        yellowCards.put("8", YELLOW_8);
        yellowCards.put("9", YELLOW_9);
        yellowCards.put("skip", YELLOW_SKIP);
        yellowCards.put("reverse", YELLOW_REVERSE);
        yellowCards.put("draw two", YELLOW_DRAW_TWO);
        imageMap.put("yellow", yellowCards);
        
        // Wild cards
        Map<String, String> wildCards = new HashMap<>();
        wildCards.put("wild", WILD);
        wildCards.put("wild draw four", WILD_DRAW_FOUR);
        imageMap.put("wild", wildCards);
        
        return imageMap;
    }
    
    public Card(String color, String type, String imagePath) {
        this.color = color;
        this.type = type;
        this.imagePath = imagePath;
    }
    

    public static Card create(String color, String type) {
        String imagePath = getImagePath(color, type);
        return new Card(color, type, imagePath);
    }
    

    public static String getImagePath(String color, String type) {
        if (CARD_IMAGES.containsKey(color.toLowerCase()) && 
            CARD_IMAGES.get(color.toLowerCase()).containsKey(type.toLowerCase())) {
            return CARD_IMAGES.get(color.toLowerCase()).get(type.toLowerCase());
        }
        
        // Return wild card as default if no match found
        return WILD;
    }
    
    @Override
    public String toString() {
        return color + " " + type;
    }
}

// CardView now extends JComponent directly
public class CardView extends JComponent {
    private Card card;
    private boolean selected = false;
    private boolean playable = false;
    private ImageIcon cardImage;
    private MouseAdapter clickListener;

    // Cache for loaded images
    private static final Map<String, ImageIcon> imageCache = new HashMap<>();

    public CardView(Card card) {
        this.card = card;
        setPreferredSize(new Dimension(70, 90));
        setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));


        // Load image
        loadCardImage();

        // Add mouse listeners
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (clickListener != null) {
                    clickListener.mouseClicked(e);
                }
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if (clickListener != null) {
                    clickListener.mouseEntered(e);
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                if (clickListener != null) {
                    clickListener.mouseExited(e);
                }
            }
        });
    }

    private void loadCardImage() {
        try {
            // Check if image is already in cache
            if (imageCache.containsKey(card.imagePath)) {
                cardImage = imageCache.get(card.imagePath);
                return;
            }

            // Load image from resources
            cardImage = new ImageIcon(getClass().getResource("/" + card.imagePath));
            if (cardImage.getIconWidth() == -1) {
                System.err.println("Image not found: " + card.imagePath);
                cardImage = null; // Image not found
            } else {
                // Resize image to fit card
                Image img = cardImage.getImage().getScaledInstance(70, 90, Image.SCALE_SMOOTH);
                cardImage = new ImageIcon(img);

                // Add to cache
                imageCache.put(card.imagePath, cardImage);
            }
        } catch (Exception e) {
            System.err.println("Error loading image: " + card.imagePath + " - " + e.getMessage());
            cardImage = null; // Error loading image
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;


        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Draw card background
        if (cardImage != null) {
            cardImage.paintIcon(this, g, 5, 5);
        } else {
            // Draw placeholder if no image
            g2d.setColor(getColorForCard());
            g2d.fillRoundRect(5, 5, getWidth() - 10, getHeight() - 10, 10, 10);

            // Draw card text
            g2d.setColor(Color.WHITE);
            g2d.setFont(new Font("Arial", Font.BOLD, 18));

            FontMetrics fm = g2d.getFontMetrics();
            String text = card.type.toUpperCase();
            int textWidth = fm.stringWidth(text);
            int textHeight = fm.getHeight();

            g2d.drawString(text, (getWidth() - textWidth) / 2, (getHeight() - textHeight) / 2 + fm.getAscent());

            // Draw color text
            g2d.setFont(new Font("Arial", Font.PLAIN, 12));
            fm = g2d.getFontMetrics();
            text = card.color.toUpperCase();
            textWidth = fm.stringWidth(text);

            g2d.drawString(text, (getWidth() - textWidth) / 2, (getHeight() / 2) + 20);
        }

        // Draw border based on state
        if (selected) {
            g2d.setColor(Color.RED);
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);
        } else if (playable) {
            g2d.setColor(new Color(100, 200, 255));
            g2d.setStroke(new BasicStroke(3));
            g2d.drawRoundRect(2, 2, getWidth() - 4, getHeight() - 4, 10, 10);
        }
    }

    private Color getColorForCard() {
        switch (card.color.toLowerCase()) {
            case "red": return new Color(220, 0, 0);
            case "blue": return new Color(0, 0, 220);
            case "green": return new Color(0, 180, 0);
            case "yellow": return new Color(220, 220, 0);
            case "wild": return new Color(0, 0, 0);
            default: return Color.GRAY;
        }
    }

    public void setOnClick(MouseAdapter listener) {
        this.clickListener = listener;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
        repaint();
    }

    public boolean isSelected() {
        return selected;
    }

    public void setPlayable(boolean playable) {
        this.playable = playable;
        repaint();
    }

    public Card getCard() {
        return card;
    }

}