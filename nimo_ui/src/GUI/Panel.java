package GUI;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

class NimoStartPanel extends JPanel {
    JPanel centerPanel = new JPanel();
    public NimoStartPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));


        // Create NimoButtons
        NimoButton startButton = new NimoButton("Start on that   particular time ",
                Color.PINK, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
            }
        });


        // Add the buttons to the panel
        add(startButton);

    }
    public void showCurrentCard(Card currentCard) {

        centerPanel.removeAll();  // Clear the previous card
        CardView cardView = new CardView(currentCard);  // Create new CardView
        centerPanel.add(cardView);  // Add it to the center panel
        centerPanel.revalidate();  // Revalidate layout
        centerPanel.repaint();     // Repaint to reflect the changes
    }
}

class NimoGamePanel extends JPanel {
    private BufferedImage backgroundImage;
    private JPanel centerPanel;
    private NimoPlayerHand playerHand;
    private NimoDeckView drawDeck;
    private CardView currentTopCard;


    public NimoGamePanel() {
        // Set up panel properties
        setLayout(new BorderLayout(20, 20));
        setBackground(new Color(73, 121, 56)); // Using consistent color from NimoColors


        // Initialize game components
        initializeComponents();

        // Add mouse listener for game interactions
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                // Handle game board clicks
                // Could be used for game state transitions or general interactions
            }
        });
    }

    private void initializeComponents() {
        // Create center panel for the current card
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setOpaque(false);
        centerPanel.setPreferredSize(new Dimension(150, 200));

        // Create draw deck
        drawDeck = new NimoDeckView();
        drawDeck.setDrawCardListener(() -> {
            // Handle draw card action
            System.out.println("Card drawn!");
            // Call your game logic here
        });

        // Create player hand panel
        playerHand = new NimoPlayerHand();

        // Create a panel for the top row (draw pile and discard pile)
        JPanel topRowPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        topRowPanel.setOpaque(false);
        topRowPanel.add(drawDeck);
        topRowPanel.add(centerPanel);

        // Add components to the main panel
        add(topRowPanel, BorderLayout.CENTER);
        add(playerHand, BorderLayout.SOUTH);

        // Add control buttons (optional)
        JPanel controlPanel = createControlPanel();
        add(controlPanel, BorderLayout.NORTH);
    }

    private JPanel createControlPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        panel.setOpaque(false);

        // Create buttons
        NimoButton newGameButton = new NimoButton("New Game", new Color(30, 144, 255), 10,
                e -> startNewGame());


        // Add buttons to panel
        panel.add(newGameButton);


        return panel;
    }

    private void startNewGame() {
        // Reset game state
        playerHand.clearHand();

        // Deal initial cards (placeholder)
        dealInitialCards();

        // Set initial top card
        Card initialCard = Card.create("red", "5");
        showCurrentCard(initialCard);
    }

    private void dealInitialCards() {
        // Example: Deal 7 cards to player
        String[] colors = {"red", "blue", "green", "yellow"};
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "skip", "reverse", "draw two"};

        for (int i = 0; i < 7; i++) {
            String randomColor = colors[(int)(Math.random() * colors.length)];
            String randomValue = values[(int)(Math.random() * values.length)];

            Card card = Card.create(randomColor, randomValue);
            CardView cardView = new CardView(card);

            // Add click handler
            cardView.setOnClick(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Handle card selection
                    System.out.println("Card selected: " + card);
                    // Add your card playing logic here
                }
            });

            playerHand.addCard(cardView);
        }
    }


    public void showCurrentCard(Card currentCard) {
        // Remove previous card if any
        centerPanel.removeAll();

        // Create a new CardView for the current card
        currentTopCard = new CardView(currentCard);

        // Add the card to the center panel
        centerPanel.add(currentTopCard);

        // Refresh the UI
        centerPanel.revalidate();
        centerPanel.repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Apply rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // Draw background image if available
        if (backgroundImage != null) {
            g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public NimoPlayerHand getPlayerHand() {
        return playerHand;
    }

    public Card getCurrentTopCard() {
        return currentTopCard != null ? currentTopCard.getCard() : null;
    }
}