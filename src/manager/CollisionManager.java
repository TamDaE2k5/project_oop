package manager;

import entity.*;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import main.GamePanel;

import javax.imageio.ImageIO;

public class CollisionManager {
    private PlayerPlane player;
    private SpawnManager spawnManager;
    private SoundManager soundManager;
    private GamePanel panel;
    private BufferedImage MPImg, RMImg, RPImg, BMImg, BRImg;
    public CollisionManager(SpawnManager spawnManager, PlayerPlane player, GamePanel panel){
        this.spawnManager = spawnManager; this.player = player;this.panel = panel;
        this.soundManager = new SoundManager();

        try{
            MPImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/flash00.png")));
            RMImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/explosion01.png")));
            RPImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/explosion07.png")));
            BMImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/explosion06.png")));
            BRImg = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/flash07.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean checkPlayerAndEnemy(Entity e1, Entity e2){
        Rectangle box1 = e1.getBounds();
        Rectangle box2 = e2.getBounds();
        return box1.intersects(box2);
    }

    public void checkAllCollisions(){
//        meteorite
        for(int i=spawnManager.meteorites.size()-1; i>=0; i--){
            Meteorite m = spawnManager.meteorites.get(i);
            if(checkPlayerAndEnemy(player, m)){
                player.currentHP -= 2;
                spawnManager.hitEffects.add(new HitEffect(m.x,m.y,MPImg));
                soundManager.playSFX("explosion.wav");
                spawnManager.meteorites.remove(i);
            }
        }

//        item
        for(int i=spawnManager.items.size()-1; i>=0; i--){
            Item it = spawnManager.items.get(i);
            if(checkPlayerAndEnemy(player, it)){

                if(it.type == Item.ItemType.HP)
                    player.currentHP += 2;


                else if(it.type == Item.ItemType.Bullet)
                    player.currentBullet += 10;


                spawnManager.items.remove(i);
            }

        }

//        rocket
        for(int i=spawnManager.rockets.size()-1; i>=0; i--){
            Rocket r = spawnManager.rockets.get(i);
            if(checkPlayerAndEnemy(player, r)){
                spawnManager.rockets.remove(i);
                spawnManager.hitEffects.add(new HitEffect(r.x,r.y,RPImg));
                soundManager.playSFX("explosion.wav");
                player.currentHP -= 4;
            }

//            meteorite
            for (int j = spawnManager.meteorites.size()-1; j >=0 ; j--) {
                Meteorite m = spawnManager.meteorites.get(j);
                if(checkPlayerAndEnemy(r, m)){
                    spawnManager.rockets.remove(i);
                    spawnManager.meteorites.remove(j);
                    spawnManager.hitEffects.add(new HitEffect(m.x, m.y, RMImg));
                    soundManager.playSFX("explosion.wav");
                    panel.addScore(10);
                }
            }
        }

//        bullet
        for(int i=spawnManager.bullets.size()-1; i>=0; i--){
            Bullet b = spawnManager.bullets.get(i);

//            meteorite
            for (int j = spawnManager.meteorites.size()-1; j >=0; j--) {
                Meteorite m = spawnManager.meteorites.get(j);
                if(checkPlayerAndEnemy(b, m)){
                    spawnManager.meteorites.remove(j);
                    spawnManager.bullets.remove(i);
                    spawnManager.hitEffects.add(new HitEffect(m.x, m.y, BMImg));
                    soundManager.playSFX("explosion.wav");
                    panel.addScore(10);

                }
            }

//            rocket
            for (int j = spawnManager.rockets.size()-1; j >=0; j--) {
                Rocket r = spawnManager.rockets.get(j);
                if(checkPlayerAndEnemy(b, r)){
                    spawnManager.rockets.remove(j);
                    spawnManager.bullets.remove(i);
                    spawnManager.hitEffects.add(new HitEffect(r.x, r.y, BRImg));
                    soundManager.playSFX("explosion.wav");
                    panel.addScore(20);

                }
            }
        }
    }

    public boolean checkExplosion(){
        boolean isPlayerHit = false;
        for(int i=spawnManager.stars.size()-1; i>=0; i--){
            Star s = spawnManager.stars.get(i);
            int starX = s.x + s.sizeWith/2, starY = s.y + s.sizeHeight/2;

            if(s.state == Entity.State.EXPLODING){
                //                check meteorite
                for(int j=spawnManager.meteorites.size()-1; j>=0; j--){
                    Meteorite m = spawnManager.meteorites.get(j);

                    int meteoriteX = m.x + m.sizeWith/2, meteoriteY = m.y + m.sizeHeight/2;

                    double distanceToMeteorite = Math.sqrt(Math.pow(meteoriteX-starX, 2) + Math.pow(meteoriteY - starY, 2));
                    if(distanceToMeteorite<=s.currentExplosionRadius) {
                        spawnManager.meteorites.remove(j);
                        panel.addScore(2);
                    }
                }

                // check rocket
                for(int j=spawnManager.rockets.size()-1; j>=0; j--){
                    Rocket r = spawnManager.rockets.get(j);
                    int rocketX = r.x + r.sizeWith/2, rocketY = r.y + r.sizeHeight/2;

                    double distanceToRocket = Math.sqrt(Math.pow(rocketX-starX, 2) + Math.pow(rocketY - starY, 2));
                    if(distanceToRocket<=s.currentExplosionRadius) {
                        spawnManager.rockets.remove(j);
                        panel.addScore(15);
                    }
                }

                int  playerX = player.x + player.sizeWith/2, playerY = player.y + player.sizeHeight/2;

                double distanceToPlayer = Math.sqrt(Math.pow(playerX-starX, 2) + Math.pow(playerY-starY, 2));
                if(distanceToPlayer<=s.currentExplosionRadius) {
                    isPlayerHit=true;
                }
            }
        }
        return isPlayerHit;
    }


}
