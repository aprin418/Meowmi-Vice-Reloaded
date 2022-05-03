package com.meowmivice.game;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class Audio {
    static private Clip clip;
    static private AudioInputStream audioStream;
    static private File file = new File("resources/Audio/mystery.wav");
    static private String currentStatus;

    static void audio() throws LineUnavailableException, UnsupportedAudioFileException, IOException {
        audioStream = AudioSystem.getAudioInputStream(file);
        clip = AudioSystem.getClip();
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
        playAudio();
    }

    static void playAudio(){
        clip.start();
        currentStatus = "playing";
    }

    static void startAudio() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
            clip.stop();
            clip.close();
            resetAudio();
            playAudio();

    }
    public static void stopAudio() {
        if("stopped".equals(currentStatus))   {
            System.out.println("Audio is already stopped.");
        }
        clip.stop();
        clip.close();
        currentStatus = "stopped";
    }

    public static void resetAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioStream = AudioSystem.getAudioInputStream(file);
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}