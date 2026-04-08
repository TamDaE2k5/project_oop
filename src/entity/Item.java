package entity;

import entity.Entity;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.awt.*;
import java.util.Objects;

public class Item extends Entity{
    public enum ItemType{
        HP, Bullet
    }

    public ItemType type;
    private BufferedImage HPImages, bulletImages;

    public Item(int startX, int startY, ItemType type){
        this.x = startX; y = startY; this.type = type; sizeWith = 30; sizeHeight = 30;
        loadImages();
    }

    @Override
    public void loadImages(){
        try{
            HPImages = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/health-green.png")));
            bulletImages = ImageIO.read(Objects.requireNonNull(getClass().getResource("/Images/ammo-rifle.png")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int timeToLive = 600;
    @Override
    public void update(){
        timeToLive--;
    }

    @Override
    public void draw(Graphics2D g){
        if(type == ItemType.HP){
            if(HPImages!=null)
                g.drawImage(HPImages, x, y, sizeWith, sizeHeight, null);
            else
                g.setColor(Color.green);
        }
        else if(type == ItemType.Bullet)
            if(bulletImages!=null)
                g.drawImage(bulletImages, x,y, sizeWith,sizeHeight, null);
            else
                g.setColor(Color.magenta);

    }
}
