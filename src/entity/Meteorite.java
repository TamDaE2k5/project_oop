package entity;

import java.awt.*;
import java.lang.Math;
import java.util.Random;


public class Meteorite extends Entity{

    private PlayerPlane target;
    private Random random;

    public Meteorite(int startX, int startY, PlayerPlane target){
        random = new Random();

        this.x = startX; this.y = startY; this.speed = random.nextInt(3)+2;
        this.sizeWith = 20; this.sizeHeight = 20;

        this.target = target;
    }


    public void updateFollow(){
        double dx = target.x - this.x;
        double dy = target.y - this.y;

        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance != 0) {
            this.x += (int) ((dx / distance) * speed);
            this.y += (int) ((dy / distance) * speed);
        }
    }

    private int velX, velY;
    private boolean lockTarget = false;
    public void updateLinear(){
        if(!lockTarget){
            lockTarget = true;
            double dx = this.target.x - this.x;
            double dy = this.target.y - this.y;

            double distance = Math.sqrt(dx * dx + dy * dy);

            if (distance != 0 ) {
                velX = (int)((dx / distance) * speed);
                velY = (int)((dy / distance) * speed);
            }
        }
        else{
            this.x += velX;
            this.y += velY;
        }
    }

    @Override
    public void update(){
        updateLinear();
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.red);
        g.fillOval(x,y,sizeWith, sizeHeight);
    }

    public boolean isOffScreen(int screenWidth, int screenHeight) {
        if (this.y > screenHeight + 500 || this.y < -500 || this.x > screenWidth + 500 || this.x < -500) {
            return true;
        }
        return false;
    }
}
