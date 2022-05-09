package com.meowmivice.game.reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.meowmivice.game.cast.CommandsLoader;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class TextParser {
    public static JSONObject locations() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/locations.json"))));

//        new FileReader("resources/Json/locations.json"));
        // typecasting obj to JSONObject
        return (JSONObject) obj;
    }

    private static void filter(List<String> userInput) {
        List<String> filterWords = new ArrayList<>(Arrays.asList("the", "to", "with"));
        for (String item : filterWords) {
            userInput.remove(item);
        }
    }

    public static List<String> textParser(String input) throws IOException, ParseException {
        // verbs();
        // pulls the list of all valid verbs from CommandsLoader
        CommandsLoader commandsLoader = new CommandsLoader();
        List<String> verbs = commandsLoader.allVerbs();

        List<String> userInput = new ArrayList<>(Arrays.asList(input.split(" ")));
        filter(userInput);

        if (userInput.size() == 3 && verbs.contains(userInput.get(0) + " " + userInput.get(1))){
            List<String> resultArr = new ArrayList<>();
            resultArr.add(userInput.get(0) + " " + userInput.get(1));
            resultArr.add(userInput.get(2));
            return resultArr;
        }

        if(!(verbs.contains(userInput.get(0)))) {
            //TODO it appears at the top of the ascii, not at the bottom
            System.out.println("That is an invalid input!");
        }
        return userInput;
    }
}