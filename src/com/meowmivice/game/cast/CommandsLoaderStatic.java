package com.meowmivice.game.cast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

// author mm

// static version of Commands Loader
// this version not in use
// incomplete test in test dir
// for use with Commands.json file to identify synonyms
// method for "inventory" missing

public class CommandsLoaderStatic {
    private static JSONObject commObj;
    private static List<String> verbs;
    private static List<String> audio;
    private static List<String> directions;

    private static JSONObject getCommObj() throws IOException, ParseException {
        // read the file and create the object to drill into
        return commObj = (JSONObject) new JSONParser().parse(new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/Commands.json"))));
    }

    // parse out verbs obj from Commands JSON
    public static Map<String, ArrayList> verbsObj() throws IOException, ParseException {
        JSONObject obj = getCommObj();
        return (Map<String, ArrayList>) obj.get("verbs");
    }

    // define each verb list of synonyms

    public static List<String> go() throws IOException, ParseException {
        return (List<String>) verbsObj().get("go");
    }

    public static List<String> look() throws IOException, ParseException {
        return (List<String>) verbsObj().get("look");
    }

    public static List<String> get() throws IOException, ParseException {
        return (List<String>) verbsObj().get("get");
    }

    public static List<String> talk() throws IOException, ParseException {
        return (List<String>) verbsObj().get("talk");
    }

    public static List<String> solve() throws IOException, ParseException {
        return (List<String>) verbsObj().get("solve");
    }

    public static List<String> restart() throws IOException, ParseException {
        return (List<String>) verbsObj().get("restart");
    }

    public static List<String> quit() throws IOException, ParseException {
        return (List<String>) verbsObj().get("quit");
    }

    public static List<String> help() throws IOException, ParseException {
        return (List<String>) verbsObj().get("help");
    }

    public static List<String> play() throws IOException, ParseException {
        return (List<String>) verbsObj().get("play");
    }

    public static List<String> stop() throws IOException, ParseException {
        return (List<String>) verbsObj().get("map");
    }

    public static List<String> map() throws IOException, ParseException {
        return (List<String>) verbsObj().get("map");
    }

    public static List<String> suspects() throws IOException, ParseException {
        return (List<String>) verbsObj().get("suspects");
    }

    public static List<String> save() throws IOException, ParseException {
        return (List<String>) verbsObj().get("save");
    }

    public static List<String> load() throws IOException, ParseException {
        return (List<String>) verbsObj().get("load");
    }

    // "inventory" command was added to json, but no associated method

    // parse out directions obj from Commands JSON
    public static Map<String, ArrayList> directionsObj() {
        return (Map<String, ArrayList>) commObj.get("directions");
    }

    // define each direction list of synonyms

    // list of north synonyms
    public static List<String> north() {
        return (List<String>) directionsObj().get("north");
    }
    // list of south synonyms
    public static List<String> south() {
        return (List<String>) directionsObj().get("south");
    }
    // list of east synonyms
    public static List<String> east() {
        return (List<String>) directionsObj().get("east");
    }
    // list of west synonyms
    public static List<String> west() {
        return (List<String>) directionsObj().get("west");
    }
    // list of upstairs synonyms
    public static List<String> upstairs() {
        return (List<String>) directionsObj().get("upstairs");
    }
    // list of upstairs synonyms
    public static List<String> downstairs() {
        return (List<String>) directionsObj().get("downstairs");
    }

    // parse out audio obj from Commands JSON
    public static Map<String, ArrayList> audioObj() {
        return (Map<String, ArrayList>) commObj.get("audio");
    }

    // create list of verb commands from verbs obj
    // can be used for help command to list valid verb commands
    public static ArrayList<String> verbCommands() throws IOException, ParseException {
        return new ArrayList<>(verbsObj().keySet());
    }

    // create list of directions commands from directions obj
    // can be used for listing valid directions
    public static ArrayList<String> directionCommands() {
        return new ArrayList<>(directionsObj().keySet());
    }

    // create list of audio commands from audio obj
    // can be used for listing valid audio commands
    public static ArrayList<String> audioCommands() {
        return new ArrayList<>(audioObj().keySet());
    }

    // creates a new list that combines all verbs and their synonyms for all valid verbs
    public static List<String> allVerbs() throws IOException, ParseException {
        for (Map.Entry<String, ArrayList> entry : verbsObj().entrySet()) {
            verbs.addAll(entry.getValue());
        }
        return verbs;
    }

    // creates a new list that combines all audio commands and their synonyms
    public static List<String> allAudio() {
        for (Map.Entry<String, ArrayList> entry : audioObj().entrySet()) {
            audio.addAll(entry.getValue());
        }
        return audio;
    }

    // creates a new list that combines all directions and their synonyms
    public static List<String> allDirections() {
        for (Map.Entry<String, ArrayList> entry : directionsObj().entrySet()) {
            directions.addAll(entry.getValue());
        }
        return directions;
    }
}