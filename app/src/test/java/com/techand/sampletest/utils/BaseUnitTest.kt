package com.techand.videoapp.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import petros.efthymiou.groovy.utils.MainCoroutineScopeRule
open class BaseUnitTest {
    @get: Rule
    val mainCoroutineScopeRule = MainCoroutineScopeRule()

    @get: Rule
    val taskExecutorRule = InstantTaskExecutorRule()

}