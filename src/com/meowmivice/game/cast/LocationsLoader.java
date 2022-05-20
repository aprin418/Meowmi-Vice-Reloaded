package com.meowmivice.game.cast;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// author mm
// for use with Locations2.json (locations.json no longer used and can be deleted)
// associated test in test dir

public class LocationsLoader {
    private JSONArray locArr;

    public LocationsLoader() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        // read the file
        InputStreamReader locReader = new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/Locations2.json")));
        // create the array to drill into
        locArr = (JSONArray) jsonParser.parse(locReader);
    }

    public Map<String, Location> load() {
        // make a map to add locations (location name, location object)
        Map<String, Location> result = new HashMap<>();
        Location location;
        NPC npc = null;
        Item item = null;

        // for each object in the JSON Array, define location name, description, directions
        for (Object o : locArr) {
            JSONObject obj = (JSONObject) o;
            String name = (String) obj.get("name");
            String description = (String) obj.get("description");
            JSONObject directions = (JSONObject) obj.get("directions");

            // if the object contains an npc, define npc name, dialogue, and random dialogue
            if (obj.containsKey("npc")) {
                JSONObject npcObj = (JSONObject) obj.get("npc");
                String npcName = (String) npcObj.get("name");
                String npcDialogue = (String) npcObj.get("dialogue");
                JSONArray npcRandDialogue = (JSONArray) npcObj.get("randDialogue");
                Boolean isVisited = (Boolean) npcObj.get("isVisited");
                // make new NPC object with name, dialogue, and random dialogue
                npc = new NPC(npcName, npcDialogue, npcRandDialogue, isVisited);
            }

            // if the object contains an item, define item name, description
            // and clue name and description, and isClue(not used)
            if (obj.containsKey("item")) {
                JSONObject itemObj = (JSONObject) obj.get("item");
                String itemName = (String) itemObj.get("name");
                String itemDescription = (String) itemObj.get("description");
                // clue in item
                JSONObject clueObj = (JSONObject) itemObj.get("clue");
                String clueName = (String) clueObj.get("name");
                String clueDescription = (String) clueObj.get("description");
                String isClue = (String) clueObj.get("isClue"); // not used
                // make new Clue object with clue name, description, and isClue
                Clue clue = new Clue(clueName, clueDescription, isClue);
                // make new Item object with item name, decription, and Clue object
                item = new Item(itemName, itemDescription, clue);
            }
            // depending on if the object has an npc or item, both, or none
            // choose how to make Location object

            // with both npc and item
            if (obj.containsKey("npc") && obj.containsKey("item")) {
                location = new Location(name, description, directions, npc, item);
            }
            // with just npc and no item
            else if (obj.containsKey("npc") && !obj.containsKey("item")) {
                location = new Location(name, description, directions, npc);
            }
            // with just item and no npc
            else if (!obj.containsKey("npc") && obj.containsKey("item")) {
                location = new Location(name, description, directions, item);
            }
            // with no npc or item
            else {
                location = new Location(name, description, directions);
            }
            // add locations to the map
            result.put(name, location);
        }
        return result;
    }

}