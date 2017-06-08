package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Gary extends GameFigure{
    private Image ship, ultShip;
    private Image afterBurnerOne;
    private Image afterBurnerTwo;
    private Image ultCannon;
    private Image[] explosion = new Image[5];
    public boolean left;
    public boolean right;
    public boolean up;
    public boolean down;
 //   public boolean garyUlt = false;
    public boolean garyUlt = true;
    public int velocityX;
    public int velocityY;
    public boolean fired = false;
    public long count;
    public boolean delay = true;
    public int ultCount;
    public int lives;
    public int lifeCount = 0;
    
    
    public Gary(int x, int y) {
        super(x, y);
        //super.state = STATE_ALIVE;
        super.state = STATE_ULTIMATE;
//        this.lives = 1;
        super.points = 0;
        ship = null;
        afterBurnerOne = null;
        afterBurnerTwo = null;
        super.health = 1000;
        try {
            ship = ImageIO.read(getClass().getResource("GalacticGaryShipFinal.png"));
            afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner1.png"));
            afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner2.png"));
            ultShip = ImageIO.read(getClass().getResource("GaryUltimateShip.png"));
            ultCannon = ImageIO.read(getClass().getResource("garyUltCannon.png"));
            for(int i = 0; i < 5; i++){
                explosion[i] = ImageIO.read(getClass().getResource("Explosion" + Integer.toString(i + 1) + ".png"));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
            System.exit(-1);
        }
    }

    public int getLives(){
        return this.lives;
    }
    
    public void setLives(int e){
        this.lives = e;
    }
    
    @Override
    public void movement(int movementSpeed) {
    }

    @Override
    public void render(Graphics2D g) {
        if (super.state == STATE_ALIVE){
            garyUlt = false;
            g.drawImage(ship, (int)super.x, (int)super.y, 
                    75, 50, null);
            if(right){
                if(delay){
                    g.drawImage(afterBurnerTwo, (int)super.x + 25, (int)super.y + 11, 
                    -75, 20, null);
                }
                else{
                    g.drawImage(afterBurnerOne, (int)super.x + 25, (int)super.y + 11, 
                    -75, 20, null);
                }
            }
            else if(left){}
            else{
                if(delay){
                    g.drawImage(afterBurnerTwo, (int)super.x + 25, (int)super.y + 11, 
                    -30, 20, null);
                }
                else{
                    g.drawImage(afterBurnerOne, (int)super.x + 25, (int)super.y + 11, 
                    -30, 20, null);
                }
            }
            count++;
            if((count % 90000000) >= 0){
                if(delay){
                    delay = false;
                }
                else{
                    delay = true;
                }
                count = 0;
            }
            if(ultCount > 2000){
                super.state = STATE_ULTIMATE;
            }
            else{
                ultCount++;
            }
        }
        else if(super.state == STATE_ULTIMATE){
            garyUlt = true;
            g.drawImage(ultShip, (int)super.x, (int)super.y, 
                    75, 50, null);
            g.drawImage(ultCannon, (int)super.x + 45, (int)super.y + 25, 
                    40, 30, null);
            if(right){
                if(delay){
                    g.drawImage(afterBurnerTwo, (int)super.x + 25, (int)super.y + 11, 
                    -75, 20, null);
                }
                else{
                    g.drawImage(afterBurnerOne, (int)super.x + 25, (int)super.y + 11, 
                    -75, 20, null);
                }
            }
            else if(left){}
            else{
                if(delay){
                    g.drawImage(afterBurnerTwo, (int)super.x + 25, (int)super.y + 11, 
                    -30, 20, null);
                }
                else{
                    g.drawImage(afterBurnerOne, (int)super.x + 25, (int)super.y + 11, 
                    -30, 20, null);
                }
            }
            count++;
            if((count % 90000000) >= 0){
                if(delay){
                    delay = false;
                }
                else{
                    delay = true;
                }
                count = 0;
            }
        }
        else if(super.state == STATE_DYING){
            for (int i = 0; i < 5; i++){
                for (int j = 0; j < 1000; j++){
                    if(i < 2){
                        g.drawImage(explosion[0], (int)super.x, (int)super.y, 
                        75, 50, null);
                    }
                    else{
                        g.drawImage(explosion[1], (int)super.x, (int)super.y, 
                        75, 50, null);
                    }
                }
            }
            super.state = STATE_DONE;
        }
    }

    public void translate(int dx, int dy) {
        super.x += dx;
        super.y += dy;
    }
    
    public float getXofLaserShoot() {
        return super.x+25;
    }
    
    public float getYofLaserShoot() {
        return super.y;
    }
    
    //used for animation for afterburner and destroyed
    @Override
    public void update() {
        velocityX = 0;
        velocityY = 0;
        lifeCount++;
        if(up){
            if(super.y - 7 > 0){
                velocityY += -7;
            }
        }
        if(down){
            if(super.y + 120 < Main.WIN_HEIGHT){
                velocityY += 7;
            }
        }
        if(right){
            if(super.x + 82 < 300){
                velocityX += 7;
            }
        }
        if(left){
            if(super.x - 7 > 0){
                velocityX += -3;
            }
        }
        translate(velocityX, velocityY);
        if (lifeCount > 100){
            super.health = 1;
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x, super.y, 75, 50);
    }

    @Override
    public void addDead() {
    }

    @Override
    public int getMovementSpeed() {
        return super.movementSpeed;
    }

    @Override
    public void setMovementSpeed(int s) {
        super.movementSpeed = s;
    }

    @Override
    public boolean isdestroyed() {
        return false;
    }

    @Override
    public void destroy() {
        //code for explosion goes here
        super.state = STATE_DYING;
    }

    @Override
    public int getPoints() {
        return super.points;
    }

    @Override
    public void setPoints(int e) {
        super.points = e;
    }

    @Override
    public int getHealth() {
        return super.health;
    }

    @Override
    public void setHealth(int e) {
        super.health = e;
    }

    @Override
    public boolean perfectHit() {
        return false;
    }

    @Override
    public void setPerfectHit(boolean e) {
        super.perfectHit = e;
    }

    @Override
    public void movementForward() {}

    @Override
    public void movementBackUp() {}

    @Override
    public void movementBackDown() {}

    @Override
    public void movementForwardUp() {}

    @Override
    public void movementForwardDown() {}

    @Override
    public Rectangle2D getPerfectHit() {return null;}

    @Override
    public void fire() {
 //       synchronized (Main.gameData.enemyFigures){
        int j = Main.gameData.friendLaserFigures.size();
            if (Main.gameData.friendFigures.size() < 3){
                fired = true;
                Laser garyLaser = new Laser(this.x + 105, this.y + 15);
                try{
                    garyLaser.laser = ImageIO.read(getClass().getResource("GaryLaser.png"));
                }catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
                    System.exit(-1);
                }
                garyLaser.setMovementSpeed(20);
                fired = false;
                Main.gameData.friendFigures.add(garyLaser);
//            }
/*                if (Main.gameData.friendLaserFigures.size() < 2){
                fired = true;
                Laser garyLaser = new Laser(this.x + 105, this.y + 15);
                try{
                    garyLaser.laser = ImageIO.read(getClass().getResource("GaryLaser.png"));
                }catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
                    System.exit(-1);
                }
                garyLaser.setMovementSpeed(20);
                fired = false;
                Main.gameData.friendLaserFigures.add(garyLaser);
//            }
*/
        }
    }

    public void setLeft(boolean b) {
        this.left = b;
    }

    public void setRight(boolean b) {
        this.right = b;
    }

    public void setUp(boolean b) {
        this.up = b;
    }

    public void setDown(boolean b) {
        this.down = b;
    }
    
    public void ultLaunch(){
        Laser ult = new Laser(this.x + 65, this.y + 20);
        try{
            ult.laser = ImageIO.read(getClass().getResource("ultLaser.png"));
        }catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
            System.exit(-1);
        }
        ult.setMovementSpeed(0);
        ult.state = STATE_ULTIMATE;
        Main.gameData.friendFigures.add(ult);
        super.state = STATE_ALIVE;
        garyUlt = false;
        ultCount = 0;
    }
}
