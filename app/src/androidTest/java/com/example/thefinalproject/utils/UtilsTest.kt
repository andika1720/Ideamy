package com.example.thefinalproject.utils

import com.example.thefinalproject.util.Utils
import org.junit.Assert.assertEquals
import org.junit.Test

class UtilsTest {

    @Test
    fun formatCurrency_withValidAmount_shouldReturnFormattedString() {
        val amount = 1000000
        val formattedCurrency = Utils.formatCurrency(amount)
        assertEquals("Rp. 1,000,000", formattedCurrency)
    }

    @Test
    fun formatCurrency_withNullAmount_shouldReturnEmptyString() {
        val formattedCurrency = Utils.formatCurrency(null)
        assertEquals("", formattedCurrency)
    }

}
