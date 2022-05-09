package com.meowmivice.game.logic;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.meowmivice.game.cast.Player;
import com.meowmivice.game.controller.Game;
import com.meowmivice.game.reader.FileReader;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Ascii {

    public Ascii() {
        super();
    }

    void ascii(String currentLocation) throws IOException {
        try {
            FileReader.fileReaderWhite("/Ascii/"+ currentLocation.toLowerCase() + ".txt");
        }
        catch (Exception e){
            System.out.println("There is no art");
        }
    }

    void displayLocation(Prompter prompter, Player player) throws Exception{
        mapFileReader("/Ascii/map.txt", player);
        prompter.prompt("Press enter to continue\n");
        Console.clear();
    }

    private String mapFileReader(String filename, Player player) throws IOException {
        try (var in = Game.class.getResourceAsStream(filename)) {
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            while ( scanner.hasNextLine() ){
                String coloredMap = "\033[37m" + scanner.nextLine() +"\033[0m";
                String colorCoded = "\033[31m" + player.getCurrentLocation().toUpperCase()+"\033[37m";
                System.out.println(coloredMap.replaceFirst(player.getCurrentLocation().toLowerCase(), colorCoded));
                System.out.print("\033[0m");
            }
        } catch (IOException e) {
            throw new RuntimeException("Uncaught", e);
        }
        return filename;
    }

}