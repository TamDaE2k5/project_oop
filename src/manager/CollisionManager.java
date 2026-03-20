package manager;

import entity.*;

import java.awt.*;

public class CollisionManager {
    private PlayerPlane player;
    private SpawnManager spawnManager;

    public CollisionManager(SpawnManager spawnManager, PlayerPlane player){
        this.spawnManager = spawnManager; this.player = player;
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
                spawnManager.meteorites.remove(i);
            }
        }

//        item
        for(int i=spawnManager.items.size()-1; i>=0; i--){
            Item it = spawnManager.items.get(i);
            if(checkPlayerAndEnemy(player, it)){
                if(it.type == Item.ItemType.HP){
                    player.currentHP += 2;
                }

                else if(it.type == Item.ItemType.Bullet){
                    player.currentBullet += 10;
                }

                spawnManager.items.remove(i);
            }

        }

//        rocket
        for(int i=spawnManager.rockets.size()-1; i>=0; i--){
            Rocket r = spawnManager.rockets.get(i);
            if(checkPlayerAndEnemy(player, r)){
                spawnManager.rockets.remove(i);
                player.currentHP -= 4;
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

                    //                    cong score


                }
            }

//            rocket
            for (int j = spawnManager.rockets.size()-1; j >=0; j--) {
                Rocket m = spawnManager.rockets.get(j);
                if(checkPlayerAndEnemy(b, m)){
                    spawnManager.rockets.remove(j);
                    spawnManager.bullets.remove(i);

                    //                    cong score


                }
            }
        }
    }

    public boolean checkExplosion(){
        for(int i=0; i<spawnManager.stars.size(); i++){
            Star s = spawnManager.stars.get(i);
            int starX = s.x + s.sizeWith/2, starY = s.y + s.sizeHeight/2;

            if(s.state == Entity.State.EXPLODING){
                //                check meteorite
                for(int j=spawnManager.meteorites.size()-1; j>=0; j--){
                    Meteorite m = spawnManager.meteorites.get(j);

                    int meteoriteX = m.x + m.sizeWith/2, meteoriteY = m.y + m.sizeHeight/2;

                    double distanceToMeteorite = Math.sqrt(Math.pow(meteoriteX-starX, 2) + Math.pow(meteoriteY - starY, 2));
                    if(distanceToMeteorite<=s.currentExplosionRadius)
                        spawnManager.meteorites.remove(j);
                }

                int  playerX = player.x + player.sizeWith/2, playerY = player.y + player.sizeHeight/2;

                double distanceToPlayer = Math.sqrt(Math.pow(playerX-starX, 2) + Math.pow(playerY-starY, 2));
                if(distanceToPlayer<=s.currentExplosionRadius) {
                    spawnManager.stars.remove(i);
                    return true;
                }
            }
        }
        return false;
    }
}
