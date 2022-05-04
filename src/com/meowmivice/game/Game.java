package com.meowmivice.game;
import com.apps.util.Console;
import com.apps.util.Prompter;
import org.json.simple.JSONObject;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Game {
    private static Prompter prompter;
    private static int count = 0;
    private static String currentLocation = "Kitchen";
    private static String prev = "Kitchen";
    private static String plug = "";
    private List<String> inventory = new ArrayList<>();
    private JSONObject locations = TextParser.locations();
    private Map directions = ((Map) locations.get(currentLocation));
    private CommandsLoader commandsLoader = new CommandsLoader();
    private List<String> go = commandsLoader.verbsObj().get("go");
    private List<String> north = commandsLoader.directionsObj().get("north");
    private List<String> east = commandsLoader.directionsObj().get("east");
    private List<String> south = commandsLoader.directionsObj().get("south");
    private List<String> west = commandsLoader.directionsObj().get("west");
    private List<String> upstairs = commandsLoader.directionsObj().get("upstairs");
    private List<String> downstairs = commandsLoader.directionsObj().get("downstairs");
    private List<String> allDirections = commandsLoader.allDirections();
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
    private List<String> audio = commandsLoader.allAudio();


    // CONSTRUCTOR
    Game(Prompter var1) throws Exception {
        prompter = var1;
    }

    void execute() throws Exception {
        boolean runGame = true;
        Audio.audio();
        welcome();
        createPlayer();
        promptToPlay();
        instructions();
        while (runGame) {
            showStatus();
            logic(directions);
        }
    }

    private void logic(Map area) throws Exception {
        String input = prompter.prompt(">").trim().toLowerCase();
        Console.clear();
        List<String> textParser = TextParser.textParser(input);

        if (textParser.size()>=2 && go.contains(textParser.get(0))) {
            go(area, textParser);
        } else if (get.contains(textParser.get(0))) {
            get(area, textParser);
        } else if (look.contains(textParser.get(0))) {
            look(area,textParser);
        } else if (talk.contains(textParser.get(0))) {
            talk(area,textParser);
        } else if(solve.contains(textParser.get(0))) {
            solve();
        } else if (quit.contains(textParser.get(0))) {
            quit();
        } else if(help.contains(textParser.get(0))){
            help();
        } else if(restart.contains(textParser.get(0))){
            restart();
        } else if (map.contains(textParser.get(0))){
            displayLocation();
        } else if(audio.contains(textParser.get(0))){
            audio(textParser.get(0));
        }
    }

    private void ascii(String currentLocation) {
        try {
            String art = Files.readString(Path.of("resources/Ascii/" + currentLocation.toLowerCase() + ".txt"));
            System.out.println("\033[37m" + art + "\033[0m");
        }
        catch (Exception e){
            System.out.println("There is no art");
        }
    }

    private void audio(String input) throws UnsupportedAudioFileException, LineUnavailableException, IOException {
        if (stop.contains(input)){
            Audio.stopAudio();
        } else if(play.contains(input)) {
            Audio.startAudio();
        }
    }

    private void displayLocation() throws Exception{
        Console.clear();
        String map = Files.readString(Path.of("resources/Ascii/map.txt"));
        String colorCoded = "\033[31m" +currentLocation.toUpperCase()+" \033[0m";
        String newMap = map.replaceFirst(currentLocation.toLowerCase(), colorCoded);
        System.out.println(newMap);
        prompter.prompt("Press enter to continue\n");
        Console.clear();
    }

    private void createPlayer() {
        System.out.println();
        String name = prompter.prompt("Please enter player's name: ").toUpperCase(Locale.ROOT);
        System.out.println();
        System.out.println("Welcome to Meowmi Vice " +  name);
        System.out.println();
    }

    private void go(Map area, List<String> input) {
        String direction = getDirection(input);

        if (input.get(1).equals("back")){
            String temp = currentLocation;
            currentLocation = prev;
            prev = temp;
        }

        else if (area.containsKey(direction)) {
            prev = currentLocation;
            currentLocation = area.get(direction).toString();
        }else {
            System.out.println("That is an invalid direction to go!");
        }
        directions = ((Map) locations.get(currentLocation));
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

    private void get(Map area, List<String> input){
        if (area.containsKey("isClue")) {
            inventory.add(area.get("name").toString());
            plug = area.get("name").toString();
            directions.remove("item");
//            System.out.println("You added " + input.get(1));

        } else if (!(area.containsKey("isClue"))){
//            System.out.println("There is no clue here");
            plug = "There is no clue here";
        } else{
            // statement never reached
            System.out.println("One must look at the item first to find the clue");
        }
    }

    private void help() throws IOException {
        String help = Files.readString(Path.of("resources/Text/commands.txt"));
        System.out.println(help);
    }

    private void instructions() throws IOException {
        String banner = Files.readString(Path.of("resources/Text/instructions.txt"));
        Console.clear();
        System.out.println(banner);
        prompter.prompt("Press enter to continue");
    }

    private void look(Map area, List<String> input) throws Exception {
        Map item = ((Map) directions.get("item"));
        Map npc = ((Map) directions.get("npc"));
        if (input.size() == 1){
            if (area.containsKey("npc") && area.containsKey("item")) {
//                System.out.println(npc.get("name") + " and a " + item.get("name") + " are at this location.");
                plug = npc.get("name") + " and a " + item.get("name") + " are at this location.";
            } else if (area.containsKey("npc") && !(area.containsKey("item"))){
//                System.out.println(npc.get("name") + " is at this location.");
                plug = npc.get("name") + " is at this location.";
            } else if(area.containsKey("item") && !(area.containsKey("npc"))){
//                System.out.println("There is a " + item.get("name") + " in this location.");
                plug = "There is a " + item.get("name") + " in this location.";
            } else {
//                System.out.println("There is nothing in this location to look at.");
                plug = "There is nothing in this location to look at.";
            }
            // if user looks at an item, recall the logic so that user can interact with it
        } else if (area.containsKey(input.get(1))){
            Map itemInput = ((Map) area.get(input.get(1)));
//            System.out.println(itemInput.get("description"));
            plug = itemInput.get("description").toString();
            showStatus();
            logic(itemInput);
        }
        else {
//            System.out.println("Cant look there");
//            TimeUnit.SECONDS.sleep(2);
//            Console.clear();
            plug = "Can't look there";
        }
    }

    private void playAgain() throws Exception {
        boolean validInput = false;
        while (!validInput) {
            String play = prompter.prompt("Please enter [P] to play again or [Q] to exit the game: ","p|q|P|Q","\nThat is not a valid input!\n");
            System.out.println();
            validInput = true;
            if ("P".equals(play) || "p".equals(play)) {
                Console.clear();
                execute();
            } else {
                quit();
            }
        }
    }

    private void promptToPlay() throws InterruptedException {
        boolean validInput = false;
        while (!validInput) {
            String play = prompter.prompt("Please enter [S] to start the game or [Q] to exit the game: ","s|q|S|Q","\nThat is not a valid input!\n");
            Console.clear();
            validInput = true;
            if ("S".equals(play) || "s".equals(play)) {
                continue;

            } else {
                quit();
            }
        }
    }

    private void quit() throws InterruptedException {
        System.out.println("Are you you sure you want to quit? (Y|N)");
        String confirm = prompter.prompt(">").strip().toLowerCase(Locale.ROOT);
        if ("yes".equals(confirm) || "y".equals(confirm)){
            System.out.println("Exiting the game...");
            TimeUnit.SECONDS.sleep(2);
            System.exit(0);
        }
    }

    private void restart() throws Exception {
        System.out.println("Are you you sure you want to restart? (Y|N)");
        String confirm = prompter.prompt(">").strip().toLowerCase(Locale.ROOT);
        if ("yes".equals(confirm) || "y".equals(confirm)){
            System.out.println("Restarting the game...");
            TimeUnit.SECONDS.sleep(2);
            execute();
        }
    }

    private void showStatus(){
        ascii(currentLocation);
        System.out.println(plug);
        plug = "";
        System.out.println("\033[34m ===========================");
        System.out.println("You are in the " + currentLocation);
        System.out.println(directions.get("description"));
//        if(item.containsKey("description")) System.out.println(item.get("description"));
        System.out.println("Inventory:" + inventory);
        System.out.println("Enter help to see a list of available commands");
        System.out.println("===========================");
        System.out.println("Directions you can go: " + showDirections(currentLocation) + "\033[0m");
    }

    private String showDirections(String currentLocation) {
        Map<String,String> directionsMap =  (Map) directions.get("directions");
        return directionsMap.keySet().toString();
    }

    private void solve() throws Exception {
        if(inventory.size() == 0){
            String banner = Files.readString(Path.of("resources/Ascii/pdog3.txt"));
            System.out.println(banner);
            prompter.prompt("Press enter to continue");
            Console.clear();
            return;
        }
        //print the ascii of the dog
        String banner = Files.readString(Path.of("resources/Ascii/pdog.txt"));
        System.out.println(banner);
        // Hard coded culprit, subject to change
        String culprit = prompter.prompt(">").strip().toLowerCase();
        Console.clear();
        banner = Files.readString(Path.of("resources/Ascii/pdog2.txt"));
        System.out.println(banner);
        Set<String> evidence = new HashSet<>(getEvidence()); // user picks out the evidence to provide

        // TODO change the hard coded nature
        //        list<> suspects
        //        player can choose the suspect
        //             in the if() we check supects.get(userinput) to see if their is culprit is true
        // Hard coded required evidence, subject to change
        Set<String> requiredEvidence = new HashSet<>(Arrays.asList("dog hair", "receipt", "insurance policy"));
        // If both cases are true, you win
        if(culprit.equals("hamione granger") && evidence.equals(requiredEvidence)){
            System.out.println("Congratulations you solved the mystery!");
            playAgain();
        }
        else {
            count++;
            if (count > 2) {
                System.out.println("You Lost. The culprit got away!");
                playAgain();
            }
            System.out.println("Sorry please collect more clues or try again.");
        }
    }

    // Method to return evidence Set for solving
    private ArrayList<String> getEvidence() {
        ArrayList<String> evidence = new ArrayList<>();
        List<String> copy = inventory;
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
            System.out.println("Add, Remove, Solve");

            String choice = prompter.prompt(">").strip().toLowerCase();
            try{
                // Add to the evidence
                if (choice.equals("a") || choice.equals("add")){
                    System.out.println("What index item would you like to add?");
                    String input = prompter.prompt(">").strip().toLowerCase();
                    int index = Integer.parseInt(input) - 1;
                    if(!evidence.contains(inventory.get(index))) {
                        evidence.add(inventory.get(index));
                        copy.remove(inventory.get(index));
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
            }
        }
        return evidence;
    }

    private void talk(Map area, List<String> input){
        //weird logic, fix later
        // TODO npc synonym list
        if (area.containsKey("npc") && input.size()>=2 && input.get(1).equals("npc")) {
            Map npc = ((Map) area.get("npc"));
            ArrayList<String> randDialogueList = (ArrayList<String>) npc.get("randdialogue"); // list from obj value
            int rand = new Random().nextInt(randDialogueList.size()); // make random int from size of list
//            System.out.println(npc.get("name") + ": " + npc.get("dialogue") + randDialogueList.get(rand));
            plug = npc.get("name") + ": " + npc.get("dialogue") + randDialogueList.get(rand);
        }
        else if (area.containsKey("npc")){
            plug = "Talk to who?";
        }
        else {
//            System.out.println("There is no one to talk to");
            plug = "There is no one to talk to";
        }
    }

    private void welcome() throws IOException {
        String banner = Files.readString(Path.of("resources/Text/splashbanner.txt"));
        System.out.println("\033[36m" + banner + "\033[0m");
        banner = Files.readString(Path.of("resources/Text/splashbanner2.txt"));
        System.out.println("\033[01;31m" + banner + "\033[0m");
    }
}