package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.*

class UserDbAdapter(mContext: Context) {

    private val userDb = UserDbHelper(mContext)
    private val db = userDb.writableDatabase


    // レコード追加
    fun add(height: String, weight: String, bmi: String, comment: String) {
        val values = ContentValues()
        values.put("height",height)
        values.put("weight",weight)
        values.put("bmi",bmi)
        values.put("comment",comment)
        values.put("insert_date",getDate().toString())

        db.insertOrThrow("BMI_DATA",null,values)


    }

    fun delete(){
        db.delete("BMI_DATA",null,null)
    }

    fun getDB(columns:Array<String>):Cursor{
        return db.query("BMI_DATA",columns,null,null,null,null,"_id ASC")

    }

    private fun getDate():String{
        var date = Date()
        val format = SimpleDateFormat("yyyyMMdd",Locale.getDefault())
        return format.format(date)
    }

}