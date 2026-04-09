package manager;

import javax.sound.sampled.*;
import java.net.URL;
import java.util.ArrayList;

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
//        try {
//            // Trỏ thẳng vào thư mục sfx
//            URL url = getClass().getResource("/Sounds/" + fileName);
//            if (url != null) {
//                AudioInputStream ais = AudioSystem.getAudioInputStream(url);
//
//                // Khởi tạo một Clip mới hoàn toàn cho mỗi lần gọi
//                Clip sfxClip = AudioSystem.getClip();
//                sfxClip.open(ais);
//                sfxClip.start(); // Phát 1 lần
//
//            } else {
//                System.out.println("Không tìm thấy SFX: " + fileName);
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }









    private Clip playlistClip; // Clip fun
    private ArrayList<String> playlist;
    private int currentTrackIndex = 0;

    public void playPlaylist(ArrayList<String> tracks) {
        this.playlist = tracks;
        this.currentTrackIndex = 0;
        if (playlist != null && !playlist.isEmpty()) {
            playNextPlaylistTrack();
        }
    }

    private void playNextPlaylistTrack() {
        if (playlist == null || playlist.isEmpty()) return;

        // Vòng lặp lại từ đầu nếu đã hết danh sách
        if (currentTrackIndex >= playlist.size()) {
            currentTrackIndex = 0;
        }

        String fileName = playlist.get(currentTrackIndex);

        try {
            URL url = getClass().getResource("/Sounds/" + fileName);
            if (url != null) {
                AudioInputStream ais = AudioSystem.getAudioInputStream(url);

                // Đóng clip của bài cũ (Chỉ đóng playlistClip)
                if (playlistClip != null) {
                    playlistClip.stop();
                    playlistClip.close();
                }

                playlistClip = AudioSystem.getClip();
                playlistClip.open(ais);

                playlistClip.addLineListener(new LineListener() {
                    @Override
                    public void update(LineEvent event) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            event.getLine().close();

                            if (playlist != null) {
                                currentTrackIndex++;
                                playNextPlaylistTrack();
                            }
                        }
                    }
                });

                playlistClip.start();

            } else {
                System.out.println("Lỗi Playlist - Không tìm thấy: " + fileName);
                currentTrackIndex++;
                playNextPlaylistTrack();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stopPlaylist() {
        this.playlist = null;
        if (playlistClip != null) {
            playlistClip.stop();
            playlistClip.close();
        }
    }
}
