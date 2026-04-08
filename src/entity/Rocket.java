package entity;

import main.GamePanel;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.Math;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class Rocket extends Entity{

    private PlayerPlane target;
    private GamePanel panel;
    private BufferedImage rocketImg;
    private double degree;

    public Rocket(PlayerPlane target, int startX, int startY){
        this.target = target;
        sizeWith = 15; sizeHeight = 35;
        loadImages();
        x = startX; y = startY; speed = 4; timeToLive = 300;
    }

    public void loadImages(){
        try{
            rocketImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/spaceMissiles_003.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(){
        if(timeToLive>0)
            timeToLive--;

        double dx = target.x - this.x;
        double dy = target.y - this.y;
        // Dùng Math.atan2 để lấy góc radian, sau đó đổi sang degree
        degree = Math.toDegrees(Math.atan2(dy, dx));
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance != 0) {
            this.x += (int) ((dx / distance) * speed);
            this.y += (int) ((dy / distance) * speed);
        }
    }

    @Override
    public void draw(Graphics2D g){
        if(rocketImg != null){
            AffineTransform oldTransform = g.getTransform();

            // Tìm tâm của viên đạn để làm trục xoay
            double cx = x + sizeWith / 2.0;
            double cy = y + sizeHeight / 2.0;

            // XOAY ẢNH
            g.rotate(Math.toRadians(degree + 90), cx, cy);
            g.drawImage(rocketImg, x, y, sizeWith, sizeHeight, null);
            // Trả lại trục tọa độ như cũ cho các vật thể khác
            g.setTransform(oldTransform);
        }
        else {
            g.setColor(Color.cyan);
            g.fillRect(x, y, sizeWith, sizeHeight);
        }
    }

    private int timeToLive;
    public boolean isAlive(){
        return timeToLive <= 0;
    }
}
