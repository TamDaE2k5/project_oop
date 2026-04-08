package entity;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import main.GamePanel;

import javax.imageio.ImageIO;

public class Bullet extends Entity{

    private double exactX, exactY; // toa do di chuyen
    private double dx, dy; // vector di chuyen

    private BufferedImage bulletImg;
    private int degree;

    public Bullet(int startX, int startY, int degree){
        exactX = startX; exactY = startY; this.degree = degree;

        x = startX; y = startY;

        speed = 10; sizeWith=15; sizeHeight=25;

        dx = (Math.cos(Math.toRadians(degree))*speed);
        dy = (Math.sin(Math.toRadians(degree))*speed);

        loadImages();
    }

    public void loadImages(){
        try {
            bulletImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/spaceMissiles_037.png")));
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public boolean isOutScreen(){
        return x > 1280 + 100 || y > 720 + 100
                || x < -100 || y < -100;
    }

    @Override
    public void update() {
        exactX += dx; exactY += dy;

        x = (int) exactX; y = (int) exactY;
    }

    @Override
    public void draw(Graphics2D g){
        if (bulletImg != null) {
            AffineTransform oldTransform = g.getTransform();

            // Tìm tâm của viên đạn để làm trục xoay
            double cx = x + sizeWith / 2.0;
            double cy = y + sizeHeight / 2.0;

            // XOAY ẢNH
            g.rotate(Math.toRadians(degree + 90), cx, cy);
            g.drawImage(bulletImg, x, y, sizeWith, sizeHeight, null);
            // Trả lại trục tọa độ như cũ cho các vật thể khác
            g.setTransform(oldTransform);
        } else {
            g.setColor(Color.pink);
            g.fillOval(x,y,sizeWith,sizeHeight);
        }
    }
}
