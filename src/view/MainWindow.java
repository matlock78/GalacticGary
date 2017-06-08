package view;

import controller.KeyBoardController;
import controller.Main;
import java.awt.Container;
import javax.swing.JFrame;

public class MainWindow extends JFrame {

    public MainWindow() {

        Container c = getContentPane();

        c.add(Main.gamePanel, "Center");
        
        KeyBoardController keyListener = new KeyBoardController();
        Main.gamePanel.addKeyListener(keyListener);
        Main.gamePanel.setFocusable(true);
        // just have one Component "true", the rest must be "false"
    }

}
