
package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;


class Boss1Ultimate  extends GameFigure{
    Image attack, bladeReturned;
    boolean up, down;
    public Point2D.Float target;
    public float dx, dy;
    public boolean desiredTarget;
    public int destroyCount;
    public int deathCount;
    public int moveCount;
    
    
    Boss1Ultimate(float x, float y) {
        super(x, y);
        super.movementSpeed = 20;
        super.level = Main.gameData.level;
        super.health = 1;
        super.state = STATE_ALIVE;
        desiredTarget = true;
        super.miss = false;
        destroyCount = 0;
        deathCount = 0;
        moveCount = 0;
        
        try {
            attack = ImageIO.read(getClass().getResource("Boss1.blade.png"));
            bladeReturned = ImageIO.read(getClass().getResource("Boss1.blade.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
        System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if (super.state == STATE_ALIVE){
            if (up){
                g.drawImage(attack, (int)super.x, (int)super.y, 
                        -60, 30, null);
            }
            else{
                g.drawImage(attack, (int)super.x, (int)super.y, 
                        -60, -30, null);
            }
        }
        else if (super.state == STATE_DYING){
            if(deathCount < 4){
                //can get rid of up and down if statement here if incorporate spin animation
                if (up){
                     g.drawImage(bladeReturned, (int)super.x, (int)super.y, 
                        -60, 30, null);
                }
                else{
                    g.drawImage(bladeReturned, (int)super.x, (int)super.y, 
                        -60, -30, null);
                }
                deathCount++;
            }
            else{
                super.state = STATE_DONE;
            }
        }
        else{
            if (up){
                g.drawImage(attack, (int)super.x, (int)super.y, 
                        -60, 30, null);
            }
            else{
                g.drawImage(attack, (int)super.x, (int)super.y, 
                        -60, -30, null);
            }
        }
    }
        

    @Override
    public void update() {
        if (super.state == STATE_ALIVE){
            if (super.x > 700 && down && moveCount < 3){ 
                super.x -= super.movementSpeed;
                moveCount++;
                if (super.y < Main.gamePanel.height){
                    super.y += (super.movementSpeed);
                }
                else{
                    super.y -= super.movementSpeed;
                }
            }
            else if (super.x > 700 && up && moveCount < 3){
                super.x += super.movementSpeed;
                moveCount++;
                if (super.y > 0){
                    super.y -= super.movementSpeed;
                }
                else{
                    super.y += super.movementSpeed;
                }
            }
            else if (desiredTarget && Main.gameData.gary.health > 0){
                if(super.x > 20){
                    double angle = Math.atan2(Math.abs(Main.gameData.gary.y - super.y), Math.abs(Main.gameData.gary.x - super.x));
                    dx = (float) (super.movementSpeed * Math.cos(angle));
                    dy = (float) (super.movementSpeed * Math.sin(angle));

                    if (Main.gameData.gary.x > super.x && Main.gameData.gary.y < super.y) { // target is upper-right side
                        dy = -dy; // dx > 0, dx < 0
                    } else if (Main.gameData.gary.x < super.x && Main.gameData.gary.y < super.y) { // target is upper-left side
                        dx = -dx;
                        dy = -dy;
                    } else if (Main.gameData.gary.x < super.x && Main.gameData.gary.y > super.y) { // target is lower-left side
                        dx = -dx;
                    } else { // target is lower-right side
                        // dx > 0 , dy > 0
                    }
                    super.x += dx;
                    super.y += dy;
                }
                else{
                    super.miss = true;
                    super.state = STATE_DYING;
                }
            }
            else if(!desiredTarget && Main.gameData.boss1.health > 0){
                if (super.x <= Main.gameData.boss1.x - 60){
                    double angle = Math.atan2(Math.abs(Main.gameData.boss1.y - super.y), Math.abs(Main.gameData.boss1.x - super.x));
                    dx = (float) (super.movementSpeed * Math.cos(angle));
                    dy = (float) (super.movementSpeed * Math.sin(angle));

                    if (Main.gameData.boss1.x > super.x && Main.gameData.boss1.y < super.y) { // target is upper-right side
                        dy = -dy; // dx > 0, dx < 0
                    } else if (Main.gameData.boss1.x < super.x && Main.gameData.boss1.y < super.y) { // target is upper-left side
                        dx = -dx;
                        dy = -dy;
                    } else if (Main.gameData.boss1.x < super.x && Main.gameData.boss1.y > super.y) { // target is lower-left side
                        dx = -dx;
                    } else { // target is lower-right side
                        // dx > 0 , dy > 0
                    }
                    super.x += dx;
                    super.y += dy;
                }
                else{
                    super.state = STATE_DYING;
                    super.miss = true;
                }
            }
            else{
                super.state = STATE_DYING;
            }
        }
        else if (super.state == STATE_DYING && !super.miss){
            if (Main.gameData.boss1.health > 0){
                desiredTarget = false;
                super.state = STATE_ALIVE;
                super.health = 1;
            }
            else if (super.miss){
                //send the blades back to boss but do no damage and no destroy animation
                
                if (super.x < Main.gameData.boss1.x - 60){}
                else{
                    super.state = STATE_DONE;
                }
            }
            else{
                if (destroyCount < 50){
                    destroyCount++;
                    super.state = STATE_DYING;
                }
                else{
                    super.state = STATE_DONE;
                }
            }
        }
        else{}
    }

    @Override
    public void fire() {}

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x, super.y, 60, 30);
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
    public void destroy() {}

    @Override
    public int getPoints() {return super.points;}

    @Override
    public void setPoints(int e) {}

    @Override
    public int getHealth() {return super.health;}

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
    
}
