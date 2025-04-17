import javax.swing.*;

public class NimoScreenManager {
    private final JFrame frame;

    public NimoScreenManager(JFrame frame) {
        this.frame = frame;
    }

    public void setScreen(JPanel panel) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(panel);
        frame.revalidate();
        frame.repaint();
    }

}
