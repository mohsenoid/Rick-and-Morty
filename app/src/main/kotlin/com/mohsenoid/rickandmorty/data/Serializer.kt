package com.mohsenoid.rickandmorty.data

import androidx.annotation.VisibleForTesting
import java.util.ArrayList

object Serializer {

    @VisibleForTesting
    private val SEPARATOR = ","

    fun serializeIntegerList(input: List<Int>): String {
        if (input.isEmpty()) return ""

        val stringBuilder = StringBuilder()
        input.forEach {
            if (stringBuilder.isNotEmpty()) stringBuilder.append(SEPARATOR)
            stringBuilder.append(it)
        }
        return stringBuilder.toString()
    }

    fun serializeStringList(input: List<String>): String {
        if (input.isEmpty()) return ""

        val stringBuilder = StringBuilder()
        input.forEach {
            if (stringBuilder.isNotEmpty()) stringBuilder.append(SEPARATOR)
            stringBuilder.append(it)
        }
        return stringBuilder.toString()
    }

    fun deserializeStringList(input: String): List<String> {
        return if (input.trim() == "") emptyList()
        else input.split(SEPARATOR).toList()
    }

    fun mapStringListToIntegerList(input: List<String>): List<Int> {
        val output = ArrayList<Int>()
        input.forEach {
            output.add(it.toInt())
        }
        return output
    }
}
