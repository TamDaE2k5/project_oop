package entity;

import util.KeyHandler;
import main.GamePanel;
import java.awt.*;

public class PlayerPlane {
    private int x, y, speed;

    private GamePanel panel;
    private KeyHandler keyH;

    public PlayerPlane(GamePanel panel, KeyHandler keyH){
        this.panel = panel; this.keyH = keyH;
        setDefaultValue();
    }

    public void setDefaultValue(){
        x = 100; y = 100; speed = 5;
    }

    // player act 1 -> handler key
    public void update(){
        if(keyH.upPressed)
            y -= speed;
        if(keyH.downPressed)
            y += speed;
        if(keyH.leftPressed)
            x -= speed;
        if(keyH.rightPressed)
            x += speed;
    }

    // player act 2 -> draw -> repaint
    public void draw(Graphics2D g){
        g.setColor(Color.WHITE);
        g.fillRect(x, y, 30, 30);
    }
}
