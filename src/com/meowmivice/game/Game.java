package com.meowmivice.game;

import com.apps.util.Prompter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

class Game {
    private String name;
    private Prompter prompter;

    // CONSTRUCTOR
    Game(Prompter var1) {
        this.prompter = var1;
    }

    void execute() throws IOException, InterruptedException {
        boolean runGame = true;
        welcome();
        createPlayer();
        promptToPlay();

        while (runGame) {
            play();
            playAgain();
        }
    }


    private void welcome() throws IOException {
        String banner = Files.readString(Path.of("resources/splashbanner.txt"));
        System.out.println(banner);
    }
    private void createPlayer() {
        System.out.println("\n");
        String name = prompter.prompt("Please enter player's name: ").toUpperCase(Locale.ROOT);
        System.out.println("Welcome to Meowmi Vice " +  name);
        this.name = name;
    }

    private void promptToPlay() throws InterruptedException {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("\n");
            String play = prompter.prompt("Please enter [S] to start the game or [Q] to exit the game: ","s|q|S|Q","\nThat is not a valid input!\n");
            validInput = true;
            if ("S".equals(play) || "s".equals(play)) {
//                    Console.clear();
                continue;

            } else {
                System.out.println("Exiting the game...");
                TimeUnit.SECONDS.sleep(2);
                System.exit(0);
            }
        }
    }

    private void play() {
        System.out.println("Test");
    }

    private void playAgain() throws InterruptedException {
        boolean validInput = false;
        while (!validInput) {
            String play = prompter.prompt("Please enter [P] to play again or [Q] to exit the game: ","p|q|P|Q","\nThat is not a valid input!\n");
            validInput = true;
            if ("P".equals(play) || "p".equals(play)) {
//                    Console.clear();
                continue;
            } else {
                System.out.println("Exiting the game...");
                TimeUnit.SECONDS.sleep(2);
                System.exit(0);
            }
        }
    }
}