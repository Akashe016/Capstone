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
import androidx.test.espresso.matcher.RootMatchers.withDecorView
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.not
import org.junit.Rule
import org.junit.Test

class DeleteInvoiceInstrumentedTest {

    @get:Rule
    var activityScenarioRule = ActivityScenarioRule(DeleteInvoice::class.java)

    @Test
    fun testInvoiceDetailsView() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DeleteInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "Gokul")
            putExtra("AMOUNT", 40000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "Description")
        }

        val scenario = ActivityScenario.launch<DeleteInvoice>(intent)

        onView(withId(R.id.clientResult)).check(matches(withText("Gokul")))
        onView(withId(R.id.amountResult)).check(matches(withText("40000.0")))
        onView(withId(R.id.dateResult)).check(matches(withText("2024-05-14")))
        onView(withId(R.id.descriptionResult)).check(matches(withText("Description")))

        scenario.close()
    }

    @Test
    fun testDeleteDialogBox() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DeleteInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "Gokul")
            putExtra("AMOUNT", 40000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "Description")
        }
        val scenario = ActivityScenario.launch<DeleteInvoice>(intent)

        onView(withId(R.id.button2)).perform(click())

        onView(withText("Delete Invoice")).check(matches(isDisplayed()))
        onView(withText("Are you sure you want to delete your invoice?")).check(matches(isDisplayed()))
        onView(withText("Yes")).check(matches(isDisplayed()))
        onView(withText("No")).check(matches(isDisplayed()))
        scenario.close()

    }

    @Test
    fun testCancelDelete() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DeleteInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "Gokul")
            putExtra("AMOUNT", 40000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "Description")
        }
        val scenario = ActivityScenario.launch<DeleteInvoice>(intent)

        onView(withId(R.id.button2)).perform(click())
        onView(withText("No")).perform(click())

        onView(withId(R.id.clientResult)).check(matches(withText("Gokul")))
        onView(withId(R.id.amountResult)).check(matches(withText("40000.0")))
        onView(withId(R.id.dateResult)).check(matches(withText("2024-05-14")))
        onView(withId(R.id.descriptionResult)).check(matches(withText("Description")))
        scenario.close()

    }

    @Test
    fun testConfirmDelete() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DeleteInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "Gokul")
            putExtra("AMOUNT", 40000)
            putExtra("DATE", "2023-05-14")
            putExtra("DESCRIPTION", "Description")
        }
        val scenario = ActivityScenario.launch<DeleteInvoice>(intent)

        onView(withId(R.id.button2)).perform(click())
        onView(withText("Yes")).perform(click())

        onView(withText("Invoice deleted successfully"))

        scenario.close()
    }

    @Test
    fun testFailedDelete() {
        val intent = Intent(ApplicationProvider.getApplicationContext(), DeleteInvoice::class.java).apply {
            putExtra("USER_ID", 1L)
            putExtra("CLIENT_NAME", "Gokul")
            putExtra("AMOUNT", 40000.0)
            putExtra("DATE", "2024-05-14")
            putExtra("DESCRIPTION", "Description")
        }
        val scenario = ActivityScenario.launch<DeleteInvoice>(intent)

        onView(withId(R.id.button2)).perform(click())
        onView(withText("Yes")).perform(click())

    }
}
