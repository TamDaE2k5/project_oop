package entity;

import java.util.Random;
import java.awt.*;

import main.GamePanel;

public class Star extends Entity{

    private Random random;
    private GamePanel panel;

    public Star(GamePanel panel){
        random = new Random();
        this.panel = panel;
        this.sizeWith = 50; this.sizeHeight = 50;

        this.x = random.nextInt(panel.screenWith); this.y = random.nextInt(panel.screenHeight);
    }

    private int timer =0;
    @Override
    public void update(){
        timer++;
        if(timer>=20) {
            sizeWith += 2;
            sizeHeight += 2;

            x -= 1;
            y -= 1;

            timer = 0;
        }
    }

    @Override
    public void draw(Graphics2D g){
        g.setColor(Color.blue);
        g.fillRect(x,y, sizeWith, sizeHeight);
    }

}
