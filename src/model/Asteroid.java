package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;


public class Asteroid extends GameFigure{
    private int direction = 1;
    private Image ship, asteroidExplosion;
    
    public Asteroid(float x, float y){
        super(x, y);
        super.health = 5;
        super.movementSpeed = -5;
        super.level = Main.gameData.level;
        super.points = 750 + (super.level * 11);
        super.movementSpeed = (int) (-5 - .4 * super.level);
        super.state = STATE_ALIVE;
        ship = null;
        
        try {
            
            if (super.level < 5){
                ship = ImageIO.read(getClass().getResource("subasteroid1.png"));
                asteroidExplosion = ImageIO.read(getClass().getResource("AsteroidExplosion1.png"));
                }
                else if (super.level >= 5 && super.level < 10){
                ship = ImageIO.read(getClass().getResource("subasteroid2.png"));
                asteroidExplosion = ImageIO.read(getClass().getResource("AsteroidExplosion2.png"));
                }
                else if (super.level >= 10 && super.level < 15){
                ship = ImageIO.read(getClass().getResource("subasteroid3.png"));
                asteroidExplosion = ImageIO.read(getClass().getResource("AsteroidExplosion3.png"));
                }
                else if (super.level >= 15 && super.level < 20){
                ship = ImageIO.read(getClass().getResource("subasteroid4.png"));
                asteroidExplosion = ImageIO.read(getClass().getResource("AsteroidExplosion4.png"));
                }
                else{
                ship = ImageIO.read(getClass().getResource("subasteroid1.png"));
                asteroidExplosion = ImageIO.read(getClass().getResource("AsteroidExplosion1.png"));
                }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
            System.exit(-1);
        }
    }
 
    @Override
    public void movement(int movementSpeed) {}
 
    @Override
    public void render(Graphics2D g) {
        if(super.state == STATE_DYING){
            for (int i = 0; i < 100; i++){
                if (super.y > Main.WIN_HEIGHT - 300){
                g.drawImage(asteroidExplosion, (int)super.x + 100, (int)super.y - 150, 
                    120, 120, null);
                }
                else{
                    g.drawImage(asteroidExplosion, (int)super.x + 100, (int)super.y, 
                        120, 120, null);
                }
            }
            this.destroy();
        }
        else{
            //collisionBox is off causing the render to occur at an odd point
            if (super.y > Main.WIN_HEIGHT - 300){
                g.drawImage(ship, (int)super.x + 100, (int)super.y - 150, 
                    200, 200, null);
            }
            else{
                g.drawImage(ship, (int)super.x + 100, (int)super.y, 
                    200, 200, null);
            }
        }
    }

    @Override
    public void update() {
        if (super.state == STATE_DYING){}
        else{
            super.x -= 2;
            if (super.x + 400 < 0) {
                this.setPoints(0);
                super.state = STATE_DONE;
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        if (super.y > Main.WIN_HEIGHT - 300){
            return new Rectangle2D.Double(super.x + 100, super.y - 150, 150, 150);
        }
        else{
            return new Rectangle2D.Double(super.x + 100, super.y, 150, 150);
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
    public void destroy() {
        if(super.perfectHit){
            SubAsteroid asteroid1 = new SubAsteroid(super.x + 150, super.y);
            asteroid1.movementBackUp();
            asteroid1.setDirection(0);
//            synchronized(Main.gameData.enemyLaserFigures){
                Main.gameData.enemyLaserFigures.add(asteroid1);
//            }            
            SubAsteroid asteroid2 = new SubAsteroid(super.x + 150, super.y);
            asteroid2.movementBackDown();
            asteroid2.setDirection(0);
//            synchronized(Main.gameData.enemyLaserFigures){
                Main.gameData.enemyLaserFigures.add(asteroid2);
//            }
        }
        else{
            SubAsteroid asteroid1 = new SubAsteroid(super.x + 150, super.y);
            asteroid1.movementForwardUp();
//            synchronized(Main.gameData.enemyFigures){
                Main.gameData.enemyLaserFigures.add(asteroid1);
//            }
            SubAsteroid asteroid2 = new SubAsteroid(super.x + 150, super.y);
            asteroid2.movementForwardDown();
//            synchronized(Main.gameData.enemyFigures){
                Main.gameData.enemyLaserFigures.add(asteroid2);
//            }
        }
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
        if (super.y > Main.WIN_HEIGHT - 300){
            return new Rectangle2D.Double(super.x + 100, super.y - 150 + 67, 150, 67);
        }
        else{
            return new Rectangle2D.Double(super.x + 100, super.y + 67, 150, 67);
        }
    }

    @Override
    public void fire() {}
}