package com.meowmivice.game.reader;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.Format;

public class Audio {
    static private Clip clip;
    static private AudioInputStream audioStream;
    private static boolean isSound = true;

    //reads the file, gets the audio, opens the audio, sets it to loop, and starts playing it
    public static void audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        InputStream is = Audio.class.getResourceAsStream("/Audio/main.wav");
        audioStream = AudioSystem.getAudioInputStream(new BufferedInputStream(is));
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        clip.start();
    }

    // pause/unpause audio
    static void toggleSound() {
        if (isSound) {
            clip.stop();
            isSound = false;
        }
        else {
            clip.start();
            isSound = true;
        }
    }

    // lower volume
    public static void lowerSoundVolume() {
        if (!isSound) {
            toggleSound();
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(-6.0f);
    }

    // increase volume
    public static void  increaseSoundVolume() {
        if (!isSound) {
            toggleSound();
        }
        FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        gainControl.setValue(+6.0f);
    }

    //stops audio and then closes the file
    public static void stopAudio() {
        clip.stop();
        clip.close();
    }

}