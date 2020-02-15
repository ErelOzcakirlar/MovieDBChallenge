package com.erel.movies.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner


/**
 * Base class for Unit tests. Inherit from it to create test cases which DO NOT contain android
 * framework dependencies or components.
 *
 * @see AndroidTest
 */
@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner.Silent::class)
abstract class UnitTest {

    private val testDispatcher = TestCoroutineDispatcher()
    val testScope = TestCoroutineScope(testDispatcher)

    @Suppress("LeakingThis")
    @Rule
    @JvmField
    val injectMocks = TestRule { statement, _ ->
        MockitoAnnotations.initMocks(this@UnitTest)
        statement
    }

    @Before
    open fun before() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    open fun after() {
        Dispatchers.resetMain()
        testScope.cleanupTestCoroutines()
    }
}