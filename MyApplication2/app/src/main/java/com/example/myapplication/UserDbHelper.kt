package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.math.MathContext

open class UserDbHelper(mContext: Context) :
    SQLiteOpenHelper(mContext, "SAMPLE_DB", null, 1) {
    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE BMI_DATA(insert_date DATE PRIMARY KEY,height string not null,weight string not null, bmi string not null,comment string);")
    }

    override fun onUpgrade(
        db: SQLiteDatabase?,
        oldVersion: Int,
        newVersion: Int
    ) {
        db?.execSQL("DROP TABLE IF EXISTS BMI_DATA;")
        onCreate(db)

    }
}