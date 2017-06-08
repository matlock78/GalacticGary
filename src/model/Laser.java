package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

public class Laser extends GameFigure {
    Image laser, ultLaser;
    public int ultCount, ultXVar;
    
    Laser(float x, float y){
        super(x,y);
        super.movementSpeed = 20;
        super.level = Main.gameData.level;
        super.health = 1;
        super.state = STATE_ALIVE;
        
        //code for alienship laser
        try {
            if (super.level < 5){
                laser = ImageIO.read(getClass().getResource("EnemyLaser1.png"));
            }
            else if (super.level >= 5 && super.level < 10){
                laser = ImageIO.read(getClass().getResource("EnemyLaser2.png"));
            }
            else if (super.level >= 10 && super.level < 15){
                laser = ImageIO.read(getClass().getResource("EnemyLaser3.png"));
            }
            else if (super.level >= 15 && super.level < 20){
                laser = ImageIO.read(getClass().getResource("EnemyLaser4.png"));
            }
            else{
                laser = ImageIO.read(getClass().getResource("EnemyLaser1.png"));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
        System.exit(-1);
        }
        super.movementSpeed = -15;
    }

    @Override
    public void render(Graphics2D g) {
        if (super.state == STATE_DYING){
            super.state = STATE_DONE;
        }
        else if(super.state == STATE_ULTIMATE){
            g.drawImage(laser, (int)super.x, (int)super.y, ultXVar, 30, null);
        }
        else{
            g.drawImage(laser, (int)super.x, (int)super.y, 
                7, 4, null);
        }
    }
    
    @Override
    public void update() {
        if (super.state == STATE_ALIVE){
            super.x += super.movementSpeed;
           if (super.x <= 0) {
               super.state = STATE_DONE;
           }
           if (super.x >= 1200){
               super.state = STATE_DONE;
           }
        }
        else{
            super.health = 999;
            if (ultCount > 150){
                super.state = STATE_DONE;
            }
            else{
                if ((ultXVar + (int)super.x)  > Main.WIN_WIDTH){
                    ultXVar = Main.WIN_WIDTH - (int)super.x;
                }
                else{
                    ultXVar += 10;
                }
                ultCount++;
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        if (super.state== STATE_ALIVE){
            return new Rectangle2D.Double(super.x, super.y, 7, 4);
        }
        else{
            return new Rectangle2D.Double(super.x, super.y, ultXVar, 30);
        }
    }
    @Override
    public void addDead() {}

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
    public void destroy() {}

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
    public Rectangle2D getPerfectHit() {
        return new Rectangle2D.Double(super.x, super.y, 7, 4);
    }

    @Override
    public void movement(int movementSpeed) {}

    @Override
    public void fire() {}
    
}
