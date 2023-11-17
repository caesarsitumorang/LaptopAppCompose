package com.caesar.laptopapp


import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onChildAt
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToIndex
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.caesar.laptopapp.ui.navigation.Screen
import com.caesar.laptopapp.ui.theme.LaptopAppTheme
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class LaptopStoreAppTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            LaptopAppTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                LaptopStoreApp(navController = navController)
            }
        }
    }
    @Test
    fun verifyStartDestination() {
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        assertEquals(Screen.Home.route, currentRoute)
    }
    @Test
    fun verifyProfileNavigation() {
        composeTestRule.onNodeWithTag("ProfileIcon").performClick()
        composeTestRule.onNodeWithTag("ProfileScreen").assertIsDisplayed()
    }

    @Test
    fun verifyLaptopItemClick() {
        composeTestRule.onNodeWithTag("LaptopList").onChildAt(0).performClick()
        composeTestRule.onNodeWithTag("DetailLaptopScreen").assertIsDisplayed()
    }

}
