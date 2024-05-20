package com.example.simpleinvoicegenerationapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class AccountInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testCreateAccountSuccess() {
        onView(withId(R.id.accountUserName)).perform(typeText("abcd"), closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText("abcd@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText("Abcd@2001"), closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withText("Your account create successfully"))
    }

    @Test
    fun testAccountAlreadyCreated() {
        onView(withId(R.id.accountUserName)).perform(typeText("abcd"), closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText("abcd@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText("Abcd@2001"), closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withText("Your account already created, Go back to login"))
    }

    @Test
    fun testCreateAccountEmptyUsername() {
        onView(withId(R.id.accountUserName)).perform(typeText(""),closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText("abc@gmail.com"),closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText("Abc@2001"),closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withId(R.id.accountUserName)).check(matches(hasErrorText("Please enter username!")))
    }

    @Test
    fun testCreateAccountEmptyEmail() {
        onView(withId(R.id.accountUserName)).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText("Abc@2001"), closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withId(R.id.accountEmail)).check(matches(hasErrorText("Please enter your email!")))
    }

    @Test
    fun testCreateAccountEmptyPassword() {
        onView(withId(R.id.accountUserName)).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText("abc@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withId(R.id.accountPassword)).check(matches(hasErrorText("Please enter password!")))
    }

    @Test
    fun testCreateAccountInvalidEmail() {
        onView(withId(R.id.accountUserName)).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText("abcd"), closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText("Abc@2001"), closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withId(R.id.accountEmail)).check(matches(hasErrorText("Please enter a correct email address!")))
    }

    @Test
    fun testCreateAccountPassword() {
        onView(withId(R.id.accountUserName)).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText("abc@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withId(R.id.accountPassword)).check(matches(hasErrorText("Please enter correct password! (eg.. One (UpperCase, LowerCase, Symbol and Number))")))
    }

    @Test
    fun testCreateAccountLengthPassword() {
        onView(withId(R.id.accountUserName)).perform(typeText("abc"), closeSoftKeyboard())
        onView(withId(R.id.accountEmail)).perform(typeText("abc@gmail.com"), closeSoftKeyboard())
        onView(withId(R.id.accountPassword)).perform(typeText("Abc@1"), closeSoftKeyboard())
        onView(withId(R.id.signUpButton)).perform(click())

        onView(withId(R.id.accountPassword)).check(matches(hasErrorText("password minimum 8 length ")))
    }
}
