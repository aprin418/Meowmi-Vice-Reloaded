package com.meowmivice.game.client;
import com.apps.util.Prompter;
import com.meowmivice.game.cast.cast.Player;
import com.meowmivice.game.cast.client.controller.Game;

import java.util.Scanner;

class Main {
    static Player player = new Player();
    public static void main(String[] args){
        try {
            Game meowmi = new Game(new Prompter(new Scanner(System.in)));
            meowmi.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}