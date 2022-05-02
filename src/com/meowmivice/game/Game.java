package com.meowmivice.game;
import com.apps.util.Console;
import com.apps.util.Prompter;
import org.json.simple.JSONObject;
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
    private List<String> inventory = new ArrayList<>();
    private JSONObject locations = TextParser.locations();
    private Map directions = ((Map) locations.get(currentLocation));
    private List<String> audio = TextParser.validAudio();
    private List<String> go = TextParser.validGo();
    private List<String> get = TextParser.validGet();
    private List<String> help = TextParser.validHelp();
    private List<String> quit = TextParser.validQuit();
    private List<String> solve = TextParser.validSolve();
    private List<String> look = TextParser.validLook();
    private List<String> talk = TextParser.validTalk();
    private List<String> restart = TextParser.validRestart();

    // CONSTRUCTOR
    Game(Prompter var1) throws Exception {
        prompter = var1;
    }

    void execute() throws Exception {
        boolean runGame = true;
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

        if (go.contains(textParser.get(0))) {
            go(area, textParser);
        } else if (get.contains(textParser.get(0))) {
            get(area, textParser);
        } else if (look.contains(textParser.get(0))) {
            look(area,textParser);
        } else if (talk.contains(textParser.get(0))) {
            talk(area);
        } else if(solve.contains(textParser.get(0))) {
            solve();
        } else if (quit.contains(textParser.get(0))) {
            quit();
        } else if(help.contains(textParser.get(0))){
            help();
        } else if(restart.contains(textParser.get(0))){
            restart();
        }
    }

    private void createPlayer() {
        System.out.println();
        String name = prompter.prompt("Please enter player's name: ").toUpperCase(Locale.ROOT);
        System.out.println();
        System.out.println("Welcome to Meowmi Vice " +  name);
        System.out.println();
    }

    private void ascii(String currentLocation) {
        try {
            System.out.println(String.join("",currentLocation).toLowerCase() );
            String art = Files.readString(Path.of("resources/Ascii/" + currentLocation.toLowerCase() + ".txt"));
            System.out.println(art);
        }
        catch (Exception e){
            System.out.println("There is no art");
        }
    }

    private void go(Map area, List<String> input) {
        if (input.get(1).equals("back")){
            String temp = currentLocation;
            currentLocation = prev;
            prev = temp;
        }
        else if (area.containsKey(input.get(1))) {
            prev = currentLocation;
            currentLocation = area.get(input.get(1)).toString();
        }else {
            System.out.println("That is an invalid input!");
        }
        directions = ((Map) locations.get(currentLocation));
    }

    private void get(Map area, List<String> input){
        if (area.containsKey("isClue")) {
            inventory.add(area.get("name").toString());
            directions.remove("item");
            System.out.println("You added " + input.get(1));

        } else if (!(area.containsKey("isClue"))){
            System.out.println("There is no clue here");
        } else{
            System.out.println("One must look at the item first to find the clue");
        }
    }

    private void help() throws IOException {
        String help = Files.readString(Path.of("resources/Text/commands.txt"));
        System.out.println(help);
    }

    private void instructions() throws IOException {
        String banner = Files.readString(Path.of("resources/Text/instructions.txt"));
        System.out.println(banner);
    }

    private void look(Map area, List<String> input) throws Exception {
        Map item = ((Map) directions.get("item"));
        Map npc = ((Map) directions.get("npc"));
        if (input.size() == 1){
            if (area.containsKey("npc") && area.containsKey("item")) {
                System.out.println(npc.get("name") + " and a " + item.get("name") + " are at this location.");
            } else if (area.containsKey("npc") && !(area.containsKey("item"))){
                System.out.println(npc.get("name") + " is at this location.");
            } else if(area.containsKey("item") && !(area.containsKey("npc"))){
                System.out.println("There is a " + item.get("name") + " in this location.");
            } else {
                System.out.println("There is nothing in this location to look at.");
            }
            // if user looks at an item, recall the logic so that user can interact with it
        } else if (area.containsKey(input.get(1))){
            Map itemInput = ((Map) area.get(input.get(1)));
            System.out.println(itemInput.get("description"));
            showStatus();
            logic(itemInput);
        }
        else {
            System.out.println("Cant look there");
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
        System.out.println("===========================");
        System.out.println("You are in the " + currentLocation);
        System.out.println(directions.get("description"));
        System.out.println("Inventory:" + inventory);
        System.out.println("Enter help to see a list of available commands");
        System.out.println("===========================");
    }

    private void solve() throws Exception {

        System.out.println("Who do you think it is?");
        // Hard coded culprit, subject to change
        String culprit = prompter.prompt(">").strip().toLowerCase();

        System.out.println("What evidence do you have to support it?");
        Set<String> evidence = new HashSet<>(getEvidence()); // user picks out the evidence to provide
        // Hard coded required evidence, subject to change
        Set<String> requiredEvidence = new HashSet<>(Arrays.asList("dog hair", "receipt", "insurance policy"));
        // If both cases are true, you win
        if(culprit.equals("hamione granger") && evidence.equals(requiredEvidence)){
            System.out.println("Congratulations you win");
            playAgain();
        }
        else {
            count++;
            if (count > 2) {
                System.out.println("You Lost. The culprit got away!");
                System.out.println("Exiting the game...");
                TimeUnit.SECONDS.sleep(2);
                System.exit(0);
            }
            System.out.println("Sorry please collect more clues or try again.");
        }
    }

    // Method to return evidence Set for solving
    private ArrayList<String> getEvidence() {
        ArrayList<String> evidence = new ArrayList<>();
        boolean isDone = false;
        // As long as they don't specify to quit, loop continues
        while(!isDone){
            System.out.println("Current Collected Evidence: ");
            int i = 1;
            for (String item: inventory) {
                System.out.println(i + " " + item);
                i++;
            }
            System.out.println("Evidence to provide: " + evidence.toString());

            System.out.println("What would you like to do?");
            System.out.println("Add, Remove, Quit");

            String choice = prompter.prompt(">").strip().toLowerCase();

            // Add to the evidence
            if (choice.equals("a") || choice.equals("add")){
                try{
                    System.out.println("What index item would you like to add?");
                    String input = prompter.prompt(">").strip().toLowerCase();
                    int index = Integer.parseInt(input) - 1;
                    if(!evidence.contains(inventory.get(index))){
                        evidence.add(inventory.get(index));
                    }
                }
                catch (Exception e){
                    System.out.println("Invalid input");
                }
            }
            // Remove evidence
            else if (choice.equals("r") || choice.equals("remove")){
                try {

                    System.out.println("What item do you want to remove?");
                    String input = prompter.prompt(">").strip().toLowerCase();
                    evidence.remove(input);
                }
                catch (Exception e){
                    System.out.println("Cant remove that");
                }
            }
            // Exit loop
            else if (choice.equals("q") || choice.equals("quit")){
                isDone = true;
            }
        }
        return evidence;
    }

    private void talk(Map area){
        if (area.containsKey("npc")) {
            Map npc = ((Map) area.get("npc"));
            System.out.println(npc.get("name") + ": " + npc.get("dialogue"));
        } else {
            System.out.println("There is no one to talk to");
        }
    }

    private void welcome() throws IOException {
        String banner = Files.readString(Path.of("resources/Text/splashbanner.txt"));
        System.out.println(banner);
    }
}