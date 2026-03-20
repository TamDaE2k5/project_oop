package util;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    public boolean upPressed, downPressed, leftPressed, rightPressed, shootPressed;

    @Override
    public void keyTyped(KeyEvent e){
        // Hàm này bắt sự kiện gõ chữ (ví dụ gõ văn bản) -> it dung
    }

    @Override
    public void keyPressed(KeyEvent e){
        int code = e.getKeyCode();
        // ko dung else if do gia su dung phim W+D thi se di cheo dc chu ma dung else if no se chi di 1 huong thoi
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            upPressed = true;
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            downPressed = true;
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            leftPressed = true;
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            rightPressed = true;
        if(code == KeyEvent.VK_SPACE)
            shootPressed = true;
    }

    @Override
    public void keyReleased(KeyEvent e){
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_W || code == KeyEvent.VK_UP)
            upPressed = false;
        if(code == KeyEvent.VK_S || code == KeyEvent.VK_DOWN)
            downPressed = false;
        if(code == KeyEvent.VK_A || code == KeyEvent.VK_LEFT)
            leftPressed = false;
        if(code == KeyEvent.VK_D || code == KeyEvent.VK_RIGHT)
            rightPressed = false;
        if(code == KeyEvent.VK_SPACE)
            shootPressed = false;
    }
}
