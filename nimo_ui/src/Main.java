import javax.swing.*;
import java.awt.*;


public class Main {
    static  colors colors = new colors();
    static  Panel panel = new Panel();
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                NimoFrame frame = new NimoFrame("Nimo Application", new Color(200, 230, 255));

                // Create the start panel and add it to the frame
                NimoStartPanel startPanel = new NimoStartPanel();
                frame.getContentPane().add(startPanel, BorderLayout.CENTER);



                // Display the frame
                frame.setLocationRelativeTo(null);
                frame.setVisible(true);

            }
        });








    }
}