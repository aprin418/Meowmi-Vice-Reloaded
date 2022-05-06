package com.meowmivice.game;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

class CulpritLoader {
    private JSONObject culpritObj;

    public CulpritLoader() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        // read the file
        // private BufferedReader in;
        InputStreamReader culpritReader = new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/Culprit.json")));
        // create the array to drill into
        culpritObj = (JSONObject) jsonParser.parse(culpritReader);
    }

    public Culprit load() {
        String name = (String) culpritObj.get("name");
        ArrayList<String> ev = (ArrayList<String>) culpritObj.get("evidence");
        Set<String> evidence = new HashSet<>(ev);
        Culprit culprit = new Culprit(name, evidence);
        return culprit;
    }
}