package com.mohsenoid.rickandmorty.test;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class DataFactory {

    private DataFactory() { /* this will prevent making a new object of this type */ }

    public static String randomString() {
        return UUID.randomUUID().toString();
    }

    public static List<String> randomStringList(int count) {
        List<String> list = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            list.add(DataFactory.randomString());
        }

        return list;
    }

    public static int randomInt() {
        return randomInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    public static int randomInt(int min, int max) {
        return ThreadLocalRandom.current().nextInt(min, max);
    }

    public static Boolean randomBoolean() {
        return Math.random() < 0.5;
    }
}
