package com.example.thefinalproject.util

import android.content.Context
import android.content.SharedPreferences
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class SharePrefTest {
    @Mock
    lateinit var context: Context

    @Mock
    lateinit var sharedPreferences: SharedPreferences

    @Mock
    lateinit var editor: SharedPreferences.Editor


    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        Mockito.`when`(context.getSharedPreferences(Mockito.anyString(), Mockito.anyInt())).thenReturn(sharedPreferences)
        Mockito.`when`(sharedPreferences.edit()).thenReturn(editor)
    }
    @Test
    fun testingGet() {
        val key = "testKey"
        val default = "1"
        val expected = "1"

        Mockito.`when`(sharedPreferences.getString(key, default)).thenReturn(expected)

        SharePref.initial(context)
        val result = SharePref.getPref(key, default)

        Assert.assertEquals(expected, result)
    }

    @Test
    fun testWrite() {
        val key = "keyTesting"
        val value = "valueTesting"

        SharePref.initial(context)
        SharePref.setPref(key, value)

        Mockito.verify(editor).putString(key, value)
        Mockito.verify(editor).apply()
        Mockito.verify(editor, Mockito.never()).commit()
    }


}