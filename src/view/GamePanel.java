package view;

import controller.Main;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JPanel;
import model.GameFigure;
import model.Background;
import model.PointSystem;

public class GamePanel extends JPanel {
    
    // size of the canvas - determined at runtime once rendered
    public static int width;
    public static int height;

    // off screen rendering
    private Graphics2D g2;
    private Image dbImage = null; // double buffer image
    public Background background = Main.gameData.background;
    private Image garyLives;
    public PointSystem pointSystem = Main.gameData.pointSystem;
    
    
    public void gameRender() {
        width = getSize().width;
        height = getSize().height;
        
        if (dbImage == null) {
            // Creates an off-screen drawable image to be used for double buffering
            dbImage = createImage(width, height);
            if (dbImage == null) {
                System.out.println("Critical Error: dbImage is null");
                System.exit(1);
            } else {
                g2 = (Graphics2D) dbImage.getGraphics();
            }
        }

// set background to png file
        g2.clearRect(0, 0, width, height);

        if (Main.animator.running) {

//            synchronized (Main.gameData.enemyFigures) {
                background.render(g2);
                pointSystem.render(g2);
                for (GameFigure f : Main.gameData.enemyFigures) {
                    f.render(g2);
//                }
            }
            
 //           synchronized (Main.gameData.enemyLaserFigures) {
                for (GameFigure f : Main.gameData.enemyLaserFigures){
                    f.render(g2);
                }
//            }
//            synchronized (Main.gameData.friendFigures) {
                for (GameFigure f : Main.gameData.friendFigures) {
                    f.render(g2);
                }
                
                for (GameFigure f : Main.gameData.friendLaserFigures) {
                    f.render(g2);
                }
//            }
//            synchronized (Main.gameData.enemySubCraftFigures) {
                for (GameFigure f : Main.gameData.enemySubCraftFigures){
                    f.render(g2);
                }
//            }       
        }
    }

    // use active rendering to put the buffered image on-screen
    public void printScreen() {
        Graphics g;
        try {
            g = this.getGraphics();
            if ((g != null) && (dbImage != null)) {
                g.drawImage(dbImage, 0, 0, null);
            }
            Toolkit.getDefaultToolkit().sync();  // sync the display on some systems
            if (g != null) {
                g.dispose();
            }
        } catch (Exception e) {
            System.out.println("Graphics error: " + e);
        }
    }
}
