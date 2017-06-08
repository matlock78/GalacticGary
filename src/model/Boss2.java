package model;

import controller.Main;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_DONE;
import static model.GameFigure.STATE_DYING;
import static model.GameFigure.STATE_ULTIMATE;
import view.GamePanel;


public class Boss2 extends GameFigure{
    public int interval = 1;
    public int ultCount = 0;
    public int count = 0;
    public float dx, dy;
    public int timeCount = 0;
    public int ultLaunch = 0;
    public int delayCount = 0;
    public int hiddenCount = 0;
    public boolean delay = false;
    public int randomInt;
    private Image bodyFlat, bodyUp, bodyDown;
    private Image headFlat, headUp, headDown;
    private Image afterBurnerOneUp, afterBurnerOneDown, afterBurnerOneFlat;
    private Image afterBurnerTwoUp, afterBurnerTwoDown, afterBurnerTwoFlat;
    private Image explosion, explosionReplaced;
    public boolean up, down, left, right, change, destinationReached;
    //we will use the displacement as a destination point for each segment during ult
    public int displacement;
    public int next, waitCount, direction;

    public Boss2(int x, int y){
        super(x, y);
        super.health = 1;
        super.movementSpeed = 5;
        super.state = STATE_ALIVE;
        headFlat = null;
        afterBurnerOneFlat = null;
        explosion = null;
        up = true;
        down = false;
        left = false;
        right = false;
        super.replace = false;
        change = false;
        //next: 0 = up, 1 = left, 2 = down, 3 = right
        next = 0;
        waitCount = 10;
        destinationReached = false;
        
        try {
            explosion = ImageIO.read(getClass().getResource("EnemyShipExplosion.png"));
            explosionReplaced = ImageIO.read(getClass().getResource("EnemyShipExplosion.png"));
                        
            headFlat = ImageIO.read(getClass().getResource("head.flat.png"));
            headUp = ImageIO.read(getClass().getResource("head.up.png"));
            headDown = ImageIO.read(getClass().getResource("head.down.png"));
            
            bodyFlat = ImageIO.read(getClass().getResource("boss2.body.flat.png"));
            bodyUp = ImageIO.read(getClass().getResource("boss2.body.up.png"));
            bodyDown = ImageIO.read(getClass().getResource("boss2.body.up.png"));
            
            afterBurnerOneFlat = ImageIO.read(getClass().getResource("Afterburner1.flat.png"));
            afterBurnerOneUp = ImageIO.read(getClass().getResource("Afterburner1.up.png"));
            afterBurnerOneDown = ImageIO.read(getClass().getResource("Afterburner3.png"));
            
            afterBurnerTwoFlat = ImageIO.read(getClass().getResource("Afterburner2.flat.png"));
            afterBurnerTwoUp = ImageIO.read(getClass().getResource("Afterburner2.up.png"));
            afterBurnerTwoDown = ImageIO.read(getClass().getResource("Afterburner4.png"));
            
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Error: Cannot open AlienShip1.1.png");
            System.exit(-1);
        }
    }

    @Override
    public void render(Graphics2D g) {
       if (super.replace){
           //draw a new head
            if(super.state == STATE_DYING){
                for (int j = 0; j < 200; j++){
                    if (next == 0 || next == 1){
                        g.drawImage(explosion, (int)super.x, (int)super.y, 
                        -100, 100, null);
                    }
                    else{
                        g.drawImage(explosion, (int)super.x - 150, (int)super.y - 100, 
                        100, 100, null);
                    }
                }
                this.destroy();
            }
            else if (super.state == STATE_ULTIMATE){
                g.drawImage(headFlat, (int)super.x, (int)super.y, 
                        150, 100, null);
            }
            else{
                //head
                if (next == 3){
                    g.drawImage(headUp, (int)super.x, (int)super.y, 
                        150, 100, null);
                }
                else if (next == 0){
                    g.drawImage(headDown, (int)super.x, (int)super.y, 
                        150, 120, null);
                }
                else if (next == 1){
                    g.drawImage(headDown, (int)super.x, (int)super.y, 
                        -150, 120, null);
                }
                else if (next == 2){
                    g.drawImage(headUp, (int)super.x, (int)super.y, 
                        -150, 100, null);
                }
            }
            if((delayCount % 90000000) >= 0){
                delay = !delay;
                delayCount = 0;
            }
        }
        else{
           //draw another body
            if(super.state == STATE_DYING){
                for (int j = 0; j < 200; j++){
                    if (next == 0 || next == 1){
                        g.drawImage(explosionReplaced, (int)super.x, (int)super.y, 
                            100, 100, null);
                    }
                    else{
                        g.drawImage(explosionReplaced, (int)super.x, (int)super.y, 
                            -100, 100, null);
                    }
                }
                this.destroy();
            }
            else if (super.state== STATE_ULTIMATE){
                g.drawImage(bodyFlat, (int)super.x, (int)super.y, 
                        100, 100, null);
                    if (delay){
                        g.drawImage(afterBurnerOneFlat, (int)super.x - 130, (int)super.y - 35, 
                            75, 15, null);
                    }
                    else{
                        g.drawImage(afterBurnerTwoFlat, (int)super.x - 130, (int)super.y - 35, 
                            -75, 15, null);
                    }
            }
            else{
                if (next == 0){
                    direction = 1;
                    g.drawImage(bodyUp, (int)super.x - 75, (int)super.y - 50, 
                        -100, 50, null);
                    if (delay){
                        g.drawImage(afterBurnerOneUp, (int)super.x - 130, (int)super.y - 35, 
                            -75, 15, null);
                    }
                    else{
                        g.drawImage(afterBurnerTwoUp, (int)super.x - 130, (int)super.y - 35, 
                            -75, 15, null);
                    }
                }
                else if (next == 1){
                    direction = 1;
                    g.drawImage(bodyDown, (int)super.x, (int)super.y, 
                        100, 50, null);
                    if (delay){
                        g.drawImage(afterBurnerOneDown, (int)super.x - 20, (int)super.y - 35, 
                            75, 15, null);
                    }
                    else{
                        g.drawImage(afterBurnerTwoDown, (int)super.x - 20, (int)super.y - 35, 
                            75, 15, null);
                    }
                }
                else if (next == 2){
                    direction = 0;
                    g.drawImage(bodyDown, (int)super.x - 75, (int)super.y - 50, 
                        -100, 50, null);
                    if (delay){
                        g.drawImage(afterBurnerOneDown, (int)super.x - 20, (int)super.y - 35, 
                            -75, 15, null);
                    }
                    else{
                        g.drawImage(afterBurnerTwoDown, (int)super.x - 20, (int)super.y - 35, 
                            -75, 15, null);
                    }
                }
                else if (next == 3){
                    direction = 0;
                    g.drawImage(bodyUp, (int)super.x - 75, (int)super.y - 50, 
                        -100, 50, null);
                    if (delay){
                        g.drawImage(afterBurnerOneUp, (int)super.x - 20, (int)super.y - 35, 
                            -75, 15, null);
                    }
                    else{
                        g.drawImage(afterBurnerTwoUp, (int)super.x - 20, (int)super.y - 35, 
                            -75, 15, null);
                    }
                }   
            }
            if((delayCount % 90000000) >= 0){
                delay = !delay;
                delayCount = 0;
            }
        }
    }

    @Override
    public void update() {
        if (super.replace){
            if(super.state == STATE_DYING){}
            else if(super.state == STATE_ULTIMATE){
                if (super.x >= 1000 && super.x <= 1050){
                    direction = 1;
                    ultAttack();
                } 
                else{
                    //move into position to launch ult
                    if (super.x > 1050){
                        super.x -= 15;
                        direction = 1;
                    }
                    else if(super.x < 1000){
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
                if (up){
                    //placement is currently at the up position
                    if (super.x <= 350 && super.y > 325){
                        if (waitCount >= 3){
                            up = false;
                            left = true;
                            next = 1;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                        dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y + dy < (Main.gamePanel.height / 2)){
                            super.y += dy;
                        }
                        if (super.x - dx > 300){
                            super.x -= dx;
                        } 
                    }
                }
                else if (left){
                    if (super.x >= 575 && super.y > 550){
                        if (waitCount >= 5){
                            left = false;
                            down = true;
                            next = 2;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                        dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y + dy < Main.gamePanel.height - 100){
                            super.y += dy;
                        }
                        if (super.x + dx < (Main.gamePanel.width / 2)){
                            super.x += dx;
                        }
                    }
                }
                else if (down){
                    if (super.x >= 1075 && super.y < 375){
                        if (waitCount >= 5){
                            down = false;
                            right = true;
                            next = 3;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                        dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y - dy > Main.gamePanel.height / 2){
                            super.y -= dy;
                        }
                        if (super.x + dx < (Main.gamePanel.width - 100)){
                            super.x += dx;
                        }
                    }
                }
                else if (right){
                    if (super.x <= 650 && super.y < 50){
                        if (waitCount == 5){
                            right = false;
                            up = true;
                            next = 0;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                        dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y - dy > 0){
                            super.y -= dy;
                        }
                        if (super.x - dx > (Main.gamePanel.width / 2)){
                            super.x -= dx;
                        }
                    }
                }

                timeCount++;
                ultCount++;
            }
        }
        
        
        
        else{
            //is a subsegment of the boss
            if(super.state == STATE_DYING){}
            else if(super.state == STATE_ULTIMATE){
                //move into position to launch ult
                if (super.x >= displacement + 15 || super.x >= displacement - 15){
                    super.x -= 15;
                    direction = 1;
                }
                else if(super.x <= displacement + 15 || super.x <= displacement - 15){
                    super.x += 15;
                    direction = 0;
                }
                else{
                    if(super.y + 75 >= GamePanel.height){
                        dy = -dy;
                    }
                    else if(super.y - 15 <= 0){
                        dy = -dy;
                    }
                    else{}
                    super.y += dy;
                }
                ultCount++;
                int k = 0;
                for (GameFigure enemyFigure : Main.gameData.enemyFigures) {
                    if (enemyFigure.state == STATE_ALIVE && enemyFigure.replace == true) {
                        k++;
                    }
                }
                if (k > 0){
                    super.state = STATE_ALIVE;
                }
            }
            else{
                if (up){
                    //placement is currently at the up position
                    if (super.x <= 300 && super.y > 325){
                        if (waitCount == 5){
                            up = false;
                            left = true;
                            next = 1;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                        dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y + dy < (Main.gamePanel.height / 2)){
                            super.y += dy;
                        }
                        if (super.x - dx > 200){
                            super.x -= dx;
                        }
                    }
                }
                else if (left){
                    if (super.x >= 550 && super.y > 550){
                        if (waitCount >= 5){
                            left = false;
                            down = true;
                            next = 2;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                        dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y + dy < Main.gamePanel.height - 100){
                            super.y += dy;
                        }
                        if (super.x + dx < (Main.gamePanel.width / 2)){
                            super.x += dx;
                        }
                    }
                }
                else if (down){

                    if (super.x >= 1075 && super.y < 375){
                        if (waitCount >= 5){
                            down = false;
                            right = true;
                            next = 3;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                        dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y - dy > Main.gamePanel.height / 2){
                            super.y -= dy;
                        }
                        if (super.x + dx < (Main.gamePanel.width - 100)){
                            super.x += dx;
                        }
                    }
                }
                else if (right){
                    if (super.x <= 650 && super.y < 50){
                        if (waitCount == 5){
                            right = false;
                            up = true;
                            next = 0;
                            waitCount = 0;
                        }
                        else{
                            waitCount++;
                        }
                    }
                    else{
                                            dx = (float) (super.movementSpeed * 1.58);
                        dy = (float)super.movementSpeed;
                        if (super.y - dy > 0){
                            super.y -= dy;
                        }
                        if (super.x - dx > (Main.gamePanel.width / 2)){
                            super.x -= dx;
                        }
                    }
                }

                timeCount++;
                ultCount++;
            }
            count++;
            
            if (ultCount >= 20){
                super.state = STATE_ULTIMATE;
            }
        }
    }

    @Override
    public void fire() {}

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
    public void movementForward() {}

    @Override
    public void movementBackUp() {}

    @Override
    public void movementBackDown() {
        if (next - interval >= 0){
            next -= interval;
            interval++;            
        }
        else if(next + interval <= 3){
            next+= interval;
            interval--;
        }
        else if(next - 1 >= 0){
            next--;
        }
        else{
            next++;
        }
        
        if (next == 0){
            up = true;
            left = false;
            down = false;
            right = false;
        }
        else if (next == 1){
            up = false;
            left = true;
            down = false;
            right = false;
        }
        else if (next == 2){
            up = false;
            left = false;
            down = true;
            right = false;
        }
        else if (next == 3){
            up = false;
            left = false;
            down = false;
            right = true;
        }
   }

    @Override
    public void movementForwardUp() {}

    @Override
    public void movementForwardDown() {}

    @Override
    public Rectangle2D getPerfectHit() {
        return null;
}
    
    public void ultAttack(){
        //Camoflauges himself and launches a volley of comets
        // moves fast 50 pixels
        Boss2Ultimate ult1 = new Boss2Ultimate(this.x - 40, this.y + 100);
        Main.gameData.enemySubCraftFigures.add(ult1);
        super.state = STATE_ALIVE;
        ultCount = 0;
        change = true;
        waitCount = 0;
        
    } 
} 
