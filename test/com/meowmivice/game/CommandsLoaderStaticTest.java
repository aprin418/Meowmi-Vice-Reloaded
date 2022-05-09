package com.meowmivice.game;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.*;

public class CommandsLoaderStaticTest {
    @Test
    public void go_shouldReturnPopulatedListOfGoSynonyms() throws IOException, ParseException {
        List<String> go = CommandsLoaderStatic.go();
        List<String> expectedGo = Arrays.asList("go", "move", "walk", "run");
        assertEquals(expectedGo, go);
    }
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