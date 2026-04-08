package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

public class HitEffect extends Entity{
    private BufferedImage imageExplosion;
    private int alive;

    public HitEffect(int x, int y, BufferedImage imageExplosion){
        this.x = x; this.y = y; this.imageExplosion = imageExplosion;
        sizeWith=30; sizeHeight=30;
        loadImages(); alive=15; this.timer = 0;
    }

    @Override
    public void update() {
        timer++;
        // vẽ nở đều thành hình tròn
        sizeWith+=2; sizeHeight+=2;
        x-=1; y-=1;
    }

    @Override
    public void draw(Graphics2D g) {
        g.drawImage(imageExplosion, x, y, sizeWith, sizeHeight, null);
    }

    public boolean isDone(){
        return timer>=alive;
    }
}
