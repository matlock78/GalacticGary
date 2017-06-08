package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import view.GamePanel;

public class AlienShip extends GameFigure{
    public int direction = 1;
    public boolean fired = false;
    private Image ship;
    private Image afterBurnerOne;
    private Image afterBurnerTwo;
    private Image explosion;
    private int dx = 5;
    private int dy = 5;
    private int count;
    private int delayCount;
    public boolean delay = false;
    
    
    AlienShip(int x, int y) {
        super(x, y);
        super.movementSpeed = (int) (15 + .4 * super.level);
        super.health = 3;
        super.points = 500 + (super.level * 11);
        super.state = STATE_ALIVE;
        super.level = Main.gameData.level;
        
        ship = null;
        afterBurnerOne = null;
        explosion = null;
        try {
            explosion = ImageIO.read(getClass().getResource("EnemyShipExplosion.png"));
           if (super.level < 5){
                ship = ImageIO.read(getClass().getResource("AlienShip1.1.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner1.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner2.png"));
                explosion = ImageIO.read(getClass().getResource("EnemyShipExplosion.png"));
            }
            else if (super.level >= 5 && super.level < 10){
                ship = ImageIO.read(getClass().getResource("AlienShip2.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner3.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner4.png"));
                explosion = ImageIO.read(getClass().getResource("AlienShipExplosion2.png"));
            }
            else if (super.level >= 10 && super.level < 15){
                ship = ImageIO.read(getClass().getResource("AlienShip3.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner5.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner6.png"));
                explosion = ImageIO.read(getClass().getResource("AlienShipExplosion3.png"));
            }
            else if (super.level >= 15 && super.level < 20){
                ship = ImageIO.read(getClass().getResource("AlienShip4.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner7.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner8.png"));
                explosion = ImageIO.read(getClass().getResource("AlienShipExplosion4.png"));
            }
            else{
                ship = ImageIO.read(getClass().getResource("AlienShip5.png"));
                afterBurnerOne = ImageIO.read(getClass().getResource("Afterburner1.png"));
                afterBurnerTwo = ImageIO.read(getClass().getResource("Afterburner2.png"));
                explosion = ImageIO.read(getClass().getResource("AlienShipExplosion5.png"));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open AlienShip1.1.png");
            System.exit(-1);
        }
    }

    @Override
    public void movement(int movementSpeed) {}

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
        else{
            super.x += dx;
            super.y += dy;

//            if (super.x + 38 > GamePanel.width) {
            if (super.x + 38 > 1200) {              
                direction = 1;
                dx = -5;
             //   super.x = GamePanel.width - 37;
            } else if (super.x < 300) {
                direction = 0;
                super.state = STATE_FIRED;
                dx = 5;
               //super.x = 400;
            }

            if (super.y + 38 > GamePanel.height) {
                dy = -dy;
                super.y = GamePanel.height - 38;
            } else if (super.y - 38 < 0) {
                dy = -dy;
                super.y = 38;
            }
            if(direction == 1){
                super.state = STATE_FIRED;
            }

            if (direction == 1 && super.state == STATE_FIRED){
                if (super.x < GamePanel.width){
                    if ((count >= 100 - (2 * super.level))){
                        fire();
                        count = 0;
                    }
                }    
            count++;
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x - 75, super.y - 50, 75, 50);
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
    public void destroy() {
        Main.gameData.points += super.points;
        super.state = STATE_DONE;
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
    public Rectangle2D getPerfectHit() {
        return new Rectangle2D.Double(super.x - 75, super.y - 50 + 25, 25, 25);
    }

    @Override
    public void fire() {
        Laser enemyLaser = new Laser(super.x - 75, super.y + 20);
        enemyLaser.setMovementSpeed(-20);
        //works with asteroidFigures
        Main.gameData.enemyLaserFigures.add(enemyLaser);
        super.state = STATE_ALIVE;
    }
}
