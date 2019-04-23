package com.example.myapplication

import android.content.Context
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import android.view.View

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ListAdapterTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getTargetContext()
        assertEquals("com.example.myapplication", appContext.packageName)
    }

    lateinit var listAdapter:ListAdapter
    lateinit var context: Context


    @Before
    fun setUp(){
        var list = arrayListOf<MutableMap<String,String?>>()

        var map = mutableMapOf<String,String?>()
        map["日"] = "20190422"
        map["身長"] = "160"
        map["体重"] = "48"
        map["BMI"] = "18.7"
        map["コメント"] = "test"
        list.add(map)

        listAdapter = ListAdapter(list)
        context = InstrumentationRegistry.getTargetContext()
    }

    @Test
    fun getItemCountTest_正常系(){
        assertEquals(1,listAdapter.itemCount)
    }

    @Test
    fun onCreateViewHolderTest_正常系(){

    }

    @Test
    fun onBindViewHolderTest_正常系(){

    }

    @Test
    fun setOnItemClickListenerTest_正常系(){

    }

    @Test
    fun createCellTest_正常系(){
        var view = View(context)
        var viewHolder = ListAdapter.ViewHolder(view)
        var method = listAdapter.javaClass.getDeclaredMethod("createCell")
        method.isAccessible = true

        method.invoke(listAdapter,viewHolder)

    }

}
