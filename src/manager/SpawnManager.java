package manager;

import entity.Enemy;
import entity.PlayerPlane;
import entity.Star;

import main.GamePanel;

import java.util.*;
import java.awt.*;

public class SpawnManager {
    private GamePanel panel;
    private PlayerPlane player;
    private Random random;

    public ArrayList<Enemy> enemies;
    public ArrayList<Star> stars;

    private int time = 0;

    public SpawnManager(GamePanel panel, PlayerPlane player){
        this.panel = panel; this.player = player;

        this.enemies = new ArrayList<>();
        this.stars = new ArrayList<>();

        this.random = new Random();
    }

    public void spawnEnemy(){
        int randomX = random.nextInt(this.player.x+1280), randomY = random.nextInt(this.player.y+720);

        Enemy e = new Enemy(randomX,randomY, this.player);
        enemies.add(e);

        Star s = new Star(panel);
        stars.add(s);
    }

    //    action 1 update
    public void update(){
        time++;
        if(time>=60){
            time = 0;
            spawnEnemy();
        }

//  tranh truong hop xoa i=1 roi thi lai khong duyet duoc i=1 nua vi du [1,2,3] xoa 2 roi con [1,3] nhung khi goi lai for thi end for roi
        for(int i=enemies.size()-1; i>=0; i--) {
            Enemy e = enemies.get(i);
            e.update();
            if(e.isOffScreen(panel.screenWith, panel.screenHeight))
                enemies.remove(i);
        }

        for(int i=stars.size()-1; i>=0; i--){
            Star s = stars.get(i);
            s.update();
        }
    }


//    action 2 draw
    public void draw(Graphics2D g){
        g.setColor(Color.red);

        for (Enemy e : enemies) {
            e.draw(g);
        }

        for(Star s:stars){
            s.draw(g);
        }
    }
}
