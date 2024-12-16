package br.com.schuster.androidcleanarchitecture

import io.mockk.clearAllMocks
import io.mockk.unmockkAll
import org.junit.After

open class BaseUnitTest {

    @After
    fun afterRun() {
        unmockkAll()
        clearAllMocks()
    }
}