package GUI;

import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			// Create main frame
			NimoFrame frame = new NimoFrame("UNO", new Color(200, 230, 255));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setSize(1000, 700); // Added size
			frame.setLayout(new BorderLayout());

			// Center panel with one card
			JPanel startPanel = new JPanel(new GridBagLayout());
			CardView cardView = new CardView(Card.create("red", "skip"));
			cardView.setPreferredSize(new Dimension(100, 150));
			startPanel.setBackground(new Color(200, 230, 255));
			startPanel.add(cardView);

			// Create player hands
			NimoPlayerHand player1 = new NimoPlayerHand(); // Top
			NimoPlayerHand player2 = new NimoPlayerHand(); // Bottom
			NimoPlayerHand player3 = new NimoPlayerHand(); // Right
			NimoPlayerHand player4 = new NimoPlayerHand(); // Left

			// Add some sample cards
			for (NimoPlayerHand hand : new NimoPlayerHand[]{player1, player2, player3, player4}) {
				hand.addCard(new CardView(Card.create("blue", "7")));
				hand.addCard(new CardView(Card.create("green", "reverse")));
				hand.addCard(new CardView(Card.create("back", "back")));
				hand.addCard(new CardView(Card.create("red", "7")));
			}

			// Add everything to frame
			frame.add(player1, BorderLayout.NORTH);
			frame.add(player2, BorderLayout.SOUTH);
			frame.add(player3, BorderLayout.EAST);
			frame.add(player4, BorderLayout.WEST);
			frame.add(startPanel, BorderLayout.CENTER);

			// Show the frame
			frame.setVisible(true);
		});

		//NimoMenu menu = new NimoMenu();
	
	}
}
