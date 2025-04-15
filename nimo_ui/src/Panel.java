import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

class NimoStartPanel extends JPanel {
    public NimoStartPanel() throws IOException {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
      setBackground(Color.ORANGE);


        // Create NimoButtons
addMouseListener(new MouseAdapter() {
    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);

    }
});


        // Add the buttons to thMouse panel


    }
    BufferedImage img = ImageIO.read(Objects.requireNonNull(getClass().getResource("/assets/unobg.jpg")));

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g ;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g.drawImage(img, 0, 0,getWidth(),getHeight(), this); // draw at (0,0)
    }
}