package com.rijks.museum.core.testing.base

import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope

interface TestConfig {
    val testDispatcher: TestDispatcher

    val testScope: TestScope

}
