package com.example.simpleinvoicegenerationapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class LoginInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(LoginActivity::class.java)

    @Test
    fun testLoginSuccess() {

        onView(withId(R.id.loginUserName))
            .perform(typeText("abcd"), closeSoftKeyboard())

        onView(withId(R.id.loginPassword))
            .perform(typeText("Abcd@2001"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        onView(withText("Login Successfully"))
    }


    @Test
    fun testLoginFailed() {

        onView(withId(R.id.loginUserName))
            .perform(typeText("abcdE"), closeSoftKeyboard())

        onView(withId(R.id.loginPassword))
            .perform(typeText("AbcdE@2001"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        onView(withText("Login failed!"))
    }


    @Test
    fun testLoginUsername() {

        onView(withId(R.id.loginUserName))
            .perform(typeText(""), closeSoftKeyboard())

        onView(withId(R.id.loginPassword))
            .perform(typeText("Abc@2001"), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.loginUserName))
            .check(matches(hasErrorText("Please enter your username!")))
    }


    @Test
     fun testLoginPassword() {

        onView(withId(R.id.loginUserName))
            .perform(typeText("abcd"), closeSoftKeyboard())

        onView(withId(R.id.loginPassword))
            .perform(typeText(""), closeSoftKeyboard())

        onView(withId(R.id.loginButton)).perform(click())

        onView(withId(R.id.loginPassword))
            .check(matches(hasErrorText("Please enter your password!")))
    }
}