package com.mohsenoid.rickandmorty.util.extension

import androidx.annotation.VisibleForTesting

@VisibleForTesting
private val SEPARATOR = ","

/**
 * Integer [List] extension function which serialize it to a CSV [String].
 *
 * @return a CSV String. It returns empty String if the [List] [isEmpty].
 */
fun List<Int>.serializeIntegerListToCsv(): String {
    if (isEmpty()) return ""

    val stringBuilder = StringBuilder()
    forEach {
        if (stringBuilder.isNotEmpty()) stringBuilder.append(SEPARATOR)
        stringBuilder.append(it)
    }
    return stringBuilder.toString()
}

/**
 * String [List] extension function which serialize it to a CSV [String].
 *
 * @return a CSV String. It returns empty String if the [List] [isEmpty].
 */
fun List<String>.serializeStringListToCsv(): String {
    if (isEmpty()) return ""

    val stringBuilder = StringBuilder()
    forEach {
        if (stringBuilder.isNotEmpty()) stringBuilder.append(SEPARATOR)
        stringBuilder.append(it)
    }
    return stringBuilder.toString()
}

/**
 * [String] extension function which deserialize a CSV [String] into a [List] of Strings.
 *
 * @return a [List] of split Strings. If the String [isBlank] this function returns [emptyList].
 */
fun String.deserializeStringList(): List<String> {
    return if (isBlank()) emptyList()
    else trim().split(SEPARATOR).toList()
}

/**
 * String [List] extension function which maps it to an integer [List].
 *
 * @return a [List] of integer.
 */
fun List<String>.mapStringListToIntegerList(): List<Int> {
    return map { it.toInt() }
}
