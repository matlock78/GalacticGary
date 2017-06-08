package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class MSSubCraft extends GameFigure{
    public int directionX = 1;
    public int directionY = 1;
     private Image ship, explosion;
     private Image afterBurnerOne;
    private Image afterBurnerTwo;
    public int fireCount, explosionCount;
     private int delayCount;
    public boolean delay = false;
    
    public MSSubCraft(float x, float y) {
        super(x,y);
        super.health = 1;
        super.movementSpeed = (int) (3 + (.4 * super.level));
        super.points = 30 + (super.level * 11);
        super.level = Main.gameData.level;
        super.state = STATE_ALIVE;
        ship = null;
        
        try {
            if (super.level < 5){
                ship = ImageIO.read(getClass().getResource("MSSubCraft1.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner1.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner2.png"));
                explosion = ImageIO.read(getClass().getResource("MSSubCraftExplosion1.png"));
                }
                else if (super.level >= 5 && super.level < 10){
                ship = ImageIO.read(getClass().getResource("MSSubCraft2.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner3.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner4.png"));
                explosion = ImageIO.read(getClass().getResource("MSSubCraftExplosion2.png"));
                }
                else if (super.level >= 10 && super.level < 15){
                ship = ImageIO.read(getClass().getResource("MSSubCraft3.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner5.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner6.png"));
                explosion = ImageIO.read(getClass().getResource("MSSubCraftExplosion3.png"));
                }
                else if (super.level >= 15 && super.level < 20){
                ship = ImageIO.read(getClass().getResource("MSSubCraft4.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner7.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner8.png"));
                explosion = ImageIO.read(getClass().getResource("MSSubCraftExplosion4.png"));
                }
                else{
                ship = ImageIO.read(getClass().getResource("MSSubCraft5.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner1.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner2.png"));
                explosion = ImageIO.read(getClass().getResource("MSSubCraftExplosion5.png"));
                }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (super.state == STATE_DYING){
            for (int i = 0; i < 100; i++){
                g.drawImage(explosion, (int)super.x, (int)super.y + 1000, 45, 30, null);
                explosionCount++;
            }
            this.destroy();     
        }
        else{
            g.drawImage(ship, (int)super.x, (int)super.y + 1000, 45, 30, null);
            if (delay){
                g.drawImage(afterBurnerOne, (int)super.x + 45, (int)super.y + 1008, 
                    30, 15, null);
            }
            else{
                g.drawImage(afterBurnerTwo, (int)super.x + 45, (int)super.y + 1008, 
                    30, 15, null);
            }
        }
    }
    @Override
    public void update() {
        if(super.state == STATE_DYING){}
        else{
            if (directionX == 1){
                super.x -= (int)(super.movementSpeed);
                if (fireCount > 100 - (2 * super.level)){
                    fire();
                    fireCount = 0;
                }
                else{
                    fireCount++;
                }
                if(super.x <= -100){
                    super.state = STATE_DONE;
                }
            }
            if(directionY == 1){
                //travels downward
                super.y += (int)(2 * super.movementSpeed);
                if ((super.y + (int)(2 * super.movementSpeed)) > Main.WIN_HEIGHT -1050){
                    directionY = 0;
                }
            }
            else{
                //travels upward
                super.y += (int)(-2 * super.movementSpeed);
                if ((super.y + (int)(-2 * super.movementSpeed)) < -985){
                    directionY = 1;
                }
            }
        }
        if (delayCount > 1){
            delay = true;
            delayCount = 0;
        }
        else{
            delay = false;
        }
        delayCount++;
        
        
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x, super.y + 1000, 45, 30);
    }

    @Override
    public void addDead() {}

    @Override
    public void movement(int movementSpeed) {}

    @Override
    public int getMovementSpeed() {return 0;}

    @Override
    public void setMovementSpeed(int s) {}

    @Override
    public boolean isdestroyed() {return false;}

    @Override
    public void destroy() {
        Main.gameData.points += super.points;
        super.state = STATE_DONE;
    }
    
    @Override
    public int getPoints() {return 0;}

    @Override
    public void setPoints(int e) {}

    @Override
    public int getHealth() {return 0;}

    @Override
    public void setHealth(int e) {}

    @Override
    public boolean perfectHit() {return false;}

    @Override
    public void setPerfectHit(boolean e) {}

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
          //adding the syncronized creates a new synchronized array list which collides with the previous
//            synchronized (Main.gameData.enemyLaserFigures){
            Laser enemyLaser = new Laser(super.x - 4, super.y + 1015);
            enemyLaser.setMovementSpeed(-20);
            //works with asteroidFigures
            Main.gameData.enemyLaserFigures.add(enemyLaser);
            super.state = STATE_ALIVE;
//        }
    }
}
