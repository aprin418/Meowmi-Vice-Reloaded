package com.meowmivice.game.cast;

import java.util.Map;

// author mm
// Location object
// each object in array of objects in Locations2.json is a location
// locations may or may not have an item, npc, or both

public class Location {
    private String name;
    private String description;
    private Map<String, String> directions;
    private NPC npc;
    private Item item;

    // Constructor for location without npc or item
    // has a name, description, and directions
    Location(String name, String description, Map<String,String> directions) {
        setName(name);
        setDescription(description);
        setDirections(directions);
    }
    // Constructor for location with just a npc
    // has a name, description, directions, and NPC object
    Location(String name, String description, Map<String,String> directions, NPC npc) {
        this(name, description, directions);
        setNpc(npc);
    }
    // Constructor for location with just an item
    // has a name, description, directions, and Item object
    Location(String name, String description, Map<String,String> directions, Item item) {
        this(name, description, directions);
        setItem(item);
    }
    // Constructor for location with npc and item
    // has a name, description, directions, NPC object, and Item object
    Location(String name, String description, Map<String,String> directions, NPC npc, Item item) {
        this(name, description, directions, npc);
        setItem(item);
    }

    public Location() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Map<String, String> getDirections() {
        return directions;
    }

    public void setDirections(Map<String, String> directions) {
        this.directions = directions;
    }

    public NPC getNpc() {
        return npc;
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
    }

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    @Override
    // TODO: Can I have different toStrings()
    public String toString() {
        return getClass().getSimpleName() + ": name= " + getName()
                + ", description= " + getDescription()
                + ", directions= " + getDirections().toString();
    }
}