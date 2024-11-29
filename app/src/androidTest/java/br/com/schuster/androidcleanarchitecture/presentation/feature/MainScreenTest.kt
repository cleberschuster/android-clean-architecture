package br.com.schuster.androidcleanarchitecture.presentation.feature

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.ext.junit.runners.AndroidJUnit4
import br.com.schuster.androidcleanarchitecture.presentation.MainActivity
import br.com.schuster.androidcleanarchitecture.utils.TestTags
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainScreenTest {

    @get:Rule(order = 0)
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun displayLoading() {
        composeRule.onNodeWithTag(
            TestTags.LOADING
        ).assertIsDisplayed()
    }

    @Test
    fun displayComment() {
        runBlocking {
            delay(3000)
        }
        composeRule.onNodeWithTag(
            TestTags.COMMENT_TEXT
        ).assertIsDisplayed()
    }
}