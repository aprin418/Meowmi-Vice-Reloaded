package com.meowmivice.game.controller;

import com.meowmivice.game.FrameMain;
import com.meowmivice.game.reader.Audio;
import com.meowmivice.game.reader.FileReader;
import com.meowmivice.game.logic.Logic;
import com.apps.util.Console;
import com.apps.util.Prompter;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.text.JTextComponent;
import java.awt.*;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class Game {

    static JPanel title;
    static JTextArea txt;

    private static Prompter prompter;
    private Logic logic = new Logic(new Prompter(new Scanner(System.in))); // create a new logic so we can call execute

    // CONSTRUCTOR
    public Game(Prompter var1) throws Exception {
        prompter = var1;
    }

    // execute
    public void execute() throws Exception {
        boolean runGame = true;
        Console.clear();
        Audio.audio(); // play audio
//        welcome(); // welcome banner
//        promptToPlay();
        instructions(); // instructions banner
        while (runGame) { // never false
            logic.showStatus();
            logic.logic();
        }
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

}