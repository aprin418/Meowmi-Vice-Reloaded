package com.meowmivice.game;

import javax.swing.*;
import java.io.IOException;

public class FrameMain {
    private static JFrame frame;
    private static Splash splash;

    static {
        try {
            splash = new Splash();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FrameMain() {
        frame = new JFrame("Meowmi Vice Reloaded");
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(splash);
        frame.pack();
        frame.setVisible(true);
    }

}