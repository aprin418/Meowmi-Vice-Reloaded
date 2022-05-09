package com.meowmivice.game.cast.cast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LocationsLoader {
    private JSONArray locArr;

    public LocationsLoader() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        // read the file
        // private BufferedReader in;
        InputStreamReader locReader = new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/Locations2.json")));
        // create the array to drill into
        locArr = (JSONArray) jsonParser.parse(locReader);
    }

    public Map<String, Location> load() {
        Map<String, Location> result = new HashMap<>();
        Location location;
        NPC npc = null;
        Item item = null;

        // for each object in the JSON Array, define variables
        for (Object o : locArr) {
            JSONObject obj = (JSONObject) o;
            String name = (String) obj.get("name");
            String description = (String) obj.get("description");
            JSONObject directions = (JSONObject) obj.get("directions");

            // if the object contains an npc, define these variables
            if (obj.containsKey("npc")) {
                JSONObject npcObj = (JSONObject) obj.get("npc");
                String npcName = (String) npcObj.get("name");
                String npcDialogue = (String) npcObj.get("dialogue");
                JSONArray npcRandDialogue = (JSONArray) npcObj.get("randDialogue");
                // make new NPC object with NPC class
                npc = new NPC(npcName, npcDialogue, npcRandDialogue);
            }

            // if the object contains an item, define these variables
            if (obj.containsKey("item")) {
                JSONObject itemObj = (JSONObject) obj.get("item");
                String itemName = (String) itemObj.get("name");
                String itemDescription = (String) itemObj.get("description");
                JSONObject clueObj = (JSONObject) itemObj.get("clue");
                // make new Item object with Item class
                String clueName = (String) clueObj.get("name");
                String clueDescription = (String) clueObj.get("description");
                String isClue = (String) clueObj.get("isClue");
                Clue clue = new Clue(clueName, clueDescription, isClue);
                item = new Item(itemName, itemDescription, clue);
            }
            // depending on if the object has an npc or item, both, or none
            // choose how to make location object with Location class
            if (obj.containsKey("npc") && obj.containsKey("item")) {
                location = new Location(name, description, directions, npc, item);
            }
            else if (obj.containsKey("npc") && !obj.containsKey("item")) {
                location = new Location(name, description, directions, npc);
            }
            else if (!obj.containsKey("npc") && obj.containsKey("item")) {
                location = new Location(name, description, directions, item);
            }
            else {
                location = new Location(name, description, directions);
            }
            // add locations to the map
            result.put(name, location);
        }
        return result;
    }

}