package com.mohsenoid.rickandmorty.data;

public interface DataCallback<T> {

    void onSuccess(T t);

    void onError(Exception exception);
}
