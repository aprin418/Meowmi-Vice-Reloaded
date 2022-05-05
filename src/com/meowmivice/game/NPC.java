package com.meowmivice.game;

import java.util.ArrayList;

public class NPC {
    private String name;
    private String dialogue;
    private ArrayList<String> randDialogue;

    NPC(String name, String dialogue, ArrayList<String> randDialogue) {
        setName(name);
        setDialogue(dialogue);
        setRandDialogue(randDialogue);
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
}