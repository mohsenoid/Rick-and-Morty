package com.mohsenoid.rickandmorty.data;

import androidx.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serializer {

    @VisibleForTesting()
    private static final String SEPARATOR = ",";

    public static String serializeIntegerList(List<Integer> input) {
        if (input == null) return null;
        if (input.size() == 0) return "";

        StringBuilder stringBuilder = new StringBuilder();
        for (int i : input) {
            if (stringBuilder.length() != 0) stringBuilder.append(SEPARATOR);
            stringBuilder.append(i);
        }

        return stringBuilder.toString();
    }

    public static String serializeStringList(List<String> input) {
        if (input == null) return null;
        if (input.size() == 0) return "";

        StringBuilder stringBuilder = new StringBuilder();
        for (String s : input) {
            if (stringBuilder.length() != 0) stringBuilder.append(SEPARATOR);
            stringBuilder.append(s);
        }

        return stringBuilder.toString();
    }

    public static List<String> deserializeStringList(String input) {
        if (input == null) return null;

        if (input.trim().equals("")) return new ArrayList<>();

        return Arrays.asList((input.split(SEPARATOR)));
    }

    public static List<Integer> mapStringListToIntegerList(List<String> input) {
        if (input == null) return null;

        List<Integer> output = new ArrayList<>();

        for (String s : input) {
            output.add(Integer.parseInt(s));
        }

        return output;
    }
}
