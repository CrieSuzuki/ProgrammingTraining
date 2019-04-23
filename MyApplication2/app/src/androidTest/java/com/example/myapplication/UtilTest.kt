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

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UtilTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.myapplication", appContext.packageName)
    }

    lateinit var util:Util
    lateinit var context: Context

    @Before
    fun setUp(){
        util = Util()
        context = InstrumentationRegistry.getTargetContext()
    }

    @Test
    fun calculation_1_正常系(){
        try{
            assertEquals("18.7",util.calculateLogic("160","48"))
        }catch(e:Exception){
            assert(false)
        }
    }

    //　キーボード制御をかけているので実際には発生しない
    @Test
    fun calculation_2_入力値不正(){
        try{
            assertEquals("18.7",util.calculateLogic("あああああ","いいいいい"))
        }catch(e:Exception){
            assert(true)
        }
    }

    //　入力チェックで弾くため実際には発生しない
    @Test
    fun calculation_3_入力値空(){
        try{
            assertEquals("NaN",util.calculateLogic("",""))
        }catch(e:Exception){
            assert(false)
        }
    }



    @Test
    fun getDateAndMonthTest(){
        var (resultMonth,resultDate)= util.getDateAndMonth(SimpleDateFormat("yyyyMMdd", Locale.getDefault()).parse("20190423"))

        assertEquals("4",resultMonth)
        assertEquals("23",resultDate)

    }

    @Test
    fun getDateTest(){
        assertEquals("20190423",util.getDate())
    }

    @Test
    fun makeAlertTest(){
        try{
            util.makeAlert(context,"test")
            assert(true)
        }catch(e:Exception){
            assert(false)
        }
    }
}
