package com.meowmivice.game.cast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Culprit {

    private String name;
    private Set<String> evidence;

    Culprit(String name, Set<String> evidence) {
        setName(name);
        setEvidence(evidence);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<String> getEvidence() {
        return evidence;
    }

    public void setEvidence(Set<String> evidence) {
        this.evidence = evidence;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": name= " + getName()
                + ", description= " + getEvidence();
    }
}