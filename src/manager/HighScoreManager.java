package manager;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class HighScoreManager {
    private static final Path HIGH_SCORE_PATH =  Path.of("highscore.dat");

    public int loadHighScore(){
        try{
            if(!Files.exists(HIGH_SCORE_PATH)){
                return 0;
            }

            String content = Files.readString(HIGH_SCORE_PATH).trim();
            if(content.isEmpty()){
                return 0;
            }

            return Integer.parseInt(content);
        }catch(IOException | NumberFormatException e){
            return 0;
        }
    }

    public void saveHighScore(int highScore){
        try{
            Files.writeString(HIGH_SCORE_PATH, Integer.toString(highScore));
        }catch(IOException e){
            e.printStackTrace();
        }
    }
}
