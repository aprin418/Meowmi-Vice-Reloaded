package com.meowmivice.game.cast.client.logic;

import com.apps.util.Console;
import com.apps.util.Prompter;
import com.meowmivice.game.cast.cast.*;
import com.meowmivice.game.cast.client.controller.Game;
import com.meowmivice.game.cast.reader.Audio;
import com.meowmivice.game.cast.reader.FileReader;
import com.meowmivice.game.cast.reader.SaveAndLoad;
import com.meowmivice.game.cast.reader.TextParser;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.*;

public class Logic {
    LocationsLoader locLoader  = new LocationsLoader();
    Map<String, Location> mapLocations = locLoader.load();

    private Ascii art = new Ascii();
    private Player player = new Player();
    private static Prompter prompter;
    private static int count = 0;
    private static boolean checkCounter = false;

    private Location currentSpot = mapLocations.get(player.getCurrentLocation());

    private static String plug = "";

    private JSONObject locations = TextParser.locations();
    private CommandsLoader commandsLoader = new CommandsLoader();

    private List<String> go = commandsLoader.verbsObj().get("go");
    private List<String> north = commandsLoader.directionsObj().get("north");
    private List<String> east = commandsLoader.directionsObj().get("east");
    private List<String> south = commandsLoader.directionsObj().get("south");
    private List<String> west = commandsLoader.directionsObj().get("west");

    // Synonym List
    private List<String> upstairs = commandsLoader.directionsObj().get("upstairs");
    private List<String> downstairs = commandsLoader.directionsObj().get("downstairs");
    private List<String> get = commandsLoader.verbsObj().get("get");
    private List<String> help = commandsLoader.verbsObj().get("help");
    private List<String> quit = commandsLoader.verbsObj().get("quit");
    private List<String> solve = commandsLoader.verbsObj().get("solve");
    private List<String> look = commandsLoader.verbsObj().get("look");
    private List<String> talk = commandsLoader.verbsObj().get("talk");
    private List<String> restart = commandsLoader.verbsObj().get("restart");
    private List<String> map = commandsLoader.verbsObj().get("map");
    private List<String> play = commandsLoader.verbsObj().get("play");
    private List<String> stop = commandsLoader.verbsObj().get("stop");
    private List<String> load = commandsLoader.verbsObj().get("load");
    private List<String> save = commandsLoader.verbsObj().get("save");


    public Logic(Prompter var1) throws Exception {
        prompter = var1;
        this.player = player;
    }

    public void logic() throws Exception {
        String input = prompter.prompt(">").trim().toLowerCase();
        Console.clear();
        List<String> textParser = TextParser.textParser(input);

        if (textParser.size()>=2 && go.contains(textParser.get(0))) {
            String direction = getDirection(textParser);
            go(textParser, direction);
        }
        else if (look.contains(textParser.get(0))) {
            look(textParser);
        } else if (talk.contains(textParser.get(0))) {
            talk(textParser);
        } else if(solve.contains(textParser.get(0))) {
            solve();
        } else if (quit.contains(textParser.get(0))) {
            Game.quit();
        } else if(help.contains(textParser.get(0))){
            Game.help();
        } else if(restart.contains(textParser.get(0))){
            Game.restart();
        } else if (map.contains(textParser.get(0))){
            art.displayLocation(prompter, player);
        } else if (save.contains(textParser.get(0))){
            SaveAndLoad.save();
        } else if (load.contains(textParser.get(0))){
            SaveAndLoad.load();
        }else if (play.contains(textParser.get(0))){
            Audio.audio();
        } else if (stop.contains(textParser.get(0))){
            Audio.stopAudio();
        } else if(textParser.get(0).equals("suspects")){
            showSuspects();
        } else if(textParser.get(0).equals("inventory")){
            showInventory();
        }
    }

    private void go(List<String> input, String direction) {

        if (input.get(1).equals("back")){
            String temp = player.getCurrentLocation();
            player.setCurrentLocation(player.getPreviousLocation());
            player.setPreviousLocation(temp);
            checkCounter = false;
        }

        else if (currentSpot.getDirections().containsKey(direction)) {
            player.setPreviousLocation(player.getCurrentLocation());
            player.setCurrentLocation(currentSpot.getDirections().get(direction));
            checkCounter = false;
        }else {
            plug = "That is an invalid direction to go!";
        }
        currentSpot = mapLocations.get(player.getCurrentLocation());
        checkCounter = false;
    }

    private String getDirection(List<String> input) {
        String direction = "null";
        if(north.contains(input.get(1))) direction = "north";
        else if(east.contains(input.get(1))) direction = "east";
        else if(south.contains(input.get(1))) direction = "south";
        else if(west.contains(input.get(1))) direction = "west";
        else if(upstairs.contains(input.get(1))) direction = "upstairs";
        else if(downstairs.contains(input.get(1))) direction = "downstairs";
        return direction;
    }

    private void get(Item currentItem)throws Exception{
        Clue clue = currentItem.getClue();
        System.out.println(currentItem.getDescription());
        System.out.println("What do you want to do?");

        String input = prompter.prompt("> ").toLowerCase().trim();
        List<String> textParser = TextParser.textParser(input);

        if(checkCounter && get.contains(textParser.get(0)) && textParser.get(1).equals("clue")){
            player.getInventory().add(currentItem.getClue().getName());
            mapLocations.get(player.getCurrentLocation()).setItem(null);

            player.getClues().put(clue.getName(),
                    "Name: " + clue.getName() +
                            "\nDescription: " + clue.getDescription() +
                            "\nObtained from: " + currentItem.getName() +
                            "\nFound in: " + player.getCurrentLocation());
            checkCounter = false;
        }
        else if (look.contains(textParser.get(0)) && textParser.get(1).equals("clue")){
            plug = currentItem.getClue().getDescription();
            checkCounter = true;
            get(currentItem);
        }
        else {
            plug = "Invalid command";
        }
    }

    private void look(List<String> input) throws Exception {
        NPC currentNpc = currentSpot.getNpc();
        Item currentItem = currentSpot.getItem();
        if (input.size() == 1){
            if (currentNpc!=null && currentItem != null) { //area.containsKey("npc") && area.containsKey("item")
                plug = currentNpc.getName() + " and a " + currentItem.getName() + " are at this location";
            } else if (currentNpc!=null && currentItem==null){
                plug = currentNpc.getName() + " is at this location.";
            } else if(currentNpc==null && currentItem!=null){
                plug = "There is a " + currentItem.getName() + " in this location.";
            } else {
                plug = "There is nothing in this location to look at.";
            }
            // if user looks at an item, recall the Logic so that user can interact with it
        } else if (input.get(1).equals("item") && currentItem!=null){
            get(currentItem);
        }
        else {
            plug = "Can't look there";
        }
    }

    public void showStatus() throws IOException {
        art.ascii(player.getCurrentLocation());
        System.out.println(plug);
        plug = "";
        System.out.println("\033[1;36m===========================");
        System.out.println("You are in the " + player.getCurrentLocation());
        System.out.println(currentSpot.getDescription());
        System.out.println("Inventory:" +"\033[37m" + player.getInventory() + "\033[1;36m");
        System.out.println("Enter help to see a list of available commands");
        System.out.println("===========================");
        System.out.println("Directions you can go: " +"\033[37m" +
                showDirections(player.getCurrentLocation()) + "\033[0m");
    }

    private String showDirections(String currentLocation) {
        Map<String,String> directionsMap =  currentSpot.getDirections();
        return directionsMap.keySet().toString();
    }

    private void solve() throws Exception {
        if(player.getInventory().size() == 0){
            FileReader.fileReaderWhite("/Ascii/pdog3.txt");
            prompter.prompt("Press enter to continue");
            Console.clear();
            return;
        }
        //print the ascii of the dog
        FileReader.fileReaderWhite("/Ascii/pdog.txt");
        // Hard coded culprit, subject to change
        String culprit = prompter.prompt(">").strip().toLowerCase();
        Console.clear();
        FileReader.fileReaderWhite("/Ascii/pdog2.txt");
        Set<String> evidence = new HashSet<>(getEvidence()); // user picks out the evidence to provide

        CulpritLoader culpLoader = new CulpritLoader();
        Culprit reqCulprit = culpLoader.load();
        // Set<String> requiredEvidence = new HashSet<>(Arrays.asList("dog hair", "receipt", "insurance policy"));
        // If both cases are true, you win
        if(culprit.equals(reqCulprit.getName()) && evidence.equals(reqCulprit.getEvidence())){
            System.out.println("Congratulations you solved the mystery!");
            Game.playAgain();
            System.exit(0);
        }
        else {
            count++;
            if (count > 2) {
                System.out.println("You Lost. The culprit got away!");
                Game.playAgain();
                System.exit(0);
            }
            System.out.println("Sorry please collect more clues or try again.");
        }
    }

    // Method to return evidence Set for solving
    private ArrayList<String> getEvidence() {
        ArrayList<String> evidence = new ArrayList<>();
        ArrayList<String> copy = new ArrayList<>(player.getInventory());
        boolean isDone = false;
        // As long as they don't specify to quit, loop continues
        while(!isDone){
            System.out.println("Current Collected Evidence: ");
            int i = 1;
            for (String item: copy) {
                System.out.println(i + " " + item);
                i++;
            }

            System.out.println("\nEvidence to provide: " + evidence.toString());

            System.out.println("What would you like to do?");
            System.out.println("Add, Remove, Solve, Inventory");

            String choice = prompter.prompt(">").strip().toLowerCase();
            try{
                // Add to the evidence
                if (choice.equals("a") || choice.equals("add")){
                    System.out.println("What index item would you like to add?");
                    String input = prompter.prompt(">").strip().toLowerCase();
                    int index = Integer.parseInt(input) - 1;
                    if(!evidence.contains(copy.get(index))) {
                        evidence.add(copy.get(index));
                        copy.remove(copy.get(index));
                    }
                }
                // Remove evidence
                else if (choice.equals("r") || choice.equals("remove")){
                    System.out.println("What item do you want to remove?");
                    String input = prompter.prompt(">").strip().toLowerCase();
                    if (evidence.contains(input)){
                        copy.add(input);
                        evidence.remove(input);
                    }
                }
                else if (choice.equals("i") || choice.equals(("inventory"))){
                    showInventory();
                }
                // Exit loop
                else if (choice.equals("s") || choice.equals("solve")){
                    isDone = true;
                }
            }
            catch (Exception e){
                System.out.println("Invalid command");
            }
            finally {
                Console.clear();
                System.out.println(plug+"\n");
            }
        }
        return evidence;
    }

    private void talk(List<String> input){

        NPC npc = currentSpot.getNpc();
        if (npc!=null && input.size()>=2 && input.get(1).equals("npc")) { //area.containsKey("npc") && input.size()>=2 && input.get(1).equals("npc")
            ArrayList<String> randDialogueList = npc.getRandDialogue(); // list from obj value
            int rand = new Random().nextInt(randDialogueList.size()); // make random int from size of list
            String randDiag = randDialogueList.get(rand);
            plug = npc.getName() + ": " + randDiag;
            addDialogue(npc.getName(),randDiag);
        }
        else if (npc!=null){
            plug = "Talk to who?";
        }
        else {
            plug = "There is no one to talk to";
        }
    }

    private void addDialogue(String name, String dialogue) {
        if(!player.getSuspects().containsKey(name)){
            player.getSuspects().put(name,dialogue);
        }
    }

    private void showSuspects(){
        if(player.getSuspects().size() == 0){
            plug = "No suspects at this time";
        }
        else{
            System.out.println("Who do you want to talk to?");
            System.out.println(player.getSuspects().keySet().toString());
            String input = prompter.prompt(">").trim();
            plug =  player.getSuspects().getOrDefault(input, "Don't know who that is");
        }
    }

    private String showInventory() {
        String plug;
        if(player.getClues().size() == 0){
            plug = "Currently no clues collected";
        }
        else {
            System.out.println("What clue would you like to review?");
            System.out.println(player.getClues().keySet());
            String input = prompter.prompt("> ").trim();
            plug = player.getClues().getOrDefault(input, "Invalid clue");
        }
        return plug;
    }
}