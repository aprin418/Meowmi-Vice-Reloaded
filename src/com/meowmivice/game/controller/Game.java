package com.meowmivice.game.controller;

import com.meowmivice.game.GameScreen;
import com.meowmivice.game.reader.Audio;
import com.meowmivice.game.reader.FileReader;
import com.meowmivice.game.logic.Logic;
import com.apps.util.Console;
import com.apps.util.Prompter;
import com.meowmivice.game.reader.TextParser;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game extends Panel{
    private static Game game = null;
    private static Prompter prompter;
    private Logic logic = new Logic(new Prompter(new Scanner(System.in))); // create a new logic so we can call execute

    public static Game getInstance() throws Exception {
        if (game == null) {
            game = new Game(new Prompter(new Scanner(System.in)));
        }
        return game;
    }

    // CONSTRUCTOR
    private Game(Prompter var1) throws Exception {
        prompter = var1;
    }

    // execute
    public void execute() throws Exception {

//        boolean runGame = true;
//        Console.clear();
//        Audio.audio(); // play audio
////        welcome(); // welcome banner
////        promptToPlay();
//        instructions(); // instructions banner
//        while (runGame) { // never false
//            logic.showStatus();
//            logic.logic();
//        }
    }

//    // welcome
//    public static void welcome() throws IOException, InterruptedException, ParseException {
//        FileReader.fileReader("/Text/splashbanner.txt");
//        System.out.println();
//        TimeUnit.SECONDS.sleep(2);
//        System.out.println("Welcome to Meowmi Vice!");
//    }

//    // prompt play
//    public static void promptToPlay() throws InterruptedException {
//        boolean validInput = false;
//        while (!validInput) {
//            String play = prompter.prompt("Please enter [S] to start the game or [Q] to exit the game: ","s|q|S|Q","\nThat is not a valid input!\n");
//            validInput = true;
//            if ("S".equals(play) || "s".equals(play)) {
//                continue;
//
//            } else {
//                quit();
//            }
//        }
//    }

    // display instructions
    public static void instructions() throws IOException {
        Console.clear();
        FileReader.fileReaderWhite("/Text/instructions.txt");
        prompter.prompt("Press enter to continue");
        Console.clear();
    }

    // quit game
    public static void quit() throws InterruptedException {
        System.out.println("Are you you sure you want to quit? (Y|N)");
        String confirm = prompter.prompt(">").strip().toLowerCase(Locale.ROOT);
        if ("yes".equals(confirm) || "y".equals(confirm)){
            System.out.println("Exiting the game...");
            TimeUnit.SECONDS.sleep(2);
            System.exit(0);
        }
    }

    // restart game
    public static void restart() throws Exception {
        System.out.println("Are you you sure you want to restart? (Y|N)");
        String confirm = prompter.prompt(">").strip().toLowerCase(Locale.ROOT);
        if ("yes".equals(confirm) || "y".equals(confirm)){
            System.out.println("Restarting the game...");
            Audio.stopAudio();
            TimeUnit.SECONDS.sleep(2);
            Game game2 = new Game(new Prompter(new Scanner(System.in))); // create a new game
            game2.execute(); // execute it
        }
    }

    // play again
    public static void playAgain() throws Exception {
        boolean validInput = false;
        while (!validInput) {
            String play = prompter.prompt("Please enter [P] to play again or [Q] to exit the game: ","p|q|P|Q","\nThat is not a valid input!\n");
            System.out.println();
            validInput = true;
            if ("P".equals(play) || "p".equals(play)) {
                Console.clear();
                Game.restart();
            } else {
                Game.quit();
            }
        }
    }

    // display help
    public static void help() throws IOException {
        FileReader.fileReaderWhite("/Text/commands.txt");
    }

//    public void handleInput(String input){
//        try {
//            parser.textParser(input);
//        } catch (IOException | ParseException e) {
//            System.out.println(e.getMessage());
//        }
//    }
public static void getCommands() {
    GameScreen.showPopUp("=======COMMANDS=======\n" +
            "Click [Look] button: view room descriptions\n" +
            "Click [Directional] buttons : navigate rooms\n" +
            "Click [item]: collect items\n" +
            "Click [NPC]: interrogate NPC\n" +
            "Click [Clues] button: view clues from collected items\n" +
            "Click [Vol down] button: decrease sound volume\n" +
            "Click [Vol up] button: increase sound volume\n" +
            "======================");
}


    public static void gameInfo() {
        GameScreen.showPopUp("You are detective Purrlock Holmes and have been called upon to solve a homicide.\n" +
                "Collect clues, talk to witnesses, and make your way around the house to solve the mystery.\n" +
                "When you are ready just enter solve with you think might be the culprit.\n" +
                "Good luck!");
    }

}