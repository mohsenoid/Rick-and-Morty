package com.mohsenoid.rickandmorty.data.mapper

interface Mapper<I, O> {

    fun map(input: I): O
}
