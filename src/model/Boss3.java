package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DONE;
import static model.GameFigure.STATE_DYING;
import static model.GameFigure.STATE_FIRED;
import static model.GameFigure.STATE_ULTIMATE;
import view.GamePanel;


public class Boss3 extends GameFigure{
    public int ultCount = 0;
    public int count = 0;
    public int direction  = 1;
    public int dx, dy;
    public int timeCount = 0;
    public int ultLaunch = 0;
    public int delayCount = 0;
    public int hiddenCount = 0;
    public boolean delay = false;
    public int randomInt;
    private Image ship;
    private Image afterBurnerOne;
    private Image afterBurnerTwo;
    private Image explosion;
    
    public Boss3(int x, int y){
        super(x, y);
        super.health = 30;
        super.movementSpeed = 5;
        super.state = STATE_ALIVE;
        dx = super.movementSpeed;
        dy = super.movementSpeed;
        ship = null;
        afterBurnerOne = null;
        explosion = null;
    
        try {
            explosion = ImageIO.read(getClass().getResource("EnemyShipExplosion.png"));
            ship = ImageIO.read(getClass().getResource("AlienShip5.png"));
            afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner1.png"));
            afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner2.png"));
            
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
        else{
           if(ultCount >= 100){
               if(hiddenCount >= 75){
                   ultCount = 0;
                   hiddenCount = 0;
                   super.state = STATE_ALIVE;
               }
               else{
                   hiddenCount++;
               }
           }
           else{
                if (direction == 0){
                    g.drawImage(ship, (int)super.x - 75, (int)super.y - 50, 
                        -75, 50, null);
                    if (delay){
                        g.drawImage(afterBurnerOne, (int)super.x - 130, (int)super.y - 35, 
                            -75, 15, null);
                    }
                    else{
                        g.drawImage(afterBurnerTwo, (int)super.x - 130, (int)super.y - 35, 
                            -75, 15, null);
                    }
                }
                else{
                    g.drawImage(ship, (int)super.x - 75, (int)super.y - 50, 
                        75, 50, null);
                    if (delay){
                        g.drawImage(afterBurnerOne, (int)super.x - 20, (int)super.y - 35, 
                            75, 15, null);
                    }
                    else{
                        g.drawImage(afterBurnerTwo, (int)super.x - 20, (int)super.y - 35, 
                            75, 15, null);
                    }
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
        Random randomNumber = new Random();
        
        if(super.state == STATE_DYING){}
        else if(super.state == STATE_ULTIMATE){
            if (super.x >= 800 && super.x <= 850){
                direction = 1;
                ultAttack();
            } 
            else{
                //move into position to launch ult
                if (super.x > 800){
                    super.x -= 15;
                    direction = 1;
                }
                else if(super.x < 800){
                    super.x += 15;
                    direction = 0;
                }
                else{}
                
                //move onto same y axis as gary
                if(super.y > Main.gameData.gary.y){
                    super.y -= 13;
                }
                else if(super.y < Main.gameData.gary.y){
                    super.y += 13;
                }
                else{}
            }
        }
        else{
            randomInt = (int)(randomNumber.nextDouble() * 10);
            
            super.x += (int)((randomNumber.nextDouble() * 10) + 10);
            super.y += (int)((randomNumber.nextDouble() * 10) + 10);
            
            if (super.x + 38 > 1200) {              
                direction = 1;
                dx = (dx * -1);
            } else if (super.x < 300) {
                direction = 0;
                super.state = STATE_FIRED;
                dx = (dx * -1);
            }
            else{}

            if (super.y + 38 > GamePanel.height) {
                dy = (dy * -1);
                super.y = GamePanel.height - 38;
            } else if (super.y - 38 < 0) {
                dy = (dy * -1);
                super.y = 38;
            }
            else{}
            
            timeCount++;
            ultCount++;
            if (super.health < 40){
                ultCount++;
            }
            if (super.health < 30){
                ultCount++;
            }
            if (super.health < 20){
                ultCount++;
            }
            
            if(direction == 1){
                if (super.state != STATE_ULTIMATE){
                    super.state = STATE_FIRED;
                }
            }

            if (direction == 1 && super.state == STATE_FIRED){
                if (super.x < GamePanel.width){
                    if ((count >= 30)){
                        fire();
                        count = 0;
                    }
                }    
            count++;
            }
            if (ultCount >= 10){
                super.state = STATE_ULTIMATE;
            }
            if (timeCount >= 30){
                if (randomInt > 3 && randomInt < 7){
                    dx *= -1; 
                }
                else if(randomInt < 3){
                    dy *= -1;
                }
                else{
                    dx *= -1;
                    dy *= -1;
                }
                timeCount = 0;
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
        //Camoflauges himself and launches a volley of comets
        // moves fast 50 pixels
        int displacement = 0;
        for (int i  = 0; i < 5; i++){
            Comet ult = new Comet(1300, displacement);
            ult.setMovementSpeed(-30);
            displacement += 100;
            Main.gameData.enemySubCraftFigures.add(ult);
            
        }
        if (super.health < 30){
            Asteroid ult = new Asteroid(1400, 200);
            ult.setMovementSpeed(-20);
            Main.gameData.enemySubCraftFigures.add(ult);
        }
        if (super.health < 20){
            Asteroid ult = new Asteroid(1400, 500);
            ult.setMovementSpeed(-30);
            Main.gameData.enemySubCraftFigures.add(ult);
        }        

        super.state = STATE_ALIVE;
        ultCount = 0;
        
    } 
}
