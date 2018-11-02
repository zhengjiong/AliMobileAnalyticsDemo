package com.zj.example.ali.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.zj.example.ali.R
import kotlinx.android.synthetic.main.activity_c.*

class CActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        btn.setOnClickListener {
            finish()
        }
    }
}
