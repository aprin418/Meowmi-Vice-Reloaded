package com.meowmivice.game.cast;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

// author mm

public class CommandsLoaderStaticTest {

    // test for verbs and their synonyms
    // only "go" tested so far
    @Test
    public void go_shouldReturnPopulatedListOfGoSynonyms() throws IOException, ParseException {
        List<String> go = CommandsLoaderStatic.go();
        List<String> expectedGo = Arrays.asList("go", "move", "walk", "run");
        assertEquals(expectedGo, go);
    }

    // test for directions and their synonyms
    // only north and south tested so far
    @Test
    public void directions_shouldReturnPopulatedListOfDirectionsSynonyms() {
        List<String> north = CommandsLoaderStatic.north();
        List<String> expectedNorth = Arrays.asList("north","forward", "straight");

        List<String> south = CommandsLoaderStatic.south();
        List<String> expectedSouth = Arrays.asList("south", "backward");


        assertEquals(expectedNorth, north);
        assertEquals(expectedSouth, south);
    }
}