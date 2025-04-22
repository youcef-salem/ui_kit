package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class NimoMenu {
    JFrame frame = new JFrame();
    ImageIcon icon = new ImageIcon("unobg.jpg");
    ImageIcon icon2 = new ImageIcon("UNO_Logo.svg.png");


    public NimoMenu() {
        // Scale the icon
        icon.setImage(icon.getImage().getScaledInstance(500, 500, Image.SCALE_SMOOTH));
        frame.setIconImage(icon2.getImage());
        // Background label with icon
        JLabel bgLabel = new JLabel(icon);
        bgLabel.setBounds(0, 0, 500, 500);

        // Add mouse listener to the label
        bgLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                frame.dispose();
                new NimoWindow();
            }
        });

        // Frame setup
        frame.setTitle("UNO");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 500);
        frame.setLayout(null);
        frame.add(bgLabel);
        frame.setVisible(true);
    }
}
