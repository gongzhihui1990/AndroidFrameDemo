package com.starkrak.framedemo

import android.view.View

import java.util.ArrayList
import java.util.Vector

class TestJava : TestJavaFather(), View.OnClickListener {


    fun print(list: List<String>): Int {
        return 1
    }

    fun print(list: Vector<String>) {}
    fun print(list: ArrayList<String>): String {
        return list.toString()
    }

    override fun onClick(v: View) {

    }

    companion object {
        fun test() {
            val java = TestJava()
            val list = ArrayList<String>()
            //        String response = java.print(list);
            java.print(list)
        }
    }

    //    public String print(String list){
    //        return list.toString();
    //    }
    //
    //    public String print(Integer list){
    //        return list.toString();
    //    }
}
