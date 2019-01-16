package com.starkrak.framedemo

import android.os.Bundle
import io.reactivex.Observable
import net.gtr.framework.rx.ProgressObserverImplementation
import net.gtr.framework.rx.RxHelper
import net.gtr.framework.util.Loger

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        RxHelper.bindOnUI(Observable
                .just("2019")
                .map { s -> s + "by gong" }
                .map { s ->
                    if (s.length > 2) {
                        throw Exception("happy new year")
                    }
                    s
                }, object : ProgressObserverImplementation<String>(this) {
            override fun onNext(t: String) {
                super.onNext(t)
                Loger.i(t)
            }
        })
    }
}
