package com.mohsenoid.rickandmorty.util.extension

import androidx.annotation.VisibleForTesting
import com.google.gson.Gson

@VisibleForTesting
private val SEPARATOR = ","

fun List<Int>.serializeIntegerList(): String {
    if (isEmpty()) return ""

    val stringBuilder = StringBuilder()
    forEach {
        if (stringBuilder.isNotEmpty()) stringBuilder.append(SEPARATOR)
        stringBuilder.append(it)
    }
    return stringBuilder.toString()
}

fun List<String>.serializeStringList(): String {
    if (isEmpty()) return ""

    val stringBuilder = StringBuilder()
    forEach {
        if (stringBuilder.isNotEmpty()) stringBuilder.append(SEPARATOR)
        stringBuilder.append(it)
    }
    return stringBuilder.toString()
}

fun String.deserializeStringList(): List<String> {
    return if (isBlank()) emptyList()
    else trim().split(SEPARATOR).toList()
}

fun List<String>.mapStringListToIntegerList(): List<Int> {
    return map { it.toInt() }
}

fun Any.toJson(): String {
    return Gson().toJson(this)
}

inline fun <reified T> String.fromJson(): T {
    return Gson().fromJson(this, T::class.java)
}
