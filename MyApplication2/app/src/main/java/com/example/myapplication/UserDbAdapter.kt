package com.example.myapplication

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import java.text.SimpleDateFormat
import java.util.*

/**
 * DB操作クラス
 * */
class UserDbAdapter(mContext: Context) {

    private val userDb = UserDbHelper(mContext)
    private val db = userDb.writableDatabase


    /**
     * データ追加
     * */
    fun add(height: String, weight: String, bmi: String, comment: String) {
        val values = ContentValues()
        values.put("height", height)
        values.put("weight", weight)
        values.put("bmi", bmi)
        values.put("comment", comment)
        values.put("insert_date", getDate())

        db.replaceOrThrow("BMI_DATA", null, values)


    }

    /**
     * データ削除
     * */
    fun delete(key: Array<String>) {
        db.delete("BMI_DATA", "insert_date = ?", key)
    }

    /**
     * データ検索
     * */
    fun getDB(columns: Array<String>): Cursor {
        return db.query("BMI_DATA", columns, null, null, null, null, null)

    }


    /**
     * 現在日付取得
     * */
    private fun getDate(): String {
        var date = Date()
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return format.format(date)
    }

}