package com.meowmivice.game.client;
import com.meowmivice.game.MainFrame;
import com.meowmivice.game.controller.Game;

class Main {
    public static void main(String[] args){
        try {
            new MainFrame();
          Game.getInstance().execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}