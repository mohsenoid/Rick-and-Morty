import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi

plugins {
    id("org.jetbrains.kotlin.plugin.power-assert")
}

@OptIn(ExperimentalKotlinGradlePluginApi::class)
powerAssert {
    functions = listOf(
        "kotlin.assert",
        "kotlin.test.assertContains",
        "kotlin.test.assertContentEquals",
        "kotlin.test.assertEquals",
        "kotlin.test.assertFails",
        "kotlin.test.assertFailsWith",
        "kotlin.test.assertFalse",
        "kotlin.test.assertIs",
        "kotlin.test.assertIsNot",
        "kotlin.test.assertNotContains",
        "kotlin.test.assertNotEquals",
        "kotlin.test.assertNotNull",
        "kotlin.test.assertNotSame",
        "kotlin.test.assertNull",
        "kotlin.test.assertSame",
        "kotlin.test.assertTrue",
        "kotlin.test.expect",
        "kotlin.test.fail",
    )

    includedSourceSets = listOf("test","androidTest")
}
