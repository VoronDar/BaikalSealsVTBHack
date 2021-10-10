package com.astery.copying.forms_adapter.utils

import android.view.View

/** lock or unlock all object with tag */
class BlockableBundle {
    private val allTag = "all"
    private var map: MutableMap<String, ArrayList<View>> = HashMap()

    fun addView(view: View, tag:String){
        if (!map.containsKey(tag)){
            map[tag] = ArrayList();
        }
        map.getValue(tag).add(view)
    }

    fun addView(view: View){
        if (!map.containsKey(allTag)){
            map[allTag] = ArrayList();
        }
        map.getValue(allTag).add(view)
    }


    fun lock(isLock: Boolean, tag:String){
        for (i in map.getValue(tag)){
            i.isClickable = !isLock
            i.isEnabled = !isLock
            i.isFocusable = !isLock
        }
    }

    fun lock(isLock: Boolean){
        for (i in map.getValue(allTag)){
            i.isClickable = !isLock
            i.isEnabled = !isLock
            i.isFocusable = !isLock
        }
    }
}