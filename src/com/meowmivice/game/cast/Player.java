package com.meowmivice.game.cast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private static Player player = null;

    // fields needed for player
    private String currentLocation = "Kitchen"; //sets starting location to kitchen
    private List<String> inventory = new ArrayList<>(); //creates inventory for player
    private Map<String, String> clues = new HashMap<>(); // needed to add clues and then review clue descriptions
    private Map<String, String> suspects = new HashMap<>(); // adds suspect and their dialogue to map after they have been interacted with to later review
    private String previousLocation = "Kitchen"; // sets previous location, so you can enter "go back" and it takes you to the previous room

    //TODO
    //logic for player if needed


    public static Player getInstance() {
        if (player == null) {
            player = new Player();
        }
        return player;
    }

    public String getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(String currentLocation) {
        this.currentLocation = currentLocation;
    }

    public List<String> getInventory() {
        return inventory;
    }

    public Map<String, String> getClues() {
        return clues;
    }

    public Map<String, String> getSuspects() {
        return suspects;
    }

    public String getPreviousLocation() {
        return previousLocation;
    }

    public void setPreviousLocation(String previousLocation) {
        this.previousLocation = previousLocation;
    }

}