package com.meowmivice.game.cast;

// author mm
// Clue object
// clue is within item within location in Locations2.json

public class Clue {
    private String name;
    private String description;
    // "isCLue" is most likely no longer used and can be removed from here, Locations2.json, and LocationsLoader
    private String isClue;

    // a clue has a name, description, and isClue(not used)
    Clue(String name, String description, String isClue) {
        setName(name);
        setDescription(description);
        setIsClue(isClue);
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

    public String getIsClue() {
        return isClue;
    }

    public void setIsClue(String isClue) {
        this.isClue = isClue;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": name= " + getName()
                + ", description= " + getDescription();
    }
}