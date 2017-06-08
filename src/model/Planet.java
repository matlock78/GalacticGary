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


class Planet extends GameFigure {
    private int direction = 1;
    private Image ship, explosion;
    
    public Planet(int x, int y) {
        super(x, y);
        super.movementSpeed = (int) (-1 - .4 * super.level);
        super.health = 10;
        super.points = 1000 + (super.level * 11);
        super.level = Main.gameData.level;
        super.state = STATE_ALIVE;
        ship = null;
        
        try {
            if (super.level < 5){
                ship = ImageIO.read(getClass().getResource("Planet5.png"));
                explosion = ImageIO.read(getClass().getResource("PlanetExplosion5.png"));
            }
                else if (super.level >= 5 && super.level < 10){
                ship = ImageIO.read(getClass().getResource("Planet6.png"));
                explosion = ImageIO.read(getClass().getResource("PlanetExplosion2.png"));
                }
                else if (super.level >= 10 && super.level < 15){
                ship = ImageIO.read(getClass().getResource("Planet7.png"));
                explosion = ImageIO.read(getClass().getResource("PlanetExplosion1.png"));
                }
                else if (super.level >= 15 && super.level < 20){
                ship = ImageIO.read(getClass().getResource("Planet8.png"));
                explosion = ImageIO.read(getClass().getResource("PlanetExplosion4.png"));
                }
                else{
                ship = ImageIO.read(getClass().getResource("Planet6.png"));
                explosion = ImageIO.read(getClass().getResource("PlanetExplosion3.png"));
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
            for (int i = 0; i < 200; i++){
                if (super.y > Main.WIN_HEIGHT - 300){
                    g.drawImage(explosion, (int)super.x - 400, (int)super.y - 300, 
                        400, 300, null);
                }
                else{
                    g.drawImage(explosion, (int)super.x - 400, (int)super.y, 
                        400, 300, null);
                }
            }
            this.destroy();
        }
        else{
            if (super.y > Main.WIN_HEIGHT - 300){
                g.drawImage(ship, (int)super.x - 400, (int)super.y - 300, 
                    400, 300, null);
            }
            else{
                g.drawImage(ship, (int)super.x - 400, (int)super.y, 
                    400, 300, null);
            }
        }
    }

    @Override
    public void update() {
        if (super.state == STATE_DYING){}
        else{
            super.x -= 1;
            if (super.x - 125 <= 0) {
                super.state = STATE_DONE;
            }
        }
    }

    @Override
    public Rectangle2D getCollisionBox() {
        if (super.y > Main.WIN_HEIGHT - 300){
            return new Rectangle2D.Double(super.x - 250, super.y - 300, 200, 250);
        }
        else{
            return new Rectangle2D.Double(super.x - 250, super.y, 200, 250);
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
        if (super.y > Main.WIN_HEIGHT - 300){
            return new Rectangle2D.Double(super.x - 250, super.y - 150 + 75, 300, 50);
        }
        else{
            return new Rectangle2D.Double(super.x - 250, super.y + 75, 300, 50);
        }
    }

    @Override
    public void fire() {}
}