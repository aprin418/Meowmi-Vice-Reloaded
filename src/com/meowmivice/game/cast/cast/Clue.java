package com.meowmivice.game.cast.cast;

public class Clue {
    private String name;
    private String description;
    private String isClue;

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