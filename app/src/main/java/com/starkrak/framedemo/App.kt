package com.starkrak.framedemo

import net.gtr.framework.app.BaseApp
import net.gtr.framework.util.Loger

class App : BaseApp() {
    override fun initApk() {
        Loger.i("initApk success")
        val str ="  12322  "
        str.trim()
    }
}
