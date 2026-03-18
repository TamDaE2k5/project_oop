package entity;

import java.awt.*;
import java.lang.Math;

public class Enemy extends Entity{

    private PlayerPlane target;

    public Enemy(int startX, int startY, PlayerPlane target){
        this.x = startX; this.y = startY; this.speed = 3;
        this.sizeWith = 20; this.sizeHeight = 20;

        this.target = target;
    }

    @Override
    public void update(){
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
        g.setColor(Color.red);
        g.fillRect(x,y,sizeWith, sizeHeight);
    }
}
