package manager;

import entity.Enemy;
import entity.PlayerPlane;
import java.awt.*;

public class CollisionManager {
    private PlayerPlane player;
    private Enemy enemy;
    private SpawnManager spawnManager;

    public CollisionManager(SpawnManager spawnManager, PlayerPlane player){
        this.spawnManager = spawnManager; this.player = player;
    }

    public boolean checkPlayerAndEnemy(){
        Rectangle playerBox = player.getBounds();

        for (int i = 0; i < spawnManager.enemies.size(); i++) {
            Enemy e = spawnManager.enemies.get(i);

            Rectangle enemyBox = e.getBounds();

            if(playerBox.intersects(enemyBox))
                return true;
        }
        return false;
    }
}
