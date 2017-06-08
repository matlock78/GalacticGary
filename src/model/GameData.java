package model;

import controller.Main;
import static java.lang.System.exit;
import view.GamePanel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import static model.GameFigure.STATE_ALIVE;

public class GameData {

    public List<GameFigure> enemyFigures;
    public List<GameFigure> friendFigures;
    public List<GameFigure> enemyLaserFigures;
    public List<GameFigure> enemySubCraftFigures;
    public List<GameFigure> friendLaserFigures;
//    public List<GameFigure> backgroundFigures;
    public Background background;
    public Gary gary;
    public Boss1 boss1;
    public Boss2 boss2;
    public Boss3 boss3;
    public Boss4 boss4;
    public Boss5 boss5;
    public PointSystem pointSystem;
    public int level = 1;
    public int enemyCount = level + 4;
    public int randomInt;
    public int temp;
    public int lives = 2;
    private long count;
    private int levelDelay;
    public int points = 0;
    public int lifeCount = 1;
    public int alienShipCount = 0;
    public int alienShipLocationX;
    public int alienShipLocationY;

    public GameData() {
        enemyFigures = Collections.synchronizedList(new ArrayList<GameFigure>());
        friendFigures = Collections.synchronizedList(new ArrayList<GameFigure>());
        enemyLaserFigures = Collections.synchronizedList(new ArrayList<GameFigure>());
        enemySubCraftFigures = Collections.synchronizedList(new ArrayList<GameFigure>());
        friendLaserFigures = Collections.synchronizedList(new ArrayList<GameFigure>());
        //backgroundFigures = Collections.synchronizedList(new ArrayList<GameFigure>());
        // GamePanel.width, height are known when rendered. 
        // Thus, at this moment,
        // we cannot use GamePanel.width and height.
        points = 0;
        lives = 2;
        level = 1;
        background = new Background();
        gary = new Gary(0, Main.WIN_HEIGHT / 2);
        pointSystem = new PointSystem(points, lives, level);
        friendFigures.add(gary);
    }

    public void add(int n) {
        levelDelay = 0;
        for( int i = 0; i < level + 5; i++){
            if (count > 600){
                generateRandomEnemy();
                count = 0;
                update();
            }
            else{
                i--;
            }
            count++;
        }
    }

    public void generateRandomEnemy() {
        Random randomNumber = new Random();
        randomInt = (int)((randomNumber.nextDouble() * 10) + 4);
//        randomInt = 7;
        if (randomInt == 5){
                enemyFigures.add( new MotherShip(GamePanel.width - 50, (int) (Math.random() * GamePanel.height)));
        }
        else if(randomInt == 6){
                enemyFigures.add( new Asteroid((int)(Math.random() * GamePanel.width + 1200), (int) (Math.random() * GamePanel.height)));
        }
        else if (randomInt == 7){
                enemyFigures.add( new Planet((int)(Math.random() * GamePanel.width + 1100), (int) (Math.random() * GamePanel.height)));
        }
        else if(randomInt == 8){
                enemyFigures.add( new Comet((int)(Math.random() * GamePanel.width + 1200), (int) (Math.random() * GamePanel.height)));
        }
        else {
                if (alienShipCount == 0){
                    alienShipLocationX = (int)(Math.random() * GamePanel.width + 1200);
                    alienShipLocationY = (int) (Math.random() * GamePanel.height);
                }
                //enemyFigures.add( new AlienShip(GamePanel.width, (int) (Math.random() * GamePanel.height)));
            enemyFigures.add( new AlienShip(alienShipLocationX + (alienShipCount * 85), alienShipLocationY + (alienShipCount * 60)));
            alienShipCount++;
            if (alienShipCount == 3){
                alienShipCount = 0;
            }
        }
    }

    public void update() {
        background.translate();
        pointSystem.update(points, lives, level);
 //       synchronized (enemyFigures) {
            ArrayList<GameFigure> remove = new ArrayList<>();

            GameFigure f;
            for (GameFigure enemyFigure : enemyFigures) {
                f = enemyFigure;
                if (f.state == GameFigure.STATE_DONE) {
                    f.destroy();
                    remove.add(f);
                }
                else if(f instanceof MotherShip){
                    if (f.state == GameFigure.STATE_SPAWN){
                        if (f.y > Main.WIN_HEIGHT - 100){
                            MSSubCraft sub = new MSSubCraft(f.x - 150, f.y);
                            Main.gameData.enemySubCraftFigures.add(sub);
                        }
                        else{
                            MSSubCraft sub = new MSSubCraft(f.x - 150, f.y);
                            Main.gameData.enemySubCraftFigures.add(sub);
                        }
                        f.state = GameFigure.STATE_ALIVE;
                    }
                }
            }
            for (GameFigure remove1 : remove) {
                this.points += remove1.points;
//                remove.get(i).addDead();
            }
            enemyFigures.removeAll(remove);

            for (GameFigure g : enemyFigures) {
                g.update();
            }
   //     }
        
//        synchronized (enemyLaserFigures) {
//            ArrayList<GameFigure> remove = new ArrayList<>();
 //           GameFigure f;
             for (GameFigure friendLaserFigure : friendLaserFigures) {
                f = friendLaserFigure;
                if (f.state == GameFigure.STATE_DONE) {
                    f.destroy();
                    remove.add(f);
                }
            }
            friendLaserFigures.removeAll(remove);

            for (GameFigure g : friendLaserFigures) {
                g.update();
            }
            
            for (GameFigure enemyLaserFigure : enemyLaserFigures) {
                f = enemyLaserFigure;
                if (f.state == GameFigure.STATE_DONE) {
                    f.destroy();
                    remove.add(f);
                }
            }
            enemyLaserFigures.removeAll(remove);

            for (GameFigure g : enemyLaserFigures) {
                g.update();
            }
  //     }
        
        //synchronized (enemySubCraftFigures) {
//            ArrayList<GameFigure> remove = new ArrayList<>();
//            GameFigure f;
//            for (int i = 0; i < enemySubCraftFigures.size(); i++) {
//                f = enemySubCraftFigures.get(i);
            for (GameFigure enemySubCraftFigure : enemySubCraftFigures) {
                f = enemySubCraftFigure;
                if (f.state == GameFigure.STATE_DONE) {
                    f.destroy();
                    remove.add(f);
                }
            }
            for (GameFigure remove1 : remove) {
                this.points += remove1.points;
                //remove.get(i).addDead();
            }
            enemySubCraftFigures.removeAll(remove);

            for (GameFigure g : enemySubCraftFigures) {
                g.update();
            }
       //}
      
//        synchronized (friendFigures) {
  //          ArrayList<GameFigure> remove = new ArrayList<>();
    //        GameFigure f;
            //increase lives
            if (lifeCount < 3){
                if (points / (lifeCount * 50000) > 1){
                    lives++;
                    lifeCount++;
                }
            }
            else{
                if (points / (lifeCount * 100000) > 1){
                    lives++;
                    lifeCount++;
                }
            }
            
            for (int i = 0; i < friendFigures.size(); i++) {
                f = friendFigures.get(i);
                if (f.state == GameFigure.STATE_DONE) {
                    if (f instanceof Gary){
                        if (lives > 0){
                            f.destroy();
                            lives--;
                            gary = new Gary(0, Main.WIN_HEIGHT / 2);
                            friendFigures.add(gary);
                        }
                        else{
                            //display game Over
                            f.destroy();
                        }
                    }
                    else{
                        f.destroy();
                    }
                    remove.add(f);
                }
            }
            friendFigures.removeAll(remove);

            for (GameFigure g : friendFigures) {
                g.update();
            }
      //  }
        
        if(enemyFigures.isEmpty() && enemyLaserFigures.isEmpty()){
            // display level screen
            //use a while loop to keep from transitioning between levels for cutscenes
            
            count = 0;
            if (level > 30){
                exit(0);
            }
            if ((levelDelay > 80)){
                level++;
                if (level == 5){
                    boss1 = new Boss1(1100, 400);
                    enemyFigures.add(boss1);
                }
                else if(level == 11){
                    int displacement = 100;
                    boss2 = new Boss2(1100, 400);
                    boss2.replace = true;
                    boss2.right = true;
                    boss2.left = false;
                    boss2.up = false;
                    boss2.down = false;
                    enemyFigures.add(boss2);
                    for (int i = 1; i < 11; i++){
                        boss2 = new Boss2(1100 + (displacement * i), 400);
                        boss2.displacement = displacement * i;
                        boss2.replace = false;
                        boss2.right = true;
                        boss2.left = false;
                        boss2.up = false;
                        boss2.down = false;
                        boss2.state = STATE_ALIVE;
                        enemyFigures.add(boss2);
                    } 
                }
                else if(level == 17){
                    boss3 = new Boss3(1100, 400);
                    enemyFigures.add(boss3);
                }
                else if(level == 23){
                    boss4 = new Boss4(1100, 400);
                    enemyFigures.add(boss4);
                }
                else if(level == 30){
                    boss5 = new Boss5(1100, 400);
                    enemyFigures.add(boss5);
                }
                
                else{
                    add(level);
                }
            }
            else{ 
                //gary.levelDisplay = true;
                levelDelay++;
            }
            //boss level
        }
    }
}