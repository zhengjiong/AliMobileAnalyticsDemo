package com.zj.example.ali

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.os.SystemClock
import com.alibaba.sdk.android.man.MANPageHitBuilder
import com.alibaba.sdk.android.man.MANServiceProvider
import com.zj.example.ali.activity.BActivity
import com.zj.example.ali.activity.ClassAlias


/**
 *
 * CreateTime:2018/10/31  14:26
 * @author 郑炯
 * @version 1.0
 */
class App : Application() {
    var lastPauseActivity: String? = null

    val activityMap: HashMap<String, AliActivityPageEntity> = hashMapOf()

    override fun onCreate() {
        super.onCreate()
        initAliPageHit()
        initManService()
    }

    fun initAliPageHit() {
        this.registerActivityLifecycleCallbacks(object : ActivityLifecycleCallbacks {

            override fun onActivityPaused(activity: Activity) {
                lastPauseActivity = activity.javaClass.simpleName

                val pageEntity = activityMap[activity::class.java.simpleName]
                if (pageEntity != null) {
                    pageEntity.duration += (SystemClock.uptimeMillis() - pageEntity.startTime)
                }

                println("onActivityPaused=${activity.javaClass.simpleName} duration=${pageEntity?.duration}")
            }

            override fun onActivityResumed(activity: Activity) {
                var pageEntity = activityMap[activity::class.java.simpleName]
                if (pageEntity == null) {
                    pageEntity = AliActivityPageEntity(lastPauseActivity, SystemClock.uptimeMillis())
                } else {
                    pageEntity.startTime = SystemClock.uptimeMillis()
                }
                activityMap[activity::class.java.simpleName] = pageEntity

                println("onActivityResumed=${activity.javaClass.simpleName}")
            }

            override fun onActivityStarted(activity: Activity) {
            }

            override fun onActivityDestroyed(activity: Activity) {
                println("onActivityDestroyed=${activity::class.java.simpleName}")

                val pageHitBuilder = MANPageHitBuilder(activity.javaClass.simpleName);

                activityMap[activity.javaClass.simpleName]?.run {
                    pageHitBuilder.setDurationOnPage(this.duration)
                    pageHitBuilder.setReferPage(this.referPage)
                }
                activityMap.remove(activity.javaClass.simpleName)

                if (activity.javaClass.simpleName == BActivity::class.java.simpleName) {
                    /**
                    4.2.2 给Activity页面增加属性统计
                    场景例子：

                    我们要开发一个购物app，在购物的app的宝贝展示页面，我们可能想要知道，此页面都展示了哪些商品？可以简单理解为，
                    我们可以对采集的页面（GoodsDetails 页面）记录上增加一个宝贝属性就可以，如item_id=xxxxxx。
                     */
                    pageHitBuilder.setProperty("item_id", "1");
                    pageHitBuilder.setProperty("item_value", "商品1");
                }

                MANServiceProvider.getService().manAnalytics.defaultTracker.send(pageHitBuilder.build())
            }

            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle?) {
                //println("onActivitySaveInstanceState=${activity::class.java.simpleName}")
            }

            override fun onActivityStopped(activity: Activity) {
                //println("onActivityStopped=${activity::class.java.simpleName}")
            }

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                //println("onActivityCreated=${activity::class.java.simpleName}")
                if (activity.javaClass.isAnnotationPresent(ClassAlias::class.java)) {
                    val alias = activity.javaClass.getAnnotation(ClassAlias::class.java)
                    println("alias=" + alias.alias)
                }
            }

        })
    }

    fun initManService() {
        /**
         * 初始化Mobile Analytics服务
         */

        // 获取MAN服务
        val manService = MANServiceProvider.getService()

        // 打开调试日志
        manService.getMANAnalytics().turnOnDebug()

        // MAN初始化方法之一
        //manService.getMANAnalytics().init(this, applicationContext)

        // MAN另一初始化方法，手动指定appKey和appSecret
        val appKey = "25253161";
        val appSecret = "0cbb1a8c6e99c5d226a44fb6a6285632";
        manService.getMANAnalytics().init(this, this, appKey, appSecret);

        // 若需要关闭 SDK 的自动异常捕获功能可进行如下操作,详见文档5.4
        manService.getMANAnalytics().turnOffCrashReporter()

        // 通过此接口关闭页面自动打点功能，详见文档4.2
        manService.getMANAnalytics().turnOffAutoPageTrack();

        // 设置渠道（用以标记该app的分发渠道名称），如果不关心可以不设置即不调用该接口，渠道设置将影响控制台【渠道分析】栏目的报表展现。如果文档3.3章节更能满足您渠道配置的需求，就不要调用此方法，按照3.3进行配置即可
        manService.getMANAnalytics().setChannel("渠道1")

        // 若AndroidManifest.xml 中的 android:versionName 不能满足需求，可在此指定；
        // 若既没有设置AndroidManifest.xml 中的 android:versionName，也没有调用setAppVersion，appVersion则为null
        //manService.getMANAnalytics().setAppVersion("3.1.1")
    }
}