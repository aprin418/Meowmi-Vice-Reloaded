package com.meowmivice.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CommandsLoader {
    private JSONParser jsonParser;
    // private BufferedReader in;
    private FileReader commReader;
    private JSONObject commObj;
    private List<String> verbs = new ArrayList<>();
    private List<String> audio = new ArrayList<>();
    private List<String> directions = new ArrayList<>();

    // Planned to make static

    public CommandsLoader() throws IOException, ParseException {
//        jsonParser = new JSONParser();
//        commReader = new FileReader("resources/Json/Commands.json");
//        // Object commObj = jsonParser.parse(commReader); // or
//        commObj = (JSONObject) jsonParser.parse(commReader);

        // refactored to below so resources folder could read for jar file
        commObj = (JSONObject) new JSONParser().parse(new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/Commands.json"))));
    }

    // parse out verbs obj from Commands JSON
    public Map<String, ArrayList> verbsObj() {
        return (Map<String, ArrayList>) commObj.get("verbs");
    }

    // parse out directions obj from Commands JSON
    public Map<String, ArrayList> directionsObj() {
        return (Map<String, ArrayList>) commObj.get("directions");
    }

    // parse out audio obj from Commands JSON
    public Map<String, ArrayList> audioObj() {
        return (Map<String, ArrayList>) commObj.get("audio");
    }

    // create list of verb commands from verbs obj
    // can be used for help command to list valid verb commands
    public ArrayList<String> verbCommands() {
        return new ArrayList<>(verbsObj().keySet());
    }

    // create list of directions commands from directions obj
    // can be used for listing valid directions
    public ArrayList<String> directionCommands() {
        return new ArrayList<>(directionsObj().keySet());
    }

    // create list of audio commands from audio obj
    // can be used for listing valid audio commands
    public ArrayList<String> audioCommands() {
        return new ArrayList<>(audioObj().keySet());
    }

    // creates a new list that combines all verbs and their synonyms for all valid verbs
    public List<String> allVerbs() {
        for (Map.Entry<String, ArrayList> entry : verbsObj().entrySet()) {
            verbs.addAll(entry.getValue());
        }
        return verbs;
    }

    // creates a new list that combines all audio commands and their synonyms
    public List<String> allAudio() {
        for (Map.Entry<String, ArrayList> entry : audioObj().entrySet()) {
            audio.addAll(entry.getValue());
        }
        return audio;
    }

    // creates a new list that combines all directions and their synonyms
    public List<String> allDirections() {
        for (Map.Entry<String, ArrayList> entry : directionsObj().entrySet()) {
            directions.addAll(entry.getValue());
        }
        return directions;
    }
}