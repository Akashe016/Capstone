package com.example.simpleinvoicegenerationapplication

import androidx.test.filters.LargeTest
import org.junit.runner.RunWith
import org.junit.runners.Suite


@RunWith(Suite::class)
@Suite.SuiteClasses(
    AccountInstrumentedTest::class,
    LoginInstrumentedTest::class,
    NewInvoiceInstrumentedTest::class,
    EditInvoiceInstrumentedTest::class,
    DeleteInvoiceInstrumentedTest::class
)
@LargeTest
class InstrumentedTestSuite {
}