package com.mohsenoid.rickandmorty.data.exception;

public class NoOfflineDataException extends Exception {

    public NoOfflineDataException() {
        super("No offline data available!");
    }
}
