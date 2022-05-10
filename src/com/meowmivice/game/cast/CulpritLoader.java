package com.meowmivice.game.cast;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

// author mm
// could be made static
// associated test is test dir

public class CulpritLoader {
    private JSONObject culpritObj;

    public CulpritLoader() throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        // read the file
        InputStreamReader culpritReader = new InputStreamReader(Objects.requireNonNull(JSONParser.class.getResourceAsStream("/Json/Culprit.json")));
        // create the object to drill into
        culpritObj = (JSONObject) jsonParser.parse(culpritReader);
    }

    public Culprit load() {
        // find name
        String name = (String) culpritObj.get("name");
        // find evidence
        ArrayList<String> ev = (ArrayList<String>) culpritObj.get("evidence");
        // make ev a Set so order doesn't matter
        Set<String> evidence = new HashSet<>(ev);
        // make a new Culprit object with name and evidence
        Culprit culprit = new Culprit(name, evidence);
        return culprit;
    }
}