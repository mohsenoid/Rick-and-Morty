package com.mohsenoid.rickandmorty.util.extension

import org.amshove.kluent.shouldEqual
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `test serialize IntegerList`() {
        // GIVEN
        val input = listOf(2, 8, 10)
        val expected = "2,8,10"

        // WHEN
        val actual = input.serializeIntegerListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test serialize empty IntegerList`() {
        // GIVEN
        val input = emptyList<Int>()
        val expected = ""

        // WHEN
        val actual = input.serializeIntegerListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test serialize StringList`() {
        // GIVEN
        val input = listOf("A", "BC", "DEF")
        val expected = "A,BC,DEF"

        // WHEN
        val actual = input.serializeStringListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test serialize empty StringList`() {
        // GIVEN
        val input = emptyList<String>()
        val expected = ""

        // WHEN
        val actual = input.serializeStringListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test deserialize StringArray`() {
        // GIVEN
        val input = "A,BC,DEF"
        val expected = listOf("A", "BC", "DEF")

        // WHEN
        val actual = input.deserializeStringList()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test deserialize empty StringArray`() {
        // GIVEN
        val input = ""
        val expected = emptyList<String>()

        // WHEN
        val actual = input.deserializeStringList()

        // THEN
        expected shouldEqual actual
    }
}
