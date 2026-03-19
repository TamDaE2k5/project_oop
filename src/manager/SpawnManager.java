package manager;

import entity.Entity;
import entity.Meteorite;
import entity.PlayerPlane;
import entity.Star;

import main.GamePanel;

import java.util.*;
import java.awt.*;

public class SpawnManager {
    private GamePanel panel;
    private PlayerPlane player;
    private Random random;

    public ArrayList<Meteorite> meteorites;
    public ArrayList<Star> stars;

    private int countTimeSpawnMeteorite = 0, countTimeSpawnStar = 0;

    public SpawnManager(GamePanel panel, PlayerPlane player){
        this.panel = panel; this.player = player;

        this.meteorites = new ArrayList<>();
        this.stars = new ArrayList<>();

        this.random = new Random();
    }

    public void spawnEnemy(){
        int randomX = random.nextInt(this.player.x+1280)+this.player.x,
                randomY = random.nextInt(this.player.y+720)+this.player.y;

        countTimeSpawnMeteorite++;
        countTimeSpawnStar++;
        if(countTimeSpawnMeteorite>=60){
            countTimeSpawnMeteorite = 0;
            Meteorite m = new Meteorite(randomX,randomY, this.player);
            meteorites.add(m);
        }

        if(countTimeSpawnStar>=240){
            countTimeSpawnStar = 0;
            Star s = new Star(random.nextInt(panel.screenWith), random.nextInt(panel.screenHeight));
            stars.add(s);
        }
    }

    //    action 1 update
    public void update(){
        spawnEnemy();
//  tranh truong hop xoa i=1 roi thi lai khong duyet duoc i=1 nua vi du [1,2,3] xoa 2 roi con [1,3] nhung khi goi lai for thi end for roi
        for(int i=meteorites.size()-1; i>=0; i--) {
            Meteorite m = meteorites.get(i);
            m.update();
            if(m.isOffScreen(panel.screenWith, panel.screenHeight))
                meteorites.remove(i);
        }

        for(int i=stars.size()-1; i>=0; i--){
            Star s = stars.get(i);
            s.update();
            if(s.state == Entity.State.DONE)
                stars.remove(i);
        }
    }


//    action 2 draw
    public void draw(Graphics2D g){
        g.setColor(Color.red);

        for (Meteorite e : meteorites) {
            e.draw(g);
        }

        for(Star s:stars){
            s.draw(g);
        }
    }
}
