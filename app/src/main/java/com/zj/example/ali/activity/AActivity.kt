package com.zj.example.ali.activity

import android.os.Bundle
import com.zj.example.ali.R
import com.zj.example.ali.start
import kotlinx.android.synthetic.main.activity_a.*
import com.alibaba.sdk.android.man.MANServiceProvider
import com.alibaba.mtl.log.e.s.send
import com.alibaba.sdk.android.man.MANService
import com.alibaba.sdk.android.man.MANHitBuilders
import com.alibaba.sdk.android.man.MANHitBuilders.MANCustomHitBuilder

@ClassAlias("AActivity-Page")
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
        customHit.setOnClickListener {
            // 事件名称：customHit
            val hitBuilder = MANHitBuilders.MANCustomHitBuilder("customHit")
                    .apply {
                        // 可使用如下接口设置时长：1分钟
                        setDurationOnEvent((1 * 60 * 1000).toLong())
                        // 设置关联的页面名称：聆听
                        setEventPage(this@AActivity.javaClass.simpleName+"-Page")
                        // 设置属性：类型摇滚
                        setProperty("type", "rock")
                        // 设置属性：歌曲标题
                        setProperty("title", "wonderful tonight")
                    }

            // 发送自定义事件打点
            MANServiceProvider.getService().manAnalytics.defaultTracker.send(hitBuilder.build())
        }
    }
}
