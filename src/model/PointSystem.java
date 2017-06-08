
package model;

import controller.Main;
import model.GameData;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;

public class PointSystem{
    private Image zero, one, two, three, four;
    private Image five, six, seven, eight, nine;
    private Image levelCount, levelPlaceHolder, tempPlaceHolder;
    private Image lifeImg, lifePlaceHolder, placeHolder;
    /*   public String points;
    public String lives;
    public String level;*/
    public int points, lives, level;
    public int displacement = 37;
    public int y = 0;
    
    PointSystem(int p, int life, int l){
        /*        points = Integer.toString(p);
        lives = Integer.toString(life);
        level = Integer.toString(l);*/
        points = p;
        lives = life;
        level = l;
        try {
            zero = ImageIO.read(getClass().getResource("0.1.png"));
            one = ImageIO.read(getClass().getResource("1.1.png"));
            two = ImageIO.read(getClass().getResource("2.1.png"));
            three = ImageIO.read(getClass().getResource("3.1.png"));
            four = ImageIO.read(getClass().getResource("4.1.png"));
            five = ImageIO.read(getClass().getResource("5.1.png"));
            six = ImageIO.read(getClass().getResource("6.1.png"));
            seven = ImageIO.read(getClass().getResource("7.1.png"));
            eight = ImageIO.read(getClass().getResource("8.1.png"));
            nine = ImageIO.read(getClass().getResource("9.1.png"));
            lifeImg = ImageIO.read(getClass().getResource("GalacticGaryShipFinal.png"));
            levelPlaceHolder = ImageIO.read(getClass().getResource("Level5.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
            System.exit(-1);
        }
    }
    public void render(Graphics2D g) {
        //points
        int pointsDisplacement = 0;
        String p = Integer.toString(points);
        if(p.length() < 10){
            for (int i = 0; i < 10 - p.length(); i++){
                placeHolder = zero;
                g.drawImage((placeHolder), 820 + (i * displacement), y, 
                    36, 40, null);
                pointsDisplacement += displacement;
            }
        }
        for (int i = 0; i < p.length(); i++){
            tempPlaceHolder = placeHolderMethod(p.charAt(i));
            g.drawImage(tempPlaceHolder, 820 + (i * displacement) + pointsDisplacement, y, 
                    36, 40, null);
        }
        

// lives display
        String l = Integer.toString(lives);
        int livesDisplacement = 0;
        g.drawImage(lifeImg, 2, 2, 60, 40, null);        
        for (int i = 0; i < l.length(); i++){
            tempPlaceHolder = placeHolderMethod(l.charAt(i));
            g.drawImage((tempPlaceHolder), 65 + (i * displacement) + livesDisplacement, y, 
                    36, 40, null);
        }
        


// level display
        String lvl = Integer.toString(level);
        int levelDisplacement = 0;
        g.drawImage(levelPlaceHolder, 400, 0, 100, 40, null);
        if(lvl.length() < 2){
            placeHolder = zero;
            g.drawImage((placeHolder), 536, y, 
                36, 40, null);
            levelDisplacement += displacement;
        }
        for (int i = 0; i < lvl.length(); i++){
            tempPlaceHolder = placeHolderMethod(lvl.charAt(i));
            g.drawImage((tempPlaceHolder), 536 + (i * displacement) + levelDisplacement, y, 
                    36, 40, null);
        }
    }
    
    public void update(int p, int life, int l){
        points = p;
        lives = life;
        level = l;
    }

    public Image placeHolderMethod(char i) {
        int temp = Character.getNumericValue(i);
        //tempPlaceHolder = null;
//        try {
            if (temp == 0){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("0.1.png"));
                tempPlaceHolder = zero;
            }
            else if (temp == 1){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("1.1.png"));
                tempPlaceHolder = one;
            }
            else if (temp == 2){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("2.1.png"));
                tempPlaceHolder = two;
            }
            else if (temp == 3){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("3.1.png"));
                tempPlaceHolder = three;
            }
            else if (temp == 4){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("4.1.png"));
                tempPlaceHolder = four;
            }
            else if (temp == 5){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("5.1.png"));
                tempPlaceHolder = five;
            }
            else if (temp == 6){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("6.1.png"));
                  tempPlaceHolder = six;
            }
            else if (temp == 7){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("7.1.png"));
                tempPlaceHolder = seven;
            }
            else if (temp == 8){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("8.1.png"));
                 tempPlaceHolder = eight;
            }
            else if (temp == 9){
//                tempPlaceHolder = ImageIO.read(getClass().getResource("9.1.png"));
                 tempPlaceHolder = nine;
            }
            else{
//                tempPlaceHolder = ImageIO.read(getClass().getResource("9.1.png"));
                tempPlaceHolder = nine;
            }
            /*        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
            System.exit(-1);
            }*/
        return tempPlaceHolder;
    }
}
