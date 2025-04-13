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
        this("NimoFrame", new Color(240, 240, 240));
    }

    public NimoFrame(String title, Color backgroundColor) {
        super(title);
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

/**
 * NimoButton: A custom JButton with rounded corners and background color
 */
class NimoButton extends JButton {
    private Color buttonColor;
    private int borderRadius;

    public NimoButton(String text) {
        this(text, new Color(100, 149, 237), 15);
    }

    public NimoButton(String text, Color buttonColor, int borderRadius) {
        super(text);
        this.buttonColor = buttonColor;
        this.borderRadius = borderRadius;

        // Make the button transparent to show our custom painting
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorderPainted(false);
        setOpaque(false);

        // Default font and foreground
        setFont(new Font("Arial", Font.BOLD, 14));
        setForeground(Color.WHITE);

        // Add a default action listener
        addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("NimoButton \"" + getText() + "\" was clicked!");
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Paint the rounded background
        if (getModel().isPressed()) {
            g2.setColor(buttonColor.darker());
        } else if (getModel().isRollover()) {
            g2.setColor(buttonColor.brighter());
        } else {
            g2.setColor(buttonColor);
        }

        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(),
                borderRadius, borderRadius));

        g2.dispose();

        // Paint the text and other components
        super.paintComponent(g);
    }

    public void setButtonColor(Color color) {
        this.buttonColor = color;
        repaint();
    }

    public Color getButtonColor() {
        return buttonColor;
    }

    public void setBorderRadius(int radius) {
        this.borderRadius = radius;
        repaint();
    }

    public int getBorderRadius() {
        return borderRadius;
    }
}

/**
 * NimoStartPanel: An example panel that uses NimoButton
 */


/**
 * Main class to demonstrate the usage
 */

