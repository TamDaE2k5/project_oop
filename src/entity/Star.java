package entity;

import java.awt.*;

import main.GamePanel;

public class Star extends Entity{

    private final int countdownDuration = 180; // 3 giây (3 * 60 FPS)
    private final int explosionDuration = 30;  // 0.5 giây (30 * 60 FPS)
    // Bán kính nổ tối đa
    public int explosionRadius = 150;
    // Bán kính nổ hiện tại (dùng để vẽ hiệu ứng lan rộng)
    public int currentExplosionRadius = 0;

    private GamePanel panel;

    public Star(int startX, int startY){
        this.sizeWith = 50; this.sizeHeight = 50;

        this.x = startX; this.y = startY;

        this.state = State.COUNTDOWN;
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
            // Tính toán bán kính nổ lan rộng theo thời gian (animation)
            // currentRadius tăng dần từ 0 đến explosionRadius trong explosionDuration giây
            currentExplosionRadius = (int) (((double) timer / explosionDuration) * explosionRadius);

            if (timer >= explosionDuration) {
                state = State.DONE;
            }
        }
    }

    @Override
    public void draw(Graphics2D g){
        if (state == State.COUNTDOWN) {

            // TẠO HIỆU ỨNG NHẤP NHÁY BÁO ĐỘNG (BẬT/TẮT MÀU DỰA TRÊN TIMER)
            // Cứ mỗi 10 frame (6 lần/giây) lại đổi màu một lần
            if ((timer / 10) % 2 == 0) {
                g.setColor(Color.YELLOW);
            } else {
                g.setColor(Color.ORANGE);
            }

            g.fillOval(x, y, sizeWith, sizeHeight);
        }

        else if (state == State.EXPLODING) {
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
