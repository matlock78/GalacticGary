package controller;


import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import model.GameData;
import static model.GameFigure.STATE_ALIVE;
import static model.GameFigure.STATE_ULTIMATE;
import model.Gary;

public class KeyBoardController implements KeyListener{

    @Override
    public void keyTyped(KeyEvent e) {
        Gary gary = (Gary) Main.gameData.friendFigures.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                gary.setLeft(false);
                break;
            case KeyEvent.VK_A:
                gary.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                gary.setRight(false);
                break;
            case KeyEvent.VK_D:
                gary.setRight(false);
                break; 
            case KeyEvent.VK_UP:
                gary.setUp(false);
                break;
            case KeyEvent.VK_W:
                gary.setUp(false);
                break;
            case KeyEvent.VK_DOWN:
                gary.setDown(false);
                break;
            case KeyEvent.VK_S:
                gary.setDown(false);
                break;
            case KeyEvent.VK_ENTER:
                gary.fire();
                break;
            case KeyEvent.VK_SPACE:
                if(gary.garyUlt){
                    gary.ultLaunch();
                }
                break;
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (Main.gameData.gary.state == STATE_ALIVE || Main.gameData.gary.state == STATE_ULTIMATE){
        Gary gary = (Gary) Main.gameData.friendFigures.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                gary.setLeft(true);
                break;
            case KeyEvent.VK_A:
                gary.setLeft(true);
                break;
            case KeyEvent.VK_RIGHT:
                gary.setRight(true);
                break;
            case KeyEvent.VK_D:
                gary.setRight(true);
                break; 
            case KeyEvent.VK_UP:
                gary.setUp(true);
                break;
            case KeyEvent.VK_W:
                gary.setUp(true);
                break;
            case KeyEvent.VK_DOWN:
                gary.setDown(true);
                break;
            case KeyEvent.VK_S:
                gary.setDown(true);
                break;
            case KeyEvent.VK_ENTER:
                gary.fire();
                break;
            case KeyEvent.VK_SPACE:
                if(gary.garyUlt){
                    gary.ultLaunch();
                }
                break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        Gary gary = (Gary) Main.gameData.friendFigures.get(0);
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT:
                gary.setLeft(false);
                break;
            case KeyEvent.VK_A:
                gary.setLeft(false);
                break;
            case KeyEvent.VK_RIGHT:
                gary.setRight(false);
                break;
            case KeyEvent.VK_D:
                gary.setRight(false);
                break; 
            case KeyEvent.VK_UP:
                gary.setUp(false);
                break;
            case KeyEvent.VK_W:
                gary.setUp(false);
                break;
            case KeyEvent.VK_DOWN:
                gary.setDown(false);
                break;
            case KeyEvent.VK_S:
                gary.setDown(false);
                break;  
        }
        gary.update();
    }
}
