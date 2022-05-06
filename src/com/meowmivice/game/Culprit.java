package com.meowmivice.game;

import java.util.ArrayList;
import java.util.Map;

public class Culprit {

    private String name;
    private ArrayList<String> evidence;

    Culprit(String name, ArrayList<String> evidence) {
        setName(name);
        setEvidence(evidence);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<String> getEvidence() {
        return evidence;
    }

    public void setEvidence(ArrayList<String> evidence) {
        this.evidence = evidence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": name= " + getName()
                + ", description = " + getEvidence();
    }
}