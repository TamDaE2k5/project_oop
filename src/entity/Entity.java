package entity;

import java.awt.*;

public class Entity {
    public int x, y, speed;
    public int sizeWith, sizeHeight;
    public int timer = 0;

    public enum State{
        ALIVE, APPEARING, COUNTDOWN, EXPLODING, DYING, DONE
    }
    public State state = State.ALIVE;


//    action -> @Override (su dung da hinh)
//    act 1 -> update
    public void update(){

    }

//    act 2 draw
    public void draw(Graphics2D g){

    }

//   act create rectangle arround entity
    public Rectangle getBounds(){
        return new Rectangle(x, y, sizeWith, sizeHeight);
    }
}
