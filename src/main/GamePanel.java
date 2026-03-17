package main;

import javax.swing.JPanel;
import java.awt.*;
import util.KeyHandler;

public class GamePanel extends JPanel{
    public final int screenWith = 1280, screenHeight = 720;

    private KeyHandler keyH = new KeyHandler();
    private GameLoop gameLoop;


    private int playerX = 100;
    private int playerY = 100;
    private int playerSpeed = 5; // Tốc độ di chuyển: 5 pixel mỗi khung hình

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
        if(keyH.upPressed == true)
            playerY -= playerSpeed;
        if(keyH.downPressed == true)
            playerY += playerSpeed;
        if(keyH.leftPressed == true)
            playerX -= playerSpeed;
        if(keyH.rightPressed == true)
            playerX += playerSpeed;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Giúp xóa màn hình cũ trước khi vẽ khung hình mới

        Graphics2D g2 = (Graphics2D) g; // Ép kiểu để dùng nhiều tính năng vẽ hơn

        g2.setColor(Color.RED);
        g2.fillRect(playerX, playerY, 50, 50);

        g2.dispose(); // Giải phóng tài nguyên bộ nhớ
    }
}
