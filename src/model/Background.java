
package model;

import controller.Main;
import model.GameData;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;

public class Background {
    
    public BufferedImage img1, img2, img3, img4;
    public int backgroundWidth;
    public int backgroundHeight;
    public double x, offset;
    public int count;
    public int level;
    
    
    Background(){
//        level = Main.GameData.level;
        backgroundHeight = GamePanel.height;
        backgroundWidth = -1200; //-GamePanel.width;
        x = 0;
        count = 0;
        offset = 1195;   
        img1 = null;
        img2 = null;
        img3 = null;
        img4 = null;
        for (int i = 0; i < 11; i++){
            try {
                img1 = ImageIO.read(getClass().getResource("Galaxy6.png"));
                img2 = ImageIO.read(getClass().getResource("Galaxy6.png"));
                //background[i] =  ImageIO.read(getClass().getResource("Galaxy" + String.valueOf(i + 1) + ".png"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
                System.exit(-1);
            }
        }
        //img = loadImage();
    }
    
    public void render(Graphics2D g) {
        g.drawImage(img1, (int)(x), 0, (int)backgroundWidth, (int)backgroundHeight, null);
        g.drawImage(img1, (int)(x + backgroundWidth), 0, (int)backgroundWidth, (int)backgroundHeight, null);
        g.drawImage(img1, (int)(x + backgroundWidth + offset), 0, (int)backgroundWidth, (int)backgroundHeight, null);
    }
    
    public void translate() {
        backgroundWidth = GamePanel.width;
        backgroundHeight = GamePanel.height;
        if (count > 2){
            x -= 1;
            count = 0;
        }
        else{
            count++;
        }
        if (x > GamePanel.width){
            x = x - backgroundWidth;
        }
        if ((x + offset) == 0){
            x = 0;
        }
    }    
}
