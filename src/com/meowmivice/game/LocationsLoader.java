package com.meowmivice.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class LocationsLoader {
    private JSONParser jsonParser;
    // private BufferedReader in;
    private InputStreamReader locReader;
    private JSONArray locObj;
    private List<String> verbs = new ArrayList<>();
    private List<String> audio = new ArrayList<>();
    private List<String> directions = new ArrayList<>();
    private Location location;

    public LocationsLoader() throws IOException, ParseException {
        jsonParser = new JSONParser();
        locReader = new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/Locations2.json")));
        // Object commObj = jsonParser.parse(commReader); // or
        locObj = (JSONArray) jsonParser.parse(locReader);
    }

    public Map<String, Location> load() {
        Map<String, Location> result = new HashMap<>();
        NPC npc = null;
        Item item = null;

        for (Object o : locObj) {
            JSONObject obj = (JSONObject) o;
            String name = (String) obj.get("name");
            String description = (String) obj.get("description");
            JSONObject directions = (JSONObject) obj.get("directions");

            if (obj.containsKey("npc")) {
                JSONObject npcObj = (JSONObject) obj.get("npc");
                String npcName = (String) npcObj.get("name");
                String npcDialogue = (String) npcObj.get("dialogue");
                JSONArray npcRandDialogue = (JSONArray) npcObj.get("randDialogue");
                npc = new NPC(npcName, npcDialogue, npcRandDialogue);
            }

            if (obj.containsKey("item")) {
                JSONObject itemObj = (JSONObject) obj.get("item");
                String itemName = (String) itemObj.get("name");
                String itemDescription = (String) itemObj.get("description");
                JSONObject itemClue = (JSONObject) itemObj.get("clue");
                item = new Item(itemName, itemDescription, itemClue);
            }

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
            result.put(name, location);
        }
        return result;
    }
}