package com.example.myapplication

import android.text.InputFilter
import android.text.Spanned
import java.util.regex.Pattern

class CustomInputFilter: InputFilter {


    private var mPattern:Pattern = Pattern.compile("[0-9]{0,3}+((\\.[0-9]{0,1})?)||(\\.)?")

    override fun filter(source: CharSequence?, start: Int, end: Int, dest: Spanned?, dstart: Int, dend: Int): CharSequence? {
        val matcher = mPattern.matcher(dest)
        return if (!matcher.matches()) "" else null
    }
}