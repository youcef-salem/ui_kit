package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class NimoPlayerChoice extends JFrame implements ActionListener {
	
	JComboBox<Integer> humanComboBox;
	JComboBox<Integer> computerComboBox;

	public NimoPlayerChoice() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new FlowLayout());

		Integer[] options = {0, 1, 2, 3, 4};

		// Human Players
		this.add(new JLabel("Human Players:"));
		humanComboBox = new JComboBox<>(options);
		humanComboBox.setSelectedIndex(2);
		humanComboBox.addActionListener(this);
		this.add(humanComboBox);

		// Computer Players
		this.add(new JLabel("Computer Players:"));
		computerComboBox = new JComboBox<>(options);
		computerComboBox.setSelectedIndex(2);
		computerComboBox.addActionListener(this);
		this.add(computerComboBox);

		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int humanPlayers = (int) humanComboBox.getSelectedItem();
		int computerPlayers = (int) computerComboBox.getSelectedItem();
		int total = humanPlayers + computerPlayers;

		if (total > 4) {
			JOptionPane.showMessageDialog(this, "Total players must not exceed 4.", "Invalid Selection", JOptionPane.WARNING_MESSAGE);

			// Reset the source combo box to keep total ≤ 4
			if (e.getSource() == humanComboBox) {
				humanComboBox.setSelectedIndex(4 - computerPlayers);
			} else if (e.getSource() == computerComboBox) {
				computerComboBox.setSelectedIndex(4 - humanPlayers);
			}
			return;
		}

		System.out.println("Human: " + humanPlayers + ", Computer: " + computerPlayers + ", Total: " + total);
	}
}
