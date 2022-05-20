package com.meowmivice.game.cast;

import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.*;

public class GameScreenLoaderTest {

    @Test
    public void load_shouldReturnPopulatedList() throws IOException, ParseException {
        LocationsLoader locLoader = new LocationsLoader();
        Map<String, Location> locations = locLoader.load();

        assertEquals(8, locations.size());

        // Test location with both npc and item
        String input = "Kitchen";
        Location location = locations.get(input);
        // name
        assertEquals("Kitchen", location.getName());
        // description
        assertEquals("A bright and sunny kitchen with a dead mouse in the center and an item that was knocked over. Pawficer Fluffenstuff is waiting to give you a summary.", location.getDescription());
        // direction
        assertEquals("Backyard", location.getDirections().get("north"));
        // npc name
        assertEquals("Pawficer Fluffenstuff", location.getNpc().getName());
        // item name
        assertEquals("trash can", location.getItem().getName());
        // clue name
        assertEquals("dog hair", location.getItem().getClue().getName());

        // Test location with no npc or item
        String input2 = "Hallway";
        Location location2 = locations.get(input2);
        // name
        assertEquals("Hallway", location2.getName());
        // direction
        assertEquals("Bedroom", location2.getDirections().get("east"));
        // assert no items or npcs in Hallway
        assertNull(location2.getItem());
        assertNull(location2.getNpc());

        // Test location with just npc
        String input3 = "Living Room";
        Location location3 = locations.get(input3);
        // npc name
        assertEquals("Birdie Holly", location3.getNpc().getName());
        // assert that no item is in Living Room
        assertNull(location3.getItem());

        // Test location with just item
        String input4 = "Bedroom";
        Location location4 = locations.get(input4);
        // item name
        assertEquals("shoe box", location4.getItem().getName());
        // clue name
        assertEquals("key", location4.getItem().getClue().getName());
        // isClue(not used)
        assertEquals("True", location4.getItem().getClue().getIsClue());
        // assert that no npc is in Bedroom
        assertNull(location4.getNpc());
    }
}