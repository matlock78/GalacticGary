package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DYING;
import static model.GameFigure.STATE_FIRED;
import view.GamePanel;


public class Boss1 extends GameFigure{
    public int ultCount = 0;
    public int count = 0;
    public int direction  = 1;
    public int dx, dy, ultdy;
    public int ultLaunch = 0;
    public int delayCount = 0;
    public int secondUltCount = 0;
    public boolean delay = false;
    public boolean attack = false;
    public boolean attack2 = false;
    private Image ship;
    private Image ultShip1, ultShip2;
    private Image afterBurnerOne;
    private Image afterBurnerTwo;
    private Image explosion;
    
    public Boss1(int x, int y){
        super(x, y);
        super.health = 30;
        super.movementSpeed = 5;
        super.state = STATE_ALIVE;
        dx = super.movementSpeed;
        dy = super.movementSpeed;
        ultdy += super.movementSpeed;
        ship = null;
        afterBurnerOne = null;
        explosion = null;
    
        try {
            ultShip1 = ImageIO.read(getClass().getResource("Boss1.ult2.png"));
            ultShip2 = ImageIO.read(getClass().getResource("Boss1.ult3.png"));
            explosion = ImageIO.read
        
        (getClass().getResource("EnemyShipExplosion.png"));
            ship = ImageIO.read(getClass().getResource("Boss1.finnished.png"));
            afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner1.png"));
            afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner2.png"));
//            explosion = ImageIO.read(getClass().getResource("EnemyShipExplosion.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open AlienShip1.1.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
       if(super.state == STATE_DYING){
            for (int j = 0; j < 200; j++){
                if (direction == 0){
                    g.drawImage(explosion, (int)super.x - 75, (int)super.y - 50, 
                        -100, 100, null);
                }
                else{
                    g.drawImage(explosion, (int)super.x - 75, (int)super.y - 50, 
                        100, 100, null);
                }
            }
            this.destroy();
        }
       else if(super.state == STATE_ALIVE || super.state == STATE_FIRED || (super.state == STATE_ULTIMATE && Main.gameData.enemySubCraftFigures.size() < 1)){
            if (direction == 0){
                g.drawImage(ship, (int)super.x - 100, (int)super.y - 50, 
                    130, 60, null);
                if (delay){
                    g.drawImage(afterBurnerOne, (int)super.x - 90, (int)super.y - 50, 
                        -85, 25, null);
                }
                else{
                    g.drawImage(afterBurnerTwo, (int)super.x - 90, (int)super.y - 50, 
                        -85, 25, null);
                }
            }
            else{
                g.drawImage(ship, (int)super.x - 75, (int)super.y - 50, 
                    -130, 60, null);
                if (delay){
                    g.drawImage(afterBurnerOne, (int)super.x - 80, (int)super.y - 50, 
                        65, 25, null);
                }
                else{
                    g.drawImage(afterBurnerTwo, (int)super.x - 80, (int)super.y - 50, 
                        65, 25, null);
                }
            }
        }
        else{
           if (Main.gameData.enemySubCraftFigures.size() == 1){
                g.drawImage(ultShip1, (int)super.x - 100, (int)super.y - 50, 
                    -130, 60, null);
                if (delay){
                    g.drawImage(afterBurnerOne, (int)super.x - 110, (int)super.y - 50, 
                        65, 25, null);
                }
                else{
                    g.drawImage(afterBurnerTwo, (int)super.x - 110, (int)super.y - 50, 
                        65, 25, null);
                }
            }
            else{
                g.drawImage(ultShip2, (int)super.x - 100, (int)super.y - 50, 
                    -130, 60, null);
                if (delay){
                    g.drawImage(afterBurnerOne, (int)super.x - 110, (int)super.y - 50, 
                        65, 25, null);
                }
                else{
                    g.drawImage(afterBurnerTwo, (int)super.x - 110, (int)super.y - 50, 
                        65, 25, null);
                }
            }
        }
        if((delayCount % 90000000) >= 0){
                if(delay){
                    delay = false;
                }
                else{
                    delay = true;
                }
                delayCount = 0;
            }
    }

    @Override
    public void update() {
        if(super.state == STATE_DYING){}
        else if(super.state == STATE_ULTIMATE){
            if (!attack){
                if (super.x >= 1000 && super.x <= 1050){
                    direction = 1;
                    ultAttack();            
                    attack = true;
                }
                else{
                    //move into position to launch ult
                    if (super.x > 1050){
                        super.x -= 15;
                        direction = 1;
                    }
                    else if(super.x < 1000){
                        super.x += 15;
                        direction = 1;
                    }
                    else{}

                    //move onto same y axis as gary
                    if(super.y > Main.gameData.gary.y + 40){
                        super.y -= 13;
                    }
                    else if(super.y < Main.gameData.gary.y - 40){
                        super.y += 13;
                    }
                    else{}
                }
            }
            else if (secondUltCount >= 5 && !attack2){
                ultAttack();
                attack2 = true;
            }
            else if (secondUltCount < 5 && attack){
                secondUltCount++;
            }
            else{
                if (Main.gameData.enemySubCraftFigures.size() > 0){
                    if(super.y + 65 > Main.gamePanel.height || super.y - 5 < 0) {
                        ultdy = ultdy * -1;
                    }
                    super.y += ultdy;
                }
                else{
                    super.state = STATE_ALIVE;
                    ultCount = 0;
                    dx = 5;
                    dy = 5;
                    attack = false;
                    attack2 = false;
                }
            }
        }
        else{
            super.x += dx;
            super.y += dy;

            if (super.x + 38 > 1200) {              
                direction = 1;
                dx += 2;
                dx = (dx * -1);
                ultCount++;
            } else if (super.x < 300) {
                direction = 0;
                super.state = STATE_FIRED;
                dx -= 2;
                dx = (dx * -1);
                ultCount++;
            }
            else{}

            if (super.y + 38 > GamePanel.height) {
                dy += 2; 
                dy = (dy * -1);
                super.y = GamePanel.height - 38;
                ultCount++;
            } else if (super.y - 38 < 0) {
                dy -= 2;
                dy = (dy * -1);
                super.y = 38;
                ultCount++;
            }
            else{}
            
            if(direction == 1){
                if (super.state != STATE_ULTIMATE){
                    super.state = STATE_FIRED;
                }
            }

            if (direction == 1 && super.state == STATE_FIRED){
                if (super.x < GamePanel.width){
                    if ((count >= 35)){
                        fire();
                        count = 0;
                    }
                }    
            count++;
            }
            if (ultCount >= 10){
                super.state = STATE_ULTIMATE;
            }
        }
    }

    @Override
    public void fire() {
        Laser enemyLaser = new Laser(super.x - 75, super.y + 20);
        enemyLaser.setMovementSpeed(-30);
        Main.gameData.enemyLaserFigures.add(enemyLaser);
        super.state = STATE_ALIVE;
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x - 75, super.y - 50, 75, 50);
    }

    @Override
    public void addDead() {
        super.state = STATE_DONE;
    }

    @Override
    public void movement(int movementSpeed) {
    }

    @Override
    public int getMovementSpeed() {
        return 0;
    }

    @Override
    public void setMovementSpeed(int s) {
     }

    @Override
    public boolean isdestroyed() {
        return false;
    }

    @Override
    public void destroy() {
        Main.gameData.points += super.points;
        super.state = STATE_DONE;
    }

    @Override
    public int getPoints() {
        return 0;
    }

    @Override
    public void setPoints(int e) {
   }

    @Override
    public int getHealth() {
        return 0;
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
        //no need to implement here instead use the collision detection to find 
        //whether it was a perfect hit and then simply subtract double and return the perfect hit as false
    }

    @Override
    public void movementForward() {
 }

    @Override
    public void movementBackUp() {
    }

    @Override
    public void movementBackDown() {
   }

    @Override
    public void movementForwardUp() {
   }

    @Override
    public void movementForwardDown() {
  }

    @Override
    public Rectangle2D getPerfectHit() {
        return null;
}
    
    public void ultAttack(){
        if (!attack){
            Boss1Ultimate ult1 = new Boss1Ultimate(this.x - 40, this.y + 100);
            ult1.down = false;
            ult1.up = true;
            Main.gameData.enemySubCraftFigures.add(ult1);
        }   
        //smae effect of up true and down false can be achieved using only one boolean value
        //will require some reworking in boss1Ultimate.java
        if (attack){
            Boss1Ultimate ult2 = new Boss1Ultimate(this.x - 40, this.y - 50);
            ult2.up = false;
            ult2.down = true;
            Main.gameData.enemySubCraftFigures.add(ult2);
            ultCount = 0;
        }
        dx = 5;
        dy = 5;
    } 
}
