package model;

import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public abstract class GameFigure implements CollisionBox {

    // public for a faster access during animation
    public float x;
    public float y;
    
    public int state;
    public static final int STATE_ALIVE = 1;
    public static final int STATE_DYING = 2;
    public static final int STATE_DONE = 0;
    public static final int STATE_COOLDOWN = 3;
    public static final int STATE_SPAWN = 4;
    public static final int STATE_HIT = 5;
    public static final int STATE_ULTIMATE = 6;
    public static final int STATE_FIRED = 7;
    public static final int STATE_ULTIMATE2 = 8;
    public static final int STATE_ULTIMATE3 = 9;
    public static final int STATE_ULTIMATE4 = 10;
    public static final int STATE_ULTIMATE5 = 11;
    public int movementSpeed, points, health;
    public Point2D.Double currentLocation, origin;
    public boolean perfectHit = false;
    public int level;
    public boolean fired = false;
    public boolean miss;
    public boolean replace;
    
    public GameFigure(float x, float y) {
        
        this.x = x;
        this.y = y;
    }

    // how to render on the canvas
    // use render instead of paint makes for quicker updates to image
    public abstract void render(Graphics2D g);

    // changes per frame
    public abstract void update();

    public abstract void fire();
    
    @Override
    public abstract Rectangle2D getCollisionBox();

    public abstract void addDead();
    
    public abstract void movement(int movementSpeed);
    
    public abstract int getMovementSpeed();
    
    public abstract void setMovementSpeed(int s);
    
    public abstract boolean isdestroyed();
    
    public abstract void destroy();
    
    public abstract int getPoints();
    
    public abstract void setPoints(int e);

    public abstract int getHealth();
    
    public abstract void setHealth(int e);
    
    public abstract boolean perfectHit();
    
    public abstract void setPerfectHit(boolean e);  
    
    public abstract void movementForward();
    
    public abstract void movementBackUp();
    
    public abstract void movementBackDown();
    
    public abstract void movementForwardUp();
    
    public abstract void movementForwardDown();
}
