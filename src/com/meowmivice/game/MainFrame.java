package com.meowmivice.game;

import com.meowmivice.game.controller.Game;
import com.meowmivice.game.reader.Audio;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.io.IOException;

public class MainFrame {
    public static JFrame frame;
    private static Splash splash;
    public static Game game;
    private static GameScreen location;


    static {
        try {
            location = new GameScreen();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            game = Game.getInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            splash = new Splash();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MainFrame() throws Exception {
        frame = new JFrame("Meowmi Vice Reloaded");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(splash);
        frame.pack();
        frame.setVisible(true);
        Audio.audio();
    }

    public static void clearContent() throws Exception {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(location);
        frame.revalidate();
        frame.repaint();
    }

}