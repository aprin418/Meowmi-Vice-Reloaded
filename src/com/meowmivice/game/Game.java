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
    private static final TextParser parser = new TextParser(new Prompter(new Scanner(System.in)));
    private static int count = 0;
    private static String currentLocation = "Kitchen";
    private List<String> inventory = new ArrayList<>();
    private JSONObject locations = TextParser.locations();
    private Map directions = ((Map) locations.get(currentLocation));

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
            logic(directions, parser);
        }
    }

    private void welcome() throws IOException {
        String banner = Files.readString(Path.of("resources/Text/splashbanner.txt"));
        System.out.println(banner);
    }
    private void createPlayer() {
        System.out.println("\n");
        String name = prompter.prompt("Please enter player's name: ").toUpperCase(Locale.ROOT);
        System.out.println("Welcome to Meowmi Vice " +  name);
    }
    private void instructions() throws IOException {
        String banner = Files.readString(Path.of("resources/Text/instructions.txt"));
        System.out.println(banner);
    }

    private void promptToPlay() throws InterruptedException {
        boolean validInput = false;
        while (!validInput) {
            System.out.println("\n");
            String play = prompter.prompt("Please enter [S] to start the game or [Q] to exit the game: ","s|q|S|Q","\nThat is not a valid input!\n");
            validInput = true;
            if ("S".equals(play) || "s".equals(play)) {
                continue;

            } else {
                System.out.println("Exiting the game...");
                TimeUnit.SECONDS.sleep(2);
                System.exit(0);
            }
        }
    }

    private void showStatus(){
        System.out.println("===========================");
        System.out.println("You are in the " + currentLocation);
        System.out.println(directions.get("description"));
        System.out.println("Inventory:" + inventory);
        System.out.println("Enter help to see a list of available commands");
        System.out.println("===========================");
    }

    private void logic(Map area, TextParser parser) throws Exception {
        List<String> validGo = TextParser.validGo();
        List<String> textParser = TextParser.textParser();
//         If statement for movement
        if (validGo.contains(textParser.get(0))) {
            // we should already be in a specific room
            // Now I want to check if userInput.get(1) actually exists within the directions
            if (area.containsKey(textParser.get(1))) {
                currentLocation = area.get(textParser.get(1)).toString();
                directions = ((Map) locations.get(currentLocation));
            } else {
                System.out.println("That is an invalid input");
            }
        } else if (textParser.get(0).equals("get")) {
            if (area.containsKey("isClue")) {
                inventory.add(area.get("name").toString());
                directions.remove("item");
                System.out.println("You added " + textParser.get(1));

            } else {
                System.out.println("There is no clue here");
            }
        } else if (textParser.get(0).equals("look")) {
            // Look at the current map
            if (textParser.size() == 1 && area.containsKey("description")){
                System.out.println(area.get("description"));
            }
            // if user looks at an item, recall the logic so that user can interact with it
            else if (area.containsKey(textParser.get(1))){
                Map item = ((Map) area.get(textParser.get(1)));
                System.out.println(item.get("description"));
                parser = new TextParser(new Prompter(new Scanner(System.in)));
                logic(item, parser);
            }
            else {
                System.out.println("Cant look there");
            }
        }else if (textParser.get(0).equals("talk")) {
            if (area.containsKey("npc")) {
                Map npc = ((Map) area.get("npc"));
                System.out.println(npc.get("name") + ": " + npc.get("dialogue"));
            } else {
                System.out.println("There is no one to talk to");
            }
        }else if("solve".equals(textParser.get(0))) {
            if (inventory.size() > 1){
                System.out.println("Who do you think is guilty?");
                String solve = prompter.prompt(">").strip().toLowerCase(Locale.ROOT);
                if ("hamione granger".equals(solve)) {
                    System.out.println("Congratulations! You have solved the mystery!");
                    playAgain();
                } else {
                    count++;
                    if (count > 1) {
                        System.out.println("You Lost. The culprit got away!");
                        System.out.println("Exiting the game...");
                        TimeUnit.SECONDS.sleep(2);
                        System.exit(0);
                    }
                    System.out.println("Sorry please collect more clues or try again.");
                }
            } else {
                int remaining = 2-inventory.size();
                System.out.println("Please collect " + remaining + " more clues to try to solve.");
            }

        } else if ("quit".equals(textParser.get(0)) || "q".equals(textParser.get(0))) {
            System.out.println("Are you you sure you want to quit? (Y|N)");
            String confirm = prompter.prompt(">").strip().toLowerCase(Locale.ROOT);
            if ("yes".equals(confirm) || "y".equals(confirm)){
                System.out.println("Exiting the game...");
                TimeUnit.SECONDS.sleep(2);
                System.exit(0);
            }

        } else if("help".equals(textParser.get(0)) || "commands".equals(textParser.get(0))){
            String banner = Files.readString(Path.of("resources/Text/commands.txt"));
            System.out.println(banner);

        }else if("restart".equals(textParser.get(0))){
            System.out.println("Are you you sure you want to restart? (Y|N)");
            String confirm = prompter.prompt(">").strip().toLowerCase(Locale.ROOT);
            if ("yes".equals(confirm) || "y".equals(confirm)){
                System.out.println("Restarting the game...");
                TimeUnit.SECONDS.sleep(2);
                execute();
            }
        }
    }

    private void playAgain() throws Exception {
        boolean validInput = false;
        while (!validInput) {
            String play = prompter.prompt("Please enter [P] to play again or [Q] to exit the game: ","p|q|P|Q","\nThat is not a valid input!\n");
            validInput = true;
            if ("P".equals(play) || "p".equals(play)) {
                execute();
            } else {
                System.out.println("Exiting the game...");
                TimeUnit.SECONDS.sleep(2);
                System.exit(0);
            }
        }
    }
}