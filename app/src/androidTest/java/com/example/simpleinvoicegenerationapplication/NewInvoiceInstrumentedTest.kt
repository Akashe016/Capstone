package com.example.simpleinvoicegenerationapplication

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.junit.Rule
import org.junit.Test

class NewInvoiceInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(NewInvoice::class.java)

    @Test
    fun testNewInvoiceEmptyUsername() {

        onView(withId(R.id.clientName)).perform(typeText("ab"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceAmount)).perform(typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDate)).perform(typeText("2024-05-14"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDesc)).perform(typeText("description"), closeSoftKeyboard())

        onView(withId(R.id.newInvoiceButton)).perform(click())

        onView(withId(R.id.clientName))
            .check(matches(hasErrorText("Client name must be at least 5 characters")))
    }

    @Test
    fun testNewInvoiceInvalidAmount() {

        onView(withId(R.id.clientName)).perform(typeText("Akash"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceAmount)).perform(typeText("2000"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDate)).perform(typeText("2024-05-14"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDesc)).perform(typeText("description"), closeSoftKeyboard())

        onView(withId(R.id.newInvoiceButton)).perform(click())

        onView(withId(R.id.newInvoiceAmount))
            .check(matches(hasErrorText("Amount must be a positive number and minimum INR 3000")))
    }

    @Test
    fun testNewInvoiceEmptyDate() {
        onView(withId(R.id.clientName)).perform(typeText("Akash"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceAmount)).perform(typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDate)).perform(typeText(""), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDesc)).perform(typeText("description"), closeSoftKeyboard())

        onView(withId(R.id.newInvoiceButton)).perform(click())

        onView(withId(R.id.newInvoiceDate))
            .check(matches(hasErrorText("Please select date!")))
    }

    @Test
    fun testNewInvoiceEmptyDescription() {

        onView(withId(R.id.clientName)).perform(typeText("Akash"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceAmount)).perform(typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDate)).perform(typeText("2024-05-14"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDesc)).perform(typeText(""), closeSoftKeyboard())

        onView(withId(R.id.newInvoiceButton)).perform(click())

        onView(withId(R.id.newInvoiceDesc))
            .check(matches(hasErrorText("Please fill the description!")))
    }

    @Test
    fun testNewInvoiceSuccessfully() {

        onView(withId(R.id.clientName)).perform(typeText("Akash"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceAmount)).perform(typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDate)).perform(typeText("2024-05-14"), closeSoftKeyboard())
        onView(withId(R.id.newInvoiceDesc)).perform(typeText("description"), closeSoftKeyboard())

        onView(withId(R.id.newInvoiceButton)).perform(click())

        onView(withText("Invoice created successfully"))
    }
}
