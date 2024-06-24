package com.mohsenoid.rickandmorty.util

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.rules.Timeout
import org.junit.runner.Description
import org.junit.runners.model.Statement

// Reusable JUnit4 TestRule to override the Main dispatcher
@ExperimentalCoroutinesApi
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher(),
    private val timeoutRule: Timeout = Timeout.seconds(10),
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun apply(
        base: Statement?,
        description: Description?,
    ): Statement = timeoutRule.apply(super.apply(base, description), description)

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
