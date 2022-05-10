package com.meowmivice.game.reader;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class Audio {
    static private Clip clip;
    static private AudioInputStream audioStream;

    //reads the file, gets the audio, opens the audio, sets it to loop, and starts playing it
    public static void audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        InputStream is = Audio.class.getResourceAsStream("/Audio/main.wav");
        audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    //stops audio and then closes the file
    public static void stopAudio() {
        clip.stop();
        clip.close();
    }

}