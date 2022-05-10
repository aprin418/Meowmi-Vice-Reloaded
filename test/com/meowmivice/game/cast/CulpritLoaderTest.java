package com.meowmivice.game.cast;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.*;

import static org.junit.Assert.*;

public class CulpritLoaderTest {

    @Test
    public void load_shouldReturnPopulatedCulprit() throws IOException, ParseException {
        CulpritLoader culpLoader = new CulpritLoader();
        Culprit culprit = culpLoader.load();

        // name matches
        assertEquals("hamione granger", culprit.getName());
        Set<String> requiredEv = culprit.getEvidence();
        // evidence in same order
        Set<String> input = new HashSet<>(Arrays.asList("insurance policy", "receipt", "loan statement"));
        // evidence in different order
        Set<String> input2 = new HashSet<>(Arrays.asList("receipt", "loan statement", "insurance policy"));
        // check that they match in same or different order
        assertEquals(input, requiredEv);
        assertEquals(input2, requiredEv);
    }
}