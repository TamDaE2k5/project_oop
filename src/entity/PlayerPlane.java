package entity;

import util.KeyHandler;
import main.GamePanel;
import java.awt.*;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

public class PlayerPlane extends Entity {

    private GamePanel panel;
    private KeyHandler keyH;

    private BufferedImage[] planeFrames;
    // Biến quản lý Animation
    private int spriteCounter = 0; // Bộ đếm thời gian đổi frame
    private int spriteNum = 0;     // Chỉ số frame hiện tại
    private boolean isMoving = false;

    @Override
    public void loadImages() {
        planeFrames = new BufferedImage[5];
        try {
            // Chú ý đường dẫn thư mục, bạn điều chỉnh lại cho đúng folder chứa ảnh nhé
            planeFrames[0] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/pixil-frame-0.png")));
            planeFrames[1] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/pixil-frame-1.png")));
            planeFrames[2] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/pixil-frame-2.png")));
            planeFrames[3] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/pixil-frame-3.png")));
            planeFrames[4] = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/pixil-frame-4.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    public PlayerPlane(GamePanel panel, KeyHandler keyH){
        this.panel = panel; this.keyH = keyH;
        setDefaultValue();
        loadImages();
    }

    public int currentHP, currentBullet, degree, timeWaitBullet;
    public void setDefaultValue(){
        x = 100; y = 100; speed = 5; sizeHeight = 40; sizeWith = 40;
        currentBullet = 25; currentHP = 50;
        degree = 270; // !
        timeWaitBullet = 10;
    }

    // player act 1 -> handler key
    @Override
    public void update(){
        if(timeWaitBullet>0)
            timeWaitBullet--;

        isMoving = false;

        if (keyH.upPressed && keyH.rightPressed) { degree = 315; y -= speed; x += speed; isMoving = true; }
        else if (keyH.upPressed && keyH.leftPressed) { degree = 225; y -= speed; x -= speed; isMoving = true; }
        else if (keyH.downPressed && keyH.rightPressed) { degree = 45; y += speed; x += speed; isMoving = true;}
        else if (keyH.downPressed && keyH.leftPressed) { degree = 135; y += speed; x -= speed; isMoving = true;}
        else if (keyH.upPressed) { degree = 270; y -= speed; isMoving = true;}
        else if (keyH.downPressed) { degree = 90; y += speed; isMoving = true;}
        else if (keyH.leftPressed) { degree = 180; x -= speed; isMoving = true;}
        else if (keyH.rightPressed) { degree = 0; x += speed; isMoving = true;}

        // chỉnh tốc độ change images
        if (isMoving) {
            spriteCounter++;
            if (spriteCounter > 5) {
                spriteNum++;
                if (spriteNum >= 5) {
                    spriteNum = 0;
                }
                spriteCounter = 0;
            }
        } else {
            spriteNum = 0; // lúc này là dừng lại
        }

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
        if (planeFrames != null && planeFrames[spriteNum] != null) {
            AffineTransform oldTransform = g.getTransform();
            // Tìm tâm để xoay
            double cx = x + sizeWith / 2.0;
            double cy = y + sizeHeight / 2.0;
            g.rotate(Math.toRadians(degree+90), cx, cy);

            // Vẽ ảnh theo chỉ số spriteNum
            g.drawImage(planeFrames[spriteNum], x, y, sizeWith, sizeHeight, null);
            // trả lại về như cũ cho vật thể khác
            g.setTransform(oldTransform);
        } else {
            g.setColor(Color.WHITE);
            g.fillRect(x, y, sizeWith, sizeHeight);
        }
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
