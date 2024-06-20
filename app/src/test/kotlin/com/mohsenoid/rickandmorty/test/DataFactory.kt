package com.mohsenoid.rickandmorty.test

import java.util.ArrayList
import java.util.UUID
import java.util.concurrent.ThreadLocalRandom

object DataFactory {

    fun randomString(): String = UUID.randomUUID().toString()

    fun randomStringList(count: Int): List<String> {
        val list: MutableList<String> = ArrayList()

        repeat(count) {
            list.add(randomString())
        }

        return list
    }

    fun randomInt(min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): Int =
        ThreadLocalRandom.current().nextInt(min, max)

    fun randomIntList(count: Int, min: Int = Int.MIN_VALUE, max: Int = Int.MAX_VALUE): List<Int> {
        val list: MutableList<Int> = ArrayList()

        repeat(count) {
            list.add(randomInt(min, max))
        }

        return list
    }

    fun randomBoolean(): Boolean = Math.random() < 0.5
}
