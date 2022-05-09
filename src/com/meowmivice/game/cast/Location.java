package com.meowmivice.game.cast;

import java.util.Map;

public class Location {
    private String name;
    private String description;
    private Map<String, String> directions;
    private NPC npc;
    private Item item;

    // Constructor for location without npc or item
    Location(String name, String description, Map<String,String> directions) {
        setName(name);
        setDescription(description);
        setDirections(directions);
    }
    // Constructor for location with just npc
    Location(String name, String description, Map<String,String> directions, NPC npc) {
        this(name, description, directions);
        setNpc(npc);
    }
    // Constructor for location with just item
    Location(String name, String description, Map<String,String> directions, Item item) {
        this(name, description, directions);
        setItem(item);
    }
    // Constructor for location with npc and item
    Location(String name, String description, Map<String,String> directions, NPC npc, Item item) {
        this(name, description, directions, npc);
        setItem(item);
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