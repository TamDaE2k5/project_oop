package main;

import javax.swing.JFrame;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame{
    GameFrame(){
        GamePanel panel = new GamePanel();
        add(panel);
        pack();

        setTitle("Chạy khỏi DEADLINE");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // exit, neu muon an process -> HIDE_ON_CLOSE
        setLocationRelativeTo(null); // can giua man hinh
        setResizable(false);
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                panel.startGame();
            }
        });
    }
}
