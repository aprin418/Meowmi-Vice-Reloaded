package com.meowmivice.game.client;
import com.apps.util.Prompter;
import com.meowmivice.game.FrameMain;
import com.meowmivice.game.controller.Game;

import java.util.Scanner;

class Main {
    public static void main(String[] args){
        try {
            FrameMain frame = new FrameMain();
//            Game meowmi = new Game(new Prompter(new Scanner(System.in)));
//            meowmi.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}