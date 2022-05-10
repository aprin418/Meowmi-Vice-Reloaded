package com.meowmivice.game.cast;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

// author mm
// Culprit object
// may not be needed if CulpritLoader is made static

public class Culprit {

    private String name;
    private Set<String> evidence;

    // a culprit has a name and list of evidence
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