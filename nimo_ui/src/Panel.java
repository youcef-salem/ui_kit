import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class NimoStartPanel extends JPanel {
    public NimoStartPanel() {
        setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));

        // Create NimoButtons
        NimoButton startButton = new NimoButton("Start on that   particular time ",
                Color.PINK, 30, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {


            }
        });
        // Add the buttons to the panel
        add(startButton);

    }
}





