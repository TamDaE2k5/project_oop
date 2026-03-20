package manager;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.net.URL;

public class SoundManager {
    private Clip bgmClip;

    public void playBGM(String fileName){
        try{
            URL url = getClass().getResource("/Sounds/"+fileName);
            if(url != null){
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
                bgmClip = AudioSystem.getClip();
                bgmClip.open(ais);
                bgmClip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else{
                System.out.println("Không tìm thấy BGM: " + fileName);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    // Dừng nhạc nền
    public void stopBGM() {
        if (bgmClip != null) {
            bgmClip.stop();
        }
    }

    // 2. HÀM PHÁT HIỆU ỨNG (SFX - Chỉ kêu 1 lần rồi thôi)
    public void playSFX(String fileName) {
        try {
            // Trỏ thẳng vào thư mục sfx
            URL url = getClass().getResource("/Sounds/" + fileName);
            if (url != null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);

                // Khởi tạo một Clip mới hoàn toàn cho mỗi lần gọi
                Clip sfxClip = AudioSystem.getClip();
                sfxClip.open(ais);
                sfxClip.start(); // Phát 1 lần

            } else {
                System.out.println("Không tìm thấy SFX: " + fileName);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
