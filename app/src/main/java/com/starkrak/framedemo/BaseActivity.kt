package com.starkrak.framedemo

import android.annotation.SuppressLint
import android.os.Bundle
import net.gtr.framework.activity.RxAppCompatActivity

@SuppressLint("Registered")
open class BaseActivity : RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val layoutID: Int
        val annotation = javaClass.getAnnotation(LayoutID::class.java)
        layoutID = annotation?.value ?: R.layout.activity_null
        setContentView(layoutID)
    }
}
