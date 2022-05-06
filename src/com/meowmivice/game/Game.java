package com.meowmivice.game;
import com.apps.util.Console;
import com.apps.util.Prompter;
import org.json.simple.JSONObject;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.TimeUnit;

class Game {
    LocationsLoader locLoader  = new LocationsLoader();
    Map<String, Location> mapLocations = locLoader.load();

    private static Prompter prompter;
    private static int count = 0;
    private static boolean checkCounter = false;

    private static String currentLocation = "Kitchen";
    private static String prev = "Kitchen";
    private Location currentSpot = mapLocations.get(currentLocation);

    private static String plug = "";
    private List<String> inventory = new ArrayList<>();
    private Map<String, String> suspectDialogue = new HashMap<>();
    private Map<String, String> cluesList = new HashMap<>();

    private JSONObject locations = TextParser.locations();
    private CommandsLoader commandsLoader = new CommandsLoader();

    private List<String> go = commandsLoader.verbsObj().get("go");
    private List<String> north = commandsLoader.directionsObj().get("north");
    private List<String> east = commandsLoader.directionsObj().get("east");
    private List<String> south = commandsLoader.directionsObj().get("south");
    private List<String> west = commandsLoader.directionsObj().get("west");
    private List<String> allDirections = commandsLoader.allDirections();

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
    private List<String> audio = commandsLoader.allAudio();


    // CONSTRUCTOR
    Game(Prompter var1) throws Exception {
        prompter = var1;
    }

    void execute() throws Exception {
        boolean runGame = true;

        Audio.audio();
        welcome();
        promptToPlay();
        instructions();
        while (runGame) {
            showStatus();
            logic();
        }

    }

    private void logic() throws Exception {
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
            quit();
        } else if(help.contains(textParser.get(0))){
            help();
        } else if(restart.contains(textParser.get(0))){
            restart();
        } else if (map.contains(textParser.get(0))){
            displayLocation();
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

    private void showInventory() {
        if(cluesList.size() == 0){
            plug = "Currently no clues collected";
        }
        else {
            System.out.println("What clue would you like to review?");
            System.out.println(cluesList.keySet());
            String input = prompter.prompt("> ").trim();
            plug = cluesList.getOrDefault(input, "Invalid clue");
        }
    }

    private void ascii(String currentLocation) throws IOException {
        try {
            FileReader.fileReaderWhite("/Ascii/"+ currentLocation.toLowerCase() + ".txt");
        }
        catch (Exception e){
            System.out.println("There is no art");
        }
    }

    private void displayLocation() throws Exception{
        mapFileReader("/Ascii/map.txt");
        prompter.prompt("Press enter to continue\n");
        Console.clear();
    }

    static String mapFileReader(String filename) throws IOException {
        try (var in = Game.class.getResourceAsStream(filename)) {
            Scanner scanner = new Scanner(in, StandardCharsets.UTF_8);
            while ( scanner.hasNextLine() ){
                String coloredMap = "\033[37m" + scanner.nextLine() +"\033[0m";
                String colorCoded = "\033[31m" +currentLocation.toUpperCase()+"\033[37m";
                System.out.println(coloredMap.replaceFirst(currentLocation.toLowerCase(), colorCoded));
                System.out.print("\033[0m");
            }
        } catch (IOException e) {
            throw new RuntimeException("Uncaught", e);
        }
        return filename;
    }

    private void go(List<String> input, String direction) {

        if (input.get(1).equals("back")){
            String temp = currentLocation;
            currentLocation = prev;
            prev = temp;
            checkCounter = false;
        }

        else if (currentSpot.getDirections().containsKey(direction)) {
            prev = currentLocation;
            currentLocation = currentSpot.getDirections().get(direction);
            checkCounter = false;
        }else {
            plug = "That is an invalid direction to go!";
        }
        currentSpot = mapLocations.get(currentLocation);
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
            inventory.add(currentItem.getClue().getName());
            mapLocations.get(currentLocation).setItem(null);

            cluesList.put(clue.getName(),
                    "Name: " + clue.getName() +
                    "\nDescription: " + clue.getDescription() +
                    "\nObtained from: " + currentItem.getName() +
                    "\nFound in: " + currentLocation);
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

    private void help() throws IOException {
        FileReader.fileReaderWhite("/Text/commands.txt");
    }

    private void instructions() throws IOException {
        Console.clear();
        FileReader.fileReaderWhite("/Text/instructions.txt");
        prompter.prompt("Press enter to continue");
        Console.clear();
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
            // if user looks at an item, recall the logic so that user can interact with it
        } else if (input.get(1).equals("item") && currentItem!=null){
            get(currentItem);
        }
        else {
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
                restart();
            } else {
                quit();
            }
        }
    }

    private void promptToPlay() throws InterruptedException {
        boolean validInput = false;
        while (!validInput) {
            String play = prompter.prompt("Please enter [S] to start the game or [Q] to exit the game: ","s|q|S|Q","\nThat is not a valid input!\n");
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
            // reset inventory
            inventory = new ArrayList<>();
            // stop the audio if its on
            Audio.stopAudio();
            // reset the current location
            currentLocation = "Kitchen";
            prev = "Kitchen";

            TimeUnit.SECONDS.sleep(2);
            Game game2 = new Game(new Prompter(new Scanner(System.in)));
            game2.execute();
        }
    }

    private void showStatus() throws IOException {
        ascii(currentLocation);
        System.out.println(plug);
        plug = "";
        System.out.println("\033[1;36m===========================");
        System.out.println("You are in the " + currentLocation);
        System.out.println(currentSpot.getDescription());
//        if(item.containsKey("description")) System.out.println(item.get("description"));
        System.out.println("Inventory:" +"\033[37m" + inventory + "\033[1;36m");
        System.out.println("Enter help to see a list of available commands");
        System.out.println("===========================");
        System.out.println("Directions you can go: " +"\033[37m" + showDirections(currentLocation) + "\033[0m");
    }

    private String showDirections(String currentLocation) {
        Map<String,String> directionsMap =  currentSpot.getDirections();
        return directionsMap.keySet().toString();
    }

    private void solve() throws Exception {
        if(inventory.size() == 0){
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

        // TODO change the hard coded nature
        //        list<> suspects
        //        player can choose the suspect
        //             in the if() we check supects.get(userinput) to see if their is culprit is true

        CulpritLoader culpLoader = new CulpritLoader();
        Culprit reqCulprit = culpLoader.load();
        // Set<String> requiredEvidence = new HashSet<>(Arrays.asList("dog hair", "receipt", "insurance policy"));
        // If both cases are true, you win
        if(culprit.equals(reqCulprit.getName()) && evidence.equals(reqCulprit.getEvidence())){
            System.out.println("Congratulations you solved the mystery!");
            playAgain();
            System.exit(0);
        }
        else {
            count++;
            if (count > 2) {
                System.out.println("You Lost. The culprit got away!");
                playAgain();
                System.exit(0);
            }
            System.out.println("Sorry please collect more clues or try again.");
        }
    }

    // Method to return evidence Set for solving
    private ArrayList<String> getEvidence() {
        ArrayList<String> evidence = new ArrayList<>();
        ArrayList<String> copy = new ArrayList<>(inventory);
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
        //weird logic, fix later
        // TODO npc synonym list
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
        if(!suspectDialogue.containsKey(name)){
            suspectDialogue.put(name,dialogue);
        }
    }

    private void showSuspects(){
        if(suspectDialogue.size() == 0){
            plug = "No suspects at this time";
        }
        else{
            System.out.println("Who do you want to talk to?");
            System.out.println(suspectDialogue.keySet().toString());
            String input = prompter.prompt(">").trim();
            plug = suspectDialogue.getOrDefault(input, "Don't know who that is");
        }
    }

    private void welcome() throws IOException, InterruptedException {
        FileReader.fileReader("/Text/splashbanner.txt");
        System.out.println();
        TimeUnit.SECONDS.sleep(2);
        System.out.println("Welcome to Meowmi Vice!");
    }

}