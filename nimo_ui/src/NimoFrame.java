import javax.swing.*;
import java.awt.*;

import java.awt.event.*;
import java.awt.geom.RoundRectangle2D;

/**
 * NimoFrame: A custom JFrame with background color
 */
public class NimoFrame extends JFrame {
    private Color backgroundColor;

    public NimoFrame() {
        this("NimoFrame", new Color(201, 166, 23));
    }

    public NimoFrame(String title, Color backgroundColor) {
        super(title);
        ImageIcon icon2 = new ImageIcon("UNO_Logo.svg.png");
        this.setIconImage(icon2.getImage());
        this.backgroundColor = backgroundColor;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);

        // Apply background color using a panel
        JPanel contentPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(backgroundColor);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        contentPanel.setLayout(new BorderLayout());
        setContentPane(contentPanel);
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
        repaint();
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }
}


