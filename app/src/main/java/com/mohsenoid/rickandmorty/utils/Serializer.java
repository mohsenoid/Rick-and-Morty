package com.mohsenoid.rickandmorty.utils;

import androidx.annotation.VisibleForTesting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Serializer {

    @VisibleForTesting()
    static final String SEPARATOR = ",";

    public static String serializeStringList(List<String> input) {
        StringBuilder stringBuilder = new StringBuilder();
        for (String s : input) {
            stringBuilder.append(s);
            stringBuilder.append(SEPARATOR);
        }
        return stringBuilder.toString();
    }

    public static List<String> deserializeStringList(String input) {
        if (input.trim().equals("")) return new ArrayList<>();
        else return Arrays.asList((input.split(SEPARATOR)));
    }
}
