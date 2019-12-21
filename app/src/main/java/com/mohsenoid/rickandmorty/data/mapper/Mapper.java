package com.mohsenoid.rickandmorty.data.mapper;

public interface Mapper<I, O> {

    O map(I input);
}
