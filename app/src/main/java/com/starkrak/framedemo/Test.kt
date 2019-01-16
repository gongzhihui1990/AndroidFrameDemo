package com.starkrak.framedemo

import net.gtr.framework.rx.ProgressObserverImplementation
import net.gtr.framework.rx.RxHelper
import net.gtr.framework.util.Loger

import io.reactivex.Observable
import io.reactivex.functions.Function

class Test {
    internal fun test1() {
        RxHelper.bindOnUI(Observable.just("1990").map { s ->
            val s2 = s + "_gong"
            Loger.i("s2:$s2")
            s2
        }.map { s ->
            if (s.length > 10) {
                throw Exception("s too long")
            }
            s
        }, object : ProgressObserverImplementation<String>() {
            override fun onNext(s: String) {
                super.onNext(s)
            }
        })
    }

}
