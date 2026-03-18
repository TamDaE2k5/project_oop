package manager;

import entity.Enemy;
import entity.Entity;
import entity.PlayerPlane;
import main.GamePanel;

import java.util.*;
import java.awt.*;

public class SpawnManager {
    private GamePanel panel;
    private PlayerPlane player;
    private Random random;
    public ArrayList<Enemy> enemies;
    private int time = 0;

    public SpawnManager(GamePanel panel, PlayerPlane player){
        this.panel = panel; this.player = player;
        this.enemies = new ArrayList<>();  this.random = new Random();
    }

    public void spawnEnemy(){
        int randomX = random.nextInt(panel.screenWith+100), randomY = random.nextInt(panel.screenHeight+100);

        Enemy e = new Enemy(randomX,randomY, this.player);
        enemies.add(e);
    }

    //    action 1 update
    public void update(){
        time++;
        if(time>=120){
            time = 0;
            spawnEnemy();
        }

        for(int i=0; i<enemies.size(); i++) {
            Enemy e = enemies.get(i);
            e.update();
        }
    }


//    action 2 draw
    public void draw(Graphics2D g){
        g.setColor(Color.red);

        for(int i=0; i<enemies.size(); i++){
            Enemy e = enemies.get(i);
            e.draw(g);
        }
    }
}
