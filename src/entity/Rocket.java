package entity;

import main.GamePanel;

import java.awt.*;

import java.lang.Math;

public class Rocket extends Entity{

    private PlayerPlane target;
    private GamePanel panel;

    public Rocket(PlayerPlane target, int startX, int startY){
        this.target = target;
        sizeWith = 25; sizeHeight = 25;

        x = startX; y = startY; speed = 4; timeToLive = 300;
    }

    @Override
    public void update(){
        if(timeToLive>0)
            timeToLive--;

        double dx = target.x - this.x;
        double dy = target.y - this.y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance != 0) {
            this.x += (int) ((dx / distance) * speed);
            this.y += (int) ((dy / distance) * speed);
        }
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.cyan);
        g.fillRect(x,y,sizeWith,sizeHeight);
    }

    private int timeToLive;
    public boolean isAlive(){
        return timeToLive <= 0;
    }
}
