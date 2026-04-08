package entity;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.lang.Math;
import java.util.Objects;
import java.util.Random;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Meteorite extends Entity{

    private PlayerPlane target;
    private Random random;

    private BufferedImage meteoriteImg;
    private double spinAngle; // Góc xoay hiện tại
    private double spinSpeed;     // Tốc độ xoay mỗi frame

    public Meteorite(int startX, int startY, PlayerPlane target){
        random = new Random();

        this.x = startX; this.y = startY; this.speed = random.nextInt(3)+2;
        this.sizeWith = 30; this.sizeHeight = 30;

        this.target = target;

        spinAngle = 0; spinSpeed = random.nextInt(11) - 5; // xoay -5->5 nghia là quay trái phải
        if(this.spinSpeed == 0) this.spinSpeed = 1; loadImages();
    }

    @Override
    public void loadImages(){
        try{
            meteoriteImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/spaceMeteors_004.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
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

        spinAngle += spinSpeed;
        if(spinAngle >= 360 || spinAngle <= -360) {
            spinAngle = 0;
        }
    }

    @Override
    public void draw(Graphics2D g){
        if(meteoriteImg!=null){
            AffineTransform oldTransform = g.getTransform();
            double cx = x + sizeWith / 2.0;
            double cy = y + sizeHeight / 2.0;

            // 3. XOAY ẢNH THEO GÓC SPIN (Không cần cộng bù 90 độ vì nó xoay vòng tròn)
            g.rotate(Math.toRadians(spinAngle), cx, cy);

            g.drawImage(meteoriteImg, x, y, sizeWith, sizeHeight, null);

            g.setTransform(oldTransform);
        }
        else{
            g.setColor(Color.red);
        g   .fillOval(x,y,sizeWith, sizeHeight);
        }
    }

    public boolean isOffScreen(int screenWidth, int screenHeight) {
        if (this.y > screenHeight + 500 || this.y < -500 || this.x > screenWidth + 500 || this.x < -500) {
            return true;
        }
        return false;
    }
}
