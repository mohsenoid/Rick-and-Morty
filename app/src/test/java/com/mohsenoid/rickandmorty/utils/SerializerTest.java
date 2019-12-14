package com.mohsenoid.rickandmorty.utils;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class SerializerTest {

    @Test
    public void testSerializeStringArray() {
        // GIVEN
        List<String> input = new ArrayList<>();
        input.add("A");
        input.add("BC");
        input.add("DE");

        String expected = "A" + Serializer.SEPARATOR + "BC" + Serializer.SEPARATOR + "DE" + Serializer.SEPARATOR;

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
    public void testDeserializeStringArray() {
        // GIVEN
        String input = "A" + Serializer.SEPARATOR + "BC" + Serializer.SEPARATOR + "DE" + Serializer.SEPARATOR;

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
}