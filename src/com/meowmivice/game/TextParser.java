package com.meowmivice.game;
import java.io.IOException;
import java.util.*;
import java.io.FileReader;
import com.apps.util.Prompter;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

class TextParser {
    private static Prompter prompter;

    TextParser(Prompter var1) {
        prompter = var1;
    }

    static JSONObject locations() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new FileReader("resources/Json/locations.json"));
        // typecasting obj to JSONObject
        return (JSONObject) obj;
    }
    private static void filter(List<String> userInput) {
        List<String> filterWords = new ArrayList<>(Arrays.asList("the", "to"));
        for (String item : filterWords) {
            userInput.remove(item);
        }
    }

    static List<String> validGo(){
        List<String> validGo = new ArrayList<>(Arrays.asList("go", "move", "walk"));
        return validGo;
    }

    private static List<String> verbs(){
        List<String> verbs = new ArrayList<>(Arrays.asList("go", "get", "look", "talk", "move", "walk", "solve", "quit", "help", "commands", "restart","q"));
        return verbs;
    }

    static List<String> textParser() {
        verbs();

        List<String> userInput = new ArrayList<>(Arrays.asList(" "));

//        while (!(verbs.contains(userInput.get(0)) && (userInput.size() == 2 || "solve".equals(userInput.get(0))))) {
        while (!(verbs().contains(userInput.get(0)))) {
            // Trim the input for additional spaces and lowercase
            String input = prompter.prompt(">").trim().toLowerCase();

            userInput = new ArrayList<>(Arrays.asList(input.split(" ")));

            filter(userInput);
        }

        return userInput;
    }


}