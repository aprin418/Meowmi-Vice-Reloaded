package com.meowmivice.game;

import com.apps.util.Prompter;

import java.io.IOException;
import java.util.Scanner;

class Main {
    public static void main(String[] args) {
        Game meowmi = new Game(new Prompter(new Scanner(System.in)));
        try {
            meowmi.execute();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
    }
