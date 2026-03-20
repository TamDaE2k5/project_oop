package entity;

import util.KeyHandler;
import main.GamePanel;
import java.awt.*;

public class PlayerPlane extends Entity {

    private GamePanel panel;
    private KeyHandler keyH;

    public PlayerPlane(GamePanel panel, KeyHandler keyH){
        this.panel = panel; this.keyH = keyH;
        setDefaultValue();
    }

    public int currentHP, currentBullet, degree, timeWaitBullet;
    public void setDefaultValue(){
        x = 100; y = 100; speed = 5; sizeHeight = 30; sizeWith = 30;
        currentBullet = 500; currentHP = 500;
        degree = 270; // !
        timeWaitBullet = 10;
    }

    // player act 1 -> handler key
    @Override
    public void update(){
        if(timeWaitBullet>0)
            timeWaitBullet--;
        if (keyH.upPressed && keyH.rightPressed) { degree = 315; y -= speed; x += speed; }
        else if (keyH.upPressed && keyH.leftPressed) { degree = 225; y -= speed; x -= speed; }
        else if (keyH.downPressed && keyH.rightPressed) { degree = 45; y += speed; x += speed; }
        else if (keyH.downPressed && keyH.leftPressed) { degree = 135; y += speed; x -= speed; }
        else if (keyH.upPressed) { degree = 270; y -= speed; }
        else if (keyH.downPressed) { degree = 90; y += speed; }
        else if (keyH.leftPressed) { degree = 180; x -= speed; }
        else if (keyH.rightPressed) { degree = 0; x += speed; }

//        add limit for screen
        if(x<0)
            x = 0;
        if(y<0)
            y=0;
        if(x > panel.screenWith - sizeWith)
            x = panel.screenWith - sizeWith;
        if(y > panel.screenHeight - sizeHeight)
            y = panel.screenHeight - sizeHeight;
    }

    // player act 2 -> draw -> repaint
    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, sizeWith, sizeHeight);
    }

//    player act 3 -> shoot
    public boolean canShoot(){
        if(keyH.shootPressed && currentBullet > 0 && timeWaitBullet == 0){
            currentBullet--;
            timeWaitBullet = 10;
            return true;
        }
        return false;
    }
}
