package main;

public class GameLoop implements Runnable{
    public GamePanel panel;

    public Thread gameThread;
    public boolean isRunning;
    public final int FPS = 60;

    GameLoop(GamePanel panel){
        this.panel = panel;

        isRunning = true;
        gameThread = new Thread(this); // gan gameLoop vao Thread
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000.0 / FPS;
        double nextDrawTime = drawInterval + System.nanoTime();

        while (isRunning) {
            panel.update();
            panel.repaint();

            try {
                double remainingTime = nextDrawTime - System.nanoTime();
                remainingTime /= 1000000; // nano -> mili s
                if (remainingTime > 0)
                    Thread.sleep((long) remainingTime);
                nextDrawTime += drawInterval;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
