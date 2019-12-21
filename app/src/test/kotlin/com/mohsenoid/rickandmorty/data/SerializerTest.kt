package com.mohsenoid.rickandmorty.data

import com.mohsenoid.rickandmorty.data.Serializer.deserializeStringList
import com.mohsenoid.rickandmorty.data.Serializer.serializeIntegerList
import com.mohsenoid.rickandmorty.data.Serializer.serializeStringList
import org.junit.Assert.assertEquals
import org.junit.Test

class SerializerTest {

    @Test
    fun `test serialize IntegerList`() {
        // GIVEN
        val input = listOf(2, 8, 10)
        val expected = "2,8,10"

        // WHEN
        val actual = serializeIntegerList(input)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test serialize empty IntegerList`() {
        // GIVEN
        val input = emptyList<Int>()
        val expected = ""

        // WHEN
        val actual = serializeIntegerList(input)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test serialize StringList`() {
        // GIVEN
        val input = listOf("A", "BC", "DEF")
        val expected = "A,BC,DEF"

        // WHEN
        val actual = serializeStringList(input)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test serialize empty StringList`() {
        // GIVEN
        val input = emptyList<String>()
        val expected = ""

        // WHEN
        val actual = serializeStringList(input)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test deserialize StringArray`() {
        // GIVEN
        val input = "A,BC,DEF"
        val expected = listOf("A", "BC", "DEF")

        // WHEN
        val actual = deserializeStringList(input)

        // THEN
        assertEquals(expected, actual)
    }

    @Test
    fun `test deserialize empty StringArray`() {
        // GIVEN
        val input = ""
        val expected = emptyList<String>()

        // WHEN
        val actual = deserializeStringList(input)

        // THEN
        assertEquals(expected, actual)
    }
}
