package com.zj.example.ali.activity

/**
 *
 * Company: 上加下信息技术成都有限公司
 * CreateTime:2018/11/8  12:57
 * @author 郑炯
 * @version 1.0
 */
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClassAlias(val alias: String) {
}