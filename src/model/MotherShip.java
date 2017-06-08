package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import view.GamePanel;

public class MotherShip extends GameFigure {
    private int direction = 1;
    private Image ship, shipSpawn, explosion;
    private int spawnCount;
    private long count, count2;
    
    MotherShip(float x, float y) {
        super(x, y);
        super.movementSpeed = (int) (2 + .4 * super.level);
        super.health = 5;
        super.points = 65 + (super.level * 11);
        super.level = Main.gameData.level;
        super.state = STATE_ALIVE;
        ship = null;
        shipSpawn = null;
        
        try {
            if (super.level < 5){
                ship = ImageIO.read(getClass().getResource("MotherShip1.png"));
                shipSpawn = ImageIO.read(getClass().getResource("MotherShipSpawn.png"));
                explosion = ImageIO.read(getClass().getResource("MotherShipExplosion5.png"));
            }
            else if (super.level >= 5 && super.level < 10){
                ship = ImageIO.read(getClass().getResource("MotherShip2.png"));
                shipSpawn = ImageIO.read(getClass().getResource("MotherShipSpawn2.png"));
                explosion = ImageIO.read(getClass().getResource("MotherShipExplosion1.png"));
            }
            else if (super.level >= 10 && super.level < 15){
                ship = ImageIO.read(getClass().getResource("MotherShip3.png"));
                shipSpawn = ImageIO.read(getClass().getResource("MotherShipSpawn3.png"));
                explosion = ImageIO.read(getClass().getResource("MotherShipExplosion3.png"));
            }
            else if (super.level >= 15 && super.level < 20){
                ship = ImageIO.read(getClass().getResource("MotherShip4.png"));
                shipSpawn = ImageIO.read(getClass().getResource("MotherShipSpawn4.png"));
                explosion = ImageIO.read(getClass().getResource("MotherShipExplosion2.png"));
            }
            else{
                ship = ImageIO.read(getClass().getResource("MotherShip5.png"));
                shipSpawn = ImageIO.read(getClass().getResource("MotherShipSpawn5.png"));
                explosion = ImageIO.read(getClass().getResource("MotherShipExplosion4.png"));
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open GalacticGaryShipFinal.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
        if(super.state == STATE_ALIVE){
            if (super.y > Main.WIN_HEIGHT - 100){
                g.drawImage(ship, (int)super.x - 200, (int)super.y + 650, 
                    150, 100, null);
            }
            else{
                g.drawImage(ship, (int)super.x - 200, (int)super.y + 800, 
                    150, 100, null);
            }
        }
        else if(super.state == STATE_SPAWN){
            if (super.y > Main.WIN_HEIGHT - 100){
                g.drawImage(shipSpawn, (int)super.x - 200, (int)super.y + 650, 
                    150, 200, null);
            }
            else{
                g.drawImage(shipSpawn, (int)super.x - 200, (int)super.y + 800, 
                    150, 200, null);
            }
        }
        else if(super.state == STATE_DYING){
            for (int i = 100; i > 0; i--){
                if (super.y > Main.WIN_HEIGHT - 100){
                g.drawImage(explosion, (int)super.x - 200, (int)super.y + 650, 
                    150, 120, null);
                }
                else{
                    g.drawImage(explosion, (int)super.x - 200, (int)super.y + 800, 
                        150, 120, null);
                }
            }
            this.destroy();
        }
    }
    
//animation
    @Override
    public void update() {
//        synchronized (Main.gameData.enemyFigures){
            //movement up and down
        if (super.state == STATE_SPAWN && spawnCount < 5){
            spawn();
        }
        else if(super.state == STATE_ALIVE){
            if (direction > 0) {
                super.y += super.movementSpeed;
                if (super.y + 950 > Main.WIN_HEIGHT) {
                    direction = -1;
                }
            } else {
                super.y -= super.movementSpeed;
                if (super.y - super.movementSpeed + 800 <= 0) {
                    direction = 1;
                }
            }
            count++;
            // && super.y + 650 < Main.WIN_HEIGHT)
            if ((count >= 200 - (2 * super.level)) && super.y + 650 < Main.WIN_HEIGHT){
                count = 0;
                super.state = STATE_SPAWN;
                spawn();
            }
        }
        else{}
    }

    //subtracted size of render and both collision boxes to put image inside of frame
    @Override
    public Rectangle2D getCollisionBox() {
        if (super.y > Main.WIN_HEIGHT - 100){
            return new Rectangle2D.Double(super.x - 200, super.y + 650, 100, 66);
        }
        else{
            return new Rectangle2D.Double(super.x - 200, super.y + 800, 100, 66);
        }
    }

    @Override
    public void addDead() {}

    @Override
    public void movement(int movementSpeed) {
        
    }

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
        return new Rectangle2D.Double(super.x - 201, super.y + 686, 100, 33);
    }

    @Override
    public void fire() {}
    
    public void spawn(){
        if(count2 > 1200){
            MSSubCraft sub = new MSSubCraft(100, 1200);
            sub.state = STATE_ALIVE;
            Main.gameData.enemySubCraftFigures.add(sub);
            super.state = STATE_ALIVE;
            spawnCount++;
            count = 0;
            count2 = 0;
        }
        else{
            count2++;
        }
    }
}
