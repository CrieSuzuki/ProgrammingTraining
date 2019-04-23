package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteOpenHelper
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before
import kotlin.Exception

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class UserDbAdapterTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.myapplication", appContext.packageName)
    }

    private lateinit var dbAdapter: UserDbAdapter
    lateinit var context: Context
    var db = mock<SQLiteOpenHelper> {
        on{writableDatabase.delete("BMI_DATA","insert_date = ?", arrayOf("20190422"))}
        on{writableDatabase.query("BMI_DATA", arrayOf("date","height","weight","bmi","comment"), null, null, null, null, null)
        } doReturn null
    }

    @Before
    fun setUp(){
        context = InstrumentationRegistry.getTargetContext()
        dbAdapter = UserDbAdapter(context)
    }

    @Test
    fun addTest_正常系(){
        try {
            var height = "testHeight"
            var weight = "testWeight"
            var bmi = "testBMI"
            var comment = "testComment"
            dbAdapter.add(height, weight, bmi, comment)
            assert(true)
        }catch (e:Exception){
            assert(false)
        }
    }

    @Test
    fun deleteTest_正常系(){
        try{
            var params = arrayOf("20190422")
            dbAdapter.delete(params)
            assert(true)
        }catch(e:Exception){
            assert(false)
        }
    }

    @Test
    fun getData_正常系(){
        try{
            var result = dbAdapter.getDB(arrayOf("input_date"))
            assertEquals(2,result.columnCount)
        }catch(e:Exception){
            assert(false)
        }
    }


}
