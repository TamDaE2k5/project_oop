package entity;

import java.awt.*;

import main.GamePanel;

public class Bullet extends Entity{

    private double exactX, exactY; // toa do di chuyen
    private double dx, dy; // vector di chuyen

    private GamePanel panel;

    public Bullet(int startX, int startY, int degree){
        exactX = startX; exactY = startY;

        x = startX; y = startY;

        speed = 15; sizeWith=20; sizeHeight=20;

        dx = (Math.cos(Math.toRadians(degree))*speed);
        dy = (Math.sin(Math.toRadians(degree))*speed);
    }

    public boolean isOutScreen(){
        return x > panel.screenWith + 100 || y > panel.screenHeight + 100
                || x < -100 || y < -100;
    }

    @Override
    public void update() {
        exactX += dx; exactY += dy;

        x = (int) exactX; y = (int) exactY;
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.pink);
        g.fillOval(x,y,sizeWith,sizeHeight);
    }
}
