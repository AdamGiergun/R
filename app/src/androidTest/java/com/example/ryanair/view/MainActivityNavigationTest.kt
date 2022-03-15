package com.example.ryanair.view

import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.example.ryanair.R
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@LargeTest
@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
class MainActivityNavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun test() = runTest(StandardTestDispatcher()) {
        activityScenarioRule.scenario.run {
            onView(withId(R.id.from)).check(matches(isDisplayed()))

            checkSpinner(R.id.origin_spinner, "KRK")
            checkSpinner(R.id.destination_spinner, "DUB")

            onView(withId(R.id.search_button)).perform(click())

            onView(withId(R.id.route_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(startsWith("Route(termsOfUse"))))

            onView(withId(R.id.filters_text))
                .check(matches(isDisplayed()))
                .check(matches(withText(containsString("DUB"))))
                .check(matches(withText(containsString("STN"))))
        }
    }

    private fun checkSpinner(id: Int, code: String) {
        onView(withId(id))
            .check(matches(isDisplayed()))
            .perform(click())
        onData(
            allOf(
                `is`(instanceOf(String::class.java)),
                endsWith(code)
            )
        ).perform(click())
        onView(withId(id)).check(matches(withSpinnerText(endsWith(code))))
    }
}