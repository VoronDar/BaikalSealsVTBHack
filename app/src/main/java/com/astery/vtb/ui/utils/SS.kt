package com.astery.copying.forms_adapter.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat

/** helps to set string*/
object SS {
    fun str(context:Context, text:Int):String{
        return context.resources.getString(text)
    }
}