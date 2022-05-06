package com.meowmivice.game;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class CulpritLoaderTest {

    @Test
    public void load_shouldReturnPopulatedCulprit() throws IOException, ParseException {
        CulpritLoader culpLoader = new CulpritLoader();
        Culprit culprit = culpLoader.load();

        assertEquals("Hamione Granger", culprit.getName());
        // TODO: array test
    }
}