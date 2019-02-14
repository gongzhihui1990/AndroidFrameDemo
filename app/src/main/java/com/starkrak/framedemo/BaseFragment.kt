package com.starkrak.framedemo

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import net.gtr.framework.fragment.RxFragment

@SuppressLint("Registered")
open class BaseFragment : RxFragment() {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutID: Int
        val annotation = javaClass.getAnnotation(LayoutID::class.java)
        layoutID = annotation?.value ?: R.layout.layout_disable
        val result = inflater.inflate(layoutID, container, false)
        if (layoutID == R.layout.layout_disable) {
            (result.findViewById<View>(R.id.hint) as TextView).text = javaClass.simpleName
        }
        return result
    }

}
