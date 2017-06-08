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


public class SubAsteroid extends Asteroid{
    private Image ship, asteroidExplosion;
    public int direction = 1;
    public int explosionCount;
    public boolean backUp = false;
    public boolean forwardUp = false;
    public boolean backDown = false;
    public boolean forwardDown = false;
    
    public SubAsteroid(float x, float y){
        super(x, y);
        super.state = STATE_ALIVE;
        super.health = 2;
        super.points = 100 + (super.level * 11);
        super.level = Main.gameData.level;
        super.movementSpeed = (int) (-3 - .4 * level);
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
    public void movement(int movementSpeed) {
    }
    
    @Override
    public void render(Graphics2D g) {
        if (super.state == STATE_DYING){
            for(int i = 0; i < 100; i++){
                g.drawImage(asteroidExplosion, (int)super.x, (int)super.y, 
                    75, 75, null);
            }
            this.destroy();
        }
        else{
            g.drawImage(ship, (int)super.x, (int)super.y, 
                75, 75, null);
        }
    }

    @Override
    public void update() {
        if (backUp){
            movementBackUp();
        }
        else if(backDown){
            movementBackDown();
        }
        else if(forwardUp){
            movementForwardUp();
        }
        else{
            movementForwardDown();
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        return new Rectangle2D.Double(super.x, super.y, 65, 65);
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
    // destruction animation and point addition to gary
        Main.gameData.points += super.points;
        super.state = STATE_DONE;
    }

    @Override
    public int getPoints() {return super.points;}

    @Override
    public void setPoints(int e) {
        super.points = e;
    }

    @Override
    public int getHealth() {return super.health;}

    @Override
    public void setHealth(int e) {
        super.health = e;
    }

    @Override
    public boolean perfectHit() {
        return super.perfectHit;
    }

    @Override
    public void setPerfectHit(boolean e) {
        super.perfectHit = e;
    }

    @Override
    public void movementForward() {}

    @Override
    public void movementBackUp() {
        super.x += (super.movementSpeed * -1);
        super.y += (super.movementSpeed);
        backUp = true;
        if (super.x > 1200 || super.y < 0){
            super.points = 0;
            super.state = STATE_DONE;
        }
    }

    @Override
    public void movementBackDown() {
        super.x += (super.movementSpeed * -1);
        super.y += (super.movementSpeed * -1);
        backDown = true;
        if (super.x > 1200 || super.y > Main.WIN_HEIGHT - 50){
            super.points = 0;
            super.state = STATE_DONE;
        }
    }

    @Override
    public void movementForwardUp() {
        super.x += (super.getMovementSpeed());
        super.y += (super.getMovementSpeed());
        forwardUp = true;
        if (super.x < 0 || super.y < 0){
            super.points = 0;
            super.state = STATE_DONE;
        }
    }

    @Override
    public void movementForwardDown() {
        super.x += (super.getMovementSpeed());
        super.y += (super.getMovementSpeed() * -1);
        forwardDown = true;
        if (super.x > 1200 || super.y > Main.WIN_HEIGHT - 50){
            super.points = 0;
            super.state = STATE_DONE;
        }
    }

    @Override
    public Rectangle2D getPerfectHit() {return null;}

    public void setDirection(int i) {
        this.direction = i;
    }
}