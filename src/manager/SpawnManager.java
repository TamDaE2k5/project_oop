package manager;

import entity.*;

import main.GamePanel;

import java.util.*;
import java.awt.*;

public class SpawnManager {
    private GamePanel panel;
    private PlayerPlane player;
    private Random random;
    private SoundManager soundManager;

    public ArrayList<Meteorite> meteorites;
    public ArrayList<Star> stars;
    public ArrayList<Item> items;
    public ArrayList<Bullet> bullets;
    public ArrayList<Rocket> rockets;

    private int countTimeSpawnMeteorite = 0, countTimeSpawnStar = 0,
            countTimeSpawnItem = 0, countTimeSpawnRocket = 0;

    public SpawnManager(GamePanel panel, PlayerPlane player){
        this.panel = panel; this.player = player;
        this.soundManager = new SoundManager();

        this.meteorites = new ArrayList<>();
        this.stars = new ArrayList<>();
        this.items = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.rockets = new ArrayList<>();

        this.random = new Random();
    }

    public int time = 0;
    public void spawnEnemy(){
        time++;

        countTimeSpawnMeteorite++;
        countTimeSpawnStar++;
        countTimeSpawnItem++;
        countTimeSpawnRocket++;

        if(countTimeSpawnMeteorite>=60){
            countTimeSpawnMeteorite = 0;
            Meteorite m = new Meteorite(random.nextInt(this.player.x+1280)+this.player.x,
                    random.nextInt(this.player.y+720)+this.player.y, this.player);
            meteorites.add(m);
//            soundManager.playSFX("thang_nao_co_tien.wav");
        }

        if(countTimeSpawnStar>=360){
            countTimeSpawnStar = 0;
            Star s = new Star(random.nextInt(panel.screenWith-50)+50, random.nextInt(panel.screenHeight-50)+50);
            stars.add(s);
            soundManager.playSFX("chay_di_cac_chau_oi.wav");
            time=0;
        }

        if(countTimeSpawnItem>=300){
            countTimeSpawnItem = 0;
            Item i = new Item(random.nextInt(panel.screenWith-100)+100, random.nextInt(panel.screenHeight-100)+100,
                    random.nextBoolean()? Item.ItemType.Bullet: Item.ItemType.HP);
            items.add(i);
        }

        if(countTimeSpawnRocket>=180){
            countTimeSpawnRocket = 0;
            Rocket r = new Rocket(player, random.nextInt(panel.screenWith), random.nextInt(panel.screenHeight));
            rockets.add(r);
        }
    }

    //    action 1 update
    public void update(){
        spawnEnemy();
        if(player.canShoot()){
            player.currentBullet--;
            bullets.add(new Bullet(player.x, player.y, player.degree));
        }
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

        for(int i=items.size()-1; i>=0; i--){
            Item it = items.get(i);
            it.update();
            if(it.timeToLive<=0)
                items.remove(i);
        }

        for(int i=bullets.size()-1; i>=0; i--){
            Bullet b = bullets.get(i);
            b.update();
            if(b.isOutScreen())
                bullets.remove(i);
        }

        for(int i=rockets.size()-1; i>=0; i--){
            Rocket r = rockets.get(i);
            r.update();
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

        for(Item i:items)
            i.draw(g);

        for(Bullet b:bullets)
            b.draw(g);

        for(Rocket r:rockets)
            r.draw(g);
    }
}
