import javax.swing.*;
import java.awt.*;

public class NimoMenu {
    JFrame frame = new JFrame();
    ImageIcon icon = new ImageIcon("unobg.jpg");
    NimoButton button;

    public NimoMenu() {
        button = new NimoButton("PLAY", Color.ORANGE, 10, e -> {
            frame.dispose();
            NimoWindow myWindow = new NimoWindow();
        });

        // Scale the icon
        icon.setImage(icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH));

        // Background label with icon
        JLabel bgLabel = new JLabel(icon);
        bgLabel.setBounds(0, 0, 500, 500);

        // Button setup
        button.setBounds(180, 200, 120, 40);
        frame.add(button);

        // Frame setup
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.add(bgLabel); // Add last so it's at the back
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new NimoMenu();
    }
}
