package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import main.GamePanel;
import manager.SoundManager;

public class Star extends Entity{

    private final int countdownDuration = 180; // 3 giây (3 * 60 FPS)
    private final int explosionDuration = 30;  // 0.5 giây (30 * 60 FPS)
    // Bán kính nổ tối đa
    public int explosionRadius = 150;
    // Bán kính nổ hiện tại (dùng để vẽ hiệu ứng lan rộng)
    public int currentExplosionRadius = 0;

    private GamePanel panel;

    // image
    private BufferedImage explosionImg;
    private BufferedImage starImg;
    // sound
    private SoundManager soundManager;

    public Star(int startX, int startY){
        this.sizeWith = 50; this.sizeHeight = 50;

        this.x = startX; this.y = startY;

        this.state = State.COUNTDOWN;

        loadImages(); soundManager = new SoundManager();
    }

    @Override
    public void loadImages() {
        try {
            starImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/Sun_Pixel.png")));
            explosionImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/explosion04.png")));
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(){
        timer++;
//        COUNTDOWN -> EXPLODING -> DONE
//        State 1
        if (state == State.COUNTDOWN) {
            if (timer >= countdownDuration) {
                state = State.EXPLODING;
                timer = 0; // Reset bộ đếm để tính thời gian nổ
            }
        }

//        state 2
        else if (state == State.EXPLODING) {
            // Tính toán bán kính nổ lan rộng theo thời gian
            // currentRadius tăng dần từ 0 đến explosionRadius trong explosionDuration giây
            currentExplosionRadius = (int) (((double) timer / explosionDuration) * explosionRadius);
            if (timer >= explosionDuration) {
                state = State.DONE;
                soundManager.playSFX("explosion.wav");
            }
        }
    }

    @Override
    public void draw(Graphics2D g){
        if (state == State.COUNTDOWN) {
            g.drawImage(starImg, x, y, sizeWith, sizeHeight, null);
        }

        else if (state == State.EXPLODING) {

            if(explosionImg != null){
                int diameter = currentExplosionRadius * 2;

                // Tính toán tọa độ X, Y để ảnh nổ xuất hiện TỪ TÂM của Ngôi sao lan ra
                int drawX = x + (sizeWith / 2) - currentExplosionRadius;
                int drawY = y + (sizeHeight / 2) - currentExplosionRadius;

                // Vẽ ảnh vụ nổ, ép kích thước của ảnh bằng với diameter để tạo hiệu ứng to dần
                g.drawImage(explosionImg, drawX, drawY, diameter, diameter, null);
            }
            else {
                // VẼ HIỆU ỨNG VÒNG TRÒN NỔ LAN RỘNG (MÀU ĐỎ/TRẮNG)
                g.setColor(Color.RED);
                // Đặt nét vẽ đậm hơn (3 pixel) để vòng tròn nhìn rõ hơn
                g.setStroke(new BasicStroke(3));

                int drawX = x + (sizeWith / 2) - currentExplosionRadius;
                int drawY = y + (sizeHeight / 2) - currentExplosionRadius;
                int diameter = currentExplosionRadius * 2;
                g.drawOval(drawX, drawY, diameter, diameter);
                // Giải phóng nét vẽ đậm
                g.setStroke(new BasicStroke(1));
            }
        }
    }
}
