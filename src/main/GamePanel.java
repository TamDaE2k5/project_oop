package main;

import javax.swing.JPanel;
import java.awt.*;
import util.KeyHandler;
import entity.PlayerPlane;


public class GamePanel extends JPanel{
    public final int screenWith = 1280, screenHeight = 720;

    private KeyHandler keyH = new KeyHandler();
    private GameLoop gameLoop;
    private PlayerPlane player = new PlayerPlane(this, keyH);

    GamePanel() {
        setPreferredSize(new Dimension(screenWith, screenHeight));
        setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        this.addKeyListener(keyH);
    }

    public void startGame(){
        GameLoop gameLoop =  new GameLoop(this);
    }

    public void update(){
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Giúp xóa màn hình cũ trước khi vẽ khung hình mới

        Graphics2D g2 = (Graphics2D) g; // Ép kiểu để dùng nhiều tính năng vẽ hơn

        g2.setColor(Color.RED);
        player.draw(g2);

        g2.dispose(); // Giải phóng tài nguyên bộ nhớ
    }
}
