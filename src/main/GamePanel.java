package main;

import javax.swing.JPanel;
import java.awt.*;
import util.KeyHandler;
import entity.PlayerPlane;

import manager.SpawnManager;
import manager.CollisionManager;
import manager.SoundManager;
import manager.HighScoreManager;

public class GamePanel extends JPanel{
    public final int screenWith = 1280, screenHeight = 720;
    private boolean isGameOver = false;

    private KeyHandler keyH = new KeyHandler();
    private GameLoop gameLoop;

    private PlayerPlane player = new PlayerPlane(this, keyH);
    private SpawnManager spawnManager = new SpawnManager(this, player);
    private CollisionManager collisionManager = new CollisionManager(spawnManager, player, this);
    private SoundManager soundManager = new SoundManager();

    private int score = 0;
    private int highScore = 0;
    private HighScoreManager highScoreManager = new HighScoreManager();

    GamePanel() {
        setPreferredSize(new Dimension(screenWith, screenHeight));
        setBackground(Color.BLACK);
        this.setDoubleBuffered(true);
        this.setFocusable(true);

        highScore = highScoreManager.loadHighScore();

        this.addKeyListener(keyH);
    }

    public void startGame(){
        GameLoop gameLoop =  new GameLoop(this);

//        soundManager.playBGM("do_thang_cho.wav");
    }

    public void update(){
        if(!isGameOver) {
            player.update();
            spawnManager.update();

            collisionManager.checkAllCollisions();
            if(collisionManager.checkExplosion())
                player.currentHP -= 5;

            if(player.currentHP <= 0) {
                isGameOver = true;
                soundManager.stopBGM();
            }
        }else if(keyH.restartPressed){
            restartGame();
//            soundManager.playBGM("do_thang_cho.wav");
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

            g2.setFont(new Font("Arial", Font.BOLD, 28));
            g2.drawString("Nhan R de choi lai", screenWith/2-120, screenHeight/2+50);
        }

        g2.setColor(Color.WHITE);
        g2.setFont(new Font("Arial", Font.BOLD, 20));
        g2.drawString("Đạn: " + player.currentBullet, 20, 30); // Căn ở góc trên bên trái
        // Vẽ chữ HP
        g2.drawString("Máu: " + player.currentHP, 20, 60);
        g2.drawString("Score: "+score, 20, 90);
        g2.drawString("High Score: "+highScore, 20, 120);

        g2.dispose(); // Giải phóng tài nguyên bộ nhớ
    }

    public void addScore(int points){
        score += points;
        if(score > highScore){
            highScore = score;
            highScoreManager.saveHighScore(highScore);
        }
    }

    private void restartGame(){
        isGameOver = false;
        score = 0;
        player.setDefaultValue();
        spawnManager.reset();
        keyH.reset();
    }
}
