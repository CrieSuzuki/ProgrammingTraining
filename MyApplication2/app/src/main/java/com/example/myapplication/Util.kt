package com.example.myapplication

import android.app.AlertDialog
import android.content.Context
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.*


class Util {
    /**
     * BMI計算処理
     * */
    fun calculateLogic(height: String, weight: String): String {
        // BMI計算して返却
        val heightMeter = height.toDouble() * 0.01
        val result = weight.toDouble() / (heightMeter * heightMeter)
        return "%.1f".format(result)
    }

    // yyyyMMddからMMとddを返す
    fun getDateAndMonth(yyyyMMdd: Date): Pair<String, String> {

        val calendar = Calendar.getInstance(TimeZone.getTimeZone("Asia/Tokyo"), Locale.JAPAN)
        calendar.time = yyyyMMdd
        var month = calendar.get(Calendar.MONTH).plus(1).toString()
        var date = calendar.get(Calendar.DAY_OF_MONTH).toString()
        return Pair(month, date)
    }

    /**
     * 現在日付取得
     * */
    fun getDate(): String {
        var date = Date()
        val format = SimpleDateFormat("yyyyMMdd", Locale.getDefault())
        return format.format(date)
    }


    fun makeAlert(context:Context,alertMessage: String) {
        AlertDialog.Builder(context).apply {
            setTitle("ERROR")
            setMessage(alertMessage)
            setPositiveButton(
                "OK"
            ) { _, _ ->
                Toast.makeText(
                    context,
                    "OK",
                    Toast.LENGTH_LONG
                )
            }
            show()
        }
    }


}