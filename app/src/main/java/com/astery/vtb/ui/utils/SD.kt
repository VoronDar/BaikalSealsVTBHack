package com.astery.copying.forms_adapter.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.core.content.res.ResourcesCompat

/** helps to set drawable*/
class SD {
    companion object{
        fun setDrawable(view: ImageView, drawable:Int, context: Context){
            view.setImageDrawable(
                getDr(drawable, context)
            )
        }
        fun getDr(drawable:Int, context: Context): Drawable? {
            return ResourcesCompat.getDrawable(
                    context.resources,
                    drawable,
                    context.theme
            )
        }
    }
}