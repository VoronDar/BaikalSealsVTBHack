package com.astery.copying.forms_adapter.utils

import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

/** new window used to show data (mainly for formAdapter, but you can use something else)*/
abstract class FormDialogue(protected val fragment: Fragment, protected val title: String, protected val cancelable:Boolean) {
    open fun close(){
        blockableBundle?.lock(false)
    }
    open fun open(){
        setCloseListener()
        blockableBundle?.lock(true)
    }
    abstract fun getLayout():ViewGroup
    protected abstract fun getCloseItem():View
    protected fun setCloseListener(){
        if (cancelable) getCloseItem().setOnClickListener{close()}
    }


    protected abstract val view: View
    var blockableBundle:BlockableBundle? = null

}