package com.meowmivice.game;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

class Audio {
    static private Clip clip;
    static private AudioInputStream audioStream;

    static void audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        InputStream is = Audio.class.getResourceAsStream("/Audio/main.wav");
        audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    public static void stopAudio() {
        clip.stop();
        clip.close();
    }

}