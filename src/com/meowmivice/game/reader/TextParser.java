package com.meowmivice.game.reader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import com.meowmivice.game.cast.CommandsLoader;
import com.meowmivice.game.logic.Logic;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class TextParser {
    // gets location from Json/locations.json
    public static JSONObject locations() throws IOException, ParseException {

        Object obj = new JSONParser().parse(new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/locations.json"))));
        return (JSONObject) obj;
    }

    //filters words for text parser. removes them if inputted
    private static void filter(List<String> userInput) {
        List<String> filterWords = new ArrayList<>(Arrays.asList("the", "to", "with"));
        for (String item : filterWords) {
            userInput.remove(item);
        }
    }


    public static List<String> textParser(String input) throws Exception {
        // verbs();
        // pulls the list of all valid verbs from CommandsLoader
        CommandsLoader commandsLoader = new CommandsLoader();
        List<String> verbs = commandsLoader.allVerbs();

        // splits the input into a list of the users input to be able to easily target the index
        List<String> userInput = new ArrayList<>(Arrays.asList(input.split(" ")));
        filter(userInput);

        // if user input size is equal to 3 and the first index and second index combined equal a verb in the verb list then return it. i.e "pick up"
        if (userInput.size() == 3 && verbs.contains(userInput.get(0) + " " + userInput.get(1))){
            List<String> resultArr = new ArrayList<>();
            resultArr.add(userInput.get(0) + " " + userInput.get(1));
            resultArr.add(userInput.get(2));
            return resultArr;
        }

        // if input is not in verb list then output invalid message. if it is valid then return it
        if(!(verbs.contains(userInput.get(0)))) {
            //TODO it appears at the top of the ascii, not at the bottom
            System.out.println("That is an invalid input!");
        }
        Logic.logic(userInput);
        return userInput;
    }
}