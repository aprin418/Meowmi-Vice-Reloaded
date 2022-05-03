package com.meowmivice.game;
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

class Audio {
    static Long currentFrame;
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

    static void resumeAudio() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        if ("playing".equals(currentStatus)) {
//            clip.stop();
//            clip.close();
//            resetAudio();
//            clip.setMicrosecondPosition(0);
//            playAudio();
//            return;
//        }
//        clip.close();
//        resetAudio();
//        clip.setMicrosecondPosition(currentFrame);
//        clip.start();

            clip.stop();
            clip.close();
            resetAudio();
            currentFrame =0L;
            clip.setMicrosecondPosition(currentFrame);
            playAudio();
            return;

    }
    public static void pauseAudio() {
        if("paused".equals(currentStatus))   {
            System.out.println("Audio is already paused.");
        }
        currentFrame = clip.getMicrosecondPosition();
        clip.stop();
        clip.close();
        currentStatus = "paused";
    }

    public static void resetAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        audioStream = AudioSystem.getAudioInputStream(file);
        clip.open(audioStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}