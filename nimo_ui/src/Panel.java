import javax.swing.*;
import java.awt.*;

class NimoStartPanel extends JPanel {
    public NimoStartPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Create NimoButtons
        NimoButton startButton = new NimoButton("Start");
        NimoButton settingsButton = new NimoButton("Settings", new Color(76, 175, 80), 20);
        NimoButton exitButton = new NimoButton("Exit", new Color(244, 67, 54), 10);

        // Add the buttons to the panel
        add(startButton);
        add(settingsButton);
        add(exitButton);
    }
}





