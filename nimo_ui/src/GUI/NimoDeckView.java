package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.IOException;

public class NimoDeckView extends JComponent {

    private static final int CARD_WIDTH = 120;
    private static final int CARD_HEIGHT = 180;
    private static final String CARD_BACK_IMAGE_PATH = "uno_card_back.png";

    private BufferedImage cardBackImage;
    private boolean isHovering = false;
    private boolean isPressed = false;
    private DrawCardListener drawCardListener;

    public interface DrawCardListener {
        void onDrawCard();
    }

    public NimoDeckView() {
        setPreferredSize(new Dimension(CARD_WIDTH, CARD_HEIGHT));
        loadCardBackImage();
        setupListeners();
    }

    private void loadCardBackImage() {
        try {
            cardBackImage = ImageIO.read(getClass().getResourceAsStream("/uno_card_back.png"));
        } catch (IOException | NullPointerException e) {
            System.err.println("Error loading card back image: " + e.getMessage());
            cardBackImage = createPlaceholderImage();
        }
    }

    private BufferedImage createPlaceholderImage() {
        BufferedImage placeholder = new BufferedImage(CARD_WIDTH, CARD_HEIGHT, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = placeholder.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill background with gradient
        GradientPaint gradient = new GradientPaint(
                0, 0, new Color(30, 80, 150),
                CARD_WIDTH, CARD_HEIGHT, new Color(15, 40, 80)
        );
        g2.setPaint(gradient);
        g2.fillRoundRect(0, 0, CARD_WIDTH, CARD_HEIGHT, 20, 20);

        // Draw border
        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, CARD_WIDTH - 3, CARD_HEIGHT - 3, 20, 20);

        // Draw UNO text
        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 32));
        FontMetrics fm = g2.getFontMetrics();
        String text = "UNO";
        int textWidth = fm.stringWidth(text);
        g2.drawString(text, (CARD_WIDTH - textWidth) / 2, CARD_HEIGHT / 2 + 10);

        g2.dispose();
        return placeholder;
    }

    private void setupListeners() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (drawCardListener != null) {
                    drawCardListener.onDrawCard();
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
                isPressed = true;
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isPressed = false;
                repaint();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                isHovering = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                isHovering = false;
                isPressed = false;
                repaint();
            }
        });
    }

    public void setDrawCardListener(DrawCardListener listener) {
        this.drawCardListener = listener;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        int width = getWidth();
        int height = getHeight();

        // Apply visual effects based on button state
        if (isPressed) {
            // Draw pressed state (slightly darker and moved down)
            g2.translate(2, 2);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
        } else if (isHovering) {
            // Draw hover glow
            drawGlowEffect(g2);
        }

        // Draw card shadow
        g2.setColor(new Color(0, 0, 0, 50));
        g2.fillRoundRect(3, 3, width - 6, height - 6, 15, 15);

        // Draw the card
        if (cardBackImage != null) {
            g2.drawImage(cardBackImage, 0, 0, width, height, null);
        }

        // Draw "Draw" label at the bottom
        if (isHovering) {
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRoundRect(width/4, height - 35, width/2, 25, 10, 10);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 14));
            FontMetrics fm = g2.getFontMetrics();
            String text = "DRAW";
            int textWidth = fm.stringWidth(text);
            g2.drawString(text, (width - textWidth) / 2, height - 16);
        }

        g2.dispose();
    }

    private void drawGlowEffect(Graphics2D g2) {
        int width = getWidth();
        int height = getHeight();

        // Create a glow effect around the card
        Composite originalComposite = g2.getComposite();
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));

        // Draw outer glow
        for (int i = 0; i < 4; i++) {
            g2.setColor(new Color(255, 255, 150, 40 - i * 10));
            g2.setStroke(new BasicStroke(i * 2 + 1));
            g2.drawRoundRect(i, i, width - i * 2, height - i * 2, 15, 15);
        }

        g2.setComposite(originalComposite);
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(CARD_WIDTH, CARD_HEIGHT);
    }

    @Override
    public Dimension getMinimumSize() {
        return new Dimension(CARD_WIDTH / 2, CARD_HEIGHT / 2);
    }
}