package manager;

import entity.Entity;
import entity.Meteorite;
import entity.Star;
import entity.PlayerPlane;
import java.awt.*;

public class CollisionManager {
    private PlayerPlane player;
    private SpawnManager spawnManager;

    public CollisionManager(SpawnManager spawnManager, PlayerPlane player){
        this.spawnManager = spawnManager; this.player = player;
    }

    public boolean checkPlayerAndEnemy(){
        Rectangle playerBox = player.getBounds();

        for (int i = 0; i < spawnManager.meteorites.size(); i++) {
            Meteorite e = spawnManager.meteorites.get(i);

            Rectangle enemyBox = e.getBounds();

            if(playerBox.intersects(enemyBox)) {
                spawnManager.meteorites.remove(i);
                return true;
            }
        }
        return false;
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
