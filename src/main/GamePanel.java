package main;

import javax.swing.JPanel;
import java.awt.*;
import util.KeyHandler;
import entity.PlayerPlane;
import manager.SpawnManager;
import manager.CollisionManager;

public class GamePanel extends JPanel{
    public final int screenWith = 1280, screenHeight = 720;
    private boolean isGameOver = false;

    private KeyHandler keyH = new KeyHandler();
    private GameLoop gameLoop;
    private PlayerPlane player = new PlayerPlane(this, keyH);
    private SpawnManager spawnManager = new SpawnManager(this, player);
    private CollisionManager collisionManager = new CollisionManager(spawnManager, player);

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
        if(!isGameOver) {
            player.update();

            spawnManager.update();

            if(collisionManager.checkPlayerAndEnemy())
                isGameOver = true;
            if(collisionManager.checkExplosion())
                isGameOver = true;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Giúp xóa màn hình cũ trước khi vẽ khung hình mới

        Graphics2D g2 = (Graphics2D) g; // Ép kiểu để dùng nhiều tính năng vẽ hơn

        g2.setColor(Color.RED);
        player.draw(g2);
        spawnManager.draw(g2);

        if (isGameOver) {
            g2.setColor(Color.WHITE);
            // Cài đặt font chữ to, in đậm
            g2.setFont(new Font("Arial", Font.BOLD, 80));

            // Vẽ chữ ra giữa màn hình
            String text = "GAME OVER!";
            // Tính toán để căn giữa chữ (tạm thời gõ cứng tọa độ, sau này có thể tính toán chính xác hơn)
            int textX = screenWith / 2 - 250;
            int textY = screenHeight / 2;

            g2.drawString(text, textX, textY);
        }

        g2.dispose(); // Giải phóng tài nguyên bộ nhớ
    }
}
