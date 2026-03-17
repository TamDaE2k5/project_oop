package main;

import javax.swing.JPanel;
import java.awt.*;

public class GamePanel extends JPanel{
    public final int screenWith = 1280, screenHeight = 720;

    GamePanel() {
        setPreferredSize(new Dimension(screenWith, screenHeight));
        setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    public void startGame(){
        GameLoop gameLoop =  new GameLoop(this);
    }

    public void update(){

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Giúp xóa màn hình cũ trước khi vẽ khung hình mới

        Graphics2D g2 = (Graphics2D) g; // Ép kiểu để dùng nhiều tính năng vẽ hơn

        // Vẽ thử một khối vuông "Deadline" màu đỏ để test
        g2.setColor(Color.RED);
        g2.fillRect(100, 100, 50, 50);

        g2.dispose(); // Giải phóng tài nguyên bộ nhớ
    }
}
