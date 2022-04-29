package com.meowmivice.game;
import java.util.*;

import java.io.FileReader;
import java.util.Iterator;
import java.util.Map;

//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.*;

class TextParser {
    public static void main(String[] args) {
        textParser();
    }
    private static void textParser() {
        String currentLocation = "S Forest";
        String input ="";

        // use the prompter
        Scanner scan = new Scanner(System.in);

        List<String> userInput = new ArrayList<>(Arrays.asList(" "));

        // the word list that we will filter out
        // Maybe get these from a filter out word json or an allowed words json
        List<String> filterWords = new ArrayList<>(Arrays.asList("the", "to"));

        // Appropriate verbs list
        List<String> verbs = new ArrayList<>(Arrays.asList("go","get","look","talk"));

        while(!verbs.contains(userInput.get(0)) && userInput.size() != 2){
            System.out.println("Enter a action");
            // displayValidCommands();
            input = scan.nextLine();
            // Trim the input for additional spaces and lowercase
            input = input.trim().toLowerCase();
            userInput = new ArrayList<>(Arrays.asList(input.split(" ")));
            // go through the filter - not a good filter
            for (String item: filterWords) {
                userInput.remove(item);
            }
        }
        if (userInput.get(0).equals("go")){
            currentLocation = userInput.get(1);
            System.out.println("You went " + userInput.get(1));
            System.out.println("You are now at " + currentLocation);
        }
        else if (userInput.get(0).equals("get")){
            System.out.println("You added " + userInput.get(1));
        }
        else if (userInput.get(0).equals("look")){
            System.out.println("description");
        }
        else if (userInput.get(0).equals("talk")){
            System.out.println("You talked to person");
        }
    }
}