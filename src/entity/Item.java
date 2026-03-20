package entity;

import entity.Entity;

import java.awt.*;

public class Item extends Entity{
    public enum ItemType{
        HP, Bullet
    }

    public ItemType type;

    public Item(int startX, int startY, ItemType type){
        this.x = startX; y = startY; this.type = type; sizeWith = 20; sizeHeight = 20;
    }

    public int timeToLive = 360;
    @Override
    public void update(){
        timeToLive--;
    }

    @Override
    public void draw(Graphics2D g){
        if(type == ItemType.HP){
            g.setColor(Color.green);
        }
        else if(type == ItemType.Bullet)
            g.setColor(Color.magenta);

        g.fillRect(x,y,sizeWith,sizeHeight);
    }
}
