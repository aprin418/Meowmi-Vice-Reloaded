package com.meowmivice.game.cast.cast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Player {
    private static Player player = null;

    private String currentLocation = "Kitchen";
    private List<String> inventory = new ArrayList<>();
    private Map<String, String> clues = new HashMap<>();
    private Map<String, String> suspects = new HashMap<>();
    private String previousLocation = "Kitchen";

    //TODO
    //logic for player


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