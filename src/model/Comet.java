package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;


class Comet extends GameFigure {
private int direction = 1;
private Image ship, ship2, cometExplosion;
public int explosionCount, shipCount;

    Comet(int x, int y) {
        super(x, y);
        super.state = STATE_ALIVE;
        super.health = 2;
        super.points = 450 + (super.level * 11);
        super.level = Main.gameData.level;
        super.movementSpeed = (int) (-10 - .8 * level);
        super.state = STATE_ALIVE;
        ship = null;
        cometExplosion = null;
        int shipCount = 0;
        
        try {
            if (super.level < 5){
                ship = ImageIO.read(getClass().getResource("Comet1.png"));
                ship2 = ImageIO.read(getClass().getResource("Comet1.5.png"));
//                cometExplosion = ImageIO.read(getClass().getResource("Asteroid Explosion4.png"));
                cometExplosion = ImageIO.read(getClass().getResource("HExplosion4.2.png"));
                }
                else if (super.level >= 5 && super.level < 10){
                ship = ImageIO.read(getClass().getResource("Comet2.png"));
                ship2 = ImageIO.read(getClass().getResource("Comet2.5.png"));
//                cometExplosion = ImageIO.read(getClass().getResource("Asteroid Explosion2.png"));
                cometExplosion = ImageIO.read(getClass().getResource("HExplosion1.2.png"));
                }
                else if (super.level >= 10 && super.level < 15){
                ship = ImageIO.read(getClass().getResource("Comet3.png"));
                ship2 = ImageIO.read(getClass().getResource("Comet3.5.png"));
//                cometExplosion = ImageIO.read(getClass().getResource("Asteroid Explosion5.png"));
                cometExplosion = ImageIO.read(getClass().getResource("HExplosion2.2.png"));
                }
                else if (super.level >= 15 && super.level < 20){
                ship = ImageIO.read(getClass().getResource("Comet4.png"));
                ship2 = ImageIO.read(getClass().getResource("Comet4.5.png"));
//                cometExplosion = ImageIO.read(getClass().getResource("Asteroid Explosion3.png"));
                cometExplosion = ImageIO.read(getClass().getResource("HExplosion3.2.png"));
                }
                else{
                ship = ImageIO.read(getClass().getResource("Comet5.png"));
                ship2 = ImageIO.read(getClass().getResource("Comet5.5.png"));
//                cometExplosion = ImageIO.read(getClass().getResource("Asteroid Explosion1.png"));
                cometExplosion = ImageIO.read(getClass().getResource("HExplosion5.2.png"));
                }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open Comet.png");
            System.exit(-1);
        }
    }

    @Override
    public void movement(int movementSpeed) {
    }

    @Override
    public void render(Graphics2D g) {
        if(super.state == STATE_DYING){
            if(explosionCount > 1){
                this.destroy();
            }
            else /*if (explosionCount > 0)*/{
                if (super.y > Main.WIN_HEIGHT - 75){
                g.drawImage(cometExplosion, (int)super.x + 400, (int)super.y - 75, 
                    150, 100, null);
                }
                else{
                    g.drawImage(cometExplosion, (int)super.x + 400, (int)super.y, 
                        150, 100, null);
                }
                explosionCount++;
            }
            /*
            else{
                                if (super.y > Main.WIN_HEIGHT - 75){
                g.drawImage(cometExplosion, (int)super.x + 425, (int)super.y - 60,
                50, 50, null);
                }
                else{
                g.drawImage(cometExplosion, (int)super.x + 425, (int)super.y + 15,
                50, 50, null);
                }
                explosionCount++;
            }
            */
        }
        else{
            if (shipCount > 4){
                if (super.y > Main.WIN_HEIGHT - 75){
                    g.drawImage(ship, (int)super.x + 400, (int)super.y - 75, 
                        200, 50, null);
                }
                else{
                    g.drawImage(ship, (int)super.x + 400, (int)super.y, 
                        200, 50, null);
                }
                if (shipCount > 9){
                    shipCount = 0;
                }
                else{
                    shipCount++;
                }
            }
            else{
                if (super.y > Main.WIN_HEIGHT - 75){
                    g.drawImage(ship2, (int)super.x + 400, (int)super.y - 75, 
                        200, 50, null);
                }
                else{
                    g.drawImage(ship2, (int)super.x + 400, (int)super.y, 
                        200, 50, null);
                }
                shipCount++;
            }
        }
    }

    @Override
    public void update() {
        if (super.state == STATE_DYING){}
        else{
            super.x -= 15;
            if (super.x + 600 < 0) {
                super.points = 0;
                super.state = STATE_DONE;
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        if (super.y > Main.WIN_HEIGHT - 75){
            return new Rectangle2D.Double(super.x + 400, super.y - 75, 75, 75);
        }
        else{
            return new Rectangle2D.Double(super.x + 400, super.y, 75, 75);
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
        if (super.y > Main.WIN_HEIGHT - 75){
            return new Rectangle2D.Double(super.x + 400, super.y - 75 + 17, 75, 75);
        }
        else{
            return new Rectangle2D.Double(super.x + 400, super.y + 17, 75, 75);
        }
    }

    @Override
    public void fire() {}
    
}
