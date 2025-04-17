import javax.swing.*;
import java.awt.*;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			NimoFrame frame = new NimoFrame("UNO", new Color(200, 230, 255));
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.setLayout(new BorderLayout());

			// Center panel with centered card
			JPanel startPanel = new JPanel(new GridBagLayout()); // centers content
			CardView cardView = new CardView(Card.create("red", "skip"));
			cardView.setPreferredSize(new Dimension(100, 150));
			startPanel.setBackground(new Color(200, 230, 255));// set visible size
			startPanel.add(cardView);

			// Player hands
			NimoPlayerHand player1 = new NimoPlayerHand();
			NimoPlayerHand player2 = new NimoPlayerHand();
			NimoPlayerHand player3 = new NimoPlayerHand();
			NimoPlayerHand player4 = new NimoPlayerHand();

			player1.addCard(new CardView(Card.create("blue", "7")));
			player1.addCard(new CardView(Card.create("green", "reverse")));
			player1.addCard(new CardView(Card.create("wild", "wild draw four")));
			player1.addCard(new CardView(Card.create("blue", "7")));
			player1.addCard(new CardView(Card.create("green", "reverse")));
			player1.addCard(new CardView(Card.create("wild", "wild draw four")));
			player1.addCard(new CardView(Card.create("yellow", "draw two")));

			player2.addCard(new CardView(Card.create("red", "7")));
			player2.addCard(new CardView(Card.create("green", "reverse")));
			player2.addCard(new CardView(Card.create("blue", "draw two")));
			player2.addCard(new CardView(Card.create("blue", "7")));
			player2.addCard(new CardView(Card.create("green", "reverse")));
			player2.addCard(new CardView(Card.create("wild", "wild draw four")));
			player2.addCard(new CardView(Card.create("wild", "wild draw four")));

			player3.addCard(new CardView(Card.create("yellow", "7")));
			player3.addCard(new CardView(Card.create("green", "reverse")));
			player3.addCard(new CardView(Card.create("blue", "7")));
			player3.addCard(new CardView(Card.create("green", "reverse")));
			player3.addCard(new CardView(Card.create("wild", "wild draw four")));
			player3.addCard(new CardView(Card.create("wild", "wild draw four")));
			player3.addCard(new CardView(Card.create("yellow", "skip")));

			player4.addCard(new CardView(Card.create("green", "6")));
			player4.addCard(new CardView(Card.create("red", "reverse")));
			player4.addCard(new CardView(Card.create("wild", "wild")));
			player4.addCard(new CardView(Card.create("blue", "7")));
			player4.addCard(new CardView(Card.create("green", "reverse")));
			player4.addCard(new CardView(Card.create("wild", "wild draw four")));
			player4.addCard(new CardView(Card.create("wild", "wild draw four")));

			// Add panels
			frame.add(player1, BorderLayout.NORTH);
			frame.add(player2, BorderLayout.SOUTH);
			frame.add(player3, BorderLayout.EAST);
			frame.add(player4, BorderLayout.WEST);
			frame.add(startPanel, BorderLayout.CENTER); // centered card

			frame.pack();
			frame.setLocationRelativeTo(null);
			frame.setVisible(true);
		});
	}
}
