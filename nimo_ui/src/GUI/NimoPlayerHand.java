package GUI;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NimoPlayerHand extends JPanel {
    private final List<CardView> playerCards;
    private static final int CARD_SPACING = 2;
    private final JPanel cardsPanel;

    public NimoPlayerHand() {
        this.playerCards = new ArrayList<>();
        setLayout(new BorderLayout());

        cardsPanel = new JPanel(); // Holds the actual cards
        cardsPanel.setOpaque(false);
        add(cardsPanel, BorderLayout.CENTER);

        setOpaque(false);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        updateLayoutBasedOnPosition(); // Detect parent layout position
    }

    private void updateLayoutBasedOnPosition() {
        Container parent = getParent();
        if (parent != null) {
            LayoutManager layout = parent.getLayout();
            if (layout instanceof BorderLayout) {
                Object constraints = ((BorderLayout) layout).getConstraints(this);

                boolean isEastOrWest = BorderLayout.EAST.equals(constraints) || BorderLayout.WEST.equals(constraints);

                if (isEastOrWest) {
                    cardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, CARD_SPACING, 0)); // horizontal
                    setPreferredSize(new Dimension(150, 500)); // tall panel
                } else {
                    cardsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 0, CARD_SPACING)); // vertical
                    setPreferredSize(new Dimension(800, 150)); // wide panel
                }
            }
        }
    }

    public void addCard(CardView cardView) {
        playerCards.add(cardView);
        cardsPanel.add(cardView);
        revalidate();
        repaint();
    }

    public void removeCard(CardView cardView) {
        playerCards.remove(cardView);
        cardsPanel.remove(cardView);
        revalidate();
        repaint();
    }

    public void clearHand() {
        playerCards.clear();
        cardsPanel.removeAll();
        revalidate();
        repaint();
    }

    public List<CardView> getPlayerCards() {
        return playerCards;
    }

    public boolean containsCard(CardView card) {
        return playerCards.contains(card);
    }
}
