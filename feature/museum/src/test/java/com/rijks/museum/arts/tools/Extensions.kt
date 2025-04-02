package com.rijks.museum.arts.tools

import com.rijks.museum.core.utils.reducer.Reducer
import io.mockk.every

fun <S> reducedState(expectedState: S): S.() -> S = { expectedState }

fun <E, S> Reducer<E, S>.addReducerMockkRule(
    event: E,
    expectedState: S,
) {
    every { this@addReducerMockkRule.invoke(event) } returns reducedState(expectedState)
}
