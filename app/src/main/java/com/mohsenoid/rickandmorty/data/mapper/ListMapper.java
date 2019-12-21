package com.mohsenoid.rickandmorty.data.mapper;

import java.util.List;

public interface ListMapper<I, O> {

    List<O> map(List<I> model);
}
