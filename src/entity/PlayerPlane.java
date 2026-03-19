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

    public int currentHP, currentBullet;
    public void setDefaultValue(){
        x = 100; y = 100; speed = 5; sizeHeight = 30; sizeWith = 30;
        currentBullet = 0; currentHP = 5;
    }

    // player act 1 -> handler key
    @Override
    public void update(){
        if(keyH.upPressed)
            y -= speed;
        if(keyH.downPressed)
            y += speed;
        if(keyH.leftPressed)
            x -= speed;
        if(keyH.rightPressed)
            x += speed;

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
}
