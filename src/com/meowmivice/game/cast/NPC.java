package com.meowmivice.game.cast;

import java.util.ArrayList;

// author mm
// NPC object
// NPC is within location in locations2.json

public class NPC {
    private String name;
    private String dialogue;
    private ArrayList<String> randDialogue;

    private boolean isVisited;

    // a NPC has a name, dialogue, and list of random dialogue
    NPC(String name, String dialogue, ArrayList<String> randDialogue, Boolean isVisited) {
        setName(name);
        setDialogue(dialogue);
        setRandDialogue(randDialogue);
        setVisited(isVisited);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDialogue() {
        return dialogue;
    }

    public void setDialogue(String dialogue) {
        this.dialogue = dialogue;
    }

    public ArrayList<String> getRandDialogue() {
        return randDialogue;
    }

    public void setRandDialogue(ArrayList randDialogue) {
        this.randDialogue = randDialogue;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

}