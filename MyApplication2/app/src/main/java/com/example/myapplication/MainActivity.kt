package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(){

    // タブ切り替え時の処理
    // TODO: 都度都度インスタンス生成するのは良くない
    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_input -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.rootLayout, InputFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_history -> {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.rootLayout, HistoryFragment.newInstance())
                    .commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        if (savedInstanceState == null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.rootLayout,InputFragment.newInstance())
            transaction.commit()
        }

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)




    }






}
