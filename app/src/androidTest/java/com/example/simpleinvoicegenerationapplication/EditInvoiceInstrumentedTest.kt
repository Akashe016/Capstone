package com.example.simpleinvoicegenerationapplication

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.widget.DatePicker
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Rule
import org.junit.Test

class EditInvoiceInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(EditInvoice::class.java)

    @Test
    fun testUpdateInvoiceSuccessfully() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), EditInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "San")
            putExtra("AMOUNT", 5000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "I am Santhosh")
        }

        val scenario = ActivityScenario.launch<EditInvoice>(intent)

        onView(withId(R.id.clientEditText)).perform(clearText(), typeText("Gokul"), closeSoftKeyboard())
        onView(withId(R.id.editAmountText)).perform(clearText(), typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.editInvoiceDate)).perform(clearText(), typeText("2024-05-14"), closeSoftKeyboard())
        onView(withId(R.id.editDescription)).perform(clearText(), typeText("Description"), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        scenario.close()
    }

    @Test
    fun testInvalidClientName() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), EditInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "San")
            putExtra("AMOUNT", 5000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "I am Santhosh")
        }

        val scenario = ActivityScenario.launch<EditInvoice>(intent)

        onView(withId(R.id.clientEditText)).perform(clearText(), typeText("Go"), closeSoftKeyboard())
        onView(withId(R.id.editAmountText)).perform(clearText(), typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.editInvoiceDate)).perform(clearText(), typeText("2024-05-01"), closeSoftKeyboard())
        onView(withId(R.id.editDescription)).perform(clearText(), typeText("Description"), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        onView(withId(R.id.clientEditText))
            .check(matches(hasErrorText("Client name must be at least 5 characters")))

        scenario.close()
    }


    @Test
    fun testInvalidAmount() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), EditInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "San")
            putExtra("AMOUNT", 5000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "I am Santhosh")
        }

        val scenario = ActivityScenario.launch<EditInvoice>(intent)

        onView(withId(R.id.clientEditText)).perform(clearText(),typeText("Gokul"), closeSoftKeyboard())
        onView(withId(R.id.editAmountText)).perform(clearText(),typeText("2000"), closeSoftKeyboard())
        onView(withId(R.id.editInvoiceDate)).perform(clearText(),typeText("2024-05-14"), closeSoftKeyboard())
        onView(withId(R.id.editDescription)).perform(clearText(),typeText("Description"), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        onView(withId(R.id.editAmountText))
            .check(matches(hasErrorText("Amount must be a positive number and minimum INR 3000")))
        scenario.close()
    }

    @Test
    fun testEmptyDate() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), EditInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "San")
            putExtra("AMOUNT", 5000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "I am Santhosh")
        }

        val scenario = ActivityScenario.launch<EditInvoice>(intent)
        onView(withId(R.id.clientEditText)).perform(clearText(),typeText("Gokul"), closeSoftKeyboard())
        onView(withId(R.id.editAmountText)).perform(clearText(),typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.editInvoiceDate)).perform(clearText(),typeText(""), closeSoftKeyboard())
        onView(withId(R.id.editDescription)).perform(clearText(),typeText("Description"), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        onView(withId(R.id.editInvoiceDate))
            .check(matches(hasErrorText("Please select date!")))

        scenario.close()

    }

    @Test
    fun testEmptyDescription() {

        val intent = Intent(ApplicationProvider.getApplicationContext(), EditInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "San")
            putExtra("AMOUNT", 5000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "I am Santhosh")
        }

        val scenario = ActivityScenario.launch<EditInvoice>(intent)

        onView(withId(R.id.clientEditText)).perform(clearText(),typeText("Gokul"), closeSoftKeyboard())
        onView(withId(R.id.editAmountText)).perform(clearText(),typeText("40000"), closeSoftKeyboard())
        onView(withId(R.id.editInvoiceDate)).perform(clearText(),typeText("2024-05-14"), closeSoftKeyboard())
        onView(withId(R.id.editDescription)).perform(clearText(),typeText(""), closeSoftKeyboard())

        onView(withId(R.id.saveButton)).perform(click())

        onView(withId(R.id.editDescription))
            .check(matches(hasErrorText("Please fill the description!")))

        scenario.close()

    }
}
