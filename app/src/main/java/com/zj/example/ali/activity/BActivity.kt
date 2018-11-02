package com.zj.example.ali.activity

import android.os.Bundle
import com.zj.example.ali.R
import com.zj.example.ali.start
import kotlinx.android.synthetic.main.activity_b.*


class BActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        btn.setOnClickListener {
            start(CActivity::class)
        }
    }
}
