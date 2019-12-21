package com.mohsenoid.rickandmorty.data;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SerializerTest {

    @Test
    public void testSerializeIntegerArray() {
        // GIVEN
        List<Integer> input = new ArrayList<>();
        input.add(2);
        input.add(8);
        input.add(10);

        String expected = "2,8,10";

        // WHEN
        String actual = Serializer.serializeIntegerList(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeEmptyIntegerArray() {
        // GIVEN
        List<Integer> input = new ArrayList<>();
        String expected = "";

        // WHEN
        String actual = Serializer.serializeIntegerList(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeNullIntegerArray() {
        assertNull(Serializer.serializeIntegerList(null));
    }

    @Test
    public void testSerializeStringArray() {
        // GIVEN
        List<String> input = new ArrayList<>();
        input.add("A");
        input.add("BC");
        input.add("DE");

        String expected = "A,BC,DE";

        // WHEN
        String actual = Serializer.serializeStringList(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeEmptyStringArray() {
        // GIVEN
        List<String> input = new ArrayList<>();
        String expected = "";

        // WHEN
        String actual = Serializer.serializeStringList(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testSerializeNullStringArray() {
        assertNull(Serializer.serializeStringList(null));
    }

    @Test
    public void testDeserializeStringArray() {
        // GIVEN
        String input = "A,BC,DE";

        List<String> expected = new ArrayList<>();
        expected.add("A");
        expected.add("BC");
        expected.add("DE");

        // WHEN
        List<String> actual = Serializer.deserializeStringList(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeEmptyStringArray() {
        // GIVEN
        String input = "";
        List<String> expected = new ArrayList<>();

        // WHEN
        List<String> actual = Serializer.deserializeStringList(input);

        // THEN
        assertEquals(expected, actual);
    }

    @Test
    public void testDeserializeNullStringArray() {
        assertNull(Serializer.deserializeStringList(null));
    }
}