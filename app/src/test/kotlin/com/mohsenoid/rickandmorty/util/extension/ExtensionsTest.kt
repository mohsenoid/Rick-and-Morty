package com.mohsenoid.rickandmorty.util.extension

import org.amshove.kluent.shouldEqual
import org.junit.Test

class ExtensionsTest {

    @Test
    fun `test serialize IntegerList`() {
        // GIVEN
        val input: List<Int> = listOf(2, 8, 10)
        val expected = "2,8,10"

        // WHEN
        val actual: String = input.serializeIntegerListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test serialize empty IntegerList`() {
        // GIVEN
        val input: List<Int> = emptyList()
        val expected = ""

        // WHEN
        val actual: String = input.serializeIntegerListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test serialize StringList`() {
        // GIVEN
        val input: List<String> = listOf("A", "BC", "DEF")
        val expected = "A,BC,DEF"

        // WHEN
        val actual: String = input.serializeStringListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test serialize empty StringList`() {
        // GIVEN
        val input: List<String> = emptyList()
        val expected = ""

        // WHEN
        val actual: String = input.serializeStringListToCsv()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test deserialize StringArray`() {
        // GIVEN
        val input = "A,BC,DEF"
        val expected: List<String> = listOf("A", "BC", "DEF")

        // WHEN
        val actual: List<String> = input.deserializeStringList()

        // THEN
        expected shouldEqual actual
    }

    @Test
    fun `test deserialize empty StringArray`() {
        // GIVEN
        val input = ""
        val expected: List<String> = emptyList()

        // WHEN
        val actual: List<String> = input.deserializeStringList()

        // THEN
        expected shouldEqual actual
    }
}
