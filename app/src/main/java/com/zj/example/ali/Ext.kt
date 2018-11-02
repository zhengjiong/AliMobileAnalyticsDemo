package com.zj.example.ali

import android.app.Activity
import android.content.Intent
import kotlin.reflect.KClass

/**
 *
 * CreateTime:2018/10/31  15:53
 * @author 郑炯
 * @version 1.0
 */

fun Activity.start(kClass: KClass<*>) {
    startActivity(Intent(this, kClass.java))
}