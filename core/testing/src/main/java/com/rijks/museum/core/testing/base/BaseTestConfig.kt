package com.rijks.museum.core.testing.base

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.UnconfinedTestDispatcher

class BaseTestConfig : TestConfig {
    @OptIn(ExperimentalCoroutinesApi::class)
    override val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    override val testScope: TestScope = TestScope(testDispatcher)
}
