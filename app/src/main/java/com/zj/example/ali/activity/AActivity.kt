package com.zj.example.ali.activity

import android.os.Bundle
import com.zj.example.ali.R
import com.zj.example.ali.start
import kotlinx.android.synthetic.main.activity_a.*
import com.alibaba.sdk.android.man.MANServiceProvider


class AActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        btn.setOnClickListener {
            start(BActivity::class)
        }
        register.setOnClickListener {
            val manService = MANServiceProvider.getService()
            // 注册用户埋点
            manService.manAnalytics.userRegister("zhengjiong")
        }
        exit.setOnClickListener {
            val manService = MANServiceProvider.getService()
            // 注册用户埋点
            manService.manAnalytics.updateUserAccount("", "")
        }
    }
}
