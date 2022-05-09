package com.meowmivice.game.cast;
import com.meowmivice.game.cast.cast.Culprit;
import com.meowmivice.game.cast.cast.CulpritLoader;
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

        assertEquals("hamione granger", culprit.getName());
        Set<String> requiredEv = culprit.getEvidence();
        // same order
        Set<String> input = new HashSet<>(Arrays.asList("insurance policy", "receipt", "loan statement"));
        // different order
        Set<String> input2 = new HashSet<>(Arrays.asList("receipt", "loan statement", "insurance policy"));
        assertEquals(input, requiredEv);
        assertEquals(input2, requiredEv);
    }
}