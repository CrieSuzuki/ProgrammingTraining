package com.example.myapplication


import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class InputFragmentTest {
    lateinit var inputFragment: InputFragment

    @Before
    fun setUp() {
        inputFragment = InputFragment.newInstance()
    }

    @Test
    fun onCreate() {
    }

    @Test
    fun onCreateView() {
    }

    @Test
    fun onViewCreated() {
    }

    @Test
    fun onButtonPressed() {
    }

    @Test
    fun onDetach() {
    }

    @Test
    fun newInstance() {
    }

    @Test
    fun isCorrectInputTest(){
        var method = inputFragment.javaClass.getDeclaredMethod("isCorrectInput")
        method.isAccessible = true
        assertEquals(true,method.invoke(inputFragment,"100","99"))
    }
}