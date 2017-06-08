package controller;

import model.Asteroid;
import model.Boss1;

import model.GameFigure;
import model.Gary;
import model.Laser;
import model.Boss2;


public class Animator implements Runnable {

    public boolean running = true;
    private final int FRAMES_PER_SECOND = 20;


    @Override
    public void run() {

        while (running) {
            long startTime = System.currentTimeMillis();
            //delay for gameData update should be put here
            processCollisions();
            Main.gamePanel.gameRender();
            Main.gamePanel.printScreen();
            Main.gameData.update();
            long endTime = System.currentTimeMillis();
            int sleepTime = (int) (1.0 / FRAMES_PER_SECOND * 1000)
                    - (int) (endTime - startTime);

            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime); // ms
                } catch (InterruptedException e) {
                }
            }
        }
        System.exit(0);
    }

    
    private synchronized void processCollisions() {
        // detect collisions between friendFigure and enemyFigures
        // if detected, mark it as STATE_DONE, so that
        // they can be removed at update() method
        //classes then set to STATE_DONE
        for (GameFigure friendFigure : Main.gameData.friendFigures) {
            for (int i = 0; i < Main.gameData.enemyFigures.size(); i++) {
                if (friendFigure.getCollisionBox().intersects(Main.gameData.enemyFigures.get(i).getCollisionBox())) {
                    if (friendFigure instanceof Gary) {
                        Main.gameData.gary.health--;
                        Main.gameData.enemyFigures.get(i).state = GameFigure.STATE_DYING;
                        if (Main.gameData.gary.health < 1) {
                            friendFigure.state = GameFigure.STATE_DYING;
                        }
                    }
                    Main.gameData.enemyFigures.get(i).health--;
                    if (Main.gameData.enemyFigures.get(i).health <= 0) {
                        //enemy subBoss2 info
                        if (Main.gameData.enemyFigures.get(i) instanceof Boss2){
                            if ((i + 1) != Main.gameData.enemyFigures.size()){
                                Main.gameData.enemyFigures.get(i + 1).replace = true;
                                for (int k = i + 1; k < Main.gameData.enemyFigures.size(); k++){
                                    if (Main.gameData.enemyFigures.get(k).replace == true){
                                        k = Main.gameData.enemyFigures.size();
                                    }
                                    else{
                                        Main.gameData.enemyFigures.get(k).movementBackDown();
                                    }
                                }
//                                Main.gameData.enemyFigures.get(i + 1).movementBackUp();
                            }
                        }
                        if (Main.gameData.enemyFigures.get(i) instanceof Asteroid) {
                            Main.gameData.enemyFigures.get(i).perfectHit = true;
                        }
                        Main.gameData.enemyFigures.get(i).state = GameFigure.STATE_DYING;
                    }
                    if (friendFigure instanceof Laser) {
                        if (friendFigure.state == GameFigure.STATE_ULTIMATE) {
                            Main.gameData.enemyFigures.get(i).health -= 10;
                            friendFigure.state = GameFigure.STATE_DONE;
                        } else {
                            friendFigure.state = GameFigure.STATE_DONE;
                        }
                    }
                }
            }
        }
        for (GameFigure enemyLaserFigure : Main.gameData.enemyLaserFigures) {
            for (GameFigure enemyFigure : Main.gameData.enemyFigures) {
                if (enemyLaserFigure.getCollisionBox().intersects(enemyFigure.getCollisionBox())) {
                    enemyFigure.health--;
                    if (enemyFigure.health <= 0) {
                        enemyFigure.state = GameFigure.STATE_DYING;
                    }
                    enemyLaserFigure.state = GameFigure.STATE_DONE;
                }
            }
        }
        for (GameFigure friendFigure : Main.gameData.friendFigures) {
            for (GameFigure enemyLaserFigure : Main.gameData.enemyLaserFigures) {
                if (friendFigure.getCollisionBox().intersects(enemyLaserFigure.getCollisionBox())) {
                    enemyLaserFigure.state = GameFigure.STATE_DONE;
                    if (friendFigure instanceof Gary) {
                        Main.gameData.gary.health--;
                        if (Main.gameData.gary.health < 1) {
                            friendFigure.state = GameFigure.STATE_DYING;
                        }
                    }
                }
            }
        }
        for (GameFigure friendFigure : Main.gameData.friendFigures) {
            for (GameFigure enemySubCraftFigure : Main.gameData.enemySubCraftFigures) {
                if (friendFigure.getCollisionBox().intersects(enemySubCraftFigure.getCollisionBox())) {
                    if (friendFigure instanceof Gary) {
                        Main.gameData.gary.health--;
                        enemySubCraftFigure.state = GameFigure.STATE_DYING;
                        if (Main.gameData.gary.health < 1) {
                            friendFigure.state = GameFigure.STATE_DYING;
                        }
                    } else if (friendFigure instanceof Laser) {
                        friendFigure.state = GameFigure.STATE_DONE;
                    }
                    enemySubCraftFigure.health--;
                    if (enemySubCraftFigure.health <= 0) {
                        enemySubCraftFigure.state = GameFigure.STATE_DYING;
                    }
                }
            }
        }
        for (GameFigure enemyFigure : Main.gameData.enemyFigures) {
            for (GameFigure enemySubCraftFigure : Main.gameData.enemySubCraftFigures) {
                if (enemyFigure.getCollisionBox().intersects(enemySubCraftFigure.getCollisionBox())) {
                    if (enemyFigure instanceof Boss1) {
                        if(enemySubCraftFigure.miss == true){
                            Main.gameData.boss1.health -= 5;
                            enemySubCraftFigure.state = GameFigure.STATE_DONE;
                        }
                        if(enemyFigure.health <= 0){
                            enemyFigure.state = GameFigure.STATE_DYING;
                        }
                    }
                }
            }
        }
    }
}
 